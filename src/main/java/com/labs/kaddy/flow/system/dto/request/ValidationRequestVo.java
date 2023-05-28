/**
 * 
 */
package com.labs.kaddy.flow.system.dto.request;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.stream.Stream;

import com.labs.kaddy.flow.system.entity.FieldRequest;

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
public class ValidationRequestVo implements BaseRequest{
	
	@ApiModelProperty(value = "ID",  allowEmptyValue = true)
	private Integer id;
	
	@ApiModelProperty(value = "title",  allowEmptyValue = true)
	private String title;
	
	@ApiModelProperty(value = "type",  allowEmptyValue = true)
	private String type;
	
	@ApiModelProperty(value = "value",  allowEmptyValue = true)
	private String value;
	
	@ApiModelProperty(value = "active",  allowEmptyValue = true)
	private boolean isActive;
	
	@ApiModelProperty(value = "enforce",  allowEmptyValue = true)
	private boolean isEnforce;
	
	@ApiModelProperty(value = "visibility",  allowEmptyValue = true)
	private boolean isVisibility;
	
	@ApiModelProperty(value = "fieldRequest",  allowEmptyValue = true)
	private FieldRequest fieldRequest;
	
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
		return Stream.of(this.value, this.title, this.isActive,
				this.type,this.isVisibility, this.isEnforce).allMatch(Objects::isNull);
	}
}
