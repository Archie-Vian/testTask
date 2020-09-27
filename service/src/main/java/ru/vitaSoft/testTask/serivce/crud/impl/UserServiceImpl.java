package ru.vitaSoft.testTask.serivce.crud.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vitaSoft.testTask.domain.repository.UserRepository;
import ru.vitaSoft.testTask.entities.model.User;
import ru.vitaSoft.testTask.serivce.crud.UserService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Реализация CRUD сервиса пользователя.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository repository;

	/**
	 * Конструктор.
	 * @param userRepository
	 */
	public UserServiceImpl(UserRepository userRepository) {
		this.repository = userRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getById(Long id) {
		if(repository.existsById(id)) {
			return repository.findById(id).get();
		}
		log.error("Пользователя с Id {} не существует!", id );
		throw new NoSuchElementException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getAll() {
		return repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User update(User updatedEntity, Long id) {
		updatedEntity.setId(id);
		return repository.save(updatedEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			log.error("Невозможно удалить несуществующео пользователя!");
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (User) authentication.getPrincipal();
	}
}
