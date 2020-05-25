package br.com.cashhouse.test.util.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.test.util.security.UserDetailsImpl;
import br.com.cashhouse.util.service.ServiceRequest;

public class ServiceAuthHelper {

	@MockBean
	private UserDetailsService userDetailsService;

	@MockBean
	private ServiceRequest headerRequest;

	@Before
	public void init() {
		when(headerRequest.getFlatmateLogged()).thenReturn(getFlatmateLogged());
//		when(headerRequest.getDashboard()).thenReturn(getFlatmateLogged().getDashboard());
		when(userDetailsService.loadUserByUsername(any(String.class))).thenReturn(getCurrentUser());
	}

	public void userDashboard(Flatmate flatmate) {
//		when(headerRequest.getDashboard()).thenReturn(flatmate.getDashboard());
	}

	public void addGuest(Flatmate guest) {
//		Dashboard dashboard = getFlatmateLogged().getDashboard();
//		dashboard.getGuests().add(guest);
	}

	public Flatmate getFlatmateLogged() {
		return getCurrentUser().getFlatmate();
	}

	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	private UserDetailsImpl getCurrentUser() {
		return (UserDetailsImpl) getAuthentication().getPrincipal();
	}

}
