package br.com.cashhouse.transaction.rest.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.transaction.dto.CreateTransaction;
import br.com.cashhouse.transaction.rest.dto.Content;
import br.com.cashhouse.transaction.rest.dto.GroupByResponse;
import br.com.cashhouse.transaction.service.TransactionService;
import br.com.cashhouse.util.service.RestSession;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

	private static final String DASHBOARD_ID = "dashboard";
	
	@Autowired
	private RestSession restSession;

	@Autowired
	private TransactionService transactionService;

	@GetMapping("")
	@ApiOperation(value = "Return a list with all transactions", response = Transaction[].class)
	public ResponseEntity<Object> findAll(
	//@formatter:off
			@RequestHeader(value = DASHBOARD_ID, required = false) String dashboardHeader,
			@ApiIgnore @QuerydslPredicate(root = Transaction.class) Predicate predicate, 
			@ApiIgnore 
			@SortDefaults({
		          @SortDefault(sort = "createdDate", direction = Direction.DESC),
		          @SortDefault(sort = "id", direction = Direction.ASC)
		      })
			@PageableDefault(page = 0, size = 20) Pageable pageable,
			@RequestParam(required = false, defaultValue = "none") String group) {
	//@formatter:on
		
		Dashboard dashboard = restSession.getDashboard(dashboardHeader);

		Page<Transaction> result = transactionService.findAll(dashboard, predicate, pageable);

		long totalElements = result.getTotalElements();
		int numberOfElements = result.getNumberOfElements();

		if (result.getContent().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		HttpStatus httpStatus = numberOfElements < totalElements ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK;

		if (group.equals("createdDate")) {

			List<Content<Transaction>> list = groupByCreatedDate(result);
			GroupByResponse<Content<Transaction>> pageFormated = new GroupByResponse<>(list, result);

			return new ResponseEntity<>(pageFormated, httpStatus);

		} else {
			return new ResponseEntity<>(result, httpStatus);
		}

	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Return a transaction entity by id", response = Transaction.class)
	public Transaction findOne(@PathVariable Long id) {
		return transactionService.findById(id);
	}

	@PostMapping("/deposit")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Returns a created deposit transaction entity", response = Transaction.class)
	public Transaction createDepoist(@RequestHeader(value = DASHBOARD_ID, required = false) String dashboardHeader, 
			@RequestBody @Valid CreateTransaction content) {
		
		Dashboard dashboard = restSession.getDashboard(dashboardHeader);
		
		return transactionService.createDeposit(dashboard, content);

	}

	@PostMapping("/withdraw")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Returns a created withdraw transaction entity", response = Transaction.class)
	public Transaction createWithdraw(@RequestHeader(value = DASHBOARD_ID, required = false) String dashboardHeader, 
			@RequestBody @Valid CreateTransaction content) {
		
		Dashboard dashboard = restSession.getDashboard(dashboardHeader);
		
		return transactionService.createwithdraw(dashboard, content);

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Return status OK when deleted", response = Transaction.class)
	public void detele(@RequestHeader(value = DASHBOARD_ID, required = false) String dashboardHeader, @PathVariable Long id) {
		
		Dashboard dashboard = restSession.getDashboard(dashboardHeader);
		
		transactionService.delete(dashboard, id);
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
