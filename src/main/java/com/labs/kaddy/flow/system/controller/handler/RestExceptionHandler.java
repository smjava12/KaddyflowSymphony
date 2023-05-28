/**
 * 
 */
package com.labs.kaddy.flow.system.controller.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.common.net.HttpHeaders;
import com.labs.kaddy.flow.infra.exception.AbstractCodeAndAffectedFieldAwareException;
import com.labs.kaddy.flow.infra.exception.ApiAbortedException;
import com.labs.kaddy.flow.infra.exception.BadRequestParameterException;
import com.labs.kaddy.flow.infra.exception.WorkflowNotFoundException;
import com.labs.kaddy.flow.infra.service.ErrorMessageService;
import com.labs.kaddy.flow.system.dto.response.ErrorMessage;
import com.labs.kaddy.flow.system.dto.response.SystemMessage;
import com.labs.kaddy.flow.system.enums.Errors;



/**
 * @author Surjyakanta
 * Propagates different types of exception
 */

@Order
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	
	public static final Logger LOG = ESAPI.getLogger(RestExceptionHandler.class);
	private final ErrorMessageService messages;
	
	public RestExceptionHandler(ErrorMessageService messages) {
		this.messages = messages;
	}
	
	private ResponseEntity<Object> buildResponseEntity (ErrorMessage errorMsfg, HttpStatus httpStatus) {
		return new ResponseEntity<Object>(errorMsfg,httpStatus);
	}
	
	@ExceptionHandler(WorkflowNotFoundException.class)
	protected ResponseEntity<Object> handleWorkflowNotFoundException(WorkflowNotFoundException e) {
		ErrorMessage errorMsg = CreateErrorMessage(e);
		LOG.error(Logger.EVENT_FAILURE, "API aborted, return not found error ", e);
		return buildResponseEntity(errorMsg, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadRequestParameterException.class)
	protected ResponseEntity<Object> handleBadRequestParameterException(BadRequestParameterException e) {
		ErrorMessage errorMsg = CreateErrorMessage(e);
		LOG.error(Logger.EVENT_FAILURE, "API aborted, return not found error ", e);
		return buildResponseEntity(errorMsg, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ApiAbortedException.class)
	protected ResponseEntity<Object> handleApiAbortedException(ApiAbortedException e) {
		ErrorMessage errorMsg = CreateErrorMessage(e);
		LOG.error(Logger.EVENT_FAILURE, "API aborted, return not found error ", e);
		return buildResponseEntity(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ErrorMessage CreateErrorMessage(AbstractCodeAndAffectedFieldAwareException e) {
		List<SystemMessage> systemMessage = createSystemMessage(e.getCode(), e.getAffectedField());
		return new ErrorMessage(systemMessage);
	}

	private List<SystemMessage> createSystemMessage(Errors code, String affectedField) {
		String message = messages.get(code);
		if(StringUtils.hasLength(affectedField)) {
			message = message.replace("%1", affectedField);
		}
		return Arrays.asList(new SystemMessage(code.name(),message));
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		String message = messages.get(Errors.SVC_10_400_3);
		ErrorMessage errorMessage = new ErrorMessage(
				Arrays.asList(new SystemMessage(Errors.SVC_10_400_3.name(), message.replace("%1",ex.getName()))));
		return buildResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
	}

	protected ResponseEntity<Object> handleMissingPathVariable(
			MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = messages.get(Errors.SVC_10_400_1);
		ErrorMessage errorMessage = new ErrorMessage(
				Arrays.asList(new SystemMessage(Errors.SVC_10_400_1.name(), message.replace("%1",ex.getVariableName()))));
		return buildResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
	}

	/*
	 * @ExceptionHandler(MethodArgumentNotValidException.class) protected
	 * ResponseEntity<Object> handleMethodArgumentNotValid(
	 * MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
	 * WebRequest request) {
	 * 
	 * Map<String, List<String>> body = new HashMap<>();
	 * 
	 * List<String> errors = ex.getBindingResult() .getFieldErrors() .stream()
	 * .map(DefaultMessageSourceResolvable::getDefaultMessage)
	 * .collect(Collectors.toList());
	 * 
	 * body.put("errors", errors);
	 * 
	 * return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST); }
	 */

	@ExceptionHandler(ConstraintViolationException.class)
	  public ResponseEntity<?> constraintViolationException(ConstraintViolationException ex, WebRequest request) {
	    List<String> errors = new ArrayList<>();

	    ex.getConstraintViolations().forEach(cv -> errors.add(cv.getMessage()));

	    Map<String, List<String>> result = new HashMap<>();
	    result.put("errors", errors);

	    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	  }
}
