package br.com.cashhouse.tests.integration.resource.user;

import static br.com.cashhouse.test.util.integration.SecurityAccess.User.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.cashhouse.heroku.App;
import br.com.cashhouse.test.util.integration.Oauth2;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = App.class)
public class GetTest extends Oauth2 {

	@Test
	public void view_DETAIL_MARCELO() throws Exception {

		loginWith(MARCELO);

		// @formatter:off
		get("/profiles/self")
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.username", is("marcelo@mail.com")))
				.andExpect(jsonPath("$.enabled", is(true)));
//				.andExpect(jsonPath("$.authorities").doesNotExist())
//				.andExpect(jsonPath("$.accountNonExpired").doesNotExist())
//				.andExpect(jsonPath("$.accountNonLocked").doesNotExist())
//				.andExpect(jsonPath("$.credentialsNonExpired").doesNotExist());
        // @formatter:on

	}

	@Test
	public void view_DETAIL_JEAN() throws Exception {

		loginWith(JEAN);

		// @formatter:off
		get("/profiles/self")
				.andExpect(jsonPath("$.id", is(8)))
				.andExpect(jsonPath("$.username", is("jean@mail.com")))
				.andExpect(jsonPath("$.enabled", is(true)));
//				.andExpect(jsonPath("$.authorities").doesNotExist())
//				.andExpect(jsonPath("$.accountNonExpired").doesNotExist())
//				.andExpect(jsonPath("$.accountNonLocked").doesNotExist())
//				.andExpect(jsonPath("$.credentialsNonExpired").doesNotExist());
        // @formatter:on

	}

}
