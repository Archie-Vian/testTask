package ru.vitaSoft.testTask.endpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.vitaSoft.testTask.*"})
@EntityScan(basePackages = {"ru.vitaSoft.testTask.*"})
@EnableJpaRepositories(basePackages = {"ru.vitaSoft.testTask.domain.repository"})
@EnableCaching
public class TestTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestTaskApplication.class, args);
	}

}
