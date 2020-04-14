package br.com.cashhouse.server.rest.doc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;
    
    @Value("${security.oauth2.client.scope}")
    private String scope;

	@Bean
	public Docket api() {
		// @formatter:off
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
					.apis(RequestHandlerSelectors.basePackage("br.com.cashhouse.server.rest"))
					.paths(PathSelectors.any())
				.build()
					.apiInfo(metadata())
	                .securityContexts(Arrays.asList(securityContext()))
	                .securitySchemes(Arrays.asList(basicAuthScheme()))
			        .globalOperationParameters(Arrays.asList(
			        		new ParameterBuilder()
				        		.name("Dashboard")
				        		.description("id")
				        		.modelRef(new ModelRef("integer"))
				        		.parameterType("header")
				        		.required(false)
			        		.build()
	        		));
        // @formatter:on
	}
	
	private SecurityContext securityContext() {
        return SecurityContext.builder()
        		.securityReferences(Collections.singletonList(new SecurityReference("oauth2schema", new AuthorizationScope[0])))
                .forPaths(PathSelectors.ant("/api/v1/**"))
                .build();
    }

    private SecurityScheme basicAuthScheme() {
        return new OAuth(
        		"oauth2schema", 
        		new ArrayList<>(), 
        		Collections.singletonList(new ResourceOwnerPasswordCredentialsGrant("http://localhost:8080/oauth/token"))
    		);
    }
    
    @Profile("development")
    @Bean
    public SecurityConfiguration securityInfo() {
    	return SecurityConfigurationBuilder.builder().clientId(clientId).clientSecret(clientSecret).build();
    }
    
	private ApiInfo metadata() {
		// @formatter:off
		return new ApiInfoBuilder()
				.title("Cash House")
				.description("The project server contains all the services that mobile and web applications can access, such as flatmates, savings cashier and transactions. The system has the option of accessing other accounts in case the user was invited.")
				.version("1.0")
				.license("MIT license")
		        .licenseUrl("https://opensource.org/licenses/MIT")
			.build();
        // @formatter:on
	}

}
