package br.com.cashhouse.user.service;

import java.util.List;

import br.com.cashhouse.core.model.Dashboard;

public interface InvitationService {
	
	public List<Dashboard> getInvitations();
	
	public void add(Long id, String username, String type);
	
	public void update(String username, String type);
	
	public void remove(Long id, String username);

}
