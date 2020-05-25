package br.com.cashhouse.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, String> {

	public Optional<Profile> findById(@Param("id") long id);

	@Query("SELECT p FROM Dashboard d JOIN d.guests p WHERE d = :dashboard")
    @PreAuthorize("hasPermission(#dashboard, 'READ')")
	public List<Profile> findByDashboard(@Param("dashboard") Dashboard dashboard);
	
	public Optional<Profile> findByUsername(String username);

}
