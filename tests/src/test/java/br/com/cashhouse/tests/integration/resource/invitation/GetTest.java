package br.com.cashhouse.tests.integration.resource.invitation;

import static br.com.cashhouse.test.util.integration.SecurityAccess.User.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
	public void view_INVITATIONS_BIRO() throws Exception {

		loginWith(BIRO);

		// @formatter:off
		get("/invitations/self")
				.andExpect(jsonPath("$[0].id", is(1)));
        // @formatter:on

	}

}
