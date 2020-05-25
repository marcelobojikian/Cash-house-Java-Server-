package br.com.cashhouse.tests.integration.resource.dashboard;

import static br.com.cashhouse.test.util.integration.SecurityAccess.User.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
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
	public void view_GUESTS_MARCELO() throws Exception {

		loginWith(MARCELO);

		// @formatter:off
		get("/dashboards/self/guests")
        		.andExpect(jsonPath("$[*].id", hasItems(2,3,4,5,6,7)));
        // @formatter:on

	}

	@Test
	public void view_FLATMATES_MARCELO() throws Exception {

		loginWith(MARCELO);

		// @formatter:off
		get("/dashboards/self/flatmates")
        		.andExpect(jsonPath("$[*].id", hasItems(1,2,3,4,5,6,7)));
        // @formatter:on

	}

	@Test
	public void view_CASHIERS_MARCELO() throws Exception {

		loginWith(MARCELO);

		// @formatter:off
		get("/dashboards/self/cashiers")
        		.andExpect(jsonPath("$[*].id", hasItems(1,2)));
        // @formatter:on

	}

	@Test
	public void view_TRANSACTIONS_MARCELO() throws Exception {

		loginWith(MARCELO);

		// @formatter:off
		get("/dashboards/self/transactions")
				.andExpect(jsonPath("$", hasSize(4)))
				.andExpect(jsonPath("$[*].id", hasItems(1,3,5,6)));
        // @formatter:on

	}

}
