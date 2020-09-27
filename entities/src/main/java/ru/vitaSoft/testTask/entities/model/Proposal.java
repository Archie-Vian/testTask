package ru.vitaSoft.testTask.entities.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ru.vitaSoft.testTask.entities.enums.ProposalStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Сущность пользовательской заявки.
 */
@Entity
@Table(name = "proposal")
public class Proposal extends BaseEntity{

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "content")
	private String content;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private ProposalStatus status;

	@Column(name = "creation_date")
	private LocalDateTime date;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ProposalStatus getStatus() {
		return status;
	}

	public void setStatus(ProposalStatus status) {
		this.status = status;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}
