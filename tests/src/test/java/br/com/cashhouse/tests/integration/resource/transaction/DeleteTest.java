package br.com.cashhouse.tests.integration.resource.transaction;

import static br.com.cashhouse.test.util.integration.SecurityAccess.User.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.cashhouse.heroku.App;
import br.com.cashhouse.test.util.integration.Oauth2;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = App.class)
public class DeleteTest extends Oauth2 {

	@Test
	@Sql(value={ "classpath:schema.sql","classpath:data.sql","classpath:scene.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	public void delete_OK() throws Exception {

		loginWith(MARCELO);

		// @formatter:off
		delete("/transactions/3")
				.andExpect(status().isOk());
		
		get("/transactions/3")
				.andExpect(status().isNotFound());
        // @formatter:on

	}

	@Test
	public void delete_Forbidden() throws Exception {

		loginWith(MARCELO).dashboard(JEAN);

		// @formatter:off
		delete("/transactions/9")
				.andExpect(status().isForbidden());
        // @formatter:on

	}

	@Test
	public void delete_NotFound() throws Exception {

		loginWith(MARCELO);

		// @formatter:off
		delete("/transactions/99")
				.andExpect(status().isForbidden());
        // @formatter:on

	}

}
