package ru.vitaSoft.testTask.endpoint.rest.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import ru.vitaSoft.testTask.serivce.crud.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserService userService;

	/**
	 * Конструктор.
	 * @param userService CRUD сервис пользователя
	 */
	public WebSecurityConfig(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.httpBasic().and()
				.csrf()
				.disable()
				.authorizeRequests()
				//Доступ только для пользователей с ролью Администратор
				.antMatchers("/admin/**").hasRole("ADMIN")
				//Доступ только для пользователей с ролью Оператор
				.antMatchers("/operator/**").hasRole("OPERATOR")
				//Доступ только для пользователей с ролью Пользователь
				.antMatchers("/user/**").hasRole("USER")
				//Все остальные страницы требуют аутентификации
				.anyRequest().authenticated();
	}

	/**
	 * Конфигурация данных аутентификации пользователя.
	 *
	 * В рамках тестового задания, за неимением методов создания пользователей,
	 * за искоючением прямого занесения в БД, кодирование паролей отключено.
	 *
	 * @param auth Менеджер аутентификации
	 * @throws Exception Возможные ошибки
	 */
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}

}
