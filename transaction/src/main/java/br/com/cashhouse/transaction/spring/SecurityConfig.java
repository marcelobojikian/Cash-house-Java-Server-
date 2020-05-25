package br.com.cashhouse.transaction.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.cashhouse.util.service.UserDetailsServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		//@formatter:off
		httpSecurity
				.authorizeRequests()
				.antMatchers("/h2/**","/h2-console/**").permitAll()
				.antMatchers("/transactions/**").hasRole("USER")
				.antMatchers("/api/v1/transactions/**").hasRole("USER")
				.anyRequest().authenticated()
			.and()
				.formLogin()
					.defaultSuccessUrl("/transactions",true)
				.permitAll();

		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().sameOrigin();
		//@formatter:on
		
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

}
