package br.com.cashhouse.test.util.factory;

import static br.com.cashhouse.test.util.factory.EntityFactory.createProfile;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.test.util.oauth.LoginWith;

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

}
