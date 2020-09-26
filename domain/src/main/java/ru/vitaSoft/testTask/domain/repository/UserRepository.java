package ru.vitaSoft.testTask.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vitaSoft.testTask.entities.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String userName);

}
