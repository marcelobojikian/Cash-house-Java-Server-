package br.com.cashhouse.util.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.cashhouse.core.exception.EntityNotFoundException;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.repository.DashboardRepository;
import br.com.cashhouse.core.repository.ProfileRepository;

@Component
@SessionAttributes("dashboard")
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WebSessionImpl implements WebSession {
	
	private Dashboard dashboard;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private DashboardRepository dashboardRepository;

	@Override
	public Profile getProfileLogged() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (Profile) authentication.getPrincipal();
	}
	
	@Override
	@Transactional
	public void changeDashboard(Long id) {
		
		if (id == null) {
			Profile profile = getProfileLogged();
			id = profile.getDashboard().getId();
		}

		dashboard = dashboardRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Dashboard not found"));
		
	}

	@Override
	@Transactional
	public Dashboard getDashboard() {
		if(dashboard == null) {
			changeDashboard(null);
		}
		return dashboard;
	}

	@Override
	public List<Dashboard> getInvitations() {
    	return dashboardRepository.findAll();
	}

	@Override
	public List<Profile> getGuests() {
		Dashboard current = getDashboard();
		return profileRepository.findByDashboard(current);
	}

}
