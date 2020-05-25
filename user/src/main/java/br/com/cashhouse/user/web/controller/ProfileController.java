package br.com.cashhouse.user.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.util.service.WebSession;

@Controller("WebProfileController")
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private WebSession webSession;

	@GetMapping("")
	public String register(Model model) {
		
		Profile profile = webSession.getProfileLogged();
		
		Dashboard dashboard = profile.getDashboard();
		
		model.addAttribute("username", profile.getUsername());
		model.addAttribute("dashboard", dashboard);
		
		return "profile/index";
	}

}
