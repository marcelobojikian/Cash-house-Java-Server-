package br.com.cashhouse.util.service;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Profile;

public interface RestSession {
	
	public Profile getProfileLogged();
	
	public Dashboard getDashboard(String headerId);

}
