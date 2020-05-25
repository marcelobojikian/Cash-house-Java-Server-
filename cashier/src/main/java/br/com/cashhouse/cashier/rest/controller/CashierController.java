package br.com.cashhouse.cashier.rest.controller;

import java.math.BigDecimal;
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

import br.com.cashhouse.cashier.rest.dto.CreateCashier;
import br.com.cashhouse.cashier.rest.dto.EntityCashier;
import br.com.cashhouse.cashier.rest.dto.UpdateCashier;
import br.com.cashhouse.cashier.service.CashierService;
import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.util.service.RestSession;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/cashiers")
public class CashierController {

	private static final String DASHBOARD_ID = "dashboard";
	
	@Autowired
	private RestSession restSession;

	@Autowired
	private CashierService cashierService;

	@GetMapping("")
	@ApiOperation(value = "Return a list with all cashiers", response = Cashier[].class)
	public ResponseEntity<List<Cashier>> findAll(@RequestHeader(value = DASHBOARD_ID, required = false) String dashboardHeader) {
		
		Dashboard dashboard = restSession.getDashboard(dashboardHeader);

		List<Cashier> cashiers = cashierService.findAll(dashboard);

		if (cashiers.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(cashiers, HttpStatus.OK);
		
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Return a cashier entity by id", response = Cashier.class)
	public Cashier findById(@PathVariable Long id) {
		return cashierService.findById(id);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Return a cashier entity created", response = Cashier.class)
	public Cashier create(@RequestHeader(value = DASHBOARD_ID, required = false) String dashboardHeader, @RequestBody @Valid CreateCashier cashier) {
		
		Dashboard dashboard = restSession.getDashboard(dashboardHeader);

		String name = cashier.getName();
		BigDecimal started = cashier.getStarted();
		BigDecimal balance = cashier.getBalance();

		if (started == null) {
			started = balance;
		}

		return cashierService.create(dashboard, name, started, balance);

	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Return a cashier entity updated", response = Cashier.class)
	public Cashier update(@PathVariable Long id, @RequestBody @Valid EntityCashier cashier) {
		return cashierService.update(id, cashier.toEntity());
	}

	@PatchMapping("/{id}")
	@ApiOperation(value = "Return a cashier entity partial updated", response = Cashier.class)
	public Cashier patch(@PathVariable Long id, @RequestBody @Valid UpdateCashier cashier) {

		String name = cashier.getName();
		
		Cashier entity = cashierService.findById(id);
		entity.setName(name);
		
		return cashierService.update(id, entity);

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Return status OK when deleted", response = Cashier.class)
	public void detele(@RequestHeader(value = DASHBOARD_ID, required = false) String dashboardHeader, @PathVariable Long id) {
		
		Dashboard dashboard = restSession.getDashboard(dashboardHeader);

		cashierService.delete(dashboard, id);

	}

}
