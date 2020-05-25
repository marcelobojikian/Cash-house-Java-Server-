package br.com.cashhouse.tests.integration.resource.flatmate;

import static br.com.cashhouse.test.util.integration.SecurityAccess.User.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
@Sql(value={ "classpath:schema.sql","classpath:data.sql","classpath:scene.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class PostTest extends Oauth2 {

	@Test
	public void save_OK() throws Exception {

		loginWith(JEAN);

		// @formatter:off
		body()
			.add("nickname", "New Flatmate");

		post("/flatmates")
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nickname", is("New Flatmate")));
        // @formatter:on

	}

	@Test
	public void guest_save_Forbidden() throws Exception {

		loginWith(GRETCHEN).dashboard(MARCELO);

		// @formatter:off
		body()
			.add("nickname", "New Gretchen Flatmate");

		post("/flatmates")
				.andExpect(status().isForbidden());
        // @formatter:on

	}

}
