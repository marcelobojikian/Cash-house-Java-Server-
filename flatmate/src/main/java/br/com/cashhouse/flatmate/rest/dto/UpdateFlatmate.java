package br.com.cashhouse.flatmate.rest.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateFlatmate {

    @NotEmpty
	String nickname;

	String password;

}
