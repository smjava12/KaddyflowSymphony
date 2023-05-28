/**
 * 
 */
package com.labs.kaddy.flow.infra.exception;

import java.util.Map;

import com.labs.kaddy.flow.system.enums.Errors;

import lombok.Getter;

/**
 * BadRequestParameterException
 * 
 * @author Surjyakanta
 *
 */
@Getter
public class BadRequestParameterException extends AbstractCodeAndAffectedFieldAwareException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2527122100156055966L;

	private Map<String, Errors> errCodeFieldMapping;

	/**
	 * @param code type Errors
	 * @param affectedField type String causing this error
	 * @param cause type Throwable
	 * 
	 */
	public BadRequestParameterException(Errors code, String affectedField, Throwable cause) {
		super("Bad input parameter at field: ", code, affectedField, cause);
	}

	/**
	 * @param code type Errors
	 * @param affectedField type String
	 * 
	 */
	public BadRequestParameterException(Errors code, String affectedField) {
		super(code, affectedField);
	}

	/**
	 * Used to display multiple error message at a time
	 * 
	 * @param errCodeFieldMapping Map
	 * 
	 */
	public BadRequestParameterException(Map<String, Errors> errCodeFieldMapping) {
		super(Errors.SVC_10_400_1, "Mandatory Filed Missing");
		this.errCodeFieldMapping = errCodeFieldMapping;
	}
}
