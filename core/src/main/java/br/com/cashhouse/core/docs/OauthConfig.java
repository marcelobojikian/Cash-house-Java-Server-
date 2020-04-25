package br.com.cashhouse.core.docs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

public class OauthConfig extends DefaultConfig {

	private String clientId;

	private String clientSecret;

	public OauthConfig(String basePackage, String clientId, String clientSecret) {
		super(basePackage);
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	@Override
	public Docket api() {
		// @formatter:off
    	return super.api()
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(basicAuthScheme()));
    	// @formatter:on
	}

	private SecurityContext securityContext() {
		// @formatter:off
        return SecurityContext.builder()
        		.securityReferences(Collections.singletonList(new SecurityReference("oauth2schema", new AuthorizationScope[0])))
                .forPaths(PathSelectors.any())
                .build();
        // @formatter:on
	}

	private SecurityScheme basicAuthScheme() {
		// @formatter:off
        return new OAuth(
        		"oauth2schema", 
        		new ArrayList<>(),
        		Collections.singletonList(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"))
    		);
        // @formatter:on
	}

	@Profile("development")
	@Bean
	public SecurityConfiguration securityInfo() {
		return SecurityConfigurationBuilder.builder().clientId(clientId).clientSecret(clientSecret).build();
	}

}
