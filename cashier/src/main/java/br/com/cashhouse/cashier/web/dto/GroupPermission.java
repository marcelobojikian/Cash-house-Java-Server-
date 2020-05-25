package br.com.cashhouse.cashier.web.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;

import br.com.cashhouse.core.model.Profile;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroupPermission {

	private List<UpdatePermission> updates = new ArrayList<>();
	
	public GroupPermission() {
	}

	public GroupPermission(List<Profile> profiles, Acl acl) {
		
		List<AccessControlEntry> aces = acl.getEntries();

		for (Profile profile : profiles) {
			UpdatePermission guest = new UpdatePermission(profile.getUsername(), aces);
			updates.add(guest);
		}

	}

}
