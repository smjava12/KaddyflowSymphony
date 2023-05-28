/**
 * 
 */
package com.labs.kaddy.flow.infra.exception.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.DataAccessException;

import com.labs.kaddy.flow.infra.exception.ApiAbortedException;
import com.labs.kaddy.flow.infra.exception.BadRequestParameterException;
import com.labs.kaddy.flow.infra.exception.WorkflowNotFoundException;
import com.labs.kaddy.flow.system.enums.Errors;

/**
 * @author Surjyakanta
 *
 *@see RestControllerExceptionTranslated
 *@See RestExceptionHandler
 */
public class RestControllerExceptionTranslatedAspect {

	RestControllerExceptionTranslatedAspect() {
	}
	
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)"
			+ "&& @annotated(com.labs.kaddy.flow.infra.exception.aspect.RestControllerExceptionTranslated)")
	public void workflowControllerPointCut() {
		
	}

	/**
	 * This method is responsible for choosing whether to proceed to the join point or to shortcut
	 * the advice method execution by returning its own return value or throwing an exception.
	 * 
	 * @param joinPoint is of ProceedingJoinPoint
	 * @return Object returns object type
	 * @throws Throwable is a generic exception
	 * 
	 */
	@Around("workflowControllerPointCut()")
	public Object translatedExceptionAround(ProceedingJoinPoint jointPoint) throws Throwable {
		try {
			return jointPoint.proceed();
		} catch(BadRequestParameterException | ApiAbortedException | WorkflowNotFoundException ex) {
			throw ex;
		} catch (DataAccessException ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1,"",ex);
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_7,"",ex);
		}
	}
	
	interface ProceedingJointPointWorkflowIdGetter {
		/**
		 * 
		 * @param joinPoint is of ProceedingJoinPoint
		 * @return String of type ProceedingJoinPoint
		 */
		String get(ProceedingJoinPoint jointPoint);
	}
}
