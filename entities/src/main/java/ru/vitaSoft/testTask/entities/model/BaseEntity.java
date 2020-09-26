package ru.vitaSoft.testTask.entities.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Суперкласс сущности. Предоставляет Id.
 */
@MappedSuperclass
public abstract class BaseEntity {
	/**
	 * Поле id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
