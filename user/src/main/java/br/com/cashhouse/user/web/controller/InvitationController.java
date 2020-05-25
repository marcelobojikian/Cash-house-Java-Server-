package br.com.cashhouse.user.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.Acl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.user.dto.InviteUser;
import br.com.cashhouse.user.service.DashboardService;
import br.com.cashhouse.user.service.InvitationService;
import br.com.cashhouse.user.service.ProfileService;
import br.com.cashhouse.user.web.dto.GuestDTO;
import br.com.cashhouse.util.service.WebSession;

@Controller("WebGuestInvitationController")
@RequestMapping("/invitations")
public class InvitationController {

	private static final String NEW = "invitations/new";
	private static final String LIST = "invitations/list";
	private static final String REDIRECT_INDEX = "redirect:/invitations";
	
	@Autowired
	private WebSession webSession;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private InvitationService invitationService;

	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping("")
	public String index(@RequestParam(value = "dashboard", required = false) Long dashboardId, Model model, HttpSession session) {
		
		List<Profile> guests = dashboardService.getGuests();
		
		if(guests == null) {
			model.addAttribute("entities", Collections.emptyList());
		} else {

			Acl acl = dashboardService.findPermission();

			List<GuestDTO> entities = new ArrayList<>();
			for (Profile profile : guests) {
				GuestDTO dto = new GuestDTO(profile, acl.getEntries());
				entities.add(dto);
			}
			
			model.addAttribute("entities", entities);
			
		}
		
		return LIST;
		
	}

	@GetMapping("/new")
	public String create(Model model) {
		InviteUser user = new InviteUser();
		user.setType("guest");
        model.addAttribute("user", user);
		return NEW;
	}
	
	// ACTIONS
	
	@PostMapping("/create")
	public String insert(@ModelAttribute("user") @Valid InviteUser user, Errors errors, Model model) {

        if (errors.hasErrors()) {
            return NEW;
        }
		
		Dashboard dashboard = webSession.getDashboard();
		
		String username = user.getUsername();
		String type = user.getType();
		
		try {

			invitationService.add(dashboard.getId(), username, type);
			
			return REDIRECT_INDEX;
			
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
            return NEW;
		}
		
	}
	
	@PostMapping("/change")
	public String insert(@RequestParam Long id, @RequestParam String type, RedirectAttributes redirectAttributes) {
		
		try {
			
			Profile profile = profileService.findById(id);
			
			invitationService.update(profile.getUsername(), type);
			
		} catch (Exception e) {
			redirectAttributes.addAttribute("message", e.getMessage());
		}
		
		return REDIRECT_INDEX;
		
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		
		Dashboard dashboard = webSession.getDashboard();
		
		try {
			
			Profile profile = profileService.findById(id);
			
			invitationService.remove(dashboard.getId(), profile.getUsername());
			
		} catch (Exception e) {
			redirectAttributes.addAttribute("message", e.getMessage());
		}
		
		return REDIRECT_INDEX;
		
	}

}
