package ru.vitaSoft.testTask.serivce.crud;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.vitaSoft.testTask.entities.model.User;

import java.util.List;

/**
 * Интерфейс CRUD сервиса пользователя. Наследует интерфейс сервиса деталей авторизации пользователя.
 */
public interface UserService extends AbstractService<User>, UserDetailsService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	User getById(Long id);

	/**
	 * {@inheritDoc}
	 */
	@Override
	List<User> getAll();

	/**
	 * {@inheritDoc}
	 */
	@Override
	User update(User updatedEntity, Long id);

	/**
	 * {@inheritDoc}
	 */
	@Override
	boolean delete(Long id);

	/**
	 * Возвращает авторизованного пользователя.
	 * @return Пользователь, от имени которого был вызван метод
	 */
	User getPrincipal();
}
