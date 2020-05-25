package br.com.cashhouse.flatmate.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.acls.model.Acl;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.model.Transaction;

interface FlatmatePermission {

	public Acl getAccessControl(Long id);

	public void create(Long id, Dashboard dashboard);

	public void update(Long id, Profile profile, Map<Integer, Boolean> permissions);

	public void delete(Long id);

	public void addPermission(Long id, Profile profile);

	public void removePermission(Long id, Profile profile);

	public void deleteWithTransactions(Long id, List<Transaction> transactions);

}
