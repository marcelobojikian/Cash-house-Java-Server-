package br.com.cashhouse.transaction.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.Acl;

import com.querydsl.core.types.Predicate;

import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.transaction.dto.CreateTransaction;

public interface TransactionService {

	public Transaction findById(Long id);

    @PreAuthorize("hasPermission(#dashboard, 'READ')")
	public List<Transaction> findAll(Dashboard dashboard);

    @PreAuthorize("hasPermission(#dashboard, 'READ')")
	public Page<Transaction> findAll(Dashboard dashboard, Predicate parameters, Pageable pageable);
	
	/* CRUD */

    @PreAuthorize("hasPermission(#dashboard, 'CREATE')")
	public Transaction createDeposit(Dashboard dashboard, CreateTransaction createTransaction);

    @PreAuthorize("hasPermission(#dashboard, 'CREATE')")
	public Transaction createwithdraw(Dashboard dashboard, CreateTransaction createTransaction);

    @PreAuthorize("hasPermission(#id, 'br.com.cashhouse.core.model.Transaction', 'DELETE')")
	public void delete(Dashboard dashboard, Long id);

	/* Actions */

    @PreAuthorize("hasPermission(#id, 'br.com.cashhouse.core.model.Transaction', 'WRITE')")
	public Transaction finish(Long id);
	
	/* Find Secundary */

    @PreAuthorize("hasPermission(#dashboard, 'READ')")
	public List<Cashier> findAllCashier(Dashboard dashboard);

    @PreAuthorize("hasPermission(#dashboard, 'READ')")
	public List<Flatmate> findAllFlatmate(Dashboard dashboard);

    @PreAuthorize("hasPermission(#dashboard, 'READ')")
    public List<Flatmate> findMyFlatmate(Dashboard dashboard);
	
	/* Permissions */
    
    public Acl findPermission(Long id);
	
	public void updatePermission(Long id, String username, Map<Integer,Boolean> permissions);
	

}
