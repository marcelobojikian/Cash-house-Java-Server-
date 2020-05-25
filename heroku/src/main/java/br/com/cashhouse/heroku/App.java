package br.com.cashhouse.heroku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("br.com.cashhouse.core.model")
@EnableJpaRepositories("br.com.cashhouse.core.repository")
@ComponentScan(
		basePackages = {
				"br.com.cashhouse.heroku",
				"br.com.cashhouse.util.exception",
				"br.com.cashhouse.util.i18n",
				"br.com.cashhouse.util.service",
				"br.com.cashhouse.util.token",
				
				"br.com.cashhouse.transaction.rest",
				"br.com.cashhouse.transaction.web",
				"br.com.cashhouse.transaction.service",
				
				"br.com.cashhouse.cashier.rest",
				"br.com.cashhouse.cashier.web",
				"br.com.cashhouse.cashier.service",
				
				"br.com.cashhouse.flatmate.rest",
				"br.com.cashhouse.flatmate.web",
				"br.com.cashhouse.flatmate.service",
				
				"br.com.cashhouse.user.rest",
				"br.com.cashhouse.user.web",
				"br.com.cashhouse.user.service"
		}
)
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
