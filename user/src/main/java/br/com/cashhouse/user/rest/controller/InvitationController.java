package br.com.cashhouse.user.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.user.dto.Propertie;
import br.com.cashhouse.user.service.InvitationService;
import br.com.cashhouse.util.service.RestSession;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/invitations/self")
public class InvitationController {
	
	@Autowired
	private RestSession restSession;

	@Autowired
	private InvitationService invitationService;

	@GetMapping("")
	@ApiOperation(value = "Return a list of dashboards invitated", response = Dashboard[].class)
	public List<Dashboard> getInvitations() {
		return invitationService.getInvitations();
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Invite a username for your dashboard")
	public void add(@RequestBody @Valid Propertie propertie) {
		
		Profile profile = restSession.getProfileLogged();
		Dashboard dashboard = profile.getDashboard();

		Long id = dashboard.getId();
		String username = propertie.getValue();

		invitationService.add(id, username, "guest");

	}

	@DeleteMapping("")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Remove a username invitatio in your dashboardn", response = Cashier.class)
	public void detele(@RequestBody @Valid Propertie propertie) {
		
		Profile profile = restSession.getProfileLogged();
		Dashboard dashboard = profile.getDashboard();

		Long id = dashboard.getId();
		String username = propertie.getValue();

		invitationService.remove(id, username);

	}

}
