/**
 * 
 */
package com.labs.kaddy.flow.system.dto.response;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.labs.kaddy.flow.system.entity.FieldRequest;
import com.labs.kaddy.flow.system.entity.WorkflowRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Surjyakanta
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Level Response DTO")
public class LevelResponse implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -6308767140228674293L;

	@ApiModelProperty(value = "ID",  allowEmptyValue = true)
	private Integer id;
	
	@ApiModelProperty(value = "workflow Id",  allowEmptyValue = true)
	private String workflowId;

	@ApiModelProperty(value = "title",  allowEmptyValue = true)
	private String title;

	@ApiModelProperty(value = "type",  allowEmptyValue = true)
	private String type;

	@ApiModelProperty(value = "isActive",  allowEmptyValue = true)
	private boolean isActive;

	@ApiModelProperty(value = "isValidation",  allowEmptyValue = true)
	private boolean isValidation;

	@ApiModelProperty(value = "category",  allowEmptyValue = true)
	private String category;

	@ApiModelProperty(value = "ID",  allowEmptyValue = true)
	private List<FieldRequest> fieldEntity;

	@ApiModelProperty(value = "workflowEntity",  allowEmptyValue = true)
	private WorkflowRequest workflowEntity;

	@ApiModelProperty(value = "createdBy",  allowEmptyValue = true)
	private String createdBy;
	
	@ApiModelProperty(value = "updatedBy",  allowEmptyValue = true)
	private String updatedBy;

	@ApiModelProperty(value = "createdAt",  allowEmptyValue = true)
	private Timestamp createdAt;

	@ApiModelProperty(value = "updatedAt",  allowEmptyValue = true)
	private Timestamp updatedAt;
}
