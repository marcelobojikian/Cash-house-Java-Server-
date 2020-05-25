package br.com.cashhouse.cashier.rest.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionCashier {

	@NotEmpty
	String typePermission;

	@NotNull
	Boolean value;

	List<String> flatmates;

}
