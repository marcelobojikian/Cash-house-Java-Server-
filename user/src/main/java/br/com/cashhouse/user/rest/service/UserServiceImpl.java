package br.com.cashhouse.user.rest.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.repository.DashboardRepository;
import br.com.cashhouse.core.repository.FlatmateRepository;
import br.com.cashhouse.util.service.ServiceRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ServiceRequest serviceRequest;

	@Autowired
	private DashboardRepository dashboardRepository;

	@Autowired
	private FlatmateRepository flatmateRepository;

	@Override
	public Collection<Dashboard> findInvitations() {
		Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();
		return dashboardRepository.findByMyInvitations(flatmateLogged);
	}

	@Override
	public Flatmate changeNickname(String nickname) {

		Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();
		flatmateLogged.setNickname(nickname);

		return flatmateRepository.save(flatmateLogged);

	}

	@Override
	public Flatmate changePassword(String password) {

		Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();

		return flatmateRepository.save(flatmateLogged);

	}

	@Override
	public Flatmate finishStepGuest() {

		Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();
		flatmateLogged.setGuestStep(true);

		return flatmateRepository.save(flatmateLogged);

	}

	@Override
	public Flatmate finishStepFirst() {

		Flatmate flatmateLogged = serviceRequest.getFlatmateLogged();
		flatmateLogged.setFirstStep(true);

		return flatmateRepository.save(flatmateLogged);

	}

}
