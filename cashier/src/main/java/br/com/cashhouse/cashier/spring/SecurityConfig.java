package br.com.cashhouse.cashier.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
				.antMatchers("/cashiers/**").hasRole("USER")
				.antMatchers("/api/v1/cashiers/**").hasRole("USER")
				.anyRequest().authenticated()
			.and()
				.formLogin()
					.defaultSuccessUrl("/cashiers",true)
				.permitAll();

		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().sameOrigin();
		//@formatter:on
		
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/**");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

}
