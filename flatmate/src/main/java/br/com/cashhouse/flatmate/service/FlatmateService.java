package br.com.cashhouse.flatmate.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.Acl;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;

public interface FlatmateService {

	public Flatmate findById(long id);

    @PreAuthorize("hasPermission(#dashboard, 'READ')")
	public List<Flatmate> findAll(Dashboard dashboard);
    
    @PreAuthorize("hasPermission(#dashboard, 'CREATE')")
	public Flatmate create(Dashboard dashboard, String nickname);

    @PreAuthorize("hasPermission(#id, 'br.com.cashhouse.core.model.Flatmate', 'WRITE')")
	public Flatmate update(long id, Flatmate flatmate);

    @PreAuthorize("hasPermission(#dashboard, 'WRITE')")
	public void delete(Dashboard dashboard, long id);
	
	/* Permissions */
    
    public Acl findPermission(Long id);
	
	public void updatePermission(Long id, String username, Map<Integer,Boolean> permissions);

}
