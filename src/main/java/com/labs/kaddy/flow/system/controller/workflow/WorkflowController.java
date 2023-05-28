/**
 * 
 */
package com.labs.kaddy.flow.system.controller.workflow;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.labs.kaddy.flow.infra.exception.aspect.RestControllerExceptionTranslated;
import com.labs.kaddy.flow.system.dto.request.WorkflowRequestVo;
import com.labs.kaddy.flow.system.dto.response.BaseResponse;
import com.labs.kaddy.flow.system.dto.response.CommonResponse;
import com.labs.kaddy.flow.system.dto.response.WorkflowDetailsList;
import com.labs.kaddy.flow.system.dto.response.WorkflowResponse;
import com.labs.kaddy.flow.system.service.workflow.WorkflowService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

/**
 * @author mak
 *
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/v1/api/workflow")
@Api(value = "/v1/api/workflow", produces = "application/json", tags = "WorkFlowAPI")
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
@AllArgsConstructor
public class WorkflowController {

	public static final Logger LOG = ESAPI.getLogger(WorkflowController.class);

	@Autowired
	WorkflowService workflowService;

	/*
	 * WorkFlow API's starts
	 */
	
	@ApiOperation(value = "Create Work-Flow", notes = "Returns Workflow result", response = WorkflowResponse.class)
	@ApiResponses(value = { @ApiResponse(code= 200, message = "entities retrieved" , response = WorkflowResponse.class),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found")})
	@PostMapping(value = "/create", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RestControllerExceptionTranslated
	public BaseResponse createWorkFlow(
			/*@RequestHeader(required = false, value= "X-Operator-ID") final String XOperatorId,
			@RequestHeader(required = false, value= "X-Customer-ID") final String XCustomerId,
			@RequestHeader(required = false, value= "X-Employee-ID") final String XEmployeeId,
			@RequestParam(required = false, value= "accessLevelCode") final String accessLevelCode,*/
			
			@ApiParam(value = "workflowRequestVo", required = true) @RequestBody WorkflowRequestVo workflowRequestVo) {
		//AuthorityHolder headerObject = new AuthorityHolder(XCustomerId, XEmployeeId, XOperatorId);
		//HeaderValidation.validateHeader(headerObject, accessLevelCode);
		//ObjectValidation.validateCreateWorkflowRequest(workflowRequestVo, XCustomerId, XEmployeeId, XOperatorId);
		Instant start = Instant.now();
		LOG.info(Logger.EVENT_SUCCESS, "Start of create workflow.. ");
		BaseResponse response = workflowService.saveWorkflow(workflowRequestVo);
		Instant finish = Instant.now();
		LOG.info(Logger.EVENT_SUCCESS, "End of Workflow.. Total time of execution: " + Duration.between(start, finish).toMillis() +"ms");
		// throw new RuntimeException();
		return response;
	}

	@GetMapping(path = "/{workflowId}/getFlow")
	@ApiOperation(value = "Get Work-Flow", notes = "Returns Workflow List", response = WorkflowDetailsList.class)
	@ApiResponses(value = { 
			@ApiResponse(code= 200, message = "OK" ),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found")})
	@RestControllerExceptionTranslated
	public WorkflowDetailsList getWorkFlow(
			@RequestParam(required = false, value = "offset" , defaultValue = "0") final Integer offset,
			@RequestParam(required = false, value = "limit" , defaultValue = "10") final Integer limit,
			
			/*@RequestHeader(required = false, value= "X-Operator-ID") final String XOperatorId,
			@RequestHeader(required = false, value= "X-Customer-ID") final String XCustomerId,
			@RequestHeader(required = false, value= "X-Employee-ID") final String XEmployeeId,
			@RequestParam(required = false, value= "accessLevelCode") final String accessLevelCode,*/
			
			@PathVariable ("workflowId") Integer workflowId) {
		//AuthorityHolder headerObject = new AuthorityHolder(XCustomerId, XEmployeeId, XOperatorId);
		//HeaderValidation.validateHeader(headerObject, accessLevelCode);
		Instant start = Instant.now();
		LOG.info(Logger.EVENT_SUCCESS, "Start of fetch workflow by workflow-ID");
		
		WorkflowDetailsList workflowDetailsList = workflowService.getWorkflowListById(workflowId, offset, limit);
		
		Instant finish = Instant.now();
		LOG.info(Logger.EVENT_SUCCESS, "Finish fetch workflow by workflow-ID... Total time of execution: " + Duration.between(start, finish).toMillis() +"ms");
		
		return workflowDetailsList;
		
	}

	
	@GetMapping(path = "/{author}/list")
	@ApiOperation(value = "Get Work-Flow List", notes = "Returns Workflow List", response = WorkflowDetailsList.class)
	@ApiResponses(value = { 
			@ApiResponse(code= 200, message = "OK" ),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found")})
	@RestControllerExceptionTranslated
	public WorkflowDetailsList getWorkFlowList(
			@RequestParam(required = false, value = "offset" , defaultValue = "0") final Integer offset,
			@RequestParam(required = false, value = "limit" , defaultValue = "100") final Integer limit,
			
			/*@RequestHeader(required = false, value= "X-Operator-ID") final String XOperatorId,
			@RequestHeader(required = false, value= "X-Customer-ID") final String XCustomerId,
			@RequestHeader(required = false, value= "X-Employee-ID") final String XEmployeeId,
			@RequestParam(required = false, value= "accessLevelCode") final String accessLevelCode,*/
			
			@PathVariable ("author") String author) {
		Instant start = Instant.now();
		LOG.info(Logger.EVENT_SUCCESS, "Start of fetch workflow by Author");
		
		WorkflowDetailsList workflowDetailsList = workflowService.getList(author, offset, limit);
		
		Instant finish = Instant.now();
		LOG.info(Logger.EVENT_SUCCESS, "Finish of fetch workflow by Author..  Total time of execution: " + Duration.between(start, finish).toMillis() +"ms");
		
		return workflowDetailsList;
		
	}
	
	@ApiOperation(value = "Update Work-Flow", notes = "Returns updated Workflow result", response = WorkflowResponse.class)
	@ApiResponses(value = { @ApiResponse(code= 200, message = "entities retrieved" , response = WorkflowResponse.class),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found")})
	@PatchMapping(value = "/{title}/{workflowId}/updateFlow", produces = MediaType.APPLICATION_JSON_VALUE)
	@RestControllerExceptionTranslated
	public BaseResponse updateWorkFlow(
			/*@RequestHeader(required = false, value= "X-Operator-ID") final String XOperatorId,
			@RequestHeader(required = false, value= "X-Customer-ID") final String XCustomerId,
			@RequestHeader(required = false, value= "X-Employee-ID") final String XEmployeeId,
			@RequestParam(required = false, value= "accessLevelCode") final String accessLevelCode,*/
			
			 @PathVariable("title") String title,
			 @PathVariable ("workflowId") Integer workflowId) {
		//AuthorityHolder headerObject = new AuthorityHolder(XCustomerId, XEmployeeId, XOperatorId);
		//HeaderValidation.validateHeader(headerObject, accessLevelCode);
		//String userId = ObjectValidation.validateModifyRequest(XOperatorId, XEmployeeId, XCustomerId);
		Instant start = Instant.now();
		LOG.info(Logger.EVENT_SUCCESS, "Started updating workflow");
		BaseResponse response = new CommonResponse();
		int result = workflowService.update(title,workflowId,"1234");
		Map<String, String> resp = new HashMap<>();
		resp.put("status", "working fine");
		response.setStatus("200", "Data is successfully updated", resp, null, null);
		// throw new RuntimeException();
		Instant finish = Instant.now();
		LOG.info(Logger.EVENT_SUCCESS, "Finish updating workflow");
		return response;
	}

	@ApiOperation(value = "Delete Work-Flow", notes = "Remove Workflow", response = WorkflowResponse.class)
	@ApiResponses(value = { @ApiResponse(code= 200, message = "entities retrieved" , response = WorkflowResponse.class),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found")})
	@DeleteMapping(value = "/{workflowId}/deleteFlow", produces = MediaType.APPLICATION_JSON_VALUE)
	@RestControllerExceptionTranslated
	public BaseResponse deleteWorkFlow(
		/*	@RequestHeader(required = false, value= "X-Operator-ID") final String XOperatorId,
			@RequestHeader(required = false, value= "X-Customer-ID") final String XCustomerId,
			@RequestHeader(required = false, value= "X-Employee-ID") final String XEmployeeId,
			@RequestParam(required = false, value= "accessLevelCode") final String accessLevelCode,*/
			
			@PathVariable("workflowId") Integer workflowId) {
		//AuthorityHolder headerObject = new AuthorityHolder(XCustomerId, XEmployeeId, XOperatorId);
		//HeaderValidation.validateHeader(headerObject, workflowId);
		// String userId = ObjectValidation.validateModifyRequest(XOperatorId,
		// XEmployeeId, XCustomerId);
		Instant start = Instant.now();
		LOG.info(Logger.EVENT_SUCCESS, "Started deleting workflow with id: " + workflowId);
		BaseResponse response = new CommonResponse();
		int result = workflowService.deleteWorkflow(workflowId, "5678");
		Map<String, String> resp = new HashMap<>();
		resp.put("status", "Workflow Data " + workflowId);
		response.setStatus("200", "Data is successfully deleted", resp, null, null);
		Instant finish = Instant.now();
		LOG.info(Logger.EVENT_SUCCESS, "Finish deleting workflow with id: " + workflowId + "  Total time of execution: " + Duration.between(start, finish).toMillis() +"ms");
		return response;
	}
}