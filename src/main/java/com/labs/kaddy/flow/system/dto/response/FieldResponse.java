/**
 * 
 */
package com.labs.kaddy.flow.system.dto.response;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.labs.kaddy.flow.system.entity.LevelRequest;
import com.labs.kaddy.flow.system.entity.ValidationRequest;

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
public class FieldResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4604764819086220476L;

	@ApiModelProperty(value = "ID",  allowEmptyValue = true)
	private Integer id;
	
	@ApiModelProperty(value = "title",  allowEmptyValue = true)
	private String title;

	@ApiModelProperty(value = "type",  allowEmptyValue = true)
	private String type;
	
	@ApiModelProperty(value = "category",  allowEmptyValue = true)
	private String category;

	@ApiModelProperty(value = "value",  allowEmptyValue = true)
	private String value;

	@ApiModelProperty(value = "level",  allowEmptyValue = true)
	private List<LevelRequest> levelRequest;
	
	@ApiModelProperty(value = "validation",  allowEmptyValue = true)
	private List<ValidationRequest> validationRequest;
	
	@ApiModelProperty(value = "isActive",  allowEmptyValue = true)
	private boolean isActive;

	@ApiModelProperty(value = "isValidation",  allowEmptyValue = true)
	private boolean isValidation;
	
	@ApiModelProperty(value = "createdBy",  allowEmptyValue = true)
	private String createdBy;
	
	@ApiModelProperty(value = "updatedBy",  allowEmptyValue = true)
	private String updatedBy;

	@ApiModelProperty(value = "createdAt",  allowEmptyValue = true)
	private Timestamp createdAt;

	@ApiModelProperty(value = "updatedAt",  allowEmptyValue = true)
	private Timestamp updatedAt;
}
