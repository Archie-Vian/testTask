package ru.vitaSoft.testTask.endpoint.rest.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Контроллер главной страницы.
 */
@RestController
public class MainController {

	/**
	 * тартовая страница
	 * @param model представление веб-страницы
	 * @return веб-страница
	 */
	@GetMapping("/")
	public ModelAndView getMain(ModelAndView model) {
		model.setViewName("index");

		return model;
	}

	/**
	 * Форма логина.
	 * @param model представление веб-страницы
	 * @return веб-страница
	 */
	@RequestMapping("/login")
	public ModelAndView loginGet(ModelAndView model) {
		model.setViewName("login");

		return model;
	}

	/**
	 * Форма логина после ошибки ввода.
	 * @param model представление веб-страницы
	 * @return веб-страница
	 */
	@RequestMapping("/login-error")
	public ModelAndView loginError(ModelAndView model) {
		model.setViewName("login");

		model.addObject("loginError", true);

		return model;
	}
}