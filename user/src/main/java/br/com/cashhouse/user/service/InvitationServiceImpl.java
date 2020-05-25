package br.com.cashhouse.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cashhouse.core.exception.EntityNotFoundException;
import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.core.repository.DashboardRepository;
import br.com.cashhouse.core.repository.PermissionRepository;
import br.com.cashhouse.core.repository.ProfileRepository;
import br.com.cashhouse.util.service.I18nService;

@Service
public class InvitationServiceImpl implements InvitationService {

	private static final String PROFILE_NOT_FOUND = "profile.not.found";
	private static final String DASHBOARD_NOT_FOUND = "dashboard.not.found";

	@Autowired
	private I18nService i18n;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private DashboardRepository dashboardRepository;
	
	private Profile getProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (Profile) authentication.getPrincipal();
	}

	private Dashboard getDashboard() {
		Profile profile = getProfile();
		return profile.getDashboard();
	}

	@Override
	public List<Dashboard> getInvitations() {
		Profile profile = getProfile();
		return dashboardRepository.findInvitations(profile);
	}
	
	@Override
	@Transactional
	public void add(Long id, String username, String type) {

		Dashboard dashboard = dashboardRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(i18n.getMessage(DASHBOARD_NOT_FOUND, id)));
		
		Profile profileGuest = profileRepository.findByUsername(username).orElseThrow(
				() -> new EntityNotFoundException(i18n.getMessage(PROFILE_NOT_FOUND, username)));
		
		if(dashboard.isOwner(profileGuest) 
				|| dashboard.isGuest(profileGuest)) {
			return;
		}
		
		dashboard.getGuests().add(profileGuest);
		
		dashboardRepository.save(dashboard);
		
		List<Cashier> cashier = dashboard.getCashiers();
		for (Cashier entity : cashier) {
			permissionRepository.add(Cashier.class, entity.getId(), profileGuest);
		}
		
		List<Flatmate> flatmates = dashboard.getFlatmates();
		for (Flatmate entity : flatmates) {
			permissionRepository.add(Flatmate.class, entity.getId(), profileGuest);
		}

		permissionRepository.add(Dashboard.class, id, profileGuest);
		
		Map<Integer, Boolean> permissions = new HashMap<>();
		permissions.put(BasePermission.READ.getMask(), true);
		
		if(type.equalsIgnoreCase("Flatmate")) {
			permissions.put(BasePermission.WRITE.getMask(), true);
		}
		
		permissionRepository.update(Dashboard.class, id, profileGuest, permissions);
		
		// READ FLATMATE AND CASHIER by DEFAULT
		
		for (Cashier entity : cashier) {
			permissionRepository.update(Cashier.class, entity.getId(), profileGuest, permissions);
		}
		
		for (Flatmate entity : flatmates) {
			permissionRepository.update(Flatmate.class, entity.getId(), profileGuest, permissions);
		}
		
	}

	@Override
	@Transactional
	public void remove(Long id, String username) {

		Dashboard dashboard = dashboardRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(i18n.getMessage(DASHBOARD_NOT_FOUND, id)));
		
		Profile profileGuest = profileRepository.findByUsername(username).orElseThrow(
				() -> new EntityNotFoundException(i18n.getMessage(PROFILE_NOT_FOUND, username)));
		
		if(!dashboard.isGuest(profileGuest)) {
			throw new EntityNotFoundException(i18n.getMessage(PROFILE_NOT_FOUND, username));
		}
		
		List<Profile> profiles = dashboard.getGuests();
		profiles.remove(profileGuest);
		
		dashboardRepository.save(dashboard);

		List<Cashier> cashiers = dashboard.getCashiers(); 
		if(!cashiers.isEmpty()) {
			for (Cashier entity : cashiers) {
				permissionRepository.remove(Cashier.class, entity.getId(), profileGuest);
			}
		}

		List<Flatmate> flatmates = dashboard.getFlatmates(); 
		if(!flatmates.isEmpty()) {
			for (Flatmate entity : flatmates) {
				permissionRepository.remove(Flatmate.class, entity.getId(), profileGuest);
			}
		}

		List<Transaction> trnasactions = dashboard.getTransactions(); 
		if(!trnasactions.isEmpty()) {
			for (Transaction entity : trnasactions) {
				permissionRepository.remove(Transaction.class, entity.getId(), profileGuest);
			}
		}
		
		permissionRepository.remove(Dashboard.class, dashboard.getId(), profileGuest);
		
	}

	@Override
	@Transactional
	public void update(String username, String type) {
		
		Dashboard dashboard = getDashboard();
		Long id = dashboard.getId();
		
		Profile profileGuest = profileRepository.findByUsername(username).orElseThrow(
				() -> new EntityNotFoundException(i18n.getMessage(PROFILE_NOT_FOUND, username)));
		
		Map<Integer, Boolean> permissions = new HashMap<>();
		permissions.put(BasePermission.READ.getMask(), true);
		
		if(type.equalsIgnoreCase("flatmate")) {
			permissions.put(BasePermission.WRITE.getMask(), true);
		} else {
			permissions.put(BasePermission.WRITE.getMask(), false);
		}
		
		permissionRepository.update(Dashboard.class, id, profileGuest, permissions);
		
	}

}
