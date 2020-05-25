package br.com.cashhouse.tests.integration.resource.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	br.com.cashhouse.tests.integration.resource.cashier.PostTest.class,
	br.com.cashhouse.tests.integration.resource.flatmate.PostTest.class,
	br.com.cashhouse.tests.integration.resource.transaction.PostDepositTest.class,
	br.com.cashhouse.tests.integration.resource.transaction.PostWithdrawTest.class
	})
public class AllPostRequestTests {

}
