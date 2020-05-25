package br.com.cashhouse.tests.integration.resource.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.cashhouse.tests.integration.resource.cashier.DeleteTest;
import br.com.cashhouse.tests.integration.resource.cashier.GetTest;
import br.com.cashhouse.tests.integration.resource.cashier.PatchTest;
import br.com.cashhouse.tests.integration.resource.cashier.PostTest;
import br.com.cashhouse.tests.integration.resource.cashier.PutTest;

@RunWith(Suite.class)
@SuiteClasses({
	GetTest.class,
	PostTest.class,
	PutTest.class,
	PatchTest.class,
	DeleteTest.class
	})
public class CashierTests {

}
