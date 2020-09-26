package ru.vitaSoft.testTask.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vitaSoft.testTask.entities.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

}
