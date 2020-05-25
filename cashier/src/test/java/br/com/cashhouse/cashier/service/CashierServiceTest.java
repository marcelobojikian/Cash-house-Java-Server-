package br.com.cashhouse.cashier.service;

import static br.com.cashhouse.test.util.factory.EntityFactory.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cashhouse.core.exception.EntityNotFoundException;
import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.core.repository.CashierRepository;
import br.com.cashhouse.core.repository.DashboardRepository;
import br.com.cashhouse.core.repository.PermissionRepository;
import br.com.cashhouse.core.repository.TransactionRepository;
import br.com.cashhouse.test.util.oauth.LoginWith;
import br.com.cashhouse.test.util.service.ServiceAuthHelper;
import br.com.cashhouse.util.service.I18nService;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class CashierServiceTest extends ServiceAuthHelper {

//	@Autowired
//	private CashierService cashierService;
//
//	@MockBean
//	private I18nService i18n;
//
//	@MockBean
//	private CashierRepository cashierRepository;
//
//	@MockBean
//	private DashboardRepository dashboardRepository;
//
//	@MockBean
//	private TransactionRepository transactionRepository;
//
//	@MockBean
//	private PermissionRepository permissionService;

//	@LoginWith(id = 1)
//	@Test
//	public void whenFindById_thenReturnCashierObject() throws Exception {
//
//		Flatmate flatmate = getFlatmateLogged();
//		Dashboard dashboard = flatmate.getDashboard();
//
//		Cashier energy = createCashier(dashboard, 1l, "Energy", 12.3);
//
//		when(cashierRepository.findByDashboardAndId(dashboard, 1l)).thenReturn(Optional.of(energy));
//
//		Cashier cashierExpect = cashierService.findById(dashboard, 1L);
//
//		assertThat(cashierExpect.getId(), is(1l));
//		assertThat(cashierExpect.getName(), is("Energy"));
//		assertThat(cashierExpect.getStarted(), is(BigDecimal.valueOf(12.3)));
//		assertThat(cashierExpect.getBalance(), is(BigDecimal.valueOf(12.3)));
//
//	}
//
//	@LoginWith(id = 1)
//	@Test(expected = EntityNotFoundException.class)
//	public void whenFindById_thenThrowEntityNotFoundException() throws Exception {
//
//		Flatmate flatmate = getFlatmateLogged();
//		Dashboard dashboard = flatmate.getDashboard();
//
//		when(cashierRepository.findByDashboardAndId(dashboard, 1l)).thenReturn(Optional.empty());
//
//		cashierService.findById(dashboard, 1L);
//
//	}
//
//	@LoginWith(id = 1)
//	@Test
//	public void whenFindAll_thenReturnObjectArray() throws Exception {
//
//		Flatmate flatmate = getFlatmateLogged();
//		Dashboard dashboard = flatmate.getDashboard();
//
//		Cashier energy = createCashier(dashboard, 1l, "Energy", 12.3);
//		Cashier garbage = createCashier(dashboard, 2l, "Garbage", 4.2, 56.6);
//
//		List<Cashier> cashiers = cashierService.findAll(dashboard);
//
//		assertThat(cashiers, contains(energy, garbage));
//
//	}
//
//	@LoginWith(id = 1)
//	@Test
//	public void whenCreate_thenReturnCashierObject() throws Exception {
//
//		Flatmate flatmate = getFlatmateLogged();
//		Dashboard dashboard = flatmate.getDashboard();
//
//		Cashier energy = createCashier(dashboard, 1l, "Energy", 12.3);
//
//		when(cashierRepository.save(any(Cashier.class))).thenReturn(energy);
//		doNothing().when(permissionService).create(Cashier.class, anyLong(), dashboard.getGuests());
//
//		Cashier cashier = cashierService.create(dashboard, "Energy", BigDecimal.valueOf(12.3), BigDecimal.valueOf(12.3));
//
//		assertThat(cashier.getId(), is(1l));
//		assertThat(cashier.getName(), is("Energy"));
//		assertThat(cashier.getStarted(), is(BigDecimal.valueOf(12.3)));
//		assertThat(cashier.getBalance(), is(BigDecimal.valueOf(12.3)));
//
//	}
//
//	@LoginWith(id = 2)
//	@Test(expected = AccessDeniedException.class)
//	public void whenCreate_thenThrowAccessDeniedException() throws Exception {
//
//		Flatmate flatmate = createFlatmate(1l, "none", "none");
//		userDashboard(flatmate);
//
//		cashierService.create(flatmate.getDashboard(), "Energy", BigDecimal.valueOf(12.3), BigDecimal.valueOf(12.3));
//
//	}
//
//	@LoginWith(roles = "ADMIN", id = 1)
//	@Test
//	public void whenUpdate_thenReturnCashierObject() throws Exception {
//
//		Flatmate flatmate = getFlatmateLogged();
//		Dashboard dashboard = flatmate.getDashboard();
//
//		Cashier energy = createCashier(dashboard, 1l, "Energy", 12.3);
//		Cashier energyNew = createCashier(dashboard, 1l, "Energy UP", 3.1, 3.2);
//
//		when(cashierRepository.findByDashboardAndId(dashboard, 1l)).thenReturn(Optional.of(energy));
//		when(cashierRepository.save(energy)).thenReturn(energy);
//
//		Cashier cashier = cashierService.update(dashboard, 1l, energyNew);
//
//		assertThat(cashier.getId(), is(1l));
//		assertThat(cashier.getName(), is("Energy UP"));
//		assertThat(cashier.getStarted(), is(BigDecimal.valueOf(3.1)));
//		assertThat(cashier.getBalance(), is(BigDecimal.valueOf(3.2)));
//
//	}
//
//	@LoginWith(roles = "ADMIN", id = 1)
//	@Test(expected = EntityNotFoundException.class)
//	public void whenUpdate_thenThrowEntityNotFoundException() throws Exception {
//
//		Flatmate flatmate = getFlatmateLogged();
//		Dashboard dashboard = flatmate.getDashboard();
//
//		Cashier energy = createCashier(dashboard, 1l, "Energy", 12.3);
//
//		when(cashierRepository.findByDashboardAndId(dashboard, 99l)).thenReturn(Optional.empty());
//		when(cashierRepository.save(any(Cashier.class))).thenReturn(energy);
//
//		cashierService.update(dashboard, 99l, energy);
//
//	}
//
//	@LoginWith(id = 2)
//	@Test(expected = AccessDeniedException.class)
//	public void whenUpdate_thenThrowAccessDeniedException() throws Exception {
//
//		Flatmate flatmate = createFlatmate(1l, "none", "none");
//		userDashboard(flatmate);
//
//		Dashboard dashboard = flatmate.getDashboard();
//
//		Cashier energy = createCashier(dashboard, 1l, "Energy", 12.3);
//
//		when(cashierRepository.save(any(Cashier.class))).thenReturn(energy);
//
//		cashierService.update(flatmate.getDashboard(), 1l, energy);
//
//	}
////
////	@LoginWith(id = 1)
////	@Test
////	public void whenUpdateName_thenReturnCashierObject() throws Exception {
////
////		Flatmate flatmate = getFlatmateLogged();
////		Dashboard dashboard = flatmate.getDashboard();
////
////		Cashier energy = createCashier(dashboard, 1l, "Energy", 12.3);
////
////		when(cashierRepository.findByDashboardAndId(dashboard, 1l)).thenReturn(Optional.of(energy));
////		when(cashierRepository.save(energy)).thenReturn(energy);
////
////		Cashier cashier = cashierService.update(dashboard, 1l, "Energy UP");
////
////		assertThat(cashier.getId(), is(1l));
////		assertThat(cashier.getName(), is("Energy UP"));
////		assertThat(cashier.getStarted(), is(BigDecimal.valueOf(12.3)));
////		assertThat(cashier.getBalance(), is(BigDecimal.valueOf(12.3)));
////
////	}
////
////	@LoginWith(id = 1)
////	@Test(expected = EntityNotFoundException.class)
////	public void whenUpdateName_thenThrowEntityNotFoundException() throws Exception {
////
////		Flatmate flatmate = getFlatmateLogged();
////		Dashboard dashboard = flatmate.getDashboard();
////
////		when(cashierRepository.findByDashboardAndId(dashboard, 99l)).thenReturn(Optional.empty());
////
////		cashierService.update(dashboard, 99l, "Energy UP");
////
////	}
////
////	@LoginWith(id = 2)
////	@Test(expected = AccessDeniedException.class)
////	public void whenUpdateName_thenThrowAccessDeniedException() throws Exception {
////
////		Flatmate flatmate = createFlatmate(1l, "none", "none");
////		userDashboard(flatmate);
////
////		Dashboard dashboard = flatmate.getDashboard();
////
////		Cashier energy = createCashier(dashboard, 1l, "Energy", 12.3);
////
////		when(cashierRepository.save(any(Cashier.class))).thenReturn(energy);
////
////		cashierService.update(1l, "Energy UP");
////
////	}
//
//	@LoginWith(id = 1)
//	@Test
//	public void whenDelete_thenReturnVoid() throws Exception {
//
//		Flatmate flatmate = getFlatmateLogged();
//		Dashboard dashboard = flatmate.getDashboard();
//
//		Cashier energy = createCashier(dashboard, 1l, "Energy", 12.3);
//
//		when(cashierRepository.findByDashboardAndId(dashboard, 1l)).thenReturn(Optional.of(energy));
//		when(transactionRepository.findByDashboardAndCashier(eq(dashboard), any(Cashier.class)))
//				.thenReturn(new ArrayList<Transaction>());
//		when(dashboardRepository.save(dashboard)).thenReturn(dashboard);
//
//		cashierService.delete(dashboard, 1l);
//
//		assertThat(dashboard.getCashiers(), empty());
//
//	}
//
//	@LoginWith(id = 1)
//	@Test(expected = EntityNotFoundException.class)
//	public void whenDelete_thenThrowEntityNotFoundException() throws Exception {
//
//		Flatmate flatmate = getFlatmateLogged();
//		Dashboard dashboard = flatmate.getDashboard();
//
//		when(cashierRepository.findByDashboardAndId(dashboard, 1l)).thenReturn(Optional.empty());
//
//		cashierService.delete(dashboard, 1l);
//
//	}
//
//	@LoginWith(id = 2)
//	@Test(expected = AccessDeniedException.class)
//	public void whenDelete_thenThrowAccessDeniedException() throws Exception {
//
//		Flatmate flatmate = createFlatmate(1l, "none", "none");
//		userDashboard(flatmate);
//
//		cashierService.delete(flatmate.getDashboard(), 1l);
//
//	}

}
