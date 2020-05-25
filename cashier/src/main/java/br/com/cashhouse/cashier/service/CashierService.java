package br.com.cashhouse.cashier.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.Acl;

import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;

public interface CashierService {

	public Cashier findById(long id);

    @PreAuthorize("hasPermission(#dashboard, 'READ')")
	public List<Cashier> findAll(Dashboard dashboard);

    @PreAuthorize("hasPermission(#dashboard, 'CREATE')")
	public Cashier create(Dashboard dashboard, String name, BigDecimal started, BigDecimal balance);

    @PreAuthorize("hasPermission(#id, 'br.com.cashhouse.core.model.Cashier', 'WRITE')")
	public Cashier update(long id, Cashier cashier);

    @PreAuthorize("hasPermission(#dashboard, 'WRITE')")
	public void delete(Dashboard dashboard, long id);
	
	/* Permissions */
    
    public Acl findPermission(Long id);
	
	public void updatePermission(Long id, String username, Map<Integer,Boolean> permissions);

}
