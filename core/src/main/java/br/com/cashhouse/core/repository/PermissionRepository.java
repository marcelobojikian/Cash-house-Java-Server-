package br.com.cashhouse.core.repository;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.util.AclUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PermissionRepository {
	
	private final MutableAclService mutableService;
	
	public PermissionRepository(JdbcMutableAclService jdbcService) {
		Assert.notNull(jdbcService, "Acl Mutable Service required");
		this.mutableService = jdbcService;
	}

	@Transactional
	public MutableAcl find(Class<?> clazz, Serializable id) {

		ObjectIdentity objectIdentity =  new ObjectIdentityImpl(clazz, id);

		MutableAcl acl = null;
		try {
			acl = (MutableAcl) mutableService.readAclById(objectIdentity);
		} catch (NotFoundException nfe) {
			acl = mutableService.createAcl(objectIdentity);
		}
		return acl;
	}

	@Transactional
	public void register(Profile profile) {

		Collection<GrantedAuthority> adminRole = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
		Authentication auth = new UsernamePasswordAuthenticationToken("system", "system", adminRole);
		
		try {

			SecurityContextHolder.getContext().setAuthentication(auth);
			
			PrincipalSid profileSid = new PrincipalSid(profile.getUsername());

			MutableAcl aclProfile = find(Profile.class, profile.getId());
	
			aclProfile.setOwner(profileSid);
	
			aclProfile = mutableService.updateAcl(aclProfile);
			
			aclProfile.insertAce(aclProfile.getEntries().size(), BasePermission.READ, profileSid, true);
			aclProfile.insertAce(aclProfile.getEntries().size(), BasePermission.WRITE, profileSid, true);
			aclProfile.insertAce(aclProfile.getEntries().size(), BasePermission.CREATE, profileSid, true);
			aclProfile.insertAce(aclProfile.getEntries().size(), BasePermission.DELETE, profileSid, true);
			aclProfile.insertAce(aclProfile.getEntries().size(), BasePermission.ADMINISTRATION, profileSid, true);
	
			mutableService.updateAcl(aclProfile);
			
			Dashboard dashboard = profile.getDashboard();
			MutableAcl aclDashboard = find(Dashboard.class, dashboard.getId());
	
			aclDashboard.setOwner(profileSid);
			
			aclDashboard.insertAce(aclDashboard.getEntries().size(), BasePermission.READ, profileSid, true);
			aclDashboard.insertAce(aclDashboard.getEntries().size(), BasePermission.WRITE, profileSid, true);
			aclDashboard.insertAce(aclDashboard.getEntries().size(), BasePermission.CREATE, profileSid, true);
			aclDashboard.insertAce(aclDashboard.getEntries().size(), BasePermission.DELETE, profileSid, true);
			aclDashboard.insertAce(aclDashboard.getEntries().size(), BasePermission.ADMINISTRATION, profileSid, true);
	
			mutableService.updateAcl(aclDashboard);
			
			log.info(String.format("%s registred, the user can manage the Profile %s and Dashboard %s", profileSid.getPrincipal(), profile.getId(), dashboard.getId()));
			
		} finally {
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		
	}
	
	@Transactional
	public MutableAcl persist(Class<?> clazz, Long id, Profile profileOwner, List<Profile> profileGuests) {
		
		ObjectIdentity objectIdentity =  new ObjectIdentityImpl(clazz, id);
		PrincipalSid owner = new PrincipalSid(profileOwner.getUsername());
		List<Sid> sidGuests = AclUtils.getSid(profileGuests);
		
		MutableAcl acl = mutableService.createAcl(objectIdentity);
		
		acl.insertAce(acl.getEntries().size(), BasePermission.READ, owner, true);
		acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, owner, true);
		acl.insertAce(acl.getEntries().size(), BasePermission.CREATE, owner, true);
		acl.insertAce(acl.getEntries().size(), BasePermission.DELETE, owner, true);
		acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, owner, true);

		for (Sid sid : sidGuests) {
			
			acl.insertAce(acl.getEntries().size(), BasePermission.READ, sid, false);
			acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, sid, false);
			acl.insertAce(acl.getEntries().size(), BasePermission.CREATE, sid, false);
			acl.insertAce(acl.getEntries().size(), BasePermission.DELETE, sid, false);
			
		}

		log.info(String.format("%s persist Acl, all users can't do nothing the Entity %s", owner.getPrincipal(), id));
		return mutableService.updateAcl(acl);
		
	}
	
	@Transactional
	public void delete(Class<?> clazz, Long id) {
		
		ObjectIdentity objectIdentity =  new ObjectIdentityImpl(clazz, id);
		mutableService.deleteAcl(objectIdentity, false);

		log.info(String.format("%s delete Acl id = %s", clazz.getSimpleName(), id));
		
	}
	
	@Transactional
	public void update(Class<?> clazz, Long id, Profile profile, Map<Integer, Boolean> permissions) {
		update(clazz, id, Collections.singletonList(profile), permissions);
	}
	
	@Transactional
	public void update(Class<?> clazz, Long id, List<Profile> profileGuests, Map<Integer, Boolean> permissions) {
		
		ObjectIdentity objectIdentity =  new ObjectIdentityImpl(clazz, id);
		List<Sid> sidGuests = AclUtils.getSid(profileGuests);
		
		MutableAcl acl = (MutableAcl) mutableService.readAclById(objectIdentity);
		List<AccessControlEntry> aces = acl.getEntries();
		
		for (int i = 0; i < aces.size(); i++) {
			
			AccessControlEntry ace = aces.get(i);
			Permission acePermission = ace.getPermission();
			Integer mask = acePermission.getMask();
			
			if(sidGuests.contains(ace.getSid())&& 
					permissions.containsKey(mask)) {

				boolean granting = permissions.get(mask);
				if(granting != ace.isGranting()) {
					acl.deleteAce(i);
					acl.insertAce(i, acePermission, ace.getSid(), granting);
				}
				
			}
			
		}
		
		mutableService.updateAcl(acl);
		log.info(String.format("List of users has new permissions in the Entity %s", id));
		
	}

	@Transactional
	public void update(Class<?> clazz, List<Long> entityIdentities, List<Profile> profiles) {

		List<ObjectIdentity> identities = AclUtils.getObjectIdentity(clazz, entityIdentities);
		List<Sid> sidGuests = AclUtils.getSid(profiles);
		
		Map<ObjectIdentity, Acl> acls = mutableService.readAclsById(identities, sidGuests);
		
		for (ObjectIdentity identity : identities) {
			
			Acl oldAcl = acls.get(identity);
			mutableService.deleteAcl(identity, false);
			MutableAcl acl = mutableService.createAcl(identity);

			Sid sidOwner = acl.getOwner();
			
			acl.insertAce(acl.getEntries().size(), BasePermission.READ, sidOwner, true);
			acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, sidOwner, true);
			acl.insertAce(acl.getEntries().size(), BasePermission.CREATE, sidOwner, true);
			acl.insertAce(acl.getEntries().size(), BasePermission.DELETE, sidOwner, true);
			
			List<AccessControlEntry> aces = oldAcl.getEntries();
			for (AccessControlEntry ace : aces) {
				PrincipalSid sid = (PrincipalSid) ace.getSid();
				if(sidGuests.contains(sid)){
					acl.insertAce(acl.getEntries().size(), ace.getPermission(), ace.getSid(), ace.isGranting());
				}
			}

			mutableService.updateAcl(acl);
			
			log.info(String.format("%s rebuild Acl, other users can do nothing the Entity %s", sidOwner, clazz.getSimpleName()));
			
		}
		
	}
	
	@Transactional
	public void add(Class<?> clazz, Long id, Profile profile) {
		
		ObjectIdentity objectIdentity =  new ObjectIdentityImpl(clazz, id);
		Sid sid = new PrincipalSid(profile.getUsername());
		
		MutableAcl acl = (MutableAcl) mutableService.readAclById(objectIdentity);

		acl.insertAce(acl.getEntries().size(), BasePermission.READ, sid, false);
		acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, sid, false);
		acl.insertAce(acl.getEntries().size(), BasePermission.CREATE, sid, false);
		acl.insertAce(acl.getEntries().size(), BasePermission.DELETE, sid, false);
		
		mutableService.updateAcl(acl);
		log.info(String.format("user %s has added permissions in the Entity %s", profile.getUsername(), id));
		
	}
	
	@Transactional
	public void remove(Class<?> clazz, Long id, Profile profile) {
		
		ObjectIdentity objectIdentity =  new ObjectIdentityImpl(clazz, id);
		Sid sid = new PrincipalSid(profile.getUsername());
		
		MutableAcl acl = (MutableAcl) mutableService.readAclById(objectIdentity);
		List<AccessControlEntry> aces = acl.getEntries();

		Integer firstIndex = null;
		ArrayDeque<AccessControlEntry> dequeAces = new ArrayDeque<>();
		for (int i = 0; i < aces.size(); i++) {
			
			AccessControlEntry ace = aces.get(i);
			
			if(firstIndex == null && ace.getSid().equals(sid)) {
				firstIndex = i;
			} else {
				if(!ace.getSid().equals(sid)) {
					dequeAces.push(ace);
				}
			}
		}
		
		for (int i = firstIndex; i < aces.size(); i++) {
			AccessControlEntry ace = dequeAces.pop();
			acl.deleteAce(i);
			if(!dequeAces.isEmpty()) {
				acl.insertAce(i, ace.getPermission(), ace.getSid(), ace.isGranting());
			}
		}
		
		mutableService.updateAcl(acl);
		log.info(String.format("user %s has added permissions in the Entity %s", profile.getUsername(), id));
		
	}

}
