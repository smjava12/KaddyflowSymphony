/**
 * 
 */
package com.labs.kaddy.flow.infra.config.logging.access;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Aspect for logging all the accessing
 * 
 * @author Surjyakanta
 *
 */
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE - 2)
public class RestControllerAccessLoggingAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger("ACCESS");
	
	/**
	 * This method throws unsupportedOperation Exception
	 */
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void kaddyFlowControllerPointCut() {
		
	}
	
	@Around("kaddyFlowControllerPointCut()")
	public Object around(ProceedingJoinPoint jointPoint) throws Throwable {
		LOGGER.info("Start processing {}.{}() with argument[s] = {}", jointPoint.getSignature().getDeclaringTypeName(),
				jointPoint.getSignature().getName(), Arrays.toString(jointPoint.getArgs()));
		Long begin = System.currentTimeMillis();
		try {
			Object result = jointPoint.proceed();
			LOGGER.info("{}ms|{}.{}() with argument[s] = {}",System.currentTimeMillis()-begin, jointPoint.getSignature().getDeclaringTypeName(),
					jointPoint.getSignature().getName(), Arrays.toString(jointPoint.getArgs()));
			return result;
		} catch(Exception ex) {
			LOGGER.info("{}ms|{}.{}() with argument[s] = {}",System.currentTimeMillis()-begin, jointPoint.getSignature().getDeclaringTypeName(),
					jointPoint.getSignature().getName(), Arrays.toString(jointPoint.getArgs()), ex.getClass().getCanonicalName());
			throw ex;
		}
	}
}
