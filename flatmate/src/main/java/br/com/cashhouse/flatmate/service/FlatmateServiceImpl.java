package br.com.cashhouse.flatmate.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cashhouse.core.exception.EntityNotFoundException;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.core.repository.DashboardRepository;
import br.com.cashhouse.core.repository.FlatmateRepository;
import br.com.cashhouse.core.repository.ProfileRepository;
import br.com.cashhouse.core.repository.TransactionRepository;
import br.com.cashhouse.util.service.I18nService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FlatmateServiceImpl implements FlatmateService {

	private static final String PROFILE_NOT_FOUND = "profile.not.found";
	private static final String FLATMATE_NOT_FOUND = "flatmate.not.found";
	private static final String DASHBOARD_NOT_FOUND = "dashboard.not.found";

	@Autowired
	private I18nService i18n;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private FlatmatePermission permissionService;

	@Autowired
	private FlatmateRepository flatmateRepository;

	@Autowired
	private DashboardRepository dashboardRepository;

	@Autowired
	private TransactionRepository transactionRepository;
	
	public Profile getProfileLogged() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (Profile) authentication.getPrincipal();
	}

	@Override
	public List<Flatmate> findAll(Dashboard dashboard) {
		return flatmateRepository.findByDashboard(dashboard);
	}

	@Override
	public Flatmate findById(long id) {
		return flatmateRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(FLATMATE_NOT_FOUND, id)));
	}

	@Override
	@Transactional
	public Flatmate create(Dashboard dashboard, String nickname) {
		
		Long dashboardId = dashboard.getId();

		dashboard = dashboardRepository.findById(dashboardId).orElseThrow(
				() -> new EntityNotFoundException(i18n.getMessage(DASHBOARD_NOT_FOUND, dashboardId)));
		
		Flatmate flatmate = new Flatmate(nickname);
		dashboard.getFlatmates().add(flatmate);

		log.info(String.format("Creating Flatmate %s", nickname));

		flatmate = flatmateRepository.save(flatmate);
		
		permissionService.create(flatmate.getId(), dashboard);
		
		return flatmate;
		
	}

	@Override
	public Flatmate update(long id, Flatmate flatmate) {

		Flatmate entity = flatmateRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(FLATMATE_NOT_FOUND, id)));

		entity.setNickname(flatmate.getNickname());

		log.info(String.format("Update Flatmate %s", entity.getNickname()));

		return flatmateRepository.save(entity);

	}

	@Override
	@Transactional
	public void delete(Dashboard dashboard, long id) {
		
		Long dashboardId = dashboard.getId();

		dashboard = dashboardRepository.findById(dashboardId).orElseThrow(
				() -> new EntityNotFoundException(i18n.getMessage(DASHBOARD_NOT_FOUND, dashboardId)));

		Flatmate flatmate = flatmateRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(FLATMATE_NOT_FOUND, id)));

		List<Transaction> transactions = transactionRepository.findByDashboardAndFlatmateRef(dashboard, flatmate);

		log.info(String.format("Deleting Flatmate %s to Dashboard %s ", flatmate.getNickname(), dashboard.getId()));

		dashboard.getFlatmates().remove(flatmate);
		dashboard.getTransactions().removeAll(transactions);
		
		if(transactions.isEmpty()) {
			log.info("Deleting without transactions ...");
			flatmateRepository.delete(flatmate);
			permissionService.delete(id);
		} else {
			log.info(String.format("Deleting with %s transactions ...", transactions.size()));
			transactionRepository.deleteAll(transactions);
			flatmateRepository.delete(flatmate);
			permissionService.deleteWithTransactions(id, transactions);
		}

		dashboardRepository.save(dashboard);

		log.info("Delete sucess");

	}

	@Override
	@Transactional
	public void updatePermission(Long id, String username, Map<Integer, Boolean> permissions) {
		
		Profile logged = getProfileLogged();
		Dashboard dashboard = logged.getDashboard();
		
		Long dashboardId = dashboard.getId();

		dashboard = dashboardRepository.findById(dashboardId).orElseThrow(
				() -> new EntityNotFoundException(i18n.getMessage(DASHBOARD_NOT_FOUND, dashboardId)));
		
		Profile profile = profileRepository.findByUsername(username).orElseThrow(
				() -> new EntityNotFoundException(PROFILE_NOT_FOUND, username));
		
		if(!dashboard.isGuest(profile)) {
			throw new EntityNotFoundException(PROFILE_NOT_FOUND, username);
		}
		
		permissionService.update(id, profile, permissions);
		
	}

	@Override
	@Transactional
	public Acl findPermission(Long id) {
		
		Profile logged = getProfileLogged();
		Dashboard dashboard = logged.getDashboard();
		
		Long dashboardId = dashboard.getId();

		dashboard = dashboardRepository.findById(dashboardId).orElseThrow(
				() -> new EntityNotFoundException(i18n.getMessage(DASHBOARD_NOT_FOUND, dashboardId)));

		Flatmate flatmate = flatmateRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(FLATMATE_NOT_FOUND, id)));
		
		if(!dashboard.getFlatmates().contains(flatmate)) {
			throw new EntityNotFoundException(FLATMATE_NOT_FOUND, id);
		}
		
		return permissionService.getAccessControl(id);
	}

}
