package br.com.cashhouse.util.expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import br.com.cashhouse.util.service.ServiceRequest;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

	@Autowired
	private ServiceRequest serviceRequest;

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		return new CustomMethodSecurityExpressionHandler(serviceRequest);
	}

}
