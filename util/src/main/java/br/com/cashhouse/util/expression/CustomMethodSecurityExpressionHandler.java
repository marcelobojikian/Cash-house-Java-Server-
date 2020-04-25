package br.com.cashhouse.util.expression;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

import br.com.cashhouse.util.service.ServiceRequest;

public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

	private final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

	private ServiceRequest serviceRequest;

	public CustomMethodSecurityExpressionHandler(ServiceRequest headerRequest) {
		super();
		this.serviceRequest = headerRequest;
	}

	@Override
	protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
			MethodInvocation invocation) {
		final CustomSecurityExpressionRoot root = new CustomSecurityExpressionRoot(serviceRequest, authentication);
		root.setPermissionEvaluator(getPermissionEvaluator());
		root.setTrustResolver(this.trustResolver);
		root.setRoleHierarchy(getRoleHierarchy());
		return root;
	}

}
