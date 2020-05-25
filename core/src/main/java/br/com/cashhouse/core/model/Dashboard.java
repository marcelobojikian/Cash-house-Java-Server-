package br.com.cashhouse.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Dashboard implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@OneToOne(cascade = CascadeType.MERGE)
	private Profile owner;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "DASHBOARD_GUEST", joinColumns = @JoinColumn(name = "ID_DASHBOARD"), inverseJoinColumns = @JoinColumn(name = "ID_PROFILE"))
	private List<Profile> guests = new ArrayList<>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "DASHBOARD_FLATMATE", joinColumns = @JoinColumn(name = "ID_DASHBOARD"), inverseJoinColumns = @JoinColumn(name = "ID_FLATMATE"))
	private List<Flatmate> flatmates = new ArrayList<>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "DASHBOARD_TRANSACTION", joinColumns = @JoinColumn(name = "ID_DASHBOARD"), inverseJoinColumns = @JoinColumn(name = "ID_TRANSACTION"))
	private List<Transaction> transactions = new ArrayList<>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "DASHBOARD_CASHIER", joinColumns = @JoinColumn(name = "ID_DASHBOARD"), inverseJoinColumns = @JoinColumn(name = "ID_CASHIER"))
	private List<Cashier> cashiers = new ArrayList<>();

	public boolean isOwner(Profile profile) {
		return this.owner.equals(profile);
	}

	public boolean isOwner(String username) {
		return this.owner.getUsername().equals(username);
	}

	public boolean isGuest(Profile profile) {
		return this.guests.contains(profile);
	}

}
