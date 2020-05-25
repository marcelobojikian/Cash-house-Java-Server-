package br.com.cashhouse.flatmate.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.flatmate.dto.CreateFlatmate;
import br.com.cashhouse.flatmate.dto.UpdateFlatmate;
import br.com.cashhouse.flatmate.rest.dto.EntityFlatmate;
import br.com.cashhouse.flatmate.service.FlatmateService;
import br.com.cashhouse.util.service.RestSession;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/flatmates")
public class FlatmateController {

	private static final String DASHBOARD_ID = "dashboard";
	
	@Autowired
	private RestSession restSession;

	@Autowired
	private FlatmateService flatmateService;

	@GetMapping("")
	@ApiOperation(value = "Return a list with all flatmates", response = Flatmate[].class)
	public ResponseEntity<List<Flatmate>> findAll(@RequestHeader(value = DASHBOARD_ID, required = false) String dashboardHeader) {

		Dashboard dashboard = restSession.getDashboard(dashboardHeader);

		List<Flatmate> flatmates = flatmateService.findAll(dashboard);

		if (flatmates.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(flatmates, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Return a flatmate entity by id", response = Flatmate.class)
	public Flatmate findById(@PathVariable Long id) {
		return flatmateService.findById(id);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Return a flatmate entity created", response = Flatmate.class)
	public Flatmate create(@RequestHeader(value = DASHBOARD_ID, required = false) String dashboardHeader, @RequestBody @Valid CreateFlatmate flatmate) {
		
		Dashboard dashboard = restSession.getDashboard(dashboardHeader);

		String nickname = flatmate.getNickname();

		return flatmateService.create(dashboard, nickname);

	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Return a flatmate entity updated", response = Flatmate.class)
	public Flatmate update(@PathVariable Long id, @RequestBody @Valid EntityFlatmate flatmate) {		
		return flatmateService.update(id, flatmate.toEntity());
	}

	@PatchMapping("/{id}")
	@ApiOperation(value = "Return a flatmate entity partial updated", response = Flatmate.class)
	public Flatmate patch(@PathVariable Long id, @RequestBody @Valid UpdateFlatmate flatmate) {

		String nickname = flatmate.getNickname();
		
		Flatmate entity = flatmateService.findById(id);
		entity.setNickname(nickname);

		return flatmateService.update(id, entity);

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Return status OK when deleted", response = Flatmate.class)
	public void detele(@RequestHeader(value = DASHBOARD_ID, required = false) String dashboardHeader, @PathVariable Long id) {
		
		Dashboard dashboard = restSession.getDashboard(dashboardHeader);
		
		flatmateService.delete(dashboard, id);
	}

}
