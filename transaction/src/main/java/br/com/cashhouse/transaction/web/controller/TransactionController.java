package br.com.cashhouse.transaction.web.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
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

import com.querydsl.core.types.Predicate;

import br.com.cashhouse.core.model.Cashier;
import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Flatmate;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.transaction.dto.CreateTransaction;
import br.com.cashhouse.transaction.rest.dto.Content;
import br.com.cashhouse.transaction.rest.dto.GroupByResponse;
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
	
	@GetMapping("/old")
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
	
	@GetMapping("")
	public String index(
			@RequestParam(value = "dashboard", required = false) Long dashboardId, 
			@QuerydslPredicate(root = Transaction.class) Predicate predicate,
			@SortDefaults({
		          @SortDefault(sort = "createdDate", direction = Direction.DESC),
		          @SortDefault(sort = "id", direction = Direction.ASC)
		      })
			@PageableDefault(page = 0, size = 20) Pageable pageable,
			Model model, HttpSession session) {
		
		if(dashboardId != null) {
			webSession.changeDashboard(dashboardId);
		}
		
		Dashboard dashboard = webSession.getDashboard();
		session.setAttribute("dashboard", dashboard);
		
		Page<Transaction> entities = transactionService.findAll(dashboard, predicate, pageable);
		List<Cashier> cashiers = transactionService.findAllCashier(dashboard);
		List<Flatmate> flatmates = transactionService.findAllFlatmate(dashboard);

		model.addAttribute("invitations", webSession.getInvitations());
		model.addAttribute("dashboard", dashboard);
		
		if(entities == null) {
			model.addAttribute("entities", Collections.emptyList());
		} else {

	        int totalElements = entities.getNumberOfElements();
	        if (totalElements <= 0) {
	        	Pageable initialPageable = PageRequest.of(0, pageable.getPageSize());
	        	entities = transactionService.findAll(dashboard, predicate, initialPageable);
	        }

			List<Content<Transaction>> list = groupByCreatedDate(entities);
			GroupByResponse<Content<Transaction>> groupDate = new GroupByResponse<>(list, entities);
			
        	model.addAttribute("entities", groupDate);
			
		}

		model.addAttribute("cashiers", cashiers);
		model.addAttribute("flatmates", flatmates);
		
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
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		
		Dashboard dashboard = webSession.getDashboard();
		
		try {
			
			transactionService.delete(dashboard, id);
			
		} catch (Exception e) {
			redirectAttributes.addAttribute(INDEX_ERROR_PARAM, e.getMessage());
		}
		
		return REDIRECT_INDEX;
		
	}

	private List<Content<Transaction>> groupByCreatedDate(Page<Transaction> list) {
		Map<LocalDate, List<Transaction>> groupedByDate = new LinkedHashMap<>();
		for (Transaction transacation : list) {
			LocalDate key = transacation.getCreatedDate().toLocalDate();
			if (!groupedByDate.containsKey(key)) {
				groupedByDate.put(key, new LinkedList<>());
			}
			List<Transaction> values = groupedByDate.get(key);
			values.add(transacation);
		}

		return apply(groupedByDate);
	}

	private <T> List<Content<T>> apply(Map<LocalDate, List<T>> data) {
		List<Content<T>> list = new ArrayList<>();
		for (Map.Entry<LocalDate, List<T>> entry : data.entrySet()) {
			list.add(new Content<T>(entry.getKey(), entry.getValue()));
		}
		return list;
	}

}
