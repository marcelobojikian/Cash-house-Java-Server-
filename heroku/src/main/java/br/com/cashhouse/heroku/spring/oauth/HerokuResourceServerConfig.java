package br.com.cashhouse.heroku.spring.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@EnableWebSecurity
@EnableResourceServer
public class HerokuResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private TokenStore tokenStore;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http
			.requestMatchers()
				.antMatchers("/api/**")
		.and()
			.authorizeRequests()
				.antMatchers("/api/**").authenticated()
				.anyRequest().permitAll();
		//@formatter:on
	}

	@Override
	public void configure(final ResourceServerSecurityConfigurer resources) {
		resources.tokenStore(tokenStore);
	}

}
