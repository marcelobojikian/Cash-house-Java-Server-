package br.com.cashhouse.cashier.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.domain.DefaultPermissionFactory;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.cashhouse.core.repository.PermissionRepository;

@Configuration
public class ApplicationCoreConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public PermissionRepository permissionService(JdbcMutableAclService aclService) {
		return new PermissionRepository(aclService);
	}

	@Bean
	public PermissionFactory permissionFactory() {
		return new DefaultPermissionFactory();
	}

}
