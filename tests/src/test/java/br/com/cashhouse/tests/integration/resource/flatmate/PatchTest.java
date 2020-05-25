package br.com.cashhouse.tests.integration.resource.flatmate;

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
	@Sql(value={ "classpath:schema.sql","classpath:data.sql","classpath:scene.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	public void patch_nickname_OK() throws Exception {

		loginWith(JEAN);

		// @formatter:off
		body().add("nickname", "Jean (test UPDATE)");

		patch("/flatmates/8")
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(8)))
				.andExpect(jsonPath("$.nickname", is("Jean (test UPDATE)")));
        // @formatter:on

	}

	@Test
	public void patch_invalid_id_Forbidden() throws Exception {

		loginWith(JEAN);

		// @formatter:off
		body().add("nickname", "Jean (test UPDATE)");

		patch("/flatmates/999")
				.andExpect(status().isForbidden());
        // @formatter:on

	}

	@Test
	public void patch_nickname_Forbidden() throws Exception {

		loginWith(JEAN);

		// @formatter:off
		body().add("nickname", "Gretchen (test UPDATE)");

		patch("/flatmates/1")
				.andExpect(status().isForbidden());
        // @formatter:on

	}

}
