package br.com.cashhouse.flatmate.rest.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.cashhouse.core.model.Flatmate;

@PreAuthorize("isAuthenticated()")
public interface FlatmateService {

	public Flatmate findById(long id);
	
	public List<Flatmate> findAll();

	@PreAuthorize("isDashboardOwner()")
	public Flatmate createGuest(String email, String nickname, String password);
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	public Flatmate update(long id, Flatmate flatmate);

	@PreAuthorize("isLoggedUser(#id)")
	public Flatmate update(Long id, String nickname);

	@PreAuthorize("isLoggedUser(#id)")
	public Flatmate update(Long id, String nickname, String password);

	@PreAuthorize("isDashboardOwner()")
	public void deleteGuest(long id);

}
