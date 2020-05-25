package br.com.cashhouse.transaction.service;

import static br.com.cashhouse.test.util.factory.EntityFactory.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cashhouse.core.exception.EntityNotFoundException;
import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.core.model.Transaction.Action;
import br.com.cashhouse.core.repository.CashierRepository;
import br.com.cashhouse.test.util.oauth.LoginWith;
import br.com.cashhouse.test.util.service.ServiceAuthHelper;
import br.com.cashhouse.util.service.I18nService;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TransactionServiceImplTest extends ServiceAuthHelper {

//	@Autowired
//	private TransactionServiceImpl transactionServiceImpl;
//
//	@MockBean
//	private I18nService i18n;
//
//	@MockBean
//	private CashierRepository cashierRepository;

//	@LoginWith(id = 1)
//	@Test
//	public void whenApplyTransactionDeposit_thenReturnVoid() throws Exception {
//
//		Flatmate flatmate = getFlatmateLogged();
//		Dashboard dashboard = flatmate.getDashboard();
//
//		Cashier energy = createCashier(dashboard, 1l, "Energy", 12.3);
//
//		Transaction transaction = createTransaction(dashboard, 1l, 2.33, null, Action.DEPOSIT);
//		transaction.setCreateBy(flatmate);
//		transaction.setAssigned(flatmate);
//		transaction.setCashier(energy);
//
//		when(cashierRepository.findById(1l)).thenReturn(Optional.of(energy));
//		when(cashierRepository.save(any(Cashier.class))).thenReturn(energy);
//
//		transactionServiceImpl.applyTransaction(transaction);
//
//		verify(cashierRepository, times(1)).save(any(Cashier.class));
//
//	}
//
//	@LoginWith(id = 1)
//	@Test
//	public void whenApplyTransactionWithdraw_thenReturnVoid() throws Exception {
//
//		Flatmate flatmate = getFlatmateLogged();
//		Dashboard dashboard = flatmate.getDashboard();
//
//		Cashier energy = createCashier(dashboard, 1l, "Energy", 12.3);
//
//		Transaction transaction = createTransaction(dashboard, 1l, 2.33, null, Action.WITHDRAW);
//		transaction.setCreateBy(flatmate);
//		transaction.setAssigned(flatmate);
//		transaction.setCashier(energy);
//
//		when(cashierRepository.findById(1l)).thenReturn(Optional.of(energy));
//		when(cashierRepository.save(any(Cashier.class))).thenReturn(energy);
//
//		transactionServiceImpl.applyTransaction(transaction);
//
//		verify(cashierRepository, times(1)).save(any(Cashier.class));
//
//	}
//
//	@LoginWith(id = 1)
//	@Test(expected = EntityNotFoundException.class)
//	public void whenApplyTransaction_thenReturnVoid() throws Exception {
//
//		Flatmate flatmate = getFlatmateLogged();
//		Dashboard dashboard = flatmate.getDashboard();
//
//		Cashier energy = createCashier(dashboard, 1l, "Energy", 12.3);
//
//		Transaction transaction = createTransaction(dashboard, 1l, 2.33, null, Action.WITHDRAW);
//		transaction.setCreateBy(flatmate);
//		transaction.setAssigned(flatmate);
//		transaction.setCashier(energy);
//
//		when(cashierRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//		transactionServiceImpl.applyTransaction(transaction);
//
//	}

}
