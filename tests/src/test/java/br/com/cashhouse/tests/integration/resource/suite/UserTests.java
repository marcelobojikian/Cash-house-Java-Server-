package br.com.cashhouse.tests.integration.resource.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.cashhouse.tests.integration.resource.user.PutTest;

@RunWith(Suite.class)
@SuiteClasses({
	br.com.cashhouse.tests.integration.resource.user.GetTest.class,
	br.com.cashhouse.tests.integration.resource.dashboard.GetTest.class,
	br.com.cashhouse.tests.integration.resource.invitation.GetTest.class,
	PutTest.class
	})
public class UserTests {

}
