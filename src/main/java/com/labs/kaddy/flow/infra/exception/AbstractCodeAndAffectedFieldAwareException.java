/**
 * 
 */
package com.labs.kaddy.flow.infra.exception;

import com.labs.kaddy.flow.system.enums.Errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * AbstractCodeAndAffectedFieldAwareException
 * 
 * @author Surjyakanta
 *
 */
@Getter
@AllArgsConstructor
public class AbstractCodeAndAffectedFieldAwareException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4180437651947127561L;
	
	private final Errors code;
	private final String affectedField;
	
	/**
	 * @param message type String
	 * @param code type Errors
	 * @param affectedField type String
	 * @param cause type Throwable
	 * 
	 */
	protected AbstractCodeAndAffectedFieldAwareException(String message, Errors code, String affectedField, Throwable cause) {
		super(message + affectedField , cause);
		this.code = code;
		this.affectedField = affectedField;
	}
}
