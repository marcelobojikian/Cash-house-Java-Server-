package br.com.cashhouse.user.service;

import static br.com.cashhouse.test.util.factory.EntityFactory.createProfile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.cashhouse.core.exception.EntityNotFoundException;
import br.com.cashhouse.core.exception.InvalidOperationException;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.repository.DashboardRepository;
import br.com.cashhouse.core.repository.PermissionRepository;
import br.com.cashhouse.core.repository.ProfileRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceImplTest {

	@InjectMocks
	private ProfileServiceImpl profileService;

	@Mock
	private DashboardRepository dashboardRepository; 

	@Mock
	private ProfileRepository profileRepository;

	@Mock
	private PermissionRepository permissionService;
	
	@Mock
	private PasswordEncoder passwordEncoder;

	@Before
	public void setup() {

		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);

		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		Profile profile = createProfile(1l, "marcelo@mail.com", "ROLE_USER");
		
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(profile);

	}

	@Test
	public void whenFindById_thenReturnProfileObject() throws Exception {
		
		Profile profile = createProfile(1l, "marcelo@mail.com", "ROLE_USER");
		
		when(profileRepository.findById(anyLong())).thenReturn(Optional.of(profile));

		Profile result = profileService.findById(1);

		assertThat(result.getId()).isEqualTo(1);
		assertThat(result.getUsername()).isEqualTo("marcelo@mail.com");
		
	}

	@Test(expected = EntityNotFoundException.class)
	public void whenFindById_thenThrowEntityNotFoundException() throws Exception {

		when(profileRepository.findById(anyLong())).thenReturn(Optional.empty());

		profileService.findById(1);

	}

	@Test
	public void whenCreate_thenReturnProfileObject() throws Exception {
		
		Profile profile = createProfile(1l, "new@mail.com", "ROLE_USER");

		when(profileRepository.findByUsername(anyString())).thenReturn(Optional.empty());
		when(dashboardRepository.save(any(Dashboard.class))).thenReturn(profile.getDashboard());
		when(profileRepository.save(any(Profile.class))).thenReturn(profile);
		doNothing().when(permissionService).register(any(Profile.class));
		
		Profile result = profileService.create("new@mail.com", "test");

		assertThat(result.getId()).isEqualTo(1);
		assertThat(result.getUsername()).isEqualTo("new@mail.com");
		
	}

	@Test(expected = InvalidOperationException.class)
	public void whenCreate_thenThrowInvalidOperationException() throws Exception {
		
		Profile profile = createProfile(1l, "marcelo@mail.com", "ROLE_USER");

		when(profileRepository.findByUsername(anyString())).thenReturn(Optional.of(profile));
		
		profileService.create("", "");
		
	}

	@Test
	public void whenChangePassword_thenReturnVoidObject() throws Exception {
		
		spy(profileRepository);
		
		profileService.changePassword("test");
		
		verify(profileRepository).save(any(Profile.class));
	}
	

}
