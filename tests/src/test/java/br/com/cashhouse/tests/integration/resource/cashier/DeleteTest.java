package br.com.cashhouse.tests.integration.resource.cashier;

import static br.com.cashhouse.test.util.integration.SecurityAccess.User.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.cashhouse.heroku.App;
import br.com.cashhouse.test.util.integration.Oauth2;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = App.class)
@Sql({ "classpath:reset.sql", "classpath:authorizations.sql" })
public class DeleteTest extends Oauth2 {

	@Test
	public void delete_OK() throws Exception {

		loginWith(MARCELO);

		// @formatter:off
		delete("/cashiers/2")
				.andExpect(status().isOk());
		
		get("/cashier/2")
				.andExpect(status().isNotFound());
		get("/transactions/3")
				.andExpect(status().isNotFound());
		get("/transactions/4")
				.andExpect(status().isNotFound());
		get("/transactions/5")
				.andExpect(status().isNotFound());
        // @formatter:on

	}

	@Test
	public void delete_Forbidden() throws Exception {

		loginWith(MARCELO).dashboard(JEAN);

		// @formatter:off
		delete("/cashiers/3")
				.andExpect(status().isForbidden());
        // @formatter:on

	}

	@Test
	public void delete_NotFound() throws Exception {

		loginWith(MARCELO);

		// @formatter:off
		delete("/cashiers/4")
				.andExpect(status().isNotFound());
        // @formatter:on

	}

}