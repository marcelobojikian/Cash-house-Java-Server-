package br.com.cashhouse.flatmate.rest.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cashhouse.core.exception.EntityNotFoundException;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.core.repository.DashboardRepository;
import br.com.cashhouse.core.repository.FlatmateRepository;
import br.com.cashhouse.core.repository.TransactionRepository;
import br.com.cashhouse.util.service.I18nService;
import br.com.cashhouse.util.service.ServiceRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FlatmateServiceImpl implements FlatmateService {

	private static final String FLATMATE_NOT_FOUND = "flatmate.not.found";

	@Autowired
	private I18nService i18n;

	@Autowired
	private ServiceRequest serviceRequest;

	@Autowired
	private FlatmateRepository flatmateRepository;

	@Autowired
	private DashboardRepository dashboardRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Flatmate findById(long id) {

		Dashboard dashboard = serviceRequest.getDashboard();

		if (dashboard.isOwner(id)) {
			return dashboard.getOwner();
		}

		return flatmateRepository.findByDashboardAndId(dashboard, id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(FLATMATE_NOT_FOUND, id)));

	}

	@Override
	public List<Flatmate> findAll() {
		Dashboard dashboard = serviceRequest.getDashboard();
		return dashboard.getGuests();
	}

	@Override
	public Flatmate createGuest(String email, String nickname, String password) {

		Dashboard dashboard = serviceRequest.getDashboard();

		Flatmate flatmate = new Flatmate(email, nickname);
		dashboard.getGuests().add(flatmate);

		log.info(String.format("Creating Flatmate %s", email));

		return flatmateRepository.save(flatmate);

	}

	@Override
	public Flatmate update(long id, Flatmate flatmate) {

		Flatmate entity = flatmateRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(FLATMATE_NOT_FOUND, id)));

		entity.setEmail(flatmate.getEmail());
		entity.setNickname(flatmate.getNickname());
		entity.setFirstStep(flatmate.isFirstStep());
		entity.setGuestStep(flatmate.isGuestStep());

		log.info(String.format("Update Flatmate %s", entity.getEmail()));

		return flatmateRepository.save(entity);

	}

	@Override
	public Flatmate update(Long id, String nickname) {

		Flatmate entity = flatmateRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(FLATMATE_NOT_FOUND, id)));

		entity.setNickname(nickname);

		log.info(String.format("Update Flatmate %s to nickname %s", entity.getEmail(), nickname));

		return flatmateRepository.save(entity);

	}

	@Override
	public Flatmate update(Long id, String nickname, String password) {

		Flatmate entity = flatmateRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(FLATMATE_NOT_FOUND, id)));

		entity.setNickname(nickname);

		log.info(String.format("Update Flatmate %s to nickname %s and password *** ", entity.getEmail(), nickname));

		return flatmateRepository.save(entity);

	}

	@Override
	@Transactional
	public void deleteGuest(long id) {

		Dashboard dashboard = serviceRequest.getDashboard();

		Flatmate entity = flatmateRepository.findByDashboardAndId(dashboard, id)
				.orElseThrow(() -> new EntityNotFoundException(i18n.getMessage(FLATMATE_NOT_FOUND, id)));

		Collection<Transaction> transactions = transactionRepository.findByDashboardAndFlatmateRef(dashboard, entity,
				entity);

		log.info(String.format("Deleting Flatmate %s to Dashboard %s ", entity.getEmail(), dashboard.getId()));

		dashboard.getGuests().remove(entity);
		dashboard.getTransactions().removeAll(transactions);

		dashboardRepository.save(dashboard);

		log.info("Delete sucess");

	}

}
