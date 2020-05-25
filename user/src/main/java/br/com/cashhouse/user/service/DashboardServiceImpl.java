package br.com.cashhouse.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.Acl;
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
import br.com.cashhouse.core.repository.CashierRepository;
import br.com.cashhouse.core.repository.FlatmateRepository;
import br.com.cashhouse.core.repository.PermissionRepository;
import br.com.cashhouse.core.repository.ProfileRepository;
import br.com.cashhouse.core.repository.TransactionRepository;
import br.com.cashhouse.util.service.I18nService;

@Service
public class DashboardServiceImpl implements DashboardService {
	
	private static final String PROFILE_NOT_FOUND = "profile.not.found";

	@Autowired
	private I18nService i18n;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private FlatmateRepository flatmateRepository;

	@Autowired
	private CashierRepository cashierRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	private Profile getProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (Profile) authentication.getPrincipal();
	}

	private Dashboard getDashboard() {
		Profile profile = getProfile();
		return profile.getDashboard();
	}
	
	@Override
	public List<Profile> getGuests() {
		Dashboard dashboard = getDashboard();
		return profileRepository.findByDashboard(dashboard);
	}

	@Override
	public List<Flatmate> getFlatmates() {
		Dashboard dashboard = getDashboard();
		return flatmateRepository.findByDashboard(dashboard);
	}

	@Override
	public List<Cashier> getCashiers() {
		Dashboard dashboard = getDashboard();
		return cashierRepository.findByDashboard(dashboard);
	}

	@Override
	public List<Transaction> getTransactions() {
		Dashboard dashboard = getDashboard();
		return transactionRepository.findByDashboard(dashboard);
	}

	@Override
	@Transactional
	public void updatePermission(String username, Map<Integer, Boolean> permissions) {
		
		Profile logged = getProfile();
		Dashboard dashboard = logged.getDashboard();
		
		Profile profile = profileRepository.findByUsername(username).orElseThrow(
				() -> new EntityNotFoundException(i18n.getMessage(PROFILE_NOT_FOUND, username)));
		
		permissionRepository.update(Dashboard.class, dashboard.getId(), profile, permissions);
		
	}

	@Override
	@Transactional
	public Acl findPermission() {
		
		Profile logged = getProfile();
		Dashboard dashboard = logged.getDashboard();
		
		return permissionRepository.find(Dashboard.class, dashboard.getId());
	}

}
