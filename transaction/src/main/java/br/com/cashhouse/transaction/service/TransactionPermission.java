package br.com.cashhouse.transaction.service;

import java.util.Map;

import org.springframework.security.acls.model.Acl;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.model.Transaction;

interface TransactionPermission {
	
	public Acl getAccessControl(Long id);
	
	public void create(Transaction transaction, Dashboard dashboard);
	
	public void finish(Long id, Dashboard dashboard);
	
	public void update(Long id, Profile profile, Map<Integer, Boolean> permissions);

	public void delete(Long id);

}
