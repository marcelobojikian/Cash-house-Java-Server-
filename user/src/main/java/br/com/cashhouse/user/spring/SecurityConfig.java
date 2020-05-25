package br.com.cashhouse.user.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationProvider authProvider;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		//@formatter:off
		httpSecurity
				.authorizeRequests()
				.antMatchers("/h2/**","/h2-console/**").permitAll()
				.antMatchers("/webjars/**").permitAll()
				.antMatchers("/register*").permitAll()
				.antMatchers("/api/v1/**").hasRole("USER")
				.anyRequest().authenticated()
			.and()
				.formLogin()
					.defaultSuccessUrl("/profile",true)
				.permitAll();

		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().sameOrigin();
		//@formatter:on
		
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

}
