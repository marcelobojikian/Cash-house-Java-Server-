package br.com.cashhouse.core.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmailRegisteredException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final String key;
	private final Object[] params;

	public EmailRegisteredException(String message) {
		super(message);
		this.key = message;
		this.params = new Object[]{};
	}

	public EmailRegisteredException(String key, Object... params) {
		this.key = key;
		this.params = params;
	}

}
