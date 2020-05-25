package br.com.cashhouse.flatmate.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateFlatmate {

	@NotBlank
	private String nickname;

}
