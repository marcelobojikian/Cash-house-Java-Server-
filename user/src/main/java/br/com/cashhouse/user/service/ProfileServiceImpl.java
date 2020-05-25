package br.com.cashhouse.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cashhouse.core.exception.EntityNotFoundException;
import br.com.cashhouse.core.exception.InvalidOperationException;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.repository.DashboardRepository;
import br.com.cashhouse.core.repository.PermissionRepository;
import br.com.cashhouse.core.repository.ProfileRepository;
import br.com.cashhouse.util.service.I18nService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProfileServiceImpl implements ProfileService {
	
	private static final String PROFILE_NOT_FOUND = "profile.not.found";

	@Autowired
	private I18nService i18n;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private DashboardRepository dashboardRepository; 

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private PermissionRepository permissionService;

	private Profile getProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (Profile) authentication.getPrincipal();
	}

	@Override
	public Profile findById(long id) {
		return profileRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(i18n.getMessage(PROFILE_NOT_FOUND, id)));
	}

	@Override
	@Transactional
	public Profile create(String username, String password) {

		Optional<Profile> entity = profileRepository.findByUsername(username);
		
		if(entity.isPresent()) {
			throw new InvalidOperationException(String.format("Username %s already exists", username));
		}
		
		Profile profile = entity.orElseGet(() -> {
			
			Profile newEntity = new Profile();
			
			newEntity.setUsername(username);
			newEntity.setEnabled(true);
			newEntity.setPassword(passwordEncoder.encode(password));
			newEntity.setRoles("ROLE_USER");
			
			return profileRepository.save(newEntity);
			
		});
		
		Dashboard dashboard = new Dashboard();
		dashboard.setOwner(profile);
		
		dashboard = dashboardRepository.save(dashboard);

		profile.setDashboard(dashboard);
		
		profileRepository.save(profile);
		
		permissionService.register(profile);

		log.info(String.format("Application user %s created", profile.getUsername()));

		return profile;

	}

	@Override
	public void changePassword(String password) {
		
		Profile profile = getProfile();
		
		String passwordEncoded = passwordEncoder.encode(password);
		
		profile.setPassword(passwordEncoded);
		
		profileRepository.save(profile);
		
	}

}
