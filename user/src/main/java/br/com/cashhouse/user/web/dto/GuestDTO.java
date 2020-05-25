package br.com.cashhouse.user.web.dto;

import java.util.List;

import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

import br.com.cashhouse.core.exception.InvalidOperationException;
import br.com.cashhouse.core.model.Profile;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GuestDTO {
	
	private static final Integer WRITE = 2;
	
	private Long id;
	
	private String username;
	
	private String type = "guest";
	
	public GuestDTO(Profile profile, List<AccessControlEntry> aces) {
		this.id = profile.getId();
		this.username = profile.getUsername();
		
		for (AccessControlEntry ace : aces) {
			
			Sid sid = ace.getSid();
			
			if(sid instanceof PrincipalSid) {
				PrincipalSid principal = (PrincipalSid) ace.getSid();
				String name = principal.getPrincipal();
				
				if(name.equals(username)) {
					
					Permission permission = ace.getPermission();
					int idPermission = permission.getMask();
					
					if(idPermission == WRITE && ace.isGranting()) {
						type = "flatmate";
					}
					
				}
			} else {
				throw new InvalidOperationException("Invalid kind of SID for " + sid);
			}
			
		}
		
	}

}
