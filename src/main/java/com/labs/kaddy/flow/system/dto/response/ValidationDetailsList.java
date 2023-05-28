/**
 * 
 */
package com.labs.kaddy.flow.system.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@ApiModel(description = "Field Response DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationDetailsList {

	@ApiModelProperty(value = "ValidationResponse" , allowEmptyValue = true, position = 1)
	private List<ValidationResponse> validationResponse;
	
	@ApiModelProperty(value = "count" , allowEmptyValue = true, position = 2)
	private int count;
	
	@ApiModelProperty(value = "Pagination" , allowEmptyValue = true, position = 3)
	private Pagination pagination;
}
