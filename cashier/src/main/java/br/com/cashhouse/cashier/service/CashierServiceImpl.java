package br.com.cashhouse.cashier.service;

import java.math.BigDecimal;
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
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.core.repository.CashierRepository;
import br.com.cashhouse.core.repository.DashboardRepository;
import br.com.cashhouse.core.repository.ProfileRepository;
import br.com.cashhouse.core.repository.TransactionRepository;
import br.com.cashhouse.util.service.I18nService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CashierServiceImpl implements CashierService {

	private static final String PROFILE_NOT_FOUND = "profile.not.found";
	private static final String CASHIER_NOT_FOUND = "cashier.not.found";
	private static final String DASHBOARD_NOT_FOUND = "dashboard.not.found";

	@Autowired
	private I18nService i18n;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private CashierPermission permissionService;

	@Autowired
	private CashierRepository cashierRepository;

	@Autowired
	private DashboardRepository dashboardRepository;

	@Autowired
	private TransactionRepository transactionRepository;
	
	public Profile getProfileLogged() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (Profile) authentication.getPrincipal();
	}

	@Override
	public List<Cashier> findAll(Dashboard dashboard) {
		return cashierRepository.findByDashboard(dashboard);
	}

	@Override
	public Cashier findById(long id) {
		return cashierRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(CASHIER_NOT_FOUND, id)));
	}

	@Override
	@Transactional
	public Cashier create(Dashboard dashboard, String name, BigDecimal started, BigDecimal balance) {
		
		Long dashboardId = dashboard.getId();

		dashboard = dashboardRepository.findById(dashboardId).orElseThrow(
				() -> new EntityNotFoundException(i18n.getMessage(DASHBOARD_NOT_FOUND, dashboardId)));

		Cashier cashier = new Cashier(name, started, balance);
		dashboard.getCashiers().add(cashier);

		log.info(String.format("Creating Cashier %s", name));

		cashier = cashierRepository.save(cashier);

		permissionService.create(cashier.getId(), dashboard);

		return cashier;
		
	}

	@Override
	public Cashier update(long id, Cashier cashier) {

		Cashier entity = cashierRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(CASHIER_NOT_FOUND, id)));

		log.info(String.format("Cashier %s changing ... ", entity.getName()));
		log.info(String.format("Name[%s], Started[%s], Balance[%s]", cashier.getName(), cashier.getStarted(),
				cashier.getBalance()));

		entity.setName(cashier.getName());
		entity.setStarted(cashier.getStarted());
		entity.setBalance(cashier.getBalance());

		return cashierRepository.save(entity);
	}

	@Override
	@Transactional
	public void delete(Dashboard dashboard, long id) {
		
		Long dashboardId = dashboard.getId();

		dashboard = dashboardRepository.findById(dashboardId).orElseThrow(
				() -> new EntityNotFoundException(i18n.getMessage(DASHBOARD_NOT_FOUND, dashboardId)));

		Cashier cashier = cashierRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(CASHIER_NOT_FOUND, id)));

		log.info(String.format("Deleting Cashier %s", cashier.getName()));

		List<Transaction> transactions = transactionRepository.findByDashboardAndCashier(dashboard, cashier);

		dashboard.getCashiers().remove(cashier);
		dashboard.getTransactions().removeAll(transactions);
		
		if(transactions.isEmpty()) {
			log.info("Deleting without transactions ...");
			cashierRepository.delete(cashier);
			permissionService.delete(id);
		} else {
			log.info(String.format("Deleting with %s transactions ...", transactions.size()));
			transactionRepository.deleteAll(transactions);
			cashierRepository.delete(cashier);
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

		Cashier cashier = cashierRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(CASHIER_NOT_FOUND, id)));
		
		if(!dashboard.getCashiers().contains(cashier)) {
			throw new EntityNotFoundException(CASHIER_NOT_FOUND, id);
		}
		
		return permissionService.getAccessControl(id);
	}

}
