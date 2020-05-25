package br.com.cashhouse.cashier.web.dto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

import br.com.cashhouse.core.exception.InvalidOperationException;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdatePermission {
	
	private String username;
	
	private Map<Integer,Boolean> permissions = new LinkedHashMap<>();
	
	public UpdatePermission() {
		permissions.put(BasePermission.READ.getMask(), false);
		permissions.put(BasePermission.WRITE.getMask(), false);
		permissions.put(BasePermission.DELETE.getMask(), false);
		permissions.put(BasePermission.CREATE.getMask(), false);
	}
	
	public UpdatePermission(String username, List<AccessControlEntry> aces) {
		this.username = username;
		permissions.put(BasePermission.READ.getMask(), false);
		permissions.put(BasePermission.WRITE.getMask(), false);
		permissions.put(BasePermission.DELETE.getMask(), false);
		permissions.put(BasePermission.CREATE.getMask(), false);
		
		for (AccessControlEntry ace : aces) {
			
			Sid sid = ace.getSid();
			
			if(sid instanceof PrincipalSid) {
				PrincipalSid principal = (PrincipalSid) ace.getSid();
				String name = principal.getPrincipal();
				
				if(name.equals(username)) {
					
					Permission permission = ace.getPermission();
					int idPermission = permission.getMask();
					
					permissions.put(idPermission, ace.isGranting());
					
				}
			} else {
				throw new InvalidOperationException("Invalid kind of SID for " + sid);
			}
			
		}
		
	}

}
