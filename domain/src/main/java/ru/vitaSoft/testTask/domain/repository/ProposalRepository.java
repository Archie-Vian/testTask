package ru.vitaSoft.testTask.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vitaSoft.testTask.entities.enums.ProposalStatus;
import ru.vitaSoft.testTask.entities.model.Proposal;

import java.util.List;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {
	List<Proposal> findByUserIdOrderByDateDesc(Long userId);
	List<Proposal> findByStatus(ProposalStatus status);
}
