package br.com.cashhouse.transaction.web.controller;

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

import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.transaction.dto.CreateTransaction;
import br.com.cashhouse.transaction.service.TransactionService;
import br.com.cashhouse.util.service.WebSession;

@Controller("WebTransactionController")
@RequestMapping("/transactions")
public class TransactionController {
	
	private static final String INDEX_ERROR_PARAM = "message";

	private static final String NEW = "transactions/new";
	private static final String LIST = "transactions/list";
	private static final String REDIRECT_INDEX = "redirect:/transactions";
	
	@Autowired
	private WebSession webSession;

	@Autowired
	private TransactionService transactionService;
	
	@GetMapping("")
	public String index(@RequestParam(value = "dashboard", required = false) Long dashboardId, Model model, HttpSession session) {
		
		if(dashboardId != null) {
			webSession.changeDashboard(dashboardId);
		}
		
		Dashboard dashboard = webSession.getDashboard();
		session.setAttribute("dashboard", dashboard);
		
		List<Transaction> entities = transactionService.findAll(dashboard);
		
		if(entities == null) {
			model.addAttribute("entities", Collections.emptyList());
		} else {

			model.addAttribute("invitations", webSession.getInvitations());
			model.addAttribute("dashboard", dashboard);
			model.addAttribute("entities", entities);
			
		}
		
		return LIST;
		
	}

	@GetMapping("/new/deposit")
	public String createDeposit(Model model) {
		return create(model, "DEPOSIT", new CreateTransaction());
	}

	@GetMapping("/new/withdraw")
	public String createWithdraw(Model model) {
		return create(model, "WITHDRAW", new CreateTransaction());
	}
	
	private String create(Model model, String type, CreateTransaction entity) {
		
		Dashboard dashboard = webSession.getDashboard();
		List<Cashier> cashiers = transactionService.findAllCashier(dashboard);
		List<Flatmate> flatmates = transactionService.findMyFlatmate(dashboard);

		model.addAttribute("type", type);
		model.addAttribute("cashiers", cashiers);
		model.addAttribute("flatmates", flatmates);
		model.addAttribute("transaction", entity);
		
		return NEW;
		
	}
	
	// ACTIONS

	@PostMapping("/{id}/finish")
	public String finish(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		
		try {
			
			transactionService.finish(id);
			
		} catch (Exception e) {
			redirectAttributes.addAttribute(INDEX_ERROR_PARAM, e.getMessage());
		}
		
		return REDIRECT_INDEX;
		
	}

	@PostMapping("/create")
	public String insert(@RequestParam String type, @ModelAttribute("transaction") @Valid CreateTransaction entity,
			Errors errors, Model model, RedirectAttributes redirectAttributes) {

		Dashboard dashboard = webSession.getDashboard();
	
        if (errors.hasErrors()) {
            return create(model, type, entity);
        }
		
		try {
			
			if(type.equalsIgnoreCase("DEPOSIT")) {
				transactionService.createDeposit(dashboard, entity);
			} else if (type.equalsIgnoreCase("WITHDRAW")) {
				transactionService.createwithdraw(dashboard, entity);
			} else {
				throw new UnsupportedOperationException("Type of Transaction is invalid");
			}
			
		} catch (Exception e) {
			redirectAttributes.addAttribute(INDEX_ERROR_PARAM, e.getMessage());
		}
		
		return REDIRECT_INDEX;
		
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		
		Dashboard dashboard = webSession.getDashboard();
		
		try {
			
			transactionService.delete(dashboard, id);
			
		} catch (Exception e) {
			redirectAttributes.addAttribute(INDEX_ERROR_PARAM, e.getMessage());
		}
		
		return REDIRECT_INDEX;
		
	}

}
