package br.com.cashhouse.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.model.Transaction;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {

    @PostFilter("hasPermission(filterObject, 'READ')")
	public List<Dashboard> findAll();

    @PreAuthorize("hasPermission(#id, 'br.com.cashhouse.core.model.Dashboard', 'READ')")
	public Optional<Dashboard> findById(Long id);

	@Query("SELECT d FROM Dashboard d WHERE :transaction MEMBER OF d.transactions")
	public Optional<Dashboard> findByTransaction(Transaction transaction);

    @PostFilter("hasPermission(filterObject, 'READ')")
	@Query("SELECT d FROM Dashboard d WHERE :profile MEMBER OF d.guests ")
	public List<Dashboard> findInvitations(@Param("profile") Profile profile);

}
