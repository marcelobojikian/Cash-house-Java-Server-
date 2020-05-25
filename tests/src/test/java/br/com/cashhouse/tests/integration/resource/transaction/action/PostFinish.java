package br.com.cashhouse.tests.integration.resource.transaction.action;

import static br.com.cashhouse.test.util.integration.SecurityAccess.User.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import br.com.cashhouse.heroku.App;
import br.com.cashhouse.test.util.integration.Oauth2;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = App.class)
@Sql(value={ "classpath:schema.sql","classpath:data.sql","classpath:scene.sql"})
public class PostFinish extends Oauth2 {

	private ResultActions finishTransaction(int id) throws Exception {
		body(); // Body empty
		return post(String.format("/transactions/%s/finish", id));
	}

	@Test
	public void finish_WITHDRAW_OK() throws Exception {

		loginWith(MARCELO);

		// @formatter:off
		finishTransaction(3)
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.id", is(3)))
				.andExpect(jsonPath("$.status", is("FINISHED")));
		
		get("/cashiers/2")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(2)))
				.andExpect(jsonPath("$.balance", is(120.0)));
        // @formatter:on

	}

//	@Test
//	public void finish_DEPOSIT_OK() throws Exception {
//
//		loginWith(MARCELO);
//
//		// @formatter:off
//		finishTransaction(5)
//				.andExpect(status().isAccepted())
//				.andExpect(jsonPath("$.id", is(5)))
//				.andExpect(jsonPath("$.status", is("FINISHED")));
//		
//		get("/cashiers/1")
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.id", is(1)))
//				.andExpect(jsonPath("$.balance", is(92.36)));
//        // @formatter:on
//
//	}

	@Test
	public void send_fail_STATUS_FINISHED() throws Exception {

		loginWith(MARCELO);

		// @formatter:off
		finishTransaction(1)
				.andExpect(status().isForbidden());
        // @formatter:on

	}

	@Test
	public void send_fail_NotFound() throws Exception {

		loginWith(MARCELO);

		// @formatter:off
		finishTransaction(999)
				.andExpect(status().isForbidden());
        // @formatter:on

	}

	@Test
	public void send_fail_NotDashboardOwner() throws Exception {

		loginWith(WILL).dashboard(MARCELO);

		// @formatter:off
		finishTransaction(3)
				.andExpect(status().isForbidden());
        // @formatter:on

	}

}
