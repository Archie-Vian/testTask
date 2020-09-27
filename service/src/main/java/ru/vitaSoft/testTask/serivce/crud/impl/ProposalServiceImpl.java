package ru.vitaSoft.testTask.serivce.crud.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.vitaSoft.testTask.domain.repository.ProposalRepository;

import ru.vitaSoft.testTask.entities.enums.ProposalStatus;
import ru.vitaSoft.testTask.entities.model.Proposal;
import ru.vitaSoft.testTask.entities.model.User;
import ru.vitaSoft.testTask.serivce.crud.ProposalService;
import ru.vitaSoft.testTask.serivce.crud.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Реализация CRUD сервиса пользовательских заявок.
 */
@Service
@Slf4j
public class ProposalServiceImpl implements ProposalService {

	private final ProposalRepository repository;
	private final UserService userService;

	/**
	 * Конструктор.
	 * @param repository Репозиторий пользовательских заявок
	 * @param userService CRUD сервис пользователя
	 */
	public ProposalServiceImpl(ProposalRepository repository, UserService userService) {
		this.repository = repository;
		this.userService = userService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean create(Proposal newEntity) {
		newEntity.setDate(LocalDateTime.now());
		if (newEntity.getContent() == null) {
			throw new IllegalArgumentException("Текст обращения не может быть пустым!");
		}
		newEntity.setStatus(ProposalStatus.DRAFT);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		newEntity.setUser(user);
		repository.save(newEntity);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Proposal getById(Long id) {
		if(repository.existsById(id)) {
			return repository.findById(id).get();
		}
		log.error("Обращения с Id {} не существует!", id );
		throw new NoSuchElementException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Proposal> getByUserId(Long userId) {
		return repository.findByUserIdOrderByDateDesc(userId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Proposal> getByStatus(ProposalStatus status) {
		return repository.findByStatus(status);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Proposal> getAll() {
		return repository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Proposal update(Proposal updatedEntity, Long id) {
		updatedEntity.setId(id);
		return repository.save(updatedEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			log.error("Невозможно удалить несуществующее обращение!");
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRelatedToPrincipal(Long id) {
		User user = userService.getPrincipal();
		return getById(id).getUser().getId().equals(user.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String dashSplit(Proposal proposal) {
		StringBuilder builder = new StringBuilder();

		for (char symbol: proposal.getContent().toCharArray()) {
			builder.append(symbol).append('-');
		}

		String separatedContent = builder.toString();
		separatedContent = separatedContent.substring(0, separatedContent.length()-1);

		return separatedContent;
	}
}
