package br.com.cashhouse.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
public class Flatmate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(unique = true)
	private String email;

	@Column
	private String nickname;

	@Column
	private boolean firstStep;

	@Column
	private boolean guestStep;

	@JsonIgnore
	@OneToOne(mappedBy = "owner")
	private Dashboard dashboard;

	public Flatmate(String email, String nickname) {
		super();
		this.email = email;
		this.nickname = nickname;
		this.firstStep = false;
		this.guestStep = false;
	}

}
