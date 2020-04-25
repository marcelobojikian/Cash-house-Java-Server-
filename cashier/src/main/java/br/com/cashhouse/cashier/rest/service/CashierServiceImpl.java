package br.com.cashhouse.cashier.rest.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cashhouse.core.exception.EntityNotFoundException;
import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.core.repository.CashierRepository;
import br.com.cashhouse.core.repository.DashboardRepository;
import br.com.cashhouse.core.repository.TransactionRepository;
import br.com.cashhouse.util.service.I18nService;
import br.com.cashhouse.util.service.ServiceRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CashierServiceImpl implements CashierService {

	private static final String CASHIER_NOT_FOUND = "cashier.not.found";

	@Autowired
	private I18nService i18n;

	@Autowired
	private ServiceRequest serviceRequest;

	@Autowired
	private CashierRepository cashierRepository;

	@Autowired
	private DashboardRepository dashboardRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Cashier findById(long id) {
		return cashierRepository.findByDashboardAndId(serviceRequest.getDashboard(), id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(CASHIER_NOT_FOUND, id)));
	}

	@Override
	public List<Cashier> findAll() {
		Dashboard dashboard = serviceRequest.getDashboard();
		return dashboard.getCashiers();
	}

	@Override
	public Cashier create(String name, BigDecimal started, BigDecimal balance) {

		Dashboard dashboard = serviceRequest.getDashboard();
		Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();

		Cashier cashier = new Cashier(name, started, balance);
		cashier.setOwner(flatmateLogged);
		dashboard.getCashiers().add(cashier);

		log.info(String.format("Flatmate %s creating Cashier %s", flatmateLogged.getNickname(), name));

		return cashierRepository.save(cashier);

	}

	@Override
	public Cashier update(long id, Cashier cashier) {

		Dashboard dashboard = serviceRequest.getDashboard();

		Cashier entity = cashierRepository.findByDashboardAndId(dashboard, id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(CASHIER_NOT_FOUND, id)));

		log.info(String.format("Cashier %s changing ... ", entity.getName()));
		log.info(String.format("Name[%s], Started[%s], Balance[%s]", cashier.getName(), cashier.getStarted(),
				cashier.getBalance()));

		entity.setName(cashier.getName());
		entity.setStarted(cashier.getStarted());
		entity.setBalance(cashier.getBalance());
		entity.setOwner(cashier.getOwner());

		return cashierRepository.save(entity);

	}

	@Override
	public Cashier update(long id, String name) {

		Dashboard dashboard = serviceRequest.getDashboard();

		Cashier entity = cashierRepository.findByDashboardAndId(dashboard, id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(CASHIER_NOT_FOUND, id)));

		log.info(String.format("Cashier %s change name to %s", entity.getName(), name));

		entity.setName(name);

		return cashierRepository.save(entity);

	}

	@Override
	@Transactional
	public void delete(long id) {

		Dashboard dashboard = serviceRequest.getDashboard();

		Cashier entity = cashierRepository.findByDashboardAndId(dashboard, id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(CASHIER_NOT_FOUND, id)));

		log.info(String.format("Deleting Cashier %s", entity.getName()));

		Collection<Transaction> transactions = transactionRepository.findByDashboardAndCashier(dashboard, entity);

		dashboard.getCashiers().remove(entity);
		dashboard.getTransactions().removeAll(transactions);

		dashboardRepository.save(dashboard);

		log.info("Delete sucess");

	}

}
