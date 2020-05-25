package br.com.cashhouse.util;

import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.model.Transaction;

public class LoginWithContextFactory implements WithSecurityContextFactory<LoginWith> {

	@Override
	public SecurityContext createSecurityContext(LoginWith withUser) {

		// @formatter:off
		Profile profile = 
				createProfile(
					withUser.id(), 
					withUser.email(),
					withUser.roles());
		// @formatter:on

		Authentication authentication = new UsernamePasswordAuthenticationToken(profile, profile.getPassword(), profile.getAuthorities());
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		return context;

	}

	public static Profile createProfile(Long id, String email, String roles) {

		Profile profile = new Profile();
		profile.setId(id);
		profile.setUsername(email);
		profile.setEnabled(true);
		profile.setRoles(roles);

		Dashboard dashboard = new Dashboard();

		dashboard.setId(1l);
		dashboard.setOwner(profile);
		dashboard.setGuests(new ArrayList<Profile>());
		dashboard.setFlatmates(new ArrayList<Flatmate>());
		dashboard.setCashiers(new ArrayList<Cashier>());
		dashboard.setTransactions(new ArrayList<Transaction>());

		return profile;
	}

}
