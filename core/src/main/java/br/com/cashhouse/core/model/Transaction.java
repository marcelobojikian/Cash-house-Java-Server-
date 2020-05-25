package br.com.cashhouse.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@RequiredArgsConstructor
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@OneToOne
	private Flatmate flatmate;

	@OneToOne
	private Cashier cashier;

	@Column
	@Enumerated(EnumType.STRING)
	private Status status;

	public enum Status {
		SENDED, FINISHED;
	}

	@Column
	@Enumerated(EnumType.STRING)
	private Action action;

	public enum Action {
		DEPOSIT, WITHDRAW;
	}

	@Column
	private BigDecimal value;

	@Column(name = "created_at")
	private LocalDateTime createdDate;

	@Column(name = "updated_at")
	private LocalDateTime updatedDate;

	@PrePersist
	public void prePersist() {
		createdDate = LocalDateTime.now();
	}

	@JsonIgnore
	@PreUpdate
	public void preUpdate() {
		updatedDate = LocalDateTime.now();
	}

	@JsonIgnore
	public boolean isAssignedTo(Flatmate flatmate) {
		return this.flatmate.equals(flatmate);
	}

	@JsonIgnore
	public boolean isSended() {
		return status != null && status.equals(Status.SENDED);
	}

	@JsonIgnore
	public boolean isFinished() {
		return status != null && status.equals(Status.FINISHED);
	}

}
