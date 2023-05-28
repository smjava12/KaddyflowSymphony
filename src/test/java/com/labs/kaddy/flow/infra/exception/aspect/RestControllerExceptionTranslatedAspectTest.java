/**
 * 
 */
package com.labs.kaddy.flow.infra.exception.aspect;

import static org.mockito.Mockito.doThrow;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.labs.kaddy.flow.infra.exception.ApiAbortedException;
import com.labs.kaddy.flow.infra.exception.aspect.RestControllerExceptionTranslatedAspect.ProceedingJointPointWorkflowIdGetter;
import com.labs.kaddy.flow.system.controller.workflow.WorkflowController;
import com.labs.kaddy.flow.system.service.workflow.WorkflowService;

/**
 * @author Surjyakanta
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
//@ExtendWith(MokitoExtention.class)
//@AutoConfigureTestDatabase
public class RestControllerExceptionTranslatedAspectTest {

	@Mock
	private ProceedingJointPointWorkflowIdGetter proceedingJointPointWorkflowIdGetter;
	
	@Mock
	private ProceedingJoinPoint proceedingJoinPoint;
	
	@Autowired
	private WorkflowController workflowController;
	
	@SpyBean
	private WorkflowService workflowService;
	
	@SpyBean
	private RestControllerExceptionTranslatedAspect exceptionTranslatedAspect;
	
	/*
	@Test
	public void testAuthorityHolder() {
		RestControllerExceptionTranslatedAspect aspect = new RestControllerExceptionTranslatedAspect(
				proceedingJointPointWorkflowIdGetter);
		aspect.workflowControllerPointCut();
		assert(aspect != null);
	}
	
	@Test
	public void testAuthorityHolder2() throws Throwable {
		RestControllerExceptionTranslatedAspect aspect = new RestControllerExceptionTranslatedAspect(
				proceedingJointPointWorkflowIdGetter);
		assertThrows(ApiAbortedException.class,
				()-> aspect.translatExceptionAround(Mockito.any(ProceedingJoinPoiny.class)));
	}*/
	
	@Ignore
	@Test(expected = ApiAbortedException.class)
	public void testRuntimeException_thenNotTranslated() throws Throwable {
		doThrow(RuntimeException.class).when(workflowService).getWorkflowListById(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
		workflowController.getWorkFlow(1, -1, 1);
		Mockito.verify(exceptionTranslatedAspect).translatedExceptionAround(Mockito.any(ProceedingJoinPoint.class));
		
	}
	
}
