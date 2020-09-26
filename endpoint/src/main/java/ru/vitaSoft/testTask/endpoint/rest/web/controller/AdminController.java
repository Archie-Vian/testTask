package ru.vitaSoft.testTask.endpoint.rest.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.vitaSoft.testTask.entities.model.User;
import ru.vitaSoft.testTask.serivce.crud.RoleService;
import ru.vitaSoft.testTask.serivce.crud.UserService;

/**
 * Контроллер страниц администратора
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;
	private final RoleService roleService;

	/**
	 * Конструктор.
	 * @param userService CRUD сервис пльзователя.
	 * @param roleService CRUD сервис роли пользователя.
	 */
	public AdminController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	/**
	 * Главная траница кабинета администратора.
	 * @param model представление веб-страницы
	 * @return веб-страница
	 */
	@GetMapping
	public ModelAndView adminMain(ModelAndView model) {
		model.setViewName("admin");
		model.addObject("users", userService.getAll());
		model.addObject("opRole", roleService.getByName("ROLE_OPERATOR"));
		return model;
	}

	/**
	 * Метод, предназначенный для назначения пользователя на роль оператора.
	 * @param id Id пользователя, назначаемого на роль оператора.
	 * @param model представление веб-страницы
	 * @return веб-страница
	 */
	@GetMapping("makeOp/{id}")
	public ModelAndView makeOperator(@PathVariable Long id, ModelAndView model) {
		model.setViewName("redirect:/admin");
		User user = userService.getById(id);
		user.grandAuthority(roleService.getByName("ROLE_OPERATOR"));
		userService.update(user, id);
		return model;
	}

}
