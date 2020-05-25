package br.com.cashhouse.flatmate.rest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.cashhouse.core.model.Flatmate;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EntityFlatmate {

    @NotNull
    @NotEmpty
	private String nickname;
    
    public Flatmate toEntity(){
    	return new Flatmate(nickname);
    }

}
