/**
 * 
 */
package com.labs.kaddy.flow.system.util;

import java.util.Objects;

import com.labs.kaddy.flow.infra.exception.BadRequestParameterException;
import com.labs.kaddy.flow.system.dto.request.LevelRequestVo;
import com.labs.kaddy.flow.system.dto.request.WorkflowRequestVo;
import com.labs.kaddy.flow.system.enums.Errors;

/**
 * @author Surjyakanta
 *
 */

public class ObjectValidation {
	
	public ObjectValidation() {
		super();
	}

	public static WorkflowRequestVo validateCreateWorkflowRequest(WorkflowRequestVo workflowRequestVo, 
			String xOperatorId , String xEmployeeId , String xCustomerId) {
		if (workflowRequestVo.isEmpty()) {
			  throw new BadRequestParameterException(Errors.SVC_10_400_1,"workflowId, author");
		}
		String userId = getOperatorIdOrEmployeeIdOrCustomerId(xOperatorId, xEmployeeId, xCustomerId);
		workflowRequestVo.setCreatedBy(userId);
		workflowRequestVo.setUpdatedBy(userId);
		return workflowRequestVo;
		
	}
	
	public static LevelRequestVo validateCreateLevelRequest(LevelRequestVo levelRequestVo, 
			String xOperatorId , String xEmployeeId , String xCustomerId) {
		if (levelRequestVo.isEmpty()) {
			  throw new BadRequestParameterException(Errors.SVC_10_400_1,"LevelId, author");
		}
		String userId = getOperatorIdOrEmployeeIdOrCustomerId(xOperatorId, xEmployeeId, xCustomerId);
		levelRequestVo.setCreatedBy(userId);
		levelRequestVo.setUpdatedBy(userId);
		return levelRequestVo;
		
	}
	
	public static String validateModifyRequest(String xOperatorId , String xEmployeeId , String xCustomerId) {
		return getOperatorIdOrEmployeeIdOrCustomerId(xOperatorId, xEmployeeId, xCustomerId);
		
	}
	
	public static void isValidMandatoryInput(String fieldName, String fieldValue, int maxLength) {
		if (Objects.isNull(fieldValue)) {
			throw new BadRequestParameterException(Errors.SVC_10_400_1, fieldName);
		}
		if (StringUtil.isBlank(fieldValue) || (maxLength>0 && fieldValue.length()> maxLength)) {
			throw new BadRequestParameterException(Errors.SVC_10_400_3, fieldName);
		}
	}
	
	public static void isValidOptionalInput(String fieldName, String fieldValue, int maxLength) {
		if (fieldValue != null && StringUtil.isBlank(fieldValue) || (maxLength>0 && fieldValue.length()> maxLength)) {
			throw new BadRequestParameterException(Errors.SVC_10_400_3, fieldName);
		}
	}
	
	public static String getOperatorIdOrEmployeeIdOrCustomerId(String xOperatorId , String xEmployeeId , String xCustomerId) {
		String userId = "";
		if(!StringUtil.isBlank(xOperatorId)) {
			userId = xOperatorId;
		} else if(!StringUtil.isBlank(xEmployeeId)) {
			userId = xEmployeeId;
		} else {
			userId = xCustomerId;
		}
		return userId;
	}
}
