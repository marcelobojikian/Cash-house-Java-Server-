package br.com.cashhouse.util.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.cashhouse.core.exception.EntityNotFoundException;
import br.com.cashhouse.core.exception.InvalidOperationException;
import br.com.cashhouse.util.service.I18nService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {

	@Autowired
	private I18nService localeService;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<ErrorResponse> methodArgumentNotValidException(HttpServletRequest request,
			MethodArgumentNotValidException e) {

		log.debug(e.getMessage(), e);

		BindingResult result = e.getBindingResult();
		FieldError fieldError = result.getFieldErrors().iterator().next();

		String message = localeService.getMessage("dto.field.invalid", fieldError.getField(),
				fieldError.getDefaultMessage());

		return buildResponse(message, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(InvalidOperationException.class)
	public ResponseEntity<ErrorResponse> invalidOperationException(InvalidOperationException ex) {
		return buildResponse(ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> entityNotFoundException(EntityNotFoundException ex) {
		return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> entityAccessDeniedHandler(AccessDeniedException ex) {
		return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(InvalidDataAccessResourceUsageException.class)
	public ResponseEntity<ErrorResponse> entityInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException ex) {
		return buildResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<ErrorResponse> buildResponse(String message, HttpStatus status) {
		ErrorResponse error = new ErrorResponse(status, message);
		return new ResponseEntity<>(error, status);
	}

}
