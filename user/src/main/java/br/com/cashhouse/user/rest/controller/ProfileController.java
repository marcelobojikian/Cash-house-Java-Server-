package br.com.cashhouse.user.rest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.user.dto.Propertie;
import br.com.cashhouse.user.dto.RegisterUser;
import br.com.cashhouse.user.service.ProfileService;
import br.com.cashhouse.util.service.RestSession;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {
	
	@Autowired
	private RestSession restSession;

	@Autowired
	private ProfileService profileService;

	@GetMapping("/self")
	@ApiOperation(value = "Return a Profile entity", response = Profile.class)
	public Profile getDetails() {
		return restSession.getProfileLogged();
	}

	@PutMapping("/self/password")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Set a profile password")
	public void updatePassword(@RequestBody @Valid Propertie propertie) {
		profileService.changePassword(propertie.getValue());
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Return a Profile entity created", response = Profile.class)
	public Profile create(@RequestBody @Valid RegisterUser registerUser) {

		String username = registerUser.getUsername();
		String password = registerUser.getPassword();

		return profileService.create(username, password);

	}

}
