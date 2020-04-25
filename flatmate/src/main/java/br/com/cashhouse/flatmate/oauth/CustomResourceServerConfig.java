package br.com.cashhouse.flatmate.oauth;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import br.com.cashhouse.util.token.ResourceTokenConfig;

@EnableWebSecurity
@EnableResourceServer
public class CustomResourceServerConfig extends ResourceTokenConfig {
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http
			.authorizeRequests()
				.antMatchers("/api/v1/flatmates/**").authenticated()
				.anyRequest().permitAll();
		//@formatter:on
	}

    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore());
    }

}
