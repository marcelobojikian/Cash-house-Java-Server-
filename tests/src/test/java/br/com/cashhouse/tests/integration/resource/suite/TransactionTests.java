package br.com.cashhouse.tests.integration.resource.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.cashhouse.tests.integration.resource.transaction.DeleteTest;
import br.com.cashhouse.tests.integration.resource.transaction.GetTest;
import br.com.cashhouse.tests.integration.resource.transaction.GetGroupTest;
import br.com.cashhouse.tests.integration.resource.transaction.PostDepositTest;
import br.com.cashhouse.tests.integration.resource.transaction.PostWithdrawTest;
import br.com.cashhouse.tests.integration.resource.transaction.action.PostFinish;

@RunWith(Suite.class)
@SuiteClasses({
	GetTest.class,
	GetGroupTest.class,
	PostDepositTest.class,
	PostWithdrawTest.class,
	DeleteTest.class,
	PostFinish.class
	})
public class TransactionTests {

}
