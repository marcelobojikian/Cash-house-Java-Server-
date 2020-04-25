package br.com.cashhouse.core.docs;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class DefaultConfig {

	private final String basePackage;

	public DefaultConfig(String basePackage) {
		this.basePackage = basePackage;
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage(basePackage))
				.build().apiInfo(metaData());
	}

	private ApiInfo metaData() {
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
