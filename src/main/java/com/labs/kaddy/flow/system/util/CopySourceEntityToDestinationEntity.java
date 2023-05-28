/**
 * 
 */
package com.labs.kaddy.flow.system.util;

import com.labs.kaddy.flow.system.dto.request.FieldRequestVo;
import com.labs.kaddy.flow.system.dto.request.LevelRequestVo;
import com.labs.kaddy.flow.system.dto.request.ValidationRequestVo;
import com.labs.kaddy.flow.system.entity.FieldRequest;
import com.labs.kaddy.flow.system.entity.LevelRequest;
import com.labs.kaddy.flow.system.entity.ValidationRequest;

/**
 * @author Surjyakanta
 *
 */
public class CopySourceEntityToDestinationEntity {
	
	public static LevelRequest levelVoRequestToEntityRequestMapper(LevelRequestVo input, LevelRequest entity) {
			
			entity.setTitle(input.getTitle());
			entity.setType(input.getType());
			entity.setActive(input.isActive());
			entity.setValidation(input.isValidation());
			entity.setCategory(input.getCategory());
			return entity;
	}

	public static FieldRequest fieldVoRequestToEntityRequestMapper(FieldRequestVo input, FieldRequest entity) {
		
		entity.setTitle(input.getTitle());
		entity.setType(input.getType());
		entity.setValue(input.getValue());
		entity.setActive(input.isActive());
		entity.setValidation(input.isValidation());
		entity.setCategory(input.getCategory());
		return entity;
	}

	public static ValidationRequest validationVoRequestToEntityRequestMapper(ValidationRequestVo input,
			ValidationRequest entity) {

		entity.setTitle(input.getTitle());
		entity.setType(input.getType());
		entity.setValue(input.getValue());
		entity.setActive(input.isActive());
		entity.setEnforce(input.isEnforce());
		entity.setVisibility(input.isVisibility());
		return entity;
	
	}
}
