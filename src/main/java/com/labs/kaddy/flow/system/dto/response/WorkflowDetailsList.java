/**
 * 
 */
package com.labs.kaddy.flow.system.dto.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Surjyakanta
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Workflow Response DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkflowDetailsList implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -7807456339030821007L;
	
	@ApiModelProperty(value = "WorkflowResponse" , allowEmptyValue = true, position = 1)
	private List<WorkflowResponse> workflowResponse;
	
	@ApiModelProperty(value = "count" , allowEmptyValue = true, position = 2)
	private int count;
	
	@ApiModelProperty(value = "Pagination" , allowEmptyValue = true, position = 3)
	private Pagination pagination;
	
}
