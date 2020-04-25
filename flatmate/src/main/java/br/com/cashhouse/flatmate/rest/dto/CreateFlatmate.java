package br.com.cashhouse.flatmate.rest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateFlatmate {

    @NotNull
    @NotEmpty
	private String email;

	private String nickname;

    @NotNull
    @NotEmpty
	private String password;

}
