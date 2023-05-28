/**
 * 
 */
package com.labs.kaddy.flow.system.dto.request;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.labs.kaddy.flow.system.entity.LevelRequest;
import com.labs.kaddy.flow.system.entity.StatusRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mak
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description= "Workflow Value Objects from Request")
public class WorkflowRequestVo implements BaseRequest, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 80580360153794646L;

	@ApiModelProperty(value = "ID",  allowEmptyValue = true)
	private Integer id;
	
	@ApiModelProperty(value = "user ID",  allowEmptyValue = true)
	private Integer userId;
	
	@ApiModelProperty(value = "Workflow title",  allowEmptyValue = true)
	private String title;
	
	@ApiModelProperty(value = "creator",  allowEmptyValue = true)
	private String author;
	
	@ApiModelProperty(value = "enabled",  allowEmptyValue = true)
	private boolean isActive;
	
	@ApiModelProperty(value = "workflow id",  allowEmptyValue = true)
	private String workflowId;

	@ApiModelProperty(value = "lebel",  allowEmptyValue = true)
	private List<LevelRequest> lebel;

	@ApiModelProperty(value = "status id",  allowEmptyValue = true)
	private Integer statusId;

	@ApiModelProperty(value = "status",  allowEmptyValue = true)
	private StatusRequest status;
	
	@ApiModelProperty(value = "createdBy",  allowEmptyValue = true)
	private String createdBy;
	
	@ApiModelProperty(value = "updatedBy",  allowEmptyValue = true)
	private String updatedBy;

	@ApiModelProperty(value = "Creation Date",  allowEmptyValue = true)
	private Timestamp createdDateTimeStamp;

	@ApiModelProperty(value = "Last Updated",  allowEmptyValue = true)
	private Timestamp updatedDateTimeStamp;
	
	@Override
	public Integer getIdentifier() throws Exception {
		return id;
	}
	
	public boolean isEmpty() {
		return Stream.of(this.userId, this.author, this.isActive,
				this.title,this.workflowId).allMatch(Objects::isNull);
	}
}
