package br.com.cashhouse.transaction.rest.converter;

import org.springframework.core.convert.converter.Converter;

import br.com.cashhouse.core.model.Transaction.Action;

public class ActionToEnumConverter implements Converter<String, Action> {

	@Override
	public Action convert(String source) {
		return Action.valueOf(source.toUpperCase());
	}

}
