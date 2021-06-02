package com.delivery.api.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(NotFoundException.class)
	protected ResponseEntity<Object> tratarNotFoundException(NotFoundException ex, WebRequest request) {
		return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> tratarConflictException(DataIntegrityViolationException ex, WebRequest request) {
		return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String mensagem = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente";
		
		return handleValidatorInternal(ex, request, mensagem, ex.getBindingResult());
	}
	
	private ResponseEntity<Object> handleValidatorInternal(Exception ex, WebRequest request, String message, BindingResult bindingResult) {
		List<ApiValidationExceptionResponse.Error> listErrors = bindingResult.getFieldErrors().stream()
			.map(campo -> {
				String msg = messageSource.getMessage(campo, LocaleContextHolder.getLocale());
				ApiValidationExceptionResponse.Error error = new ApiValidationExceptionResponse.Error();
				error.setField(campo.getField());
				error.setMessage(msg);
				return error;
			}).collect(Collectors.toList());
		
		ApiValidationExceptionResponse response = new ApiValidationExceptionResponse();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setMessage(message);
		response.setErrors(listErrors);
		
		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);

	}
}
