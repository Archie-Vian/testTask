package ru.vitaSoft.testTask.serivce.crud;

import ru.vitaSoft.testTask.entities.model.BaseEntity;

import java.util.List;

/**
 * Суперинтерфейс сервисного слоя.
 * @param <Entity> Сущность, которая будет обрабатываться в рамках сервиса
 */
public interface AbstractService <Entity extends BaseEntity> {

	/**
	 * Найти сущность по заданному Id.
	 * @param id Id искомой сущности
	 * @return Искомая сущность
	 */
	Entity getById(Long id);

	/**
	 * Найти все сущности реализуемого типа.
	 * @return Список искомых сущностей
	 */
	List<Entity> getAll();

	/**
	 * Обновить сущность с выбранным Id.
	 * @param updatedEntity обновленное состояние сущности
	 * @param id Id сущности для обновления
	 * @return Обновлённая сущность
	 */
	Entity update(Entity updatedEntity, Long id);

	/**
	 * Удалить сущность с выбраным Id.
	 * @param id Id сущности для удаления
	 * @return Успешно ли было произведено удаление.
	 */
	boolean delete(Long id);
}
