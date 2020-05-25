package br.com.cashhouse.tests.integration.resource.flatmate;

import static br.com.cashhouse.test.util.integration.SecurityAccess.User.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
public class PutTest extends Oauth2 {

	@Test
	@Sql(value={ "classpath:schema.sql","classpath:data.sql","classpath:scene.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	public void update_OK() throws Exception {

		loginWith(MARCELO);

		// @formatter:off
		body()
			.add("nickname", "Carol (test UPDATE)");
		
		put("/flatmates/6")
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(6)))
				.andExpect(jsonPath("$.nickname", is("Carol (test UPDATE)")));
        // @formatter:on

	}

	@Test
	public void update_invalid_id_NotFound() throws Exception {

		loginWith(MARCELO);

		// @formatter:off
		body()
			.add("nickname", "Carol (test UPDATE)");
		
		put("/flatmates/999")
				.andExpect(status().isForbidden());
        // @formatter:on

	}

}
