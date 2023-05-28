/**
 * 
 */
package com.labs.kaddy.flow.infra.exception;

import com.labs.kaddy.flow.system.enums.Errors;
/**
 * @author Surjyakanta
 *
 */
public class WorkflowNotFoundException extends AbstractCodeAndAffectedFieldAwareException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7511500401658846659L;

	/**
	 * @param code type Errors
	 * @param affectedField type String causing this error
	 * @param cause type Throwable
	 * 
	 */
	public WorkflowNotFoundException(Errors code, String affectedField, Throwable cause) {
	    super("Workflow not find for ", code, affectedField, cause);
	  }

	/**
	 * @param code type Errors
	 * @param affectedField type String causing this error
	 * 
	 */
	public WorkflowNotFoundException(Errors code, String affectedField) {
	    super( code, affectedField);
	  }
	
	
}
