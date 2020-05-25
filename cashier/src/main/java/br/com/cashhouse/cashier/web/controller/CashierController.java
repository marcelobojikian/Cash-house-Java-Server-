package br.com.cashhouse.cashier.web.controller;

import java.math.BigDecimal;
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

import br.com.cashhouse.cashier.rest.dto.CreateCashier;
import br.com.cashhouse.cashier.rest.dto.UpdateCashier;
import br.com.cashhouse.cashier.service.CashierService;
import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.util.service.WebSession;

@Controller("WebCashierController")
@RequestMapping("/cashiers")
public class CashierController {

	private static final String NEW = "cashiers/new";
	private static final String EDIT = "cashiers/edit";
	private static final String LIST = "cashiers/list";
	private static final String REDIRECT_INDEX = "redirect:/cashiers";
	
	@Autowired
	private WebSession webSession;

	@Autowired
	private CashierService cashierService;
	
	@GetMapping("")
	public String index(@RequestParam(value = "dashboard", required = false) Long dashboardId, Model model, HttpSession session) {
		
		if(dashboardId != null) {
			webSession.changeDashboard(dashboardId);
		}
		
		Dashboard dashboard = webSession.getDashboard();
		session.setAttribute("dashboard", dashboard);
		
		List<Cashier> entities = cashierService.findAll(dashboard);
		
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
		
		model.addAttribute("cashier", new CreateCashier());
		
		return NEW;
		
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		
		Cashier entity = cashierService.findById(id);
		UpdateCashier dto = new UpdateCashier();
		dto.setName(entity.getName());
		
		model.addAttribute("id", id);
		model.addAttribute("cashier", dto);
		
		return EDIT;
		
	}
	
	// ACTIONS
	
	@PostMapping("/create")
	public String insertUser(@ModelAttribute("cashier") @Valid CreateCashier entity, Errors errors, Model model) {

        if (errors.hasErrors()) {
            return NEW;
        }
		
		Dashboard dashboard = webSession.getDashboard();
		
		String name = entity.getName();
		BigDecimal started = entity.getStarted();
		BigDecimal balance = entity.getBalance();
		
		try {

			cashierService.create(dashboard, name, started, balance);
			
			return REDIRECT_INDEX;
			
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
            return NEW;
		}
		
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") Long id, @ModelAttribute("cashier") @Valid UpdateCashier entity, Errors errors, Model model) {

        if (errors.hasErrors()) {
            return EDIT;
        }

		String name  = entity.getName();
		
		try {

			Cashier cashier = cashierService.findById(id);

			cashier.setName(name);

			cashierService.update(id, cashier);
			
			return REDIRECT_INDEX;
			
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
	        return EDIT;
		}
		
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		
		Dashboard dashboard = webSession.getDashboard();
		
		try {

			cashierService.delete(dashboard, id);
			
		} catch (Exception e) {
			redirectAttributes.addAttribute("message", e.getMessage());
		}
		
        return REDIRECT_INDEX;
		
	}

}
