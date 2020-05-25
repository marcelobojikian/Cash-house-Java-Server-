package br.com.cashhouse.user.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.cashhouse.core.repository.PermissionRepository;
import br.com.cashhouse.util.service.UserDetailsServiceImpl;

@Configuration
public class ApplicationCoreConfig {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public PermissionRepository permissionService(JdbcMutableAclService aclService) {
		return new PermissionRepository(aclService);
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() throws Exception {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

}
