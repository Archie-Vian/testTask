package ru.vitaSoft.testTask.endpoint.rest.web.controller;

import lombok.var;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.vitaSoft.testTask.entities.enums.ProposalStatus;
import ru.vitaSoft.testTask.entities.model.Proposal;
import ru.vitaSoft.testTask.serivce.crud.ProposalService;

/**
 * Контроллер страницы оператора.
 */
@RestController
@RequestMapping("/proposals")
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
	 * Страница рассмотрения заявок.
	 * @param model представление веб-страницы
	 * @return веб-страница
	 */
	@GetMapping
	public ModelAndView operatorMain(ModelAndView model) {
		model.setViewName("operator");
		
		var proposals = proposalService.getByStatus(ProposalStatus.IDLE);
		
		for (Proposal proposal : proposals) {
			proposal.setContent(proposalService.dashSplit(proposal));
		}
		
		model.addObject("proposals", proposals);
		
		return model;
	}

	/**
	 * Метод принятия пользовательской заявки.
	 * @param id Id пользовательской заявки
	 * @param model представление веб-страницы
	 * @return веб-страница
	 */
	@GetMapping("accept/{id}")
	public ModelAndView acceptProposal(@PathVariable Long id, ModelAndView model) {
		model.setViewName("redirect:/proposals");
		
		var proposal = proposalService.getById(id);
		proposal.setStatus(ProposalStatus.ACCEPTED);
		proposalService.update(proposal, id);
		
		return model;
	}

	/**
	 * Метод отклонения пользовательской заявки.
	 * @param id Id пользовательской заявки
	 * @param model представление веб-страницы
	 * @return веб-страница
	 */
	@GetMapping("decline/{id}")
	public ModelAndView declineProposal(@PathVariable Long id, ModelAndView model) {
		model.setViewName("redirect:/proposals");
		
		Proposal proposal = proposalService.getById(id);
		proposal.setStatus(ProposalStatus.DECLINED);
		proposalService.update(proposal, id);
		
		return model;
	}

}
