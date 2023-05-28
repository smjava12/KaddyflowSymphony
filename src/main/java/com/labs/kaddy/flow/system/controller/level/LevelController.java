/**
 * 
 */
package com.labs.kaddy.flow.system.controller.level;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.labs.kaddy.flow.infra.exception.aspect.RestControllerExceptionTranslated;
import com.labs.kaddy.flow.system.constants.ApplicationConstants;
import com.labs.kaddy.flow.system.dto.request.LevelRequestVo;
import com.labs.kaddy.flow.system.dto.response.BaseResponse;
import com.labs.kaddy.flow.system.dto.response.CommonResponse;
import com.labs.kaddy.flow.system.dto.response.LevelDetailsList;
import com.labs.kaddy.flow.system.dto.response.LevelResponse;
import com.labs.kaddy.flow.system.service.level.LevelService;
import com.labs.kaddy.flow.system.util.HeaderValidation;

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
@RequestMapping(value = "/v1/api/level")
@Api(value = "/v1/api/level", produces = "application/json", tags = "LevelAPI")
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
@AllArgsConstructor
public class LevelController {

	public static final Logger LOGGER = ESAPI.getLogger(LevelController.class);

	@Autowired
	LevelService levelService;

	/*
	 * Level API's Starts
	 */
	@ApiOperation(value = "Create Level", notes = "Returns Level result", response = LevelResponse.class)
	@ApiResponses(value = { @ApiResponse(code= 200, message = "entities retrieved" , response = LevelResponse.class),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found")})
	@PostMapping(value = "/create/level/{workflowRequestId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RestControllerExceptionTranslated
	public BaseResponse createLevel(
			/*@RequestHeader(required = false, value= "X-Operator-ID") final String XOperatorId,
			@RequestHeader(required = false, value= "X-Customer-ID") final String XCustomerId,
			@RequestHeader(required = false, value= "X-Employee-ID") final String XEmployeeId,
			@RequestParam(required = false, value= "accessLevelCode") final String accessLevelCode,*/
			@PathVariable(required = true, value = ApplicationConstants.WORKFLOWREQUESTID) final Integer workflowRequestId,
			@ApiParam(value = "levelRequestVo", required = true) @Valid @RequestBody LevelRequestVo levelRequestVo) {
		//AuthorityHolder headerObject = new AuthorityHolder(XCustomerId, XEmployeeId, XOperatorId);
		//HeaderValidation.validateHeader(headerObject, accessLevelCode);
		//ObjectValidation.validateCreateLevelRequest(levelRequestVo, XCustomerId, XEmployeeId, XOperatorId);
		Instant start = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "Start of create Level.. " + System.currentTimeMillis());
		BaseResponse response = levelService.saveLevel(levelRequestVo,workflowRequestId);
		Instant finish = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "End of create Level..  Total time of execution: " + Duration.between(start, finish).toMillis() +"ms");
		return response;
	}


	@GetMapping(path = "/getById/{workflowRequestId}")
	@ApiOperation(value = "Get Work-Flow Level", notes = "Returns Level List By WorkflowId", response = LevelDetailsList.class)
	@ApiResponses(value = { 
			@ApiResponse(code= 200, message = "OK" ),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found")})
	@RestControllerExceptionTranslated
	public LevelDetailsList getWorkFlow(
			@RequestParam(required = false, value = "offset" , defaultValue = "0") final Integer offset,
			@RequestParam(required = false, value = "limit" , defaultValue = "10") final Integer limit,
			
			/*@RequestHeader(required = false, value= "X-Operator-ID") final String XOperatorId,
			@RequestHeader(required = false, value= "X-Customer-ID") final String XCustomerId,
			@RequestHeader(required = false, value= "X-Employee-ID") final String XEmployeeId,
			@RequestParam(required = false, value= "accessLevelCode") final String accessLevelCode,
			
			@PathVariable ("workflowId") String workflowId*/
			@PathVariable(required = true, value = ApplicationConstants.WORKFLOWREQUESTID) final Integer workflowRequestId) {
		//AuthorityHolder headerObject = new AuthorityHolder(XCustomerId, XEmployeeId, XOperatorId);
		//HeaderValidation.validateHeader(headerObject, accessLevelCode);
		Instant start = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "Start of fetch workflow by workflow-ID");
		
		LevelDetailsList levelDetailsList = levelService.getLevelListById(workflowRequestId, offset, limit);
		Instant finish = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "Finish fetch workflow by workflow-ID..  Total time of execution: " + Duration.between(start, finish).toMillis() +"ms");
		
		return levelDetailsList;
		
	}

	@ApiOperation(value = "Update Level", notes = "Update the requested Levels", response = BaseResponse.class)
	@ApiResponses(value = { 
			@ApiResponse(code= 200, message = "Level updated successfully" ),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found")})
	@PatchMapping(value = "/updateLevel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@RestControllerExceptionTranslated
	public BaseResponse updateLevel(
			@ApiParam(value = "changes", required = true) @Valid @RequestBody  Map<String, Object> changes,
			@PathVariable(required = true, value = ApplicationConstants.ID) final Integer id) {
		
		Instant start = Instant.now();
		HeaderValidation.validateMap(changes);
		LOGGER.info(Logger.EVENT_SUCCESS, "Start of create Level.. ");
		BaseResponse response = levelService.update(changes,id,"1234");
		Instant finish = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "Start of create Level..  Total time of execution: " + Duration.between(start, finish).toMillis() +"ms");
		// throw new RuntimeException();
		return response;
	}


	@ApiOperation(value = "Delete Level", notes = "Delete the requested Levels", response = BaseResponse.class)
	@ApiResponses(value = { 
			@ApiResponse(code= 200, message = "Level deleted successfully" ),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found")})
	@DeleteMapping(path = "/deleteLevel/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@RestControllerExceptionTranslated
	public BaseResponse deleteLevel(
			@PathVariable(required = true, value = ApplicationConstants.ID) final Integer id) {
		Instant start = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "Start of create Level.. ");
		if (id != null && id > 0) {
			// TODO: Delete the WorkFlow and return from here
			BaseResponse response = levelService.deleteLevelRequest(id,"5678");
			Instant finish = Instant.now();
			LOGGER.info(Logger.EVENT_SUCCESS, "Start of create Level..  Total time of execution: " + Duration.between(start, finish).toMillis() +"ms");
			return response;
		} else {
			BaseResponse response = new CommonResponse();
			response.setStatus("403", "Data is Not Found", null, null, null);
			return response;

		}
	}
	/*
	 * Level API's Ends
	 */
}