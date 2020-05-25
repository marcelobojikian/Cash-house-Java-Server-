package br.com.cashhouse.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InviteUser {

	@NotEmpty(message = "Username field should not be empty")
	@Email
	private String username;

	private String type;

}
