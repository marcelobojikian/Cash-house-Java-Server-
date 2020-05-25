package br.com.cashhouse.tests.integration.resource.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.cashhouse.tests.integration.resource.flatmate.DeleteTest;
import br.com.cashhouse.tests.integration.resource.flatmate.GetTest;
import br.com.cashhouse.tests.integration.resource.flatmate.PatchTest;
import br.com.cashhouse.tests.integration.resource.flatmate.PostTest;
import br.com.cashhouse.tests.integration.resource.flatmate.PutTest;

@RunWith(Suite.class)
@SuiteClasses({
	GetTest.class,
	PostTest.class,
	PutTest.class,
	PatchTest.class,
	DeleteTest.class
	})
public class FlatmateTests {

}
