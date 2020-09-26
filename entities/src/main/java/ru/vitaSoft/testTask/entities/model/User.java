package ru.vitaSoft.testTask.entities.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.Set;


/**
 * Сущность пользователя. Реализует интерфейс авторизации пользователя.
 */
@Entity
@Table(name = "usr")
public class User extends BaseEntity implements UserDetails {

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Transient
	private String passwordConfirm;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Proposal> proposals;

	public boolean hasRole(Role role) {
		return roles.contains(role);
	}

	//---------------------UserDetails implementation-----------------------//

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	//-----------------------Getter/Setter--------------------------//

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public void grandAuthority(Role role) {
		this.roles.add(role);
	}

	public Set<Proposal> getProposals() {
		return proposals;
	}

	public void addProposals(Set<Proposal> proposals) {
		this.proposals.addAll(proposals);
	}
}
