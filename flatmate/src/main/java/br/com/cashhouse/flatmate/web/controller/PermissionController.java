package br.com.cashhouse.flatmate.web.controller;

import java.util.List;

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
import br.com.cashhouse.flatmate.service.FlatmateService;
import br.com.cashhouse.flatmate.web.dto.GroupPermission;
import br.com.cashhouse.flatmate.web.dto.UpdatePermission;
import br.com.cashhouse.util.service.WebSession;

@Controller("WebFlatmatePermissionController")
@RequestMapping("/flatmates/{id}/permissions")
@PreAuthorize("hasRole('ADMIN')")
public class PermissionController {
	
	private static final String INDEX = "redirect:/flatmates";

	@Autowired
	private FlatmateService flatmateService;
	
	@Autowired
	private WebSession webSession;
	
	@GetMapping("")
	public String home(@PathVariable("id") Long id, Model model) {

		Acl acl = flatmateService.findPermission(id);
		
		List<Profile> guests = webSession.getGuests();
		
		GroupPermission groupPermission = new GroupPermission(guests, acl);
		
		model.addAttribute("id", id);
		model.addAttribute("groupPermission", groupPermission);
		return "flatmates/permissions/list";
	}
	
	// ACTIONS

	@PostMapping("/update")
	public String updateUser(@PathVariable("id") Long id, @ModelAttribute("groupPermission") @Valid GroupPermission groupPermission, Model model) {
		
		List<UpdatePermission> updates = groupPermission.getUpdates();
		
		for (UpdatePermission up : updates) {
			flatmateService.updatePermission(id, up.getUsername(),up.getPermissions());
		}

		return INDEX;
	}

}
