package ru.vitaSoft.testTask.endpoint.rest.web.controller;

import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vitaSoft.testTask.entities.model.User;
import ru.vitaSoft.testTask.serivce.crud.RoleService;
import ru.vitaSoft.testTask.serivce.crud.UserService;

import java.util.List;

/**
 * Контроллер действий администратора
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

	private final UserService userService;
	private final RoleService roleService;

	/**
	 * Конструктор.
	 */
	public AdminController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	/**
	 * Просмотр списка зарегестрированных пользователей.
	 * @return Список пользователей
	 */
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		return ResponseEntity.ok(userService.getAll());
	}

	/**
	 * Метод, предназначенный для назначения пользователя на роль оператора.
	 * @param id Id пользователя, назначаемого на роль оператора.
	 */
	@GetMapping(value = "makeOp/{id}")
	public ResponseEntity makeOperator(@PathVariable Long id) {
		var user = userService.getById(id);
		user.grandAuthority(roleService.getByName("ROLE_OPERATOR"));
		userService.update(user, id);
		return new ResponseEntity(HttpStatus.OK);
	}

}
