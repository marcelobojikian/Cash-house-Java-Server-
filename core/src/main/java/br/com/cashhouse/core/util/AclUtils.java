package br.com.cashhouse.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

import br.com.cashhouse.core.model.Profile;

public class AclUtils {
	
	private AclUtils() {
		
	}

	public static List<Sid> getSid(List<Profile> profiles) {
		List<Sid> sids = new ArrayList<>();
		for (Profile profile : profiles) {
			String username = profile.getUsername();
			sids.add(new PrincipalSid(username));
		}
		return sids;
	}

	public static Map<Sid, Profile> getSidMap(List<Profile> profiles) {
		Map<Sid, Profile> sids = new HashMap<>();
		for (Profile profile : profiles) {
			String username = profile.getUsername();
			sids.put(new PrincipalSid(username), profile);
		}
		return sids;
	}

	public static List<ObjectIdentity> getObjectIdentity(Class<?> clazz, List<Long> entityIdentities) {
		List<ObjectIdentity> identities = new ArrayList<>();
		for (Long id : entityIdentities) {
			identities.add(new ObjectIdentityImpl(clazz, id));
		}
		return identities;
	}

	public static List<Profile> filterByPermissionWrite(MutableAcl acl, List<Profile> profiles) {
		return filterByPermission(acl, profiles, BasePermission.WRITE);
	}

	public static List<Profile> filterByPermissionRead(MutableAcl acl, List<Profile> profiles) {
		return filterByPermission(acl, profiles, BasePermission.READ);
	}

	public static List<Profile> filterByPermission(MutableAcl acl, List<Profile> profiles, Permission permission) {

		Set<Profile> result = new HashSet<>();
		Map<Sid, Profile> sidProfiles = getSidMap(profiles);
		List<AccessControlEntry> aces = acl.getEntries();

		for (AccessControlEntry ace : aces) {

			Sid sid = ace.getSid();
			Permission acePermission = ace.getPermission();
			boolean granted = ace.isGranting();

			if (granted && sid instanceof PrincipalSid) {

				if (sidProfiles.containsKey(sid) && acePermission.equals(permission)) {
					result.add(sidProfiles.get(sid));
				}
			}

		}

		return new ArrayList<Profile>(result);
	}

}
