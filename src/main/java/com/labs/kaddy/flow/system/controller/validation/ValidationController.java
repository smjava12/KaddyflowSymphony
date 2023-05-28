/**
 * 
 */
package com.labs.kaddy.flow.system.controller.validation;

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
import com.labs.kaddy.flow.system.dto.request.ValidationRequestVo;
import com.labs.kaddy.flow.system.dto.response.BaseResponse;
import com.labs.kaddy.flow.system.dto.response.CommonResponse;
import com.labs.kaddy.flow.system.dto.response.ValidationDetailsList;
import com.labs.kaddy.flow.system.service.validation.ValidationService;
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
@RequestMapping("/v1/api/validation")
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class ValidationController {

	@Autowired
	ValidationService validationService;

	public static final Logger LOGGER = ESAPI.getLogger(ValidationController.class);

	/*
	 * Validation API's Starts
	 */
	@ApiOperation(value = "Create Validation", notes = "Returns Validation result", response = BaseResponse.class)
	@ApiResponses(value = { @ApiResponse(code= 200, message = "entities retrieved" , response = BaseResponse.class),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found")})
	@PostMapping(value = "/create/validation/{fieldRequestId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RestControllerExceptionTranslated
	public BaseResponse createValidation(
			@PathVariable(required = true, value = ApplicationConstants.FIELDREQUESTID) final Integer fieldRequestId,
			@ApiParam(value = "fieldRequest", required = true) @RequestBody ValidationRequestVo validationRequestVo) {

		Instant start = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "Start of create Validation.. " + System.currentTimeMillis());
		BaseResponse response = validationService.saveValidation(validationRequestVo, fieldRequestId);
		Instant finish = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "End of create Validation..  Total time of execution: " + Duration.between(start, finish).toMillis() +"ms");
		return response;
	}

	@GetMapping(path = "/getAllValidation/{fieldRequestId}")
	@ApiOperation(value = "Get Work-Flow Validation", notes = "Returns Validation List By Field Request Id", response = ValidationDetailsList.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found") })
	@RestControllerExceptionTranslated
	public ValidationDetailsList getAllValidation(
			@RequestParam(required = false, value = "offset", defaultValue = "0") final Integer offset,
			@RequestParam(required = false, value = "limit", defaultValue = "10") final Integer limit,

			@PathVariable(required = true, value = ApplicationConstants.FIELDREQUESTID) final Integer fieldRequestId) {
		Instant start = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "Start of fetch Validation by FIELDREQUESTID");

		ValidationDetailsList validationDetailsList = validationService.getFieldListById(fieldRequestId, offset, limit);
		Instant finish = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "Finish fetch Validation by FIELDREQUESTID..  Total time of execution: "
				+ Duration.between(start, finish).toMillis() + "ms");

		return validationDetailsList;

	}


	@ApiOperation(value = "Update Validation", notes = "Update the requested Validation", response = BaseResponse.class)
	@ApiResponses(value = { 
			@ApiResponse(code= 200, message = "Validation updated successfully" ),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found")})
	@PatchMapping(value = "/updateValidation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@RestControllerExceptionTranslated
	public BaseResponse updateField(
			@ApiParam(value = "changes", required = true) @Valid @RequestBody  Map<String, Object> changes,
			@PathVariable(required = true, value = ApplicationConstants.ID) final Integer id) {
		
		Instant start = Instant.now();
		HeaderValidation.validateMap(changes);
		LOGGER.info(Logger.EVENT_SUCCESS, "Start of update Validation.. ");
		BaseResponse response = validationService.updateValidation(changes,id,"1234");
		Instant finish = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "End of update Validation..  Total time of execution: " + Duration.between(start, finish).toMillis() +"ms");
		return response;
	}


	@ApiOperation(value = "Delete Validation", notes = "Delete the requested Validation", response = BaseResponse.class)
	@ApiResponses(value = { 
			@ApiResponse(code= 200, message = "Validation deleted successfully" ),
			@ApiResponse(code = 500, message = "internal server error"),
			@ApiResponse(code = 404, message = "some field not found")})
	@DeleteMapping(path = "/deleteValidation/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@RestControllerExceptionTranslated
	public BaseResponse deleteField(
			@PathVariable(required = true, value = ApplicationConstants.ID) final Integer id) {
		Instant start = Instant.now();
		LOGGER.info(Logger.EVENT_SUCCESS, "Start of delete Validation.. ");
		if (id != null && id > 0) {
			// TODO: Delete the WorkFlow and return from here
			BaseResponse response = validationService.deleteField(id,"5678");
			Instant finish = Instant.now();
			LOGGER.info(Logger.EVENT_SUCCESS, "End of Delete Validation..  Total time of execution: " + Duration.between(start, finish).toMillis() +"ms");
			return response;
		} else {
			BaseResponse response = new CommonResponse();
			response.setStatus("403", "Data is Not Found", null, null, null);
			return response;

		}
	}
	/*
	 * Validation API's Ends
	 */
}