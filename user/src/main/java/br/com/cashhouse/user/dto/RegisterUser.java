package br.com.cashhouse.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterUser {

	@NotEmpty
	@Email
	private String username;

	@NotEmpty
	private String password;

}
