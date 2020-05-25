package br.com.cashhouse.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;

public interface CashierRepository extends JpaRepository<Cashier, Long> {

    @PreAuthorize("hasPermission(#id, 'br.com.cashhouse.core.model.Cashier', 'READ')")
	public Optional<Cashier> findById(@Param("id") long id);

	@Query("SELECT c FROM Dashboard d JOIN d.cashiers c WHERE d = :dashboard")
    @PostFilter("hasPermission(filterObject, 'READ')")
	public List<Cashier> findByDashboard(@Param("dashboard") Dashboard dashboard);

}
