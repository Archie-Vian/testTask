package ru.vitaSoft.testTask.endpoint.rest.web.controller;

import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vitaSoft.testTask.entities.enums.ProposalStatus;
import ru.vitaSoft.testTask.entities.model.Proposal;
import ru.vitaSoft.testTask.serivce.crud.ProposalService;
import ru.vitaSoft.testTask.serivce.crud.UserService;

import java.util.List;

/**
 * Контроллер страницы пользователя.
 */
@RestController
@RequestMapping("/user/proposals")
public class UserController {

	private final ProposalService proposalService;
	private final UserService userService;


	/**
	 * Конструктор.
	 * @param proposalService CRUD сервис пользовательских заявок.
	 * @param userService CRUD сервис пользователя.
	 */
	public UserController(ProposalService proposalService, UserService userService) {
		this.proposalService = proposalService;
		this.userService = userService;
	}

	/**
	 * Создание пользователем заявки.
	 * @return Успешно ли было произведено создание заявки
	 */
	@PostMapping("/create")
	public ResponseEntity createProposalGet(@RequestBody Proposal proposal) {
		var isSucceed = proposalService.create(proposal);
		if (!isSucceed) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(HttpStatus.CREATED);
	}

	/**
	 * Поиск всех созданных пользователем заявок.
	 * @return список созданных пользователем заявок
	 */
	@GetMapping
	public ResponseEntity<List<Proposal>> getProposals() {
		var user = userService.getPrincipal();
		var userProposals = proposalService.getByUserId(user.getId());
		return ResponseEntity.ok(userProposals);
	}

	/**
	 * Редактирование пользовательской заявки.
	 * @param id Id редактируемой заявки
	 * @param proposal одновленное состояние заявки
	 * @return обновленная заявка
	 */
	@PutMapping("/edit/{id}")
	public ResponseEntity editProposal(@PathVariable Long id, @RequestBody Proposal proposal) {
		if (!proposalService.isRelatedToPrincipal(id) || !proposalService.isDraft(id)) {
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
		if (proposalService.isBlank(proposal)) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		proposalService.update(proposal, id);
		return ResponseEntity.ok(proposalService.getById(id));
	}

	/**
	 * Метод отправки пользовательской заявки на рассмотрение оператором.
	 * @param id Id пользовательской заявки
	 * @return пользовательская заявка
	 */
	@GetMapping("/send/{id}")
	public ResponseEntity sendProposal(@PathVariable Long id) {
		if (!proposalService.isRelatedToPrincipal(id) || !proposalService.isDraft(id)) {
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
		var proposal = proposalService.getById(id);
		proposal.setStatus(ProposalStatus.IDLE);
		proposalService.update(proposal, id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(proposal);
	}
}
