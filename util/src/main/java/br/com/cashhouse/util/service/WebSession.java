package br.com.cashhouse.util.service;

import java.util.List;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Profile;

public interface WebSession {
	
	public void changeDashboard(Long id);
	
	public Profile getProfileLogged();
	
	public Dashboard getDashboard();
	
	public List<Dashboard> getInvitations();
	
	public List<Profile> getGuests();

}
