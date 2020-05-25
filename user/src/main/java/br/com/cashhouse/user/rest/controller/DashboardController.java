package br.com.cashhouse.user.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.user.service.DashboardService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/dashboards/self")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping("/guests")
	@ApiOperation(value = "Return a list of guests in a dashboard", response = Profile[].class)
	public List<Profile> getGuests() {
		return dashboardService.getGuests();
	}

	@GetMapping("/flatmates")
	@ApiOperation(value = "Return a list of flatmates in a dashboard", response = Flatmate[].class)
	public List<Flatmate> getFlatmates() {
		return dashboardService.getFlatmates();
	}

	@GetMapping("/transactions")
	@ApiOperation(value = "Return a list of transactions in a dashboard", response = Transaction[].class)
	public List<Transaction> getTransactions() {
		return dashboardService.getTransactions();
	}

	@GetMapping("/cashiers")
	@ApiOperation(value = "Return a list of cashiers in a dashboard", response = Cashier[].class)
	public List<Cashier> getCashiers() {
		return dashboardService.getCashiers();
	}

}
