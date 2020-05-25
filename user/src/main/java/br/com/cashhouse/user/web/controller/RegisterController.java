package br.com.cashhouse.user.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cashhouse.user.dto.RegisterUser;
import br.com.cashhouse.user.service.ProfileService;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	private ProfileService profileService;

    @GetMapping("")
    public String register(Model model) {
        model.addAttribute("user", new RegisterUser());
        return "register";
    }
    
    @PostMapping("")
    public String registration(@ModelAttribute("user") @Valid RegisterUser user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register";
        }
        
        profileService.create(user.getUsername(), user.getPassword());

        return "redirect:/login";
    }

}
