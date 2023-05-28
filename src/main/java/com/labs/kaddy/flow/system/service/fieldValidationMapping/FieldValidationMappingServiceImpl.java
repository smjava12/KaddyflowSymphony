/**
 * 
 */
package com.labs.kaddy.flow.system.service.fieldValidationMapping;

import java.io.Serializable;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.labs.kaddy.flow.system.dto.request.BaseRequest;
import com.labs.kaddy.flow.system.dto.request.FieldValidationMappingRequestVo;
import com.labs.kaddy.flow.system.entity.FieldValidationMapping;
import com.labs.kaddy.flow.system.enums.Action;
import com.labs.kaddy.flow.system.repository.FieldValidationMappingRepository;
import com.labs.kaddy.flow.system.service.base.BaseServiceImpl;

/**
 * @author Surjyakanta
 *
 */
@Service
public class FieldValidationMappingServiceImpl<T, I extends Serializable> extends BaseServiceImpl<T, I>
			implements FieldValidationMappingService<T, I>  {

	@Autowired
	FieldValidationMappingRepository fieldValidationMapingRepository;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected CrudRepository getRepository() {
		return fieldValidationMapingRepository;
	}

	Function<FieldValidationMappingRequestVo, FieldValidationMapping> convert = new Function<FieldValidationMappingRequestVo, FieldValidationMapping>() {

		@Override
		public FieldValidationMapping apply(FieldValidationMappingRequestVo input) {
			FieldValidationMapping entity = new FieldValidationMapping();
			entity.setValidationID(input.getValidationID());
			entity.setFieldID(input.getFieldID());
			entity.setTitle(input.getTitle());
			entity.setToolTip(input.getToolTip());
			entity.setActive(input.isActive());
			return entity;
		}
	};
	
	@SuppressWarnings("unchecked")
	@Override
	protected T convertObjectToEntity(BaseRequest request, Action action) throws Exception {
		FieldValidationMapping entity = null;
		switch (action) {
		case INSERT:
			entity = convert.apply((FieldValidationMappingRequestVo) request);
			break;
		case UPDATE:
		case SEARCH:
			entity = convert.apply((FieldValidationMappingRequestVo) request);
			entity.setId(request.getIdentifier());
			break;
		}
		return (T) entity;
	}
	
}
