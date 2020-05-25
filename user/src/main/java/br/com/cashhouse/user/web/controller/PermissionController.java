package br.com.cashhouse.user.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.Acl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.user.service.DashboardService;
import br.com.cashhouse.user.service.ProfileService;
import br.com.cashhouse.user.web.dto.UpdatePermission;

@Controller("WebDashboardPermissionController")
@RequestMapping("/invitations/{id}/permissions")
@PreAuthorize("hasRole('ADMIN')")
public class PermissionController {
	
	private static final String INDEX = "redirect:/invitations";

	@Autowired
	private DashboardService dashboardService;

	@Autowired
	private ProfileService profileService;
	
	@GetMapping("")
	public String home(@PathVariable("id") Long id, Model model) {

		Acl acl = dashboardService.findPermission();
		
		Profile profile = profileService.findById(id);
		
		UpdatePermission updatePermission = new UpdatePermission(profile.getUsername(), acl.getEntries());
		
		model.addAttribute("id", id);
		model.addAttribute("updatePermission", updatePermission);
		return "invitations/permissions/list";
	}
	
	// ACTIONS

	@PostMapping("/update")
	public String updateUser(@PathVariable("id") Long id, @ModelAttribute("updatePermission") @Valid UpdatePermission updatePermission, Model model) {
		
		dashboardService.updatePermission(updatePermission.getUsername(),updatePermission.getPermissions());

		return INDEX;
	}

}
