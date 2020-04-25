package br.com.cashhouse.flatmate.rest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EntityFlatmate {

    @NotNull
    @NotEmpty
	private String email;

    @NotNull
    @NotEmpty
	private String nickname;
    
    @NotNull
	private boolean firstStep;
    
    @NotNull
	private boolean guestStep;

    @NotNull
	private Long dashboard;
    
    public Flatmate toEntity(){
    	
    	Dashboard dashboardEntity = new Dashboard();
    	dashboardEntity.setId(dashboard);

    	Flatmate entity = new Flatmate(email, nickname);
    	entity.setFirstStep(firstStep);
    	entity.setGuestStep(guestStep);
    	entity.setDashboard(dashboardEntity);
    	
    	return entity;
    	
    }

}
