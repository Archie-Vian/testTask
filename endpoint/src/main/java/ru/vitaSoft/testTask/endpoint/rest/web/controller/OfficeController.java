package ru.vitaSoft.testTask.endpoint.rest.web.controller;

import lombok.var;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.vitaSoft.testTask.entities.enums.ProposalStatus;
import ru.vitaSoft.testTask.entities.model.Proposal;
import ru.vitaSoft.testTask.entities.model.User;
import ru.vitaSoft.testTask.serivce.crud.ProposalService;
import ru.vitaSoft.testTask.serivce.crud.UserService;

import java.util.List;

/**
 * Контроллер страницы пользователя.
 */
@RestController
@RequestMapping("/office")
public class OfficeController {

	private final ProposalService proposalService;
	private final UserService userService;


	/**
	 * Конструктор.
	 * @param proposalService CRUD сервис пользовательских заявок.
	 * @param userService CRUD сервис пользователя.
	 */
	public OfficeController(ProposalService proposalService, UserService userService) {
		this.proposalService = proposalService;
		this.userService = userService;
	}

	/**
	 * Основная страница кабинета пользователя.
	 * @param model представление веб-страницы
	 * @return веб-страница
	 */
	@GetMapping
	public ModelAndView officeMain(ModelAndView model) {
		model.setViewName("office");

		var user = userService.getPrincipal();
		var draftProposals = proposalService.getByUserId(user.getId());
		model.addObject("proposals", draftProposals);

		return model;
	}

	/**
	 * Страница создания пользователем заявки.
	 * @param model представление веб-страницы
	 * @return веб-страница
	 */
	@GetMapping("create")
	public ModelAndView createProposalGet(ModelAndView model) {
		model.setViewName("proposal-create-form");

		return model;
	}

	/**
	 * Страница отправки результатов создания пользовательской заявки.
	 * @param content Содержимое создаваемой пользователем заявки
	 * @param model представление веб-страницы
	 * @return веб-страница
	 */
	@PostMapping("create")
	public ModelAndView createProposalPost(@RequestParam String content, ModelAndView model){
		model.setViewName("redirect:/office");

		var proposal = new Proposal();
		proposal.setContent(content);
		proposalService.create(proposal);

		return model;
	}

	/**
	 * Метод отправки пользовательской заявки на рассмотрение оператором.
	 * @param id Id пользовательской заявки
	 * @param model представление веб-страницы
	 * @return веб-страница
	 */
	@GetMapping("send/{id}")
	public ModelAndView sendProposal(@PathVariable Long id, ModelAndView model) {
		model.setViewName("redirect:/office");

		if (!proposalService.isRelatedToPrincipal(id)) {
			return model;
		}

		var proposal = proposalService.getById(id);
		proposal.setStatus(ProposalStatus.IDLE);
		proposalService.update(proposal, id);

		return model;
	}

	/**
	 * Страница редактирования черновика пользовательской заявки.
	 * @param id Id редактируемой заявки
	 * @param model представление веб-страницы
	 * @return веб-страница
	 */
	@GetMapping("edit/{id}")
	public ModelAndView editProposal(@PathVariable Long id, ModelAndView model) {
		model.setViewName("proposal-form");

		var proposal = proposalService.getById(id);

		if(!proposal.getStatus().equals(ProposalStatus.DRAFT)
			|| !proposalService.isRelatedToPrincipal(id)) {
			model.setViewName("redirect:/office");
			return model;
		}

		model.addObject("proposal", proposal);

		return model;
	}

	/**
	 * Страница отправки результатов редактирования пользовательской заявки.
	 * @param id Id редактируемой заявки
	 * @param content изменённое содержание пользовательской заявки
	 * @param model представление веб-страницы
	 * @return веб-страница
	 */
	@PostMapping("edit/{id}")
	public ModelAndView submitProposalEdit(@PathVariable Long id, @RequestParam String content, ModelAndView model) {
		model.setViewName("redirect:/office");
		if (!proposalService.isRelatedToPrincipal(id)) {
			return model;
		}

		Proposal proposal = proposalService.getById(id);

		if(proposal.getStatus().equals(ProposalStatus.DRAFT)) {
			proposal.setContent(content);
			proposalService.update(proposal, id);
		}
		
		return model;
	}
}
