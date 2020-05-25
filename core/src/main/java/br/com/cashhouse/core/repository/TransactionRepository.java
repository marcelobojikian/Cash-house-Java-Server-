package br.com.cashhouse.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.QTransaction;
import br.com.cashhouse.core.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, QuerydslPredicateExecutor<Transaction>,
		QuerydslBinderCustomizer<QTransaction> {

    @PreAuthorize("hasPermission(#id, 'br.com.cashhouse.core.model.Transaction', 'READ')")
	public Optional<Transaction> findById(@Param("id") long id);
    
	@Query("SELECT t FROM Dashboard d JOIN d.transactions t WHERE d = :dashboard")
    @PostFilter("hasPermission(filterObject, 'READ')")
	public List<Transaction> findByDashboard(@Param("dashboard") Dashboard dashboard);

	@Query("SELECT t FROM Dashboard d JOIN d.transactions t WHERE d = :dashboard AND t.cashier = :cashier")
	public List<Transaction> findByDashboardAndCashier(@Param("dashboard") Dashboard dashboard, @Param("cashier") Cashier cashier);

	@Query("SELECT t FROM Dashboard d JOIN d.transactions t WHERE d = :dashboard AND t.flatmate = :flatmate")
	public List<Transaction> findByDashboardAndFlatmateRef(@Param("dashboard") Dashboard dashboard, @Param("flatmate") Flatmate flatmate);

	default Page<Transaction> findAll(List<Flatmate> flatmates, Predicate parameters,
			Pageable pageable) {
		QTransaction transaction = QTransaction.transaction;
		BooleanExpression inDashboard = transaction.flatmate.in(flatmates);

		// @formatter:off
		return findAll(
					inDashboard
					.and(
						parameters
					)
				, pageable);
		// @formatter:on
	}

	@Override
	default void customize(QuerydslBindings bindings, QTransaction transaction) {
		bindings.excluding(transaction.id);
	}

}
