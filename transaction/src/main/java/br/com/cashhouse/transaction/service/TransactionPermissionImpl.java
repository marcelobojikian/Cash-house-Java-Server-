package br.com.cashhouse.transaction.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.core.repository.PermissionRepository;
import br.com.cashhouse.core.util.AclUtils;

@Service
public class TransactionPermissionImpl implements TransactionPermission {

	@Autowired
	private PermissionRepository permissionRepository;
	
	public Profile getProfileLogged() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (Profile) authentication.getPrincipal();
	}

	@Override
	public Acl getAccessControl(Long id) {
		return permissionRepository.find(Transaction.class, id);
	}

	@Override
	@Transactional
	public void create(Transaction transaction, Dashboard dashboard) {
		 
		Long idTransaction = transaction.getId();
		List<Profile> profiles = dashboard.getGuests();
		Profile owner = dashboard.getOwner();
		
		permissionRepository.persist(Transaction.class, idTransaction, owner, profiles);

		Map<Integer, Boolean> permissions = new HashMap<>();
		permissions.put(BasePermission.READ.getMask(), true);
		permissions.put(BasePermission.DELETE.getMask(), true);
		permissions.put(BasePermission.WRITE.getMask(), true);
		
		permissionRepository.update(Transaction.class, idTransaction, owner, permissions);

		Long idFlatmate = transaction.getFlatmate().getId();
		MutableAcl acl = permissionRepository.find(Flatmate.class, idFlatmate);
		
		List<Profile> profilesWrite = AclUtils.filterByPermissionWrite(acl, profiles);

		permissions = new HashMap<>();
		permissions.put(BasePermission.READ.getMask(), true);
		permissions.put(BasePermission.DELETE.getMask(), true);
		
		permissionRepository.update(Transaction.class, idTransaction, profilesWrite, permissions);
		
	}

	@Override
	@Transactional
	public void finish(Long id, Dashboard dashboard) {
		List<Profile> profiles = dashboard.getGuests();
		Profile owner = dashboard.getOwner();

		Map<Integer, Boolean> permissions = new HashMap<>();
		permissions.put(BasePermission.READ.getMask(), true);
		permissions.put(BasePermission.DELETE.getMask(), false);
		permissions.put(BasePermission.WRITE.getMask(), false);

		permissionRepository.update(Transaction.class, id, profiles, permissions);

		permissions = new HashMap<>();
		permissions.put(BasePermission.READ.getMask(), true);
		permissions.put(BasePermission.WRITE.getMask(), false);
		permissions.put(BasePermission.DELETE.getMask(), true);

		permissionRepository.update(Transaction.class, id, owner, permissions);
		
	}

	@Override
	public void update(Long id, Profile profile, Map<Integer, Boolean> permissions) {
		permissionRepository.update(Transaction.class, id, profile, permissions);
	}

	@Override
	public void delete(Long id) {
		permissionRepository.delete(Transaction.class, id);
	}

}
