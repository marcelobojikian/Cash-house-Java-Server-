package br.com.cashhouse.test.util.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.cashhouse.core.model.Flatmate;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class UserDetailsImpl extends User {

	private static final long serialVersionUID = 7891348495056908822L;

	Flatmate flatmate;

	public UserDetailsImpl(Flatmate flatmate, String password, Collection<? extends GrantedAuthority> authorities) {
		super(flatmate.getEmail(), password, authorities);
		this.flatmate = flatmate;
	}

	public Flatmate getFlatmate() {
		return flatmate;
	}

}
