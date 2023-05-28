/**
 * 
 */
package com.labs.kaddy.flow.system.dto.request;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description= "Level Value Objects from Request")
public class LevelRequestVo implements BaseRequest {
	
	@ApiModelProperty(value = "ID",  allowEmptyValue = true)
	private Integer id;
	
	@ApiModelProperty(value = "workflow Id",  allowEmptyValue = true)
	private String workflowId;
	
	//@ApiModelProperty(value = "workflow Request Id",  allowEmptyValue = true)
	//private Integer workflowRequestId;

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

	@ApiModelProperty(value = "fieldEntity",  allowEmptyValue = true)
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

	@Override
	public Integer getIdentifier() throws Exception {
		return id;
	}
	
	public boolean isEmpty() {
		return Stream.of(this.workflowId, this.title, this.isActive,
				this.type,this.isValidation, this.category).allMatch(Objects::isNull);
	}

}
