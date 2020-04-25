package br.com.cashhouse.test.util.factory;

import static br.com.cashhouse.test.util.factory.EntityFactory.createFlatmate;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.test.util.oauth.LoginWith;
import br.com.cashhouse.test.util.security.UserDetailsImpl;

public class LoginWithContextFactory implements WithSecurityContextFactory<LoginWith> {

	@Override
	public SecurityContext createSecurityContext(LoginWith withUser) {

		// @formatter:off
		Flatmate userInfo = 
			createFlatmate(
				withUser.id(), 
				withUser.email(), 
				withUser.nickname());
		// @formatter:on

		String[] roles = withUser.roles();
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (int i = 0; i < roles.length; i++) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + roles[i]));
		}

		UserDetails principal = new UserDetailsImpl(userInfo, withUser.password(), grantedAuthorities);

		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(),
				principal.getAuthorities());
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		return context;

	}

}
