package br.com.cashhouse.util.exception;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cashhouse.core.model.Dashboard;
import br.com.cashhouse.core.model.Transaction;
import br.com.cashhouse.core.model.Transaction.Action;
import br.com.cashhouse.core.model.Transaction.Status;
import br.com.cashhouse.core.exception.EntityNotFoundException;
import br.com.cashhouse.core.exception.InvalidOperationException;
import br.com.cashhouse.util.LoginWith;
import br.com.cashhouse.util.service.I18nService;
import lombok.Getter;
import lombok.Setter;

@RunWith(SpringRunner.class)
@WebMvcTest(RestExceptionHandlerTest.SampleController.class)
@ContextConfiguration(classes = { RestExceptionHandlerTest.SampleController.class, RestExceptionHandler.class,
		HttpServletRequest.class })
public class RestExceptionHandlerTest {

	DateTimeFormatter dateFormatter = DateTimeFormatter.BASIC_ISO_DATE;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private I18nService localeService;

	@LoginWith(roles = "ADMIN")
	@Test
	public void should_throw_MethodArgumentNotValidException() throws Exception {

		// @formatter:off
		MockHttpServletRequestBuilder request = get("/exception/bean/validator")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"value\": \"\"}")
				.characterEncoding("utf-8");
		
		when(localeService.getMessage(eq("dto.field.invalid"),eq("value"),eq("must not be empty"))).thenReturn("Field 'value' must not be empty");
		
		mockMvc.perform(request)
			      	.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
					.andExpect(jsonPath("$.error", is(HttpStatus.BAD_REQUEST.getReasonPhrase())))
					.andExpect(jsonPath("$.message", is("Field 'value' must not be empty")))
					.andExpect(jsonPath("$.timestamp", notNullValue()));
		// @formatter:on

	}

	@LoginWith(roles = "ADMIN")
	@Test
	public void should_throw_NoContentException() throws Exception {

		when(localeService.getMessage("body.no.content")).thenReturn("Body of request is invalid");

		// @formatter:off
		mockMvc.perform(get("/exception/no_content"))
			      	.andExpect(status().isNoContent());
		// @formatter:on

	}

	@LoginWith(roles = "ADMIN")
	@Test
	public void should_throw_InvalidOperationException() throws Exception {

		// @formatter:off
		mockMvc.perform(get("/exception/transaction_invalid/status"))
			      	.andExpect(status().isMethodNotAllowed())
					.andExpect(jsonPath("$.status", is(HttpStatus.METHOD_NOT_ALLOWED.value())))
					.andExpect(jsonPath("$.error", is(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())))
					.andExpect(jsonPath("$.message", is("message")))
					.andExpect(jsonPath("$.timestamp", notNullValue()));
		// @formatter:on

	}

	@LoginWith(roles = "ADMIN")
	@Test
	public void should_throw_EntityNotFoundException_Class() throws Exception {

		// @formatter:off
		mockMvc.perform(get("/exception/not_found"))
			      	.andExpect(status().isNotFound())
					.andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
					.andExpect(jsonPath("$.error", is(HttpStatus.NOT_FOUND.getReasonPhrase())))
					.andExpect(jsonPath("$.message", is("message")))
					.andExpect(jsonPath("$.timestamp", notNullValue()));
		// @formatter:on

	}

	@LoginWith(roles = "ADMIN")
	@Test
	public void should_throw_SpringAccessDeniedException() throws Exception {

		// @formatter:off
		mockMvc.perform(get("/exception/spring/access_denied"))
			      	.andExpect(status().isForbidden())
					.andExpect(jsonPath("$.status", is(HttpStatus.FORBIDDEN.value())))
					.andExpect(jsonPath("$.error", is(HttpStatus.FORBIDDEN.getReasonPhrase())))
					.andExpect(jsonPath("$.message", is("Spring AccessDeniedException")))
					.andExpect(jsonPath("$.timestamp", notNullValue()));
		// @formatter:on

	}

	@RestController
	@RequestMapping("/exception")
	public static class SampleController {

		@GetMapping("/bean/validator")
		public void throwMethodArgumentNotValidException(@RequestBody @Valid SimpleBean propertie) throws Exception {
		}

		@GetMapping("/no_content")
		public ResponseEntity<Object> throwNoContentException() throws Exception {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		@GetMapping("/transaction_invalid/status")
		public ResponseEntity<?> throwInvalidOperationException() throws Exception {
			throw new InvalidOperationException("message");
		}

		@GetMapping("/not_found")
		public ResponseEntity<?> throwEntityNotFoundException_Class() throws Exception {
			throw new EntityNotFoundException("message");
		}

		@GetMapping("/spring/access_denied")
		public ResponseEntity<?> throwSpringAccessDeniedException() throws Exception {
			throw new AccessDeniedException("Spring AccessDeniedException");
		}

	}

	@Getter
	@Setter
	public static class SimpleBean {
		@NotEmpty
		String value;
	}

	public static Transaction createTransaction(Long id, Double value, Status status, Action action) {

		Transaction transaction = new Transaction();
		transaction.setId(id);
		transaction.setValue(BigDecimal.valueOf(value));
		transaction.setStatus(status);
		transaction.setAction(action);

		return transaction;
	}

	public static Transaction createTransaction(Dashboard dashboard, Long id, Double value, Status status,
			Action action) {
		Transaction transaction = createTransaction(id, value, status, action);
		dashboard.getTransactions().add(transaction);
		return transaction;
	}

}
