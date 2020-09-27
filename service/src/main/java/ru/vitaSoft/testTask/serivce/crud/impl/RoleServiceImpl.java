package ru.vitaSoft.testTask.serivce.crud.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.vitaSoft.testTask.domain.repository.RoleRepository;
import ru.vitaSoft.testTask.entities.model.Role;
import ru.vitaSoft.testTask.serivce.crud.RoleService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Реализация CRUD сервиса ролей пользователя.
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

	private final RoleRepository repository;

	/**
	 * Конструктор
	 * @param roleRepository Репозиторий ролей пользователя
	 */
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.repository = roleRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role getById(Long id) {
		if(repository.existsById(id)) {
			return repository.findById(id).get();
		}
		log.error("Роли с Id {} не существует!", id );
		throw new NoSuchElementException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> getAll() {
		return repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role update(Role updatedEntity, Long id) {
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
			log.error("Невозможно удалить несуществующую роль!");
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role getByName(String name) {
		return repository.findByName(name);
	}
}
