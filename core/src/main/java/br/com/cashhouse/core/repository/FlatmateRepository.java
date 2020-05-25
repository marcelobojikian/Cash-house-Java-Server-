package br.com.cashhouse.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;

public interface FlatmateRepository extends JpaRepository<Flatmate, Long> {

    @PreAuthorize("hasPermission(#id, 'br.com.cashhouse.core.model.Flatmate', 'READ')")
	public Optional<Flatmate> findById(@Param("id") long id);
    
	@Query("SELECT f FROM Dashboard d JOIN d.flatmates f WHERE d = :dashboard")
    @PostFilter("hasPermission(filterObject, 'READ')")
	public List<Flatmate> findByDashboard(@Param("dashboard") Dashboard dashboard);

	@Query("SELECT f FROM Dashboard d JOIN d.flatmates f WHERE d = :dashboard")
    @PostFilter("hasPermission(filterObject, 'WRITE')")
	public List<Flatmate> findMyFlatmates(@Param("dashboard") Dashboard dashboard);
    
	@Query("SELECT f FROM Flatmate f WHERE f.id = :id")
    @PreAuthorize("hasPermission(#id, 'br.com.cashhouse.core.model.Flatmate', 'WRITE')")
	public Optional<Flatmate> findMyFlatmate(@Param("id") Long id);

}
