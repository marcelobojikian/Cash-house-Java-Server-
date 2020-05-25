package br.com.cashhouse.transaction.converter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cashhouse.core.model.Transaction.Action;
import br.com.cashhouse.core.model.Transaction.Status;
import br.com.cashhouse.transaction.App;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TransactionConverterTest {

	@Autowired
	ConversionService conversionService;

	@Test
	public void whenConvertStringToStatus_thenSuccess() {

		assertThat(conversionService.convert("sended", Status.class), is(Status.SENDED));
		assertThat(conversionService.convert("finished", Status.class), is(Status.FINISHED));

	}

	@Test
	public void whenConvertStringToAction_thenSuccess() {

		assertThat(conversionService.convert("deposit", Action.class), is(Action.DEPOSIT));
		assertThat(conversionService.convert("withdraw", Action.class), is(Action.WITHDRAW));

	}

}