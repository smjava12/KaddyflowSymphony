/**
 * 
 */
package com.labs.kaddy.flow.system.dto.request;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.labs.kaddy.flow.system.entity.LevelRequest;
import com.labs.kaddy.flow.system.entity.ValidationRequest;

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
public class FieldRequestVo implements BaseRequest {
	
	@ApiModelProperty(value = "ID",  allowEmptyValue = true)
	private Integer id;
	
	@ApiModelProperty(value = "title",  allowEmptyValue = true)
	private String title;

	@ApiModelProperty(value = "type",  allowEmptyValue = true)
	private String type;
	
	@ApiModelProperty(value = "value",  allowEmptyValue = true)
	private String value;
	
	@ApiModelProperty(value = "level",  allowEmptyValue = true)
	private LevelRequest levelRequest;
	
	@ApiModelProperty(value = "validation",  allowEmptyValue = true)
	private List<ValidationRequest> validationRequest;
	
	@ApiModelProperty(value = "isActive",  allowEmptyValue = true)
	private boolean isActive;

	@ApiModelProperty(value = "isValidation",  allowEmptyValue = true)
	private boolean isValidation;

	@ApiModelProperty(value = "category",  allowEmptyValue = true)
	private String category;

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
		return Stream.of(this.isActive, this.type, this.category,this.isValidation,
				this.title).allMatch(Objects::isNull);
	}
}
