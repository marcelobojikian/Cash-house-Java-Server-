package br.com.cashhouse.user.service;

import br.com.cashhouse.core.model.Profile;

public interface ProfileService {

	public Profile findById(long id);
	
	public Profile create(String username, String password);
	
	public void changePassword(String password);

}
