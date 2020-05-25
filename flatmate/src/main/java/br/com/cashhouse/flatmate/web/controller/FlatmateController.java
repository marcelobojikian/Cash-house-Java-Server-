package br.com.cashhouse.flatmate.web.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.flatmate.dto.CreateFlatmate;
import br.com.cashhouse.flatmate.dto.UpdateFlatmate;
import br.com.cashhouse.flatmate.service.FlatmateService;
import br.com.cashhouse.util.service.WebSession;

@Controller("WebFlatmateController")
@RequestMapping("/flatmates")
public class FlatmateController {

	private static final String NEW = "flatmates/new";
	private static final String EDIT = "flatmates/edit";
	private static final String LIST = "flatmates/list";
	private static final String REDIRECT_INDEX = "redirect:/flatmates";
	
	@Autowired
	private WebSession webSession;

	@Autowired
	private FlatmateService flatmateService;
	
	@GetMapping("")
	public String index(@RequestParam(value = "dashboard", required = false) Long dashboardId, Model model, HttpSession session) {
		
		if(dashboardId != null) {
			webSession.changeDashboard(dashboardId);
		}
		
		Dashboard dashboard = webSession.getDashboard();
		session.setAttribute("dashboard", dashboard);
		
		List<Flatmate> entities = flatmateService.findAll(dashboard);
		
		if(entities == null) {
			model.addAttribute("entities", Collections.emptyList());
		} else {
			
			model.addAttribute("invitations", webSession.getInvitations());
			model.addAttribute("dashboard", dashboard);
			model.addAttribute("entities", entities);
			
		}
		
		return LIST;
		
	}

	@GetMapping("/new")
	public String create(Model model) {
		
		model.addAttribute("flatmate", new CreateFlatmate());
		
		return NEW;
		
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		
		Flatmate entity = flatmateService.findById( id);
		UpdateFlatmate dto = new UpdateFlatmate();
		dto.setNickname(entity.getNickname());
		
		model.addAttribute("id", id);
		model.addAttribute("flatmate", dto);
		
		return EDIT;
		
	}
	
	// ACTIONS
	
	@PostMapping("/create")
	public String insert(@ModelAttribute("flatmate") @Valid CreateFlatmate entity, Errors errors, Model model) {

        if (errors.hasErrors()) {
            return NEW;
        }
		
		Dashboard dashboard = webSession.getDashboard();

		String nickname = entity.getNickname();
		
		try {

			flatmateService.create(dashboard, nickname);
			
			return REDIRECT_INDEX;
			
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
            return NEW;
		}
		
	}

	@PostMapping("/update/{id}")
	public String update(@PathVariable("id") Long id, @ModelAttribute("flatmate") @Valid UpdateFlatmate entity, Errors errors, Model model) {

        if (errors.hasErrors()) {
            return EDIT;
        }
		
		String nickname = entity.getNickname();
		
		try {

			Flatmate flatmate = flatmateService.findById(id);
	
			flatmate.setNickname(nickname);
			
			flatmateService.update(id, flatmate);
			
			return REDIRECT_INDEX;
			
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
	        return EDIT;
		}
		
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		
		Dashboard dashboard = webSession.getDashboard();
		
		try {
		
			flatmateService.delete(dashboard, id);
			
		} catch (Exception e) {
			redirectAttributes.addAttribute("message", e.getMessage());
		}
		
        return REDIRECT_INDEX;
		
	}

}
