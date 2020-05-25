package br.com.cashhouse.cashier.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.acls.model.Acl;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.model.Transaction;

interface CashierPermission {
	
	public Acl getAccessControl(Long id);
	
	public void create(Long id, Dashboard dashboard);
	
	public void update(Long id, Profile profile, Map<Integer, Boolean> permissions);

	public void delete(Long id);

	public void deleteWithTransactions(Long id, List<Transaction> transactions);

}
