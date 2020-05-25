package br.com.cashhouse.cashier.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.model.Permission;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cashhouse.cashier.rest.dto.PermissionCashier;
import br.com.cashhouse.cashier.service.CashierService;

@RestController
@RequestMapping("/api/v1/cashiers/{id}/permission")
public class PermissionController {
	
	@Autowired
	private PermissionFactory permissionFactory;

	@Autowired
	private CashierService cashierService;

	@PostMapping("")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void cashierPermission(
			@PathVariable Long id,
			@RequestBody @Valid PermissionCashier permissionCashier) {
		
		List<String> flatmates = permissionCashier.getFlatmates();
		String type = permissionCashier.getTypePermission();
		Boolean value = permissionCashier.getValue();

		Permission permission = permissionFactory.buildFromName(type);
		
		Map<Integer, Boolean> permissions = new HashMap<>();
		permissions.put(permission.getMask(), value);
		
		for (String username : flatmates) {
			cashierService.updatePermission(id, username, permissions);
		}

	}

}
