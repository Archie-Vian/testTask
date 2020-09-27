package ru.vitaSoft.testTask.serivce.crud;

import ru.vitaSoft.testTask.entities.model.Role;

import java.util.List;

/**
 * Интерфейс CRUD сервиса роли пользователя.
 */
public interface RoleService extends AbstractService<Role> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	Boolean create(Role newEntity);

	/**
	 * {@inheritDoc}
	 */
	@Override
	Role getById(Long id);

	/**
	 * {@inheritDoc}
	 */
	@Override
	List<Role> getAll();

	/**
	 * {@inheritDoc}
	 */
	@Override
	Role update(Role updatedEntity, Long id);

	/**
	 * {@inheritDoc}
	 */
	@Override
	Boolean delete(Long id);

	/**
	 * Найти роль по её имени.
	 * @param name Имя роли
	 * @return Искомая роль
	 */
	Role getByName(String name);
}
