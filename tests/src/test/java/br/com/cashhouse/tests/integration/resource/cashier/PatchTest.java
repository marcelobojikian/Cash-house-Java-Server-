package br.com.cashhouse.tests.integration.resource.cashier;

import static br.com.cashhouse.test.util.integration.SecurityAccess.User.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.cashhouse.heroku.App;
import br.com.cashhouse.test.util.integration.Oauth2;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = App.class)
public class PatchTest extends Oauth2 {

	@Test
	public void patch_NoContent_Fail() throws Exception {

		loginWith(JEAN);

		// @formatter:off
		patch("/cashiers/3", "{}")
				.andExpect(status().isBadRequest());
        // @formatter:on

	}

	@Test
	@Sql(value={ "classpath:schema.sql","classpath:data.sql","classpath:scene.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	public void patch_name_OK() throws Exception {

		loginWith(JEAN);

		// @formatter:off
		body().add("name", "New Name");

		patch("/cashiers/3")
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(3)))
				.andExpect(jsonPath("$.name", is("New Name")))
				.andExpect(jsonPath("$.started", is(12.45)))
				.andExpect(jsonPath("$.balance", is(3.11)));
        // @formatter:on

	}

	@Test
	public void patch_balance_Fail() throws Exception {

		loginWith(JEAN);

		// @formatter:off
		body().add("balance", 32);

		patch("/cashiers/3")
				.andExpect(status().isBadRequest());
        // @formatter:on

	}

	@Test
	public void patch_started_Fail() throws Exception {

		loginWith(JEAN);

		// @formatter:off
		body().add("started", "New Name");

		patch("/cashiers/3")
				.andExpect(status().isBadRequest());
        // @formatter:on

	}

	@Test
	public void patch_invalid_id_Forbidden() throws Exception {

		loginWith(JEAN);

		// @formatter:off
		body().add("name", "New Name");

		patch("/cashiers/999")
				.andExpect(status().isForbidden());
        // @formatter:on

	}

	@Test
	public void patch_name_Forbidden() throws Exception {

		loginWith(GRETCHEN).dashboard(JEAN);

		// @formatter:off
		body().add("name", "Gretchen New Name");

		patch("/cashiers/3")
				.andExpect(status().isForbidden());
        // @formatter:on

	}

}
