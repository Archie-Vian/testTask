package ru.vitaSoft.testTask.serivce.crud;

import ru.vitaSoft.testTask.entities.enums.ProposalStatus;
import ru.vitaSoft.testTask.entities.model.Proposal;

import java.util.List;

/**
 * Интервейс CRUD сервиса пользовательских заявок.
 */
public interface ProposalService extends AbstractService<Proposal> {

	/**
	 * Создание пользовательской заявки.
	 * @param newEntity Сущность заявки для добавления в БД
	 * @return Успешно ли было произведено создание
	 */
	Boolean create(Proposal newEntity);

	/**
	 * {@inheritDoc}
	 */
	@Override
	Proposal getById(Long id);

	/**
	 * {@inheritDoc}
	 */
	@Override
	List<Proposal> getAll();

	/**
	 * Найти все заявки для пользователя с указаным Id.
	 * @param userId Id пользователя
	 * @return Список найденых заявок
	 */
	List<Proposal> getByUserId(Long userId);

	/**
	 * Найти все заявки с указаным статусом.
	 * @param status Статус заявки
	 * @return Список найденых заявок
	 */
	List<Proposal> getByStatus(ProposalStatus status);

	/**
	 * {@inheritDoc}
	 */
	@Override
	Proposal update(Proposal updatedEntity, Long id);

	/**
	 * {@inheritDoc}
	 */
	@Override
	boolean delete(Long id);

	/**
	 * Принадлежит ли заявка авторизованному пользователю.
	 * @param proposalId Id пользовательской заявки
	 * @return Результат проверки
	 */
	boolean isRelatedToPrincipal(Long proposalId);

	/**
	 * Разделение символов контента пользовательских заявок знаками '-'.
	 * @param proposal Пользовательская заявка
	 * @return Разделенное содержимое контента заявки
	 */
	String dashSplit(Proposal proposal);
}
