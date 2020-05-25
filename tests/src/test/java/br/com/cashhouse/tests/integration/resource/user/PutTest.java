package br.com.cashhouse.tests.integration.resource.user;

import static br.com.cashhouse.test.util.integration.SecurityAccess.User.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class PutTest extends Oauth2 {

	@Test
	public void whenUpdatePassword__thenStatus_OK() throws Exception {

		loginWith(JEAN);

		// @formatter:off
		body()
			.add("value", "New password");

		put("/profiles/self/password")
				.andExpect(status().isOk());
        // @formatter:on

	}

}
