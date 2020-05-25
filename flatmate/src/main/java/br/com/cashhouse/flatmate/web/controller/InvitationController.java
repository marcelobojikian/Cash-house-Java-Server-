package br.com.cashhouse.flatmate.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Acl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.flatmate.service.FlatmateService;
import br.com.cashhouse.flatmate.web.dto.GroupPermission;
import br.com.cashhouse.util.service.WebSession;

@Controller("WebFlatmateInvitationController")
@RequestMapping("/flatmates/{id}/invitations")
public class InvitationController {
	
	private static final String INDEX = "redirect:/flatmates";
	
	@Autowired
	private WebSession webSession;

	@Autowired
	private FlatmateService flatmateService;
	
	@GetMapping("")
	public String index(@RequestParam(value = "dashboard", required = false) Long dashboardId, @PathVariable("id") Long id, Model model, HttpSession session) {

		Acl acl = flatmateService.findPermission(id);
		
		Flatmate flatmate = flatmateService.findById(id);
		
		List<Profile> guests = webSession.getGuests();
		
		GroupPermission groupPermission = new GroupPermission(guests, acl);
		
		model.addAttribute("id", id);
		model.addAttribute("flatmate", flatmate);
		model.addAttribute("groupPermission", groupPermission);
		
		return "flatmates/invitations/list";
		
	}
	
	// ACTIONS
	
	@PostMapping("/enable")
	public String insert(@PathVariable("id") Long id, @RequestParam String username) {
		
		Map<Integer, Boolean> permissions = new HashMap<>();
		permissions.put(BasePermission.WRITE.getMask(), true);
		
		flatmateService.updatePermission(id, username, permissions);
		
		return INDEX;
		
	}

	@PostMapping("/disable")
	public String deleteUser(@PathVariable("id") Long id, @RequestParam String username) {
		
		Map<Integer, Boolean> permissions = new HashMap<>();
		permissions.put(BasePermission.WRITE.getMask(), false);
		
		flatmateService.updatePermission(id, username, permissions);
		
		return INDEX;
		
	}

}
