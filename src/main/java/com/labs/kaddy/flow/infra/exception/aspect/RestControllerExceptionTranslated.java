/**
 * 
 */
package com.labs.kaddy.flow.infra.exception.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for translating the exceptions thrown by the annotated method to certain types of exception.
 * The annotated method usually contains a {@code PathVariable} which name is "workflowId". SO the 
 * {@code RestExceptionHandler} can correctly log the workflowId when logging.
 * However some annotated method, it doesn't contain the "workflowId" in {@code PathVariable}. In this case, the 
 * workflowId can't be get. And in the {@code RestExceptionHandler}, the workflowId in the log line will be {@code null}.
 * If this is the expected behaviour, then it's ok. If this is not the expected behavior,You may customize the how to get
 * the workflowId by defining a {@code ProceedingJointPointWorkflowIdGetter}.
 * 
 * @author Surjyakanta
 *
 */


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RestControllerExceptionTranslated {

}
