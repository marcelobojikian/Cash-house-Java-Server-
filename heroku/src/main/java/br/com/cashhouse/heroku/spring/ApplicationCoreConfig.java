package br.com.cashhouse.heroku.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.domain.DefaultPermissionFactory;
import org.springframework.security.acls.domain.PermissionFactory;
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

        // from documentation
		aclService.setClassIdentityQuery("select currval(pg_get_serial_sequence('acl_class', 'id'))");
		aclService.setSidIdentityQuery("select currval(pg_get_serial_sequence('acl_sid', 'id'))");

        // additional adjustments
		aclService.setObjectIdentityPrimaryKeyQuery("select acl_object_identity.id from acl_object_identity, acl_class where acl_object_identity.object_id_class = acl_class.id and acl_class.class=? and acl_object_identity.object_id_identity = cast(? as varchar)");
		aclService.setFindChildrenQuery("select obj.object_id_identity as obj_id, class.class as class from acl_object_identity obj, acl_object_identity parent, acl_class class where obj.parent_object = parent.id and obj.object_id_class = class.id and parent.object_id_identity = cast(? as varchar) and parent.object_id_class = (select id FROM acl_class where acl_class.class = ?)");

		return new PermissionRepository(aclService);
	}

	@Bean
	public PermissionFactory permissionFactory() {
		return new DefaultPermissionFactory();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() throws Exception {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

}
