package br.com.cashhouse.util.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import br.com.cashhouse.core.exception.EntityNotFoundException;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.repository.DashboardRepository;
import br.com.cashhouse.core.repository.FlatmateRepository;

public class ServiceRequestTest {

	private static final String DASHBOARD_ID = "dashboard";

	private ServiceRequest serviceRequest;

	@Mock
	private HttpServletRequest request;

	private FlatmateRepository flatmateRepository;

	private DashboardRepository dashboardRepository;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);

		flatmateRepository = Mockito.mock(FlatmateRepository.class);
		dashboardRepository = Mockito.mock(DashboardRepository.class);

		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);

		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("");

		serviceRequest = new ServiceRequest(flatmateRepository, dashboardRepository);

		ServletWebRequest webRequest = Mockito.mock(ServletWebRequest.class);
		when(webRequest.getNativeRequest()).thenReturn(request);

	}

	@Test
	public void should_return_true_when_no_header() throws Exception {
		when(request.getHeader(DASHBOARD_ID)).thenReturn(null);
		assertTrue(serviceRequest.preHandle(request, null, null));
	}

	@Test
	public void should_return_true_when_invalid_header() throws Exception {
		when(request.getHeader(DASHBOARD_ID)).thenReturn("21e");
		assertTrue(serviceRequest.preHandle(request, null, null));
	}

	@Test
	public void should_return_true_when_header() throws Exception {
		when(request.getHeader(DASHBOARD_ID)).thenReturn("1");
		assertTrue(serviceRequest.preHandle(request, null, null));
	}

//	@Test
//	public void should_return_new_Dashboard_when_flatame_dont_have_dashboard() throws Exception {
//
//		when(request.getHeader(DASHBOARD_ID)).thenReturn(null);
//		serviceRequest.preHandle(request, null, null);
//
//		Flatmate flatmate = new Flatmate();
//
//		Dashboard expected = new Dashboard();
//
//		when(flatmateRepository.findByEmail(anyString())).thenReturn(Optional.of(flatmate));
//		when(dashboardRepository.findByOwner(flatmate)).thenReturn(null);
//		when(dashboardRepository.save(any(Dashboard.class))).thenReturn(expected);
//
//		Dashboard dashboard = serviceRequest.getDashboard();
//
//		assertThat(dashboard).isEqualTo(expected);
//
//	}
//
//	@Test
//	public void should_return_Dashboard_when_header_dashboard_null() throws Exception {
//
//		when(request.getHeader(DASHBOARD_ID)).thenReturn(null);
//		serviceRequest.preHandle(request, null, null);
//
//		Flatmate flatmate = createFlatmate(1l);
//		Dashboard dashboard = flatmate.getDashboard();
//
//		when(flatmateRepository.findByEmail(anyString())).thenReturn(Optional.of(flatmate));
//		when(dashboardRepository.findByOwner(flatmate)).thenReturn(dashboard);
//
//		Dashboard expected = serviceRequest.getDashboard();
//
//		assertThat(dashboard).isEqualTo(expected);
//
//	}
//
//	@Test
//	public void should_return_Dashboard_when_header_flatmate_owner() throws Exception {
//
//		when(request.getHeader(DASHBOARD_ID)).thenReturn("1");
//		serviceRequest.preHandle(request, null, null);
//
//		Flatmate flatmate = createFlatmate(1l);
//		Dashboard dashboard = flatmate.getDashboard();
//
//		when(flatmateRepository.findByEmail(anyString())).thenReturn(Optional.of(flatmate));
//		when(dashboardRepository.findById(1l)).thenReturn(Optional.of(dashboard));
//
//		Dashboard expected = serviceRequest.getDashboard();
//
//		assertThat(dashboard).isEqualTo(expected);
//
//	}
//
//	@Test
//	public void should_return_Dashboard_when_header_flatmate_guest() throws Exception {
//
//		when(request.getHeader(DASHBOARD_ID)).thenReturn("1");
//		serviceRequest.preHandle(request, null, null);
//
//		Flatmate flatmate = createFlatmate(1l);
//		Dashboard dashboard = flatmate.getDashboard();
//
//		Flatmate guest = createFlatmate(2l);
//		dashboard.getGuests().add(guest);
//
//		when(flatmateRepository.findByEmail(anyString())).thenReturn(Optional.of(guest));
//		when(dashboardRepository.findById(1l)).thenReturn(Optional.of(dashboard));
//
//		Dashboard expected = serviceRequest.getDashboard();
//
//		assertThat(dashboard).isEqualTo(expected);
//		assertThat(dashboard.getGuests()).contains(guest);
//
//	}
//
//	@Test(expected = EntityNotFoundException.class)
//	public void should_throw_EntityNotFoundException() throws Exception {
//
//		when(request.getHeader(DASHBOARD_ID)).thenReturn("1");
//		serviceRequest.preHandle(request, null, null);
//
//		Flatmate guest = createFlatmate(2l);
//
//		when(flatmateRepository.findByEmail(anyString())).thenReturn(Optional.of(guest));
//		when(dashboardRepository.findById(1l)).thenReturn(Optional.empty());
//
//		serviceRequest.getDashboard();
//
//	}
//
//	@Test(expected = AccessDeniedException.class)
//	public void should_throw_AccessDeniedException() throws Exception {
//
//		when(request.getHeader(DASHBOARD_ID)).thenReturn("1");
//		serviceRequest.preHandle(request, null, null);
//
//		Flatmate flatmate = createFlatmate(1l);
//		Dashboard dashboard = flatmate.getDashboard();
//
//		Flatmate guest = createFlatmate(2l);
//
//		when(flatmateRepository.findByEmail(anyString())).thenReturn(Optional.of(guest));
//		when(dashboardRepository.findById(1l)).thenReturn(Optional.of(dashboard));
//
//		serviceRequest.getDashboard();
//
//	}
//
//	private Flatmate createFlatmate(Long id) {
//
//		Flatmate flatmate = new Flatmate();
//		flatmate.setId(id);
//		flatmate.setEmail("email");
//
//		Dashboard dashboard = new Dashboard();
//		flatmate.setDashboard(dashboard);
//		dashboard.setOwner(flatmate);
//
//		return flatmate;
//	}

}
