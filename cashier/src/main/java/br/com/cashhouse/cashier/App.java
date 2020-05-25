package br.com.cashhouse.cashier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EntityScan("br.com.cashhouse.core.model")
@EnableJpaRepositories("br.com.cashhouse.core.repository")
@ComponentScan(
		value = {
				"br.com.cashhouse.cashier",
				"br.com.cashhouse.util.exception",
				"br.com.cashhouse.util.i18n",
				"br.com.cashhouse.util.service",
				"br.com.cashhouse.util.token"
		})
@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
