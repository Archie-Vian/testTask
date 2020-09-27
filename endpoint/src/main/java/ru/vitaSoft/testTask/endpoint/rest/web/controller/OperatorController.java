package ru.vitaSoft.testTask.endpoint.rest.web.controller;

import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vitaSoft.testTask.entities.enums.ProposalStatus;
import ru.vitaSoft.testTask.entities.model.Proposal;
import ru.vitaSoft.testTask.serivce.crud.ProposalService;

import java.util.List;

/**
 * Контроллер действий оператора.
 */
@RestController
@RequestMapping("/operator/proposals")
public class OperatorController {

	private final ProposalService proposalService;

	/**
	 * Конструктор.
	 * @param proposalService CRUD сервис пользовательских заявок.
	 */
	public OperatorController(ProposalService proposalService) {
		this.proposalService = proposalService;
	}

	/**
	 * Просмотр отправленных на рассмотрение заявок.
	 * @return список заявок, ожидающих рассмотрения
	 */
	@GetMapping
	public ResponseEntity<List<Proposal>> getIdleProposes() {
		var proposals = proposalService.getByStatus(ProposalStatus.IDLE);
		for (Proposal proposal : proposals) {
			proposal.setContent(proposalService.dashSplit(proposal));
		}
		return ResponseEntity.ok(proposals);
	}

	/**
	 * Метод принятия пользовательской заявки.
	 * @param id Id пользовательской заявки
	 */
	@GetMapping("accept/{id}")
	public ResponseEntity acceptProposal(@PathVariable Long id) {
		var proposal = proposalService.getById(id);
		if (proposal.getStatus().equals(ProposalStatus.IDLE)) {
			proposal.setStatus(ProposalStatus.ACCEPTED);
			proposalService.update(proposal, id);
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * Метод отклонения пользовательской заявки.
	 * @param id Id пользовательской заявки
	 */
	@GetMapping("decline/{id}")
	public ResponseEntity declineProposal(@PathVariable Long id) {
		Proposal proposal = proposalService.getById(id);
		if (proposal.getStatus().equals(ProposalStatus.IDLE)) {
			proposal.setStatus(ProposalStatus.DECLINED);
			proposalService.update(proposal, id);
		}
		return new ResponseEntity(HttpStatus.OK);
	}
}
