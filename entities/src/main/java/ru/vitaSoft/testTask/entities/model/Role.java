package ru.vitaSoft.testTask.entities.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Сущность роли. Реализует интерфейс прав пользователя.
 */
@Entity
@Table(name = "role")
public class Role extends BaseEntity implements GrantedAuthority {

	@Column(name = "user_role")
	private String name;

	@ManyToMany(mappedBy = "roles")
	private List<User> user;

	@Override
	public String getAuthority() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
