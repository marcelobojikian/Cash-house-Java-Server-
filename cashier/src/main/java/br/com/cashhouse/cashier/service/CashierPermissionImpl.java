package br.com.cashhouse.cashier.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.core.repository.PermissionRepository;

@Service
class CashierPermissionImpl implements CashierPermission {

	@Autowired
	private PermissionRepository permissionRepository;
	
	public Profile getProfileLogged() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (Profile) authentication.getPrincipal();
	}

	@Override
	public Acl getAccessControl(Long id) {
		return permissionRepository.find(Cashier.class, id);
	}

	@Override
	@Transactional
	public void create(Long id, Dashboard dashboard) {
		List<Profile> guests = dashboard.getGuests();
		Profile owner = dashboard.getOwner();
		
		permissionRepository.persist(Cashier.class, id, owner, guests);

		Map<Integer, Boolean> permissions = new HashMap<>();
		permissions.put(BasePermission.READ.getMask(), true);
		
		permissionRepository.update(Cashier.class, id, guests, permissions);
		
	}

	@Override
	public void update(Long id, Profile profile, Map<Integer, Boolean> permissions) {
		permissionRepository.update(Cashier.class, id, profile, permissions);
	}

	@Override
	public void delete(Long id) {
		permissionRepository.delete(Cashier.class, id);
	}

	@Override
	public void deleteWithTransactions(Long id, List<Transaction> transactions) {
		
		for (Transaction transaction : transactions) {
			permissionRepository.delete(Transaction.class, transaction.getId());
		}

		permissionRepository.delete(Cashier.class, id);
		
	}

}
