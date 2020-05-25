package br.com.cashhouse.transaction.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import br.com.cashhouse.core.exception.EntityNotFoundException;
import br.com.cashhouse.core.exception.InvalidOperationException;
import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.core.model.Transaction.Action;
import br.com.cashhouse.core.model.Transaction.Status;
import br.com.cashhouse.core.repository.CashierRepository;
import br.com.cashhouse.core.repository.DashboardRepository;
import br.com.cashhouse.core.repository.FlatmateRepository;
import br.com.cashhouse.core.repository.ProfileRepository;
import br.com.cashhouse.core.repository.TransactionRepository;
import br.com.cashhouse.transaction.dto.CreateTransaction;
import br.com.cashhouse.util.service.I18nService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
	
	private static final String FORMAT_LOG_INVALID_OPERATION = "Invalid operation, Transaction %s with status equal to %s";

	private static final String PROFILE_NOT_FOUND = "profile.not.found";
	private static final String DASHBOARD_NOT_FOUND = "dashboard.not.found";
	private static final String CASHIER_NOT_FOUND = "cashier.not.found";
	private static final String FLATMATE_NOT_FOUND = "flatmate.not.found";
	private static final String TRANSACTION_NOT_FOUND = "transaction.not.found";
	private static final String TRANSACTION_STATUS_INVALID_OPERATION = "transaction.status.invalid.operation";

	@Autowired
	private I18nService i18n;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private TransactionPermission permissionService;

	@Autowired
	private CashierRepository cashierRepository;

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
	public Transaction findById(Long id) {
		return transactionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(TRANSACTION_NOT_FOUND, id)));
	}

	@Override
	public List<Transaction> findAll(Dashboard dashboard) {
		return transactionRepository.findByDashboard(dashboard);
	}

	@Override
	@Transactional
	public Page<Transaction> findAll(Dashboard dashboard, Predicate parameters, Pageable pageable) {
		List<Flatmate> flatmates = flatmateRepository.findMyFlatmates(dashboard);
		return transactionRepository.findAll(flatmates, parameters, pageable);
	}

	@Override
	@Transactional
	public Transaction createDeposit(Dashboard dashboard, CreateTransaction createTransaction) {
		return create(dashboard, createTransaction, Action.DEPOSIT);
	}

	@Override
	@Transactional
	public Transaction createwithdraw(Dashboard dashboard, CreateTransaction createTransaction) {
		return create(dashboard, createTransaction, Action.WITHDRAW);
	}

	private Transaction create(Dashboard dashboard, CreateTransaction createTransaction, Action action) {
		
		Long dashboardId = dashboard.getId();

		dashboard = dashboardRepository.findById(dashboardId).orElseThrow(
				() -> new EntityNotFoundException(i18n.getMessage(DASHBOARD_NOT_FOUND, dashboardId)));
		
		Long cashierId = createTransaction.getCashier();
		Long flatmateId = createTransaction.getFlatmate();
		BigDecimal value = createTransaction.getValue();
		
		Cashier cashier = cashierRepository.findById(cashierId)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(CASHIER_NOT_FOUND, cashierId)));
		
		Flatmate flatmate = flatmateRepository.findMyFlatmate(flatmateId)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(FLATMATE_NOT_FOUND, flatmateId)));

		Transaction transaction = new Transaction();

		transaction.setAction(action);
		transaction.setStatus(Status.SENDED);
		transaction.setCashier(cashier);
		transaction.setFlatmate(flatmate);
		transaction.setValue(value);

		dashboard.getTransactions().add(transaction);

		transaction = transactionRepository.save(transaction);
		
		permissionService.create(transaction, dashboard);
		
		return transaction;

	}

	@Override
	@Transactional
	public void delete(Dashboard dashboard, Long id) {
		
		Long dashboardId = dashboard.getId();

		dashboard = dashboardRepository.findById(dashboardId).orElseThrow(
				() -> new EntityNotFoundException(i18n.getMessage(DASHBOARD_NOT_FOUND, dashboardId)));

		Transaction entity = transactionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(TRANSACTION_NOT_FOUND, id)));

		dashboard.getTransactions().remove(entity);
		
		permissionService.delete(entity.getId());
		
		transactionRepository.delete(entity);
		
		dashboardRepository.save(dashboard);

	}

	@Override
	@Transactional
	public synchronized Transaction finish(Long id) {

		Transaction entity = transactionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(TRANSACTION_NOT_FOUND, id)));
		
		Cashier cashier = entity.getCashier();
		Long cashierId = cashier.getId();

		if (entity.isSended()) {
			
			cashier = cashierRepository.findById(cashierId)
					.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(CASHIER_NOT_FOUND, cashierId)));
			
			log.info(String.format("Action %s in a Cashier %s current balance %s", entity.getAction(), cashierId,
					cashier.getBalance()));
			
			BigDecimal value = entity.getValue();

			if (entity.getAction().equals(Action.WITHDRAW)) {
				cashier.withdraw(value);
			} else {
				cashier.deposit(value);
			}

			log.info(String.format("Apply %s. Changed balance to %s", value, cashier.getBalance()));

			cashierRepository.save(cashier);

			entity.setStatus(Status.FINISHED);

			Dashboard dashboard = dashboardRepository.findByTransaction(entity).orElseThrow(
					() -> new EntityNotFoundException(i18n.getMessage(DASHBOARD_NOT_FOUND)));

			permissionService.finish(entity.getId(), dashboard);

			return transactionRepository.save(entity);
		} else {
			log.info(String.format(String.format(FORMAT_LOG_INVALID_OPERATION, id, entity.getStatus())));
			throw new InvalidOperationException(i18n.getMessage(TRANSACTION_STATUS_INVALID_OPERATION, id, entity.getStatus()));
		}

	}

	@Override
	public List<Cashier> findAllCashier(Dashboard dashboard) {
		return cashierRepository.findByDashboard(dashboard);
	}

	@Override
	public List<Flatmate> findAllFlatmate(Dashboard dashboard) {
		return flatmateRepository.findByDashboard(dashboard);
	}

	@Override
	public List<Flatmate> findMyFlatmate(Dashboard dashboard) {
		return flatmateRepository.findMyFlatmates(dashboard);
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

		Transaction transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(TRANSACTION_NOT_FOUND, id)));
		
		if(!dashboard.getTransactions().contains(transaction)) {
			throw new EntityNotFoundException(FLATMATE_NOT_FOUND, id);
		}
		
		return permissionService.getAccessControl(id);
	}

}
