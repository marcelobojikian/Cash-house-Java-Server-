package br.com.cashhouse.tests.integration.resource.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	br.com.cashhouse.tests.integration.resource.cashier.GetTest.class,
	br.com.cashhouse.tests.integration.resource.dashboard.GetTest.class,
	br.com.cashhouse.tests.integration.resource.flatmate.GetTest.class,
	br.com.cashhouse.tests.integration.resource.invitation.GetTest.class,
	br.com.cashhouse.tests.integration.resource.transaction.GetTest.class,
	br.com.cashhouse.tests.integration.resource.transaction.GetGroupTest.class,
	br.com.cashhouse.tests.integration.resource.user.GetTest.class,
	})
public class AllGetRequestTests {

}
