package br.com.cashhouse.transaction.rest.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import br.com.cashhouse.core.exception.EntityNotFoundException;
import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.core.model.Transaction.Action;
import br.com.cashhouse.core.model.Transaction.Status;
import br.com.cashhouse.core.repository.CashierRepository;
import br.com.cashhouse.core.repository.DashboardRepository;
import br.com.cashhouse.core.repository.FlatmateRepository;
import br.com.cashhouse.core.repository.TransactionRepository;
import br.com.cashhouse.transaction.rest.dto.UpdateTransaction;
import br.com.cashhouse.core.exception.InvalidOperationException;
import br.com.cashhouse.util.service.I18nService;
import br.com.cashhouse.util.service.ServiceRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
	
	private static final String FORMAT_LOG_INVALID_OPERATION = "Invalid operation, Transaction %s with status equal to %s";

	private static final String CASHIER_NOT_FOUND = "cashier.not.found";
	private static final String FLATMATE_NOT_FOUND = "flatmate.not.found";
	private static final String TRANSACTION_NOT_FOUND = "transaction.not.found";
	private static final String TRANSACTION_STATUS_INVALID_OPERATION = "transaction.status.invalid.operation";
	private static final String FLATMATE_ACCESS_DENIED = "flatmate.access.denied";
	private static final String FLATMATE_ACCESS_FIELD_DENIED = "flatmate.access.field.denied";
	private static final String FLATMATE_ASSIGNED_NOT_FOUND = "flatmate.assigned.not.found";
	private static final String FLATMATE_CREATEDBY_NOT_FOUND = "flatmate.createBy.not.found";

	@Autowired
	private I18nService i18n;

	@Autowired
	private ServiceRequest serviceRequest;

	@Autowired
	private CashierRepository cashierRepository;

	@Autowired
	private FlatmateRepository flatmateRepository;

	@Autowired
	private DashboardRepository dashboardRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Cashier findCashierById(Long id) {
		return cashierRepository.findByDashboardAndId(serviceRequest.getDashboard(), id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(CASHIER_NOT_FOUND, id)));
	}

	@Override
	public Flatmate findFlatmateById(Long id) {

		Dashboard dashboard = serviceRequest.getDashboard();

		if (dashboard.isOwner(id)) {
			return dashboard.getOwner();
		}

		return flatmateRepository.findByDashboardAndId(dashboard, id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(FLATMATE_NOT_FOUND, id)));

	}

	@Override
	public Transaction findById(Long id) {
		Dashboard dashboard = serviceRequest.getDashboard();
		return transactionRepository.findByDashboardAndId(dashboard, id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(TRANSACTION_NOT_FOUND, id)));
	}

	@Override
	public List<Transaction> findAll() {
		Dashboard dashboard = serviceRequest.getDashboard();
		return dashboard.getTransactions();
	}

	@Override
	public Page<Transaction> findAll(Predicate parameters, Pageable pageable) {
		Dashboard dashboard = serviceRequest.getDashboard();
		Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();
		return transactionRepository.findAll(flatmateLogged, dashboard, parameters, pageable);
	}

	@Override
	public Transaction createDeposit(Cashier cashier, BigDecimal value) {

		Dashboard dashboard = serviceRequest.getDashboard();
		Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();

		Transaction transaction = new Transaction();

		transaction.setAction(Transaction.Action.DEPOSIT);
		transaction.setStatus(Status.CREATED);
		transaction.setAssigned(flatmateLogged);
		transaction.setCashier(cashier);
		transaction.setCreateBy(flatmateLogged);
		transaction.setValue(value);

		dashboard.getTransactions().add(transaction);

		return transactionRepository.save(transaction);
	}

	@Override
	public Transaction createDeposit(Cashier cashier, Flatmate flatmateAssign, BigDecimal value) {

		Dashboard dashboard = serviceRequest.getDashboard();
		Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();

		Transaction transaction = new Transaction();

		transaction.setAction(Transaction.Action.DEPOSIT);
		transaction.setStatus(Status.CREATED);
		transaction.setAssigned(flatmateAssign);
		transaction.setCashier(cashier);
		transaction.setCreateBy(flatmateLogged);
		transaction.setValue(value);

		dashboard.getTransactions().add(transaction);

		return transactionRepository.save(transaction);
	}

	@Override
	public Transaction createwithdraw(Cashier cashier, BigDecimal value) {

		Dashboard dashboard = serviceRequest.getDashboard();
		Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();

		Transaction transaction = new Transaction();

		transaction.setAction(Transaction.Action.WITHDRAW);
		transaction.setStatus(Status.CREATED);
		transaction.setAssigned(flatmateLogged);
		transaction.setCashier(cashier);
		transaction.setCreateBy(flatmateLogged);
		transaction.setValue(value);

		dashboard.getTransactions().add(transaction);

		return transactionRepository.save(transaction);
	}

	@Override
	public Transaction createwithdraw(Cashier cashier, Flatmate flatmateAssign, BigDecimal value) {

		Dashboard dashboard = serviceRequest.getDashboard();
		Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();

		Transaction transaction = new Transaction();

		transaction.setAction(Transaction.Action.WITHDRAW);
		transaction.setStatus(Status.CREATED);
		transaction.setAssigned(flatmateAssign);
		transaction.setCashier(cashier);
		transaction.setCreateBy(flatmateLogged);
		transaction.setValue(value);

		dashboard.getTransactions().add(transaction);

		return transactionRepository.save(transaction);
	}

	@Override
	public Transaction update(Long id, Transaction newTransaction) {

		Dashboard dashboard = serviceRequest.getDashboard();

		Transaction entity = transactionRepository.findByDashboardAndId(dashboard, id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(TRANSACTION_NOT_FOUND, id)));

		Long cashierId = newTransaction.getCashier().getId();
		Long assignedId = newTransaction.getAssigned().getId();
		Long createById = newTransaction.getCreateBy().getId();

		if (dashboard.isOwner(assignedId)) {
			entity.setAssigned(dashboard.getOwner());
		} else {
			Flatmate assigned = flatmateRepository.findByDashboardAndId(dashboard, assignedId).orElseThrow(
					() -> new EntityNotFoundException(i18n.getMessage(FLATMATE_ASSIGNED_NOT_FOUND, assignedId)));
			entity.setAssigned(assigned);
		}

		if (dashboard.isOwner(createById)) {
			entity.setCreateBy(dashboard.getOwner());
		} else {
			Flatmate createBy = flatmateRepository.findByDashboardAndId(dashboard, createById).orElseThrow(
					() -> new EntityNotFoundException(i18n.getMessage(FLATMATE_CREATEDBY_NOT_FOUND, createById)));
			entity.setCreateBy(createBy);
		}

		Cashier cashier = cashierRepository.findByDashboardAndId(dashboard, cashierId)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(CASHIER_NOT_FOUND, cashierId)));
		entity.setCashier(cashier);

		entity.setAction(newTransaction.getAction());
		entity.setStatus(newTransaction.getStatus());
		entity.setValue(newTransaction.getValue());

		return transactionRepository.save(entity);

	}

	@Override
	public Transaction update(Long id, UpdateTransaction content) {

		Dashboard dashboard = serviceRequest.getDashboard();

		Transaction entity = transactionRepository.findByDashboardAndId(dashboard, id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(TRANSACTION_NOT_FOUND, id)));

		if (!content.haveChanges()) {
			return entity;
		}

		if (!entity.isAvailableToChange()) {
			log.info(String.format(String.format(FORMAT_LOG_INVALID_OPERATION,
					entity.getId(), entity.getStatus())));
			throw new InvalidOperationException(
					i18n.getMessage(TRANSACTION_STATUS_INVALID_OPERATION, entity.getId(), entity.getStatus()));
		}

		if (content.haveValue()) {
			entity.setValue(content.getValue());
		}

		if (content.haveCashier()) {
			Cashier cashier = cashierRepository.findByDashboardAndId(dashboard, content.getCashier()).orElseThrow(
					() -> new EntityNotFoundException(i18n.getMessage(CASHIER_NOT_FOUND, content.getCashier())));
			entity.setCashier(cashier);
		}

		if (content.haveAssigned()) {
			Long idAssigned = content.getAssigned();
			Flatmate flatmateAssigned = flatmateRepository.findByDashboardAndId(dashboard, idAssigned)
					.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(FLATMATE_NOT_FOUND, idAssigned)));

			Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();

			if (!dashboard.isOwner(flatmateLogged)) {
				throw new AccessDeniedException(
						i18n.getMessage(FLATMATE_ACCESS_FIELD_DENIED, flatmateLogged.getNickname(), "assigned"));
			}

			if (!entity.isCreateBy(flatmateLogged) && !entity.isAssignedTo(flatmateLogged)) {
				throw new AccessDeniedException(i18n.getMessage(FLATMATE_ACCESS_DENIED, flatmateLogged.getNickname()));
			}

			entity.setAssigned(flatmateAssigned);
		}

		return transactionRepository.save(entity);
	}

	@Override
	public Transaction updateValue(Long id, BigDecimal value) {

		Dashboard dashboard = serviceRequest.getDashboard();
		Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();

		Transaction entity = transactionRepository.findByDashboardAndId(dashboard, id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(TRANSACTION_NOT_FOUND, id)));

		if (!entity.isAvailableToChange()) {
			log.info(String.format(String.format(FORMAT_LOG_INVALID_OPERATION,
					entity.getId(), entity.getStatus())));
			throw new InvalidOperationException(
					i18n.getMessage(TRANSACTION_STATUS_INVALID_OPERATION, entity.getId(), entity.getStatus()));
		} else if (!entity.isCreateBy(flatmateLogged) && !entity.isAssignedTo(flatmateLogged)) {
			throw new org.springframework.security.access.AccessDeniedException(
					i18n.getMessage(FLATMATE_ACCESS_DENIED, flatmateLogged.getNickname()));
		}

		entity.setValue(value);

		return transactionRepository.save(entity);

	}

	@Override
	public Transaction updateCashier(Long id, Cashier cashier) {

		Dashboard dashboard = serviceRequest.getDashboard();
		Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();

		Transaction entity = transactionRepository.findByDashboardAndId(dashboard, id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(TRANSACTION_NOT_FOUND, id)));

		if (!entity.isAvailableToChange()) {
			log.info(String.format(String.format(FORMAT_LOG_INVALID_OPERATION,
					entity.getId(), entity.getStatus())));
			throw new InvalidOperationException(
					i18n.getMessage(TRANSACTION_STATUS_INVALID_OPERATION, entity.getId(), entity.getStatus()));
		} else if (!entity.isCreateBy(flatmateLogged) && !entity.isAssignedTo(flatmateLogged)) {
			throw new org.springframework.security.access.AccessDeniedException(
					i18n.getMessage(FLATMATE_ACCESS_DENIED, flatmateLogged.getNickname()));
		}

		entity.setCashier(cashier);

		return transactionRepository.save(entity);

	}

	@Override
	public Transaction updateFlatmateAssigned(Long id, Flatmate flatmateAssigned) {

		Dashboard dashboard = serviceRequest.getDashboard();
		Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();

		Transaction entity = transactionRepository.findByDashboardAndId(dashboard, id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(TRANSACTION_NOT_FOUND, id)));

		if (!entity.isAvailableToChange()) {
			log.info(String.format(String.format(FORMAT_LOG_INVALID_OPERATION,
					entity.getId(), entity.getStatus())));
			throw new InvalidOperationException(
					i18n.getMessage(TRANSACTION_STATUS_INVALID_OPERATION, entity.getId(), entity.getStatus()));
		} else if (!entity.isCreateBy(flatmateLogged) && !entity.isAssignedTo(flatmateLogged)) {
			throw new org.springframework.security.access.AccessDeniedException(
					i18n.getMessage(FLATMATE_ACCESS_DENIED, flatmateLogged.getNickname()));
		}

		entity.setAssigned(flatmateAssigned);

		return transactionRepository.save(entity);

	}

	@Override
	public void delete(Long id) {

		Dashboard dashboard = serviceRequest.getDashboard();

		Transaction entity = transactionRepository.findByDashboardAndId(dashboard, id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(TRANSACTION_NOT_FOUND, id)));

		dashboard.getTransactions().remove(entity);
		dashboardRepository.save(dashboard);

	}

	@Override
	public Collection<Transaction> findByFlatmateReferences(Flatmate createBy, Flatmate assigned) {
		Dashboard dashboard = serviceRequest.getDashboard();
		return transactionRepository.findByDashboardAndFlatmateRef(dashboard, createBy, assigned);
	}

	@Override
	public Collection<Transaction> findByCashierReferences(Cashier cashier) {
		Dashboard dashboard = serviceRequest.getDashboard();
		return transactionRepository.findByDashboardAndCashier(dashboard, cashier);
	}

	@Override
	public Transaction send(Transaction transaction) {

		Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();

		if (!transaction.isCreated()) {
			log.info(String.format(String.format(FORMAT_LOG_INVALID_OPERATION,
					transaction.getId(), transaction.getStatus())));
			throw new InvalidOperationException(i18n.getMessage(TRANSACTION_STATUS_INVALID_OPERATION,
					transaction.getId(), transaction.getStatus()));
		}

		if (transaction.isAssignedTo(flatmateLogged)) {

			transaction.setStatus(Status.SENDED);

			return transactionRepository.save(transaction);

		} else {
			throw new AccessDeniedException(i18n.getMessage(FLATMATE_ACCESS_DENIED, flatmateLogged.getNickname()));
		}

	}

	@Override
	public Transaction finish(Transaction transaction) {

		if (transaction.isSended()) {

			applyTransaction(transaction);

			transaction.setStatus(Status.FINISHED);

			return transactionRepository.save(transaction);
		} else {
			log.info(String.format(String.format(FORMAT_LOG_INVALID_OPERATION,
					transaction.getId(), transaction.getStatus())));
			throw new InvalidOperationException(i18n.getMessage(TRANSACTION_STATUS_INVALID_OPERATION,
					transaction.getId(), transaction.getStatus()));
		}

	}

	@Override
	public Transaction cancel(Transaction transaction) {

		if (transaction.isSended()) {

			transaction.setStatus(Status.CANCELED);

			return transactionRepository.save(transaction);

		} else {
			log.info(String.format(String.format(FORMAT_LOG_INVALID_OPERATION,
					transaction.getId(), transaction.getStatus())));
			throw new InvalidOperationException(i18n.getMessage(TRANSACTION_STATUS_INVALID_OPERATION,
					transaction.getId(), transaction.getStatus()));
		}

	}

	@Override
	public Transaction delete(Transaction transaction) {

		Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();

		if (!transaction.isCreated()) {
			log.info(String.format(String.format(FORMAT_LOG_INVALID_OPERATION,
					transaction.getId(), transaction.getStatus())));
			throw new InvalidOperationException(i18n.getMessage(TRANSACTION_STATUS_INVALID_OPERATION,
					transaction.getId(), transaction.getStatus()));
		}

		if (transaction.isAssignedTo(flatmateLogged)) {

			transaction.setStatus(Status.DELETED);

			return transactionRepository.save(transaction);

		} else {
			throw new AccessDeniedException(i18n.getMessage(FLATMATE_ACCESS_DENIED, flatmateLogged.getNickname()));
		}

	}

	public synchronized void applyTransaction(Transaction transaction) {

		Long cashierId = transaction.getCashier().getId();
		Cashier cashier = cashierRepository.findById(cashierId)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(CASHIER_NOT_FOUND, cashierId)));

		log.info(String.format("Action %s in a Cashier %s current balance %s", transaction.getAction(), cashierId,
				cashier.getBalance()));

		if (transaction.getAction().equals(Action.WITHDRAW)) {
			cashier.withdraw(transaction.getValue());
		} else {
			cashier.deposit(transaction.getValue());
		}

		log.info(String.format("Apply %s. Changed balance to %s", transaction.getValue(), cashier.getBalance()));

		cashierRepository.save(cashier);

	}

}
