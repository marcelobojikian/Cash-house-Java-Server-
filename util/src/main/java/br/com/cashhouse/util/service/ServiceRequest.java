package br.com.cashhouse.util.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.repository.DashboardRepository;
import br.com.cashhouse.core.repository.FlatmateRepository;
import lombok.NoArgsConstructor;
import br.com.cashhouse.core.exception.EntityNotFoundException;

@NoArgsConstructor
@Service
public class ServiceRequest extends HandlerInterceptorAdapter {

	private static final String DASHBOARD_ID = "dashboard";
	private Long dashboardId;

	@Autowired
	private FlatmateRepository flatmateRepository;

	@Autowired
	private DashboardRepository dashboardRepository;

	public ServiceRequest(FlatmateRepository flatmateRepository, DashboardRepository dashboardRepository) {
		this.flatmateRepository = flatmateRepository;
		this.dashboardRepository = dashboardRepository;
	}

	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public Flatmate getFlatmateLogged() {
		String email = getAuthentication().getName();
		return flatmateRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Flatmate not found"));
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String headerId = request.getHeader(DASHBOARD_ID);

		if (headerId == null || !headerId.matches("([0-9])")) {
			dashboardId = null;
		} else {
			dashboardId = Long.parseLong(headerId);
		}

		return true;

	}

	public Dashboard getDashboard() {

		Flatmate flatmateLogged = getFlatmateLogged();

		if (dashboardId == null) {

			Dashboard dashboardLogged = dashboardRepository.findByOwner(flatmateLogged);

			if (dashboardLogged == null) {
				return createDashboard(flatmateLogged);
			}

			return dashboardLogged;

		} else {

			Dashboard dashboardRequested = dashboardRepository.findById(dashboardId)
					.orElseThrow(() -> new EntityNotFoundException("Dashboard not found"));

			if (dashboardRequested.isOwner(flatmateLogged) || dashboardRequested.isGuest(flatmateLogged)) {

				return dashboardRequested;

			} else {
				throw new AccessDeniedException("flatmate.access.denied" + flatmateLogged.getNickname());
			}

		}

	}

	public synchronized Dashboard createDashboard(Flatmate flatmate) {
		Dashboard dashboard = new Dashboard();
		dashboard.setOwner(flatmate);
		return dashboardRepository.save(dashboard);
	}

}
