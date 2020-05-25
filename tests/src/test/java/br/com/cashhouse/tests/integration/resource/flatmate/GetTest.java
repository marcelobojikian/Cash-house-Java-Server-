package br.com.cashhouse.tests.integration.resource.flatmate;

import static br.com.cashhouse.test.util.integration.SecurityAccess.User.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import br.com.cashhouse.heroku.App;
import br.com.cashhouse.test.util.integration.Oauth2;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = App.class)
public class GetTest extends Oauth2 {

	@Test
	public void find_Id_OK() throws Exception {

		loginWith(JEAN);

		// @formatter:off
		get("/flatmates/9")
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(9)))
				.andExpect(jsonPath("$.nickname", is("Gretchen")));
        // @formatter:on

	}

	@Test
	public void guest_find_Id_OK() throws Exception {

		loginWith(GRETCHEN).dashboard(JEAN);

		// @formatter:off
		get("/flatmates/10")
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(10)))
				.andExpect(jsonPath("$.nickname", is("Fernando")));
        // @formatter:on

	}

	@Test
	public void guest_find_Owner_OK() throws Exception {

		loginWith(GRETCHEN).dashboard(JEAN);

		// @formatter:off
		get("/flatmates/8")
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(8)))
				.andExpect(jsonPath("$.nickname", is("Jean")));
        // @formatter:on

	}

	@Test
	public void find_all_OK() throws Exception {

		loginWith(JEAN);

		// @formatter:off
		get("/flatmates")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].id", is(8)))
				.andExpect(jsonPath("$[0].nickname", is("Jean")))
				.andExpect(jsonPath("$[1].id", is(9)))
				.andExpect(jsonPath("$[1].nickname", is("Gretchen")))
				.andExpect(jsonPath("$[2].id", is(10)))
				.andExpect(jsonPath("$[2].nickname", is("Fernando")));
        // @formatter:on

	}

	@Test
	public void firstAccess_find_all_OK() throws Exception {

		loginWith(GRETCHEN);

		// @formatter:off
		get("/flatmates")
				.andExpect(status().isNoContent());
        // @formatter:on

	}

	@Test
	public void guest_find_all_OK() throws Exception {

		loginWith(GRETCHEN).dashboard(JEAN);

		// @formatter:off
		get("/flatmates").andDo(MockMvcResultHandlers.print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].id", is(8)))
				.andExpect(jsonPath("$[0].nickname", is("Jean")))
				.andExpect(jsonPath("$[1].id", is(9)))
				.andExpect(jsonPath("$[1].nickname", is("Gretchen")))
				.andExpect(jsonPath("$[2].id", is(10)))
				.andExpect(jsonPath("$[2].nickname", is("Fernando")));
        // @formatter:on

	}

	@Test
	public void find_notFound() throws Exception {

		loginWith(JEAN);

		// @formatter:off
		get("/flatmates/5")
				.andExpect(status().isForbidden());
        // @formatter:on

	}

	@Test
	public void guest_find_notFound() throws Exception {

		loginWith(GRETCHEN).dashboard(JEAN);

		// @formatter:off
		get("/flatmates/5")
				.andExpect(status().isForbidden());
        // @formatter:on

	}

}
