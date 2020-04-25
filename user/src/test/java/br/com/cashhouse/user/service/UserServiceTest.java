package br.com.cashhouse.user.service;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.repository.DashboardRepository;
import br.com.cashhouse.core.repository.FlatmateRepository;
import br.com.cashhouse.test.util.oauth.LoginWith;
import br.com.cashhouse.test.util.service.ServiceAuthHelper;
import br.com.cashhouse.user.rest.service.UserService;
import br.com.cashhouse.user.rest.service.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest extends ServiceAuthHelper {

	@Autowired
	private UserService userService;

	@MockBean
	private DashboardRepository dashboardService;

	@MockBean
	private FlatmateRepository flatmateRepository;

	@TestConfiguration
	static class UserServiceImplTestContextConfiguration {
		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}
	}

	@LoginWith(id = 1l)
	@Test
	public void whenFindInvitations_thenReturnObjectArray() throws Exception {

		when(dashboardService.findByMyInvitations(any(Flatmate.class))).thenReturn(Collections.emptyList());

		Collection<Dashboard> dashboards = userService.findInvitations();

		assertThat(dashboards, empty());

	}

	@LoginWith(id = 1l)
	@Test
	public void whenChangeNickname_thenReturnFlatmateObject() throws Exception {

		Flatmate flatmate = getFlatmateLogged();

		when(flatmateRepository.save(any(Flatmate.class))).thenReturn(flatmate);

		Flatmate flatmateChanged = userService.changeNickname("new nickname");

		assertThat(flatmateChanged.getId(), is(1l));
		assertThat(flatmateChanged.getNickname(), is("new nickname"));

	}

	@LoginWith(id = 1l)
	@Test
	public void whenChangePassword_thenReturnFlatmateObject() throws Exception {

		Flatmate flatmate = getFlatmateLogged();

		when(flatmateRepository.save(any(Flatmate.class))).thenReturn(flatmate);

		Flatmate flatmateChanged = userService.changePassword("new password");

		assertThat(flatmateChanged.getId(), is(1l));

	}

	@LoginWith(id = 1l)
	@Test
	public void whenFinishStepGuest_thenReturnFlatmateObject() throws Exception {

		Flatmate flatmate = getFlatmateLogged();

		when(flatmateRepository.save(any(Flatmate.class))).thenReturn(flatmate);

		Flatmate flatmateChanged = userService.finishStepGuest();

		assertThat(flatmateChanged.getId(), is(1l));
		assertTrue(flatmateChanged.isGuestStep());

	}

	@LoginWith(id = 1l)
	@Test
	public void whenFinishStepFirst_thenReturnFlatmateObject() throws Exception {

		Flatmate flatmate = getFlatmateLogged();

		when(flatmateRepository.save(any(Flatmate.class))).thenReturn(flatmate);

		Flatmate flatmateChanged = userService.finishStepFirst();

		assertThat(flatmateChanged.getId(), is(1l));
		assertTrue(flatmateChanged.isFirstStep());

	}

}
