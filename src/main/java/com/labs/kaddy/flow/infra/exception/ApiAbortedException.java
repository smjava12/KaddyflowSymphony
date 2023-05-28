/**
 * 
 */
package com.labs.kaddy.flow.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.labs.kaddy.flow.system.enums.Errors;

/**
 * @author Surjyakanta
 *
 */
@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class ApiAbortedException extends AbstractCodeAndAffectedFieldAwareException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8606949384694963649L;

	/**
	 * @param code type Errors
	 * @param affectedField type String causing this error
	 * @param cause type Throwable
	 * 
	 */
	public ApiAbortedException(Errors code, String affectedField, Throwable cause) {
		super("API aborted during processing field: ", code, affectedField, cause);
	}
	
	/**
	 * @param code type Errors
	 * @param affectedField type String
	 * 
	 */
	public ApiAbortedException(String message, Errors code, String affectedField) {
		super(code, affectedField);
	}

}
