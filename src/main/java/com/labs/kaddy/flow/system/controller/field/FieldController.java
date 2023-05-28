/**
 * 
 */
package com.labs.kaddy.flow.system.controller.field;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
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
import com.labs.kaddy.flow.system.dto.request.FieldRequestVo;
import com.labs.kaddy.flow.system.dto.request.WorkflowRequestVo;
import com.labs.kaddy.flow.system.dto.response.BaseResponse;
import com.labs.kaddy.flow.system.dto.response.CommonResponse;
import com.labs.kaddy.flow.system.dto.response.FieldDetailsList;
import com.labs.kaddy.flow.system.dto.response.FieldResponse;
import com.labs.kaddy.flow.system.dto.response.LevelDetailsList;
import com.labs.kaddy.flow.system.dto.response.LevelResponse;
import com.labs.kaddy.flow.system.service.field.FieldService;
import com.labs.kaddy.flow.system.util.HeaderValidation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author mak
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/v1/api/field")
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class FieldController {

	@Autowired
	FieldService fieldService;

	public static final Logger LOGGER = ESAPI.getLogger(FieldController.class);

	/*
	 * Field API's Starts
	 */
	@ApiOperation(value = "Create Field", notes = "Returns Field result", response = BaseResponse.class)
	@ApiResponses(value = { @ApiResponse(code= 200, message = "entities retrieved" , response = BaseResponse.class),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found")})
	@PostMapping(value = "/create/field/{levelRequestId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RestControllerExceptionTranslated
	public BaseResponse createField(
			@PathVariable(required = true, value = ApplicationConstants.LEVELREQUESTID) final Integer levelRequestId,
			@ApiParam(value = "fieldRequest", required = true) @RequestBody FieldRequestVo fieldRequestVo) {

		Instant start = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "Start of create Field.. " + System.currentTimeMillis());
		BaseResponse response = fieldService.saveField(fieldRequestVo, levelRequestId);
		Instant finish = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "End of create Field..  Total time of execution: " + Duration.between(start, finish).toMillis() +"ms");
		return response;
	}

	@GetMapping(path = "/getAllField/{levelRequestId}")
	@ApiOperation(value = "Get Work-Flow Field", notes = "Returns Field List By WorkflowId", response = FieldDetailsList.class)
	@ApiResponses(value = { 
			@ApiResponse(code= 200, message = "OK" ),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found")})
	@RestControllerExceptionTranslated
	public FieldDetailsList getFields(
			@RequestParam(required = false, value = "offset" , defaultValue = "0") final Integer offset,
			@RequestParam(required = false, value = "limit" , defaultValue = "10") final Integer limit,
			
			@PathVariable(required = true, value = ApplicationConstants.LEVELREQUESTID) final Integer levelRequestId) {
		Instant start = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "Start of fetch workflow by workflow-ID");
		
		FieldDetailsList fieldDetailsList = fieldService.getFieldListById(levelRequestId, offset, limit);
		Instant finish = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "Finish fetch workflow by workflow-ID..  Total time of execution: " + Duration.between(start, finish).toMillis() +"ms");
		
		return fieldDetailsList;
		
	}

	@ApiOperation(value = "Update Field", notes = "Update the requested Field", response = BaseResponse.class)
	@ApiResponses(value = { 
			@ApiResponse(code= 200, message = "Field updated successfully" ),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found")})
	@PatchMapping(value = "/updateField/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@RestControllerExceptionTranslated
	public BaseResponse updateField(
			@ApiParam(value = "changes", required = true) @Valid @RequestBody  Map<String, Object> changes,
			@PathVariable(required = true, value = ApplicationConstants.ID) final Integer id) {
		
		Instant start = Instant.now();
		HeaderValidation.validateMap(changes);
		LOGGER.info(Logger.EVENT_SUCCESS, "Start of update Field.. ");
		BaseResponse response = fieldService.updateField(changes,id,"1234");
		Instant finish = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "End of update Field..  Total time of execution: " + Duration.between(start, finish).toMillis() +"ms");
		return response;
	}


	@ApiOperation(value = "Delete Field", notes = "Delete the requested Field", response = BaseResponse.class)
	@ApiResponses(value = { 
			@ApiResponse(code= 200, message = "Field deleted successfully" ),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found")})
	@DeleteMapping(path = "/deleteField/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@RestControllerExceptionTranslated
	public BaseResponse deleteField(
			@PathVariable(required = true, value = ApplicationConstants.ID) final Integer id) {
		Instant start = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "Start of delete Level.. ");
		if (id != null && id > 0) {
			// TODO: Delete the WorkFlow and return from here
			BaseResponse response = fieldService.deleteField(id,"5678");
			Instant finish = Instant.now();
			LOGGER.info(Logger.EVENT_SUCCESS, "End of Delete Field..  Total time of execution: " + Duration.between(start, finish).toMillis() +"ms");
			return response;
		} else {
			BaseResponse response = new CommonResponse();
			response.setStatus("403", "Data is Not Found", null, null, null);
			return response;

		}
	}
	/*
	 * Field API's Ends
	 */
}