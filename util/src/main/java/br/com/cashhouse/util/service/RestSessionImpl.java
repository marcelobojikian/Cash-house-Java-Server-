package br.com.cashhouse.util.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cashhouse.core.exception.EntityNotFoundException;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.repository.DashboardRepository;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class RestSessionImpl implements RestSession {

	@Autowired
	private DashboardRepository dashboardRepository;

	@Override
	public Profile getProfileLogged() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (Profile) authentication.getPrincipal();
	}

	@Override
	@Transactional
	public Dashboard getDashboard(String headerId) {
		
		Long dashboardId = null;

		if (headerId != null && headerId.matches("([0-9])")) {
			dashboardId = Long.parseLong(headerId);
		}

		if (dashboardId == null) {
			Profile profileLogged = getProfileLogged();
			dashboardId = profileLogged.getDashboard().getId();
		}

		return dashboardRepository.findById(dashboardId)
				.orElseThrow(() -> new EntityNotFoundException("Dashboard not found"));

	}

}
