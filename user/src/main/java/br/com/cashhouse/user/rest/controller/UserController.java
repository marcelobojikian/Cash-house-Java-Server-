package br.com.cashhouse.user.rest.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.user.rest.dto.Propertie;
import br.com.cashhouse.user.rest.service.UserService;
import br.com.cashhouse.util.service.ServiceRequest;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/users/self")
public class UserController {

	@Autowired
	private ServiceRequest headerRequest;

	@Autowired
	private UserService userService;

	@GetMapping("/detail")
	@ApiOperation(value = "Return a user details entity", response = Flatmate.class)
	public Flatmate getDetails() {
		return headerRequest.getFlatmateLogged();
	}

	@GetMapping("/dashboard")
	@ApiOperation(value = "Return a dashboard entity", response = Dashboard.class)
	public Dashboard getDashboard() {
		return headerRequest.getDashboard();
	}

	@GetMapping("/invitations")
	@ApiOperation(value = "Return a list of guest dashboard entities", response = Dashboard[].class)
	public Collection<Dashboard> getDashboardInvited() {
		return userService.findInvitations();
	}

	@PutMapping("/nickname")
	@ApiOperation(value = "Return a flatmate entity with update nickname", response = Flatmate.class)
	public Flatmate updateNickname(@RequestBody @Valid Propertie propertie) {
		return userService.changeNickname(propertie.getValue());
	}

	@PutMapping("/password")
	@ApiOperation(value = "Return a flatmate entity with update password", response = Flatmate.class)
	public Flatmate updatePassword(@RequestBody @Valid Propertie propertie) {
		return userService.changePassword(propertie.getValue());
	}

	@PostMapping("/step/guest/finish")
	@ApiOperation(value = "Return a flatmate entity with guest step completed", response = Flatmate.class)
	public Flatmate finishStepGuest() {
		return userService.finishStepGuest();
	}

	@PostMapping("/step/first/finish")
	@ApiOperation(value = "Return a flatmate entity with first step completed", response = Flatmate.class)
	public Flatmate finishStepFirst() {
		return userService.finishStepFirst();
	}

}
