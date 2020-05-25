package br.com.cashhouse.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.acls.model.Acl;

import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.model.Transaction;

public interface DashboardService {
	
	public List<Profile> getGuests();
	
	public List<Flatmate> getFlatmates();

	public List<Cashier> getCashiers();

	public List<Transaction> getTransactions();
	
	/* Permissions */
    
    public Acl findPermission();
	
	public void updatePermission(String username, Map<Integer,Boolean> permissions);

}
