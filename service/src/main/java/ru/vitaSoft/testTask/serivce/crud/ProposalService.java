package ru.vitaSoft.testTask.serivce.crud;

import ru.vitaSoft.testTask.entities.enums.ProposalStatus;
import ru.vitaSoft.testTask.entities.model.Proposal;

import java.util.List;

/**
 * Интервейс CRUD сервиса пользовательских заявок.
 */
public interface ProposalService extends AbstractService<Proposal> {

	/**
	 * {@inheritDoc}
	 */
	@Override
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
	Boolean delete(Long id);

	/**
	 * Принадлежит ли заявка авторизованному пользователю.
	 * @param proposalId Id пользовательской заявки
	 * @return Результат проверки
	 */
	Boolean isRelatedToPrincipal(Long proposalId);

	/**
	 * Разделение символов контента пользовательских заявок знаками '-'.
	 * @param proposal Пользовательская заявка
	 * @return Разделенное содержимое контента заявки
	 */
	String dashSplit(Proposal proposal);

	/**
	 * Является ли заявка черновой.
	 * @param id Id заявки
	 * @return Результат условия
	 */
	Boolean isDraft(Long id);

	/**
	 * Находится ли заявка в процессе рассмотрения.
	 * @param id Id заявки
	 * @return Результат условия
	 */
	Boolean isIdle(Long id);

	/**
	 * Отсутствует ли содержимое.
	 * @param proposal пользовательская заявка
	 * @return результат условия
	 */
	Boolean isBlank(Proposal proposal);
}
