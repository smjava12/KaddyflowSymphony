/**
 * 
 */
package com.labs.kaddy.flow.system.service.field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.PostUpdate;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.labs.kaddy.flow.infra.exception.ApiAbortedException;
import com.labs.kaddy.flow.infra.exception.BadRequestParameterException;
import com.labs.kaddy.flow.infra.exception.WorkflowNotFoundException;
import com.labs.kaddy.flow.system.dto.request.BaseRequest;
import com.labs.kaddy.flow.system.dto.request.FieldRequestVo;
import com.labs.kaddy.flow.system.dto.response.BaseResponse;
import com.labs.kaddy.flow.system.dto.response.CommonResponse;
import com.labs.kaddy.flow.system.dto.response.FieldDetailsList;
import com.labs.kaddy.flow.system.dto.response.FieldResponse;
import com.labs.kaddy.flow.system.dto.response.Pagination;
import com.labs.kaddy.flow.system.entity.FieldRequest;
import com.labs.kaddy.flow.system.entity.LevelRequest;
import com.labs.kaddy.flow.system.enums.Action;
import com.labs.kaddy.flow.system.enums.Errors;
import com.labs.kaddy.flow.system.repository.FieldRepository;
import com.labs.kaddy.flow.system.repository.LevelRepository;
import com.labs.kaddy.flow.system.service.base.BaseServiceImpl;
import com.labs.kaddy.flow.system.util.CopySourceEntityToDestinationEntity;

/**
 * @author Surjyakanta
 *
 */
@Service
public class FieldServiceImpl<T, I extends Serializable> extends BaseServiceImpl<T, I>
			implements FieldService<T, I>   {

	private static final Logger LOGGER = ESAPI.getLogger(FieldServiceImpl.class);

	@Autowired
	FieldRepository fieldRepository;
	@Autowired
	LevelRepository levelRepository;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected CrudRepository getRepository() {
		return fieldRepository;
	}



	@SuppressWarnings("deprecation")
	@Transactional
	@Override
	public BaseResponse saveField(FieldRequestVo fieldRequestVo, Integer levelRequestId) {

		if(null == fieldRequestVo.getTitle() || StringUtils.isEmpty(fieldRequestVo)) {
			throw new BadRequestParameterException(Errors.SVC_10_400_3,"LevelRequestVo Entity", null);
		}
		Optional<LevelRequest> optionalLevelRequest = levelRepository.findByIdAndIsActiveTrue(levelRequestId);
		if (!optionalLevelRequest.isPresent()) {
			throw new WorkflowNotFoundException(Errors.SVC_10_404_1, levelRequestId.toString());
		}
		if (((FieldRepository) getRepository()).findByLevelRequestIdAndIsActiveTrue(levelRequestId).get().stream()
				.anyMatch(o -> o.getTitle().equals(fieldRequestVo.getTitle()))) {
			throw new BadRequestParameterException(Errors.SVC_10_500_3, "Level Title already Exists: "+fieldRequestVo.getTitle());
		}

		fieldRequestVo.setLevelRequest(optionalLevelRequest.get());
		LOGGER.debug(Logger.EVENT_SUCCESS, "Before save ... "+ fieldRequestVo);
		BaseResponse response = insert(fieldRequestVo);
		LOGGER.debug(Logger.EVENT_SUCCESS, "After save ... "+ response);
		FieldResponse fieldResponse = convertEntityToDtoResponse.apply((FieldRequest) response.getOutput().getData());
		response.setStatus("200", "Data is successfully added", fieldResponse, null, null);
		return response;

	}

	@Override
	public FieldDetailsList getFieldListById(Integer levelRequestId, Integer offset, Integer limit) {
		Long totalRecords = ((FieldRepository) getRepository()).countByLevelRequestId(levelRequestId);
		Integer totalCount = totalRecords.intValue();
		if (totalCount == 0) {
			return getEmptyFieldDetailsList();
		}
		if (offset >= totalCount) {
			throw new BadRequestParameterException(Errors.SVC_10_400_3, "offset");
		}
		List<FieldRequest> result = new ArrayList<>();
		try {
			result = fieldRepository.findByLevelRequestIdAndIsActiveTrue(levelRequestId).get().stream()
					.skip(offset).limit(limit).collect(Collectors.toList());
			Pagination pagiantion = new Pagination(offset, limit, totalCount);
			return getFieldDetailsList(result, pagiantion);
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "", ex);
		}
	}

	@Transactional
	@Override
	public BaseResponse updateField(@Valid Map<String, Object> changes, Integer id, String user) {

		if (fieldRepository.countByIdAndIsActiveTrue(id) < 1) {
			throw new BadRequestParameterException(Errors.SVC_10_404_1, "Field not found for: {} " + id);
		}

		try {

			FieldRequest field = fieldRepository.findByIdAndIsActiveTrue(id).get();

			FieldRequestVo fieldRequestVo = convertEntityToRequestVo.apply(field);

			changes.forEach((change, value) -> {

				switch (change) {
				case "title":
					fieldRequestVo.setTitle((String) value);
					break;
				case "value":
					fieldRequestVo.setType((String) value);
					break;
				case "type":
					fieldRequestVo.setType((String) value);
					break;
				case "category":
					fieldRequestVo.setCategory((String) value);
					break;
				case "isActive":
					fieldRequestVo.setActive((boolean) value);
					break;
				case "isValidation":
					fieldRequestVo.setValidation((boolean) value);
					break;
				}
			});

			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

			Set<ConstraintViolation<FieldRequestVo>> violations = validator.validate(fieldRequestVo, PostUpdate.class);

			if (!violations.isEmpty()) {
				throw new ApiAbortedException(Errors.SVC_10_500_1, "", null);
			}
			CopySourceEntityToDestinationEntity.fieldVoRequestToEntityRequestMapper(fieldRequestVo, field);
			field.setUpdatedBy(user);
			BaseResponse response = new CommonResponse();
			LOGGER.debug(Logger.EVENT_SUCCESS, "Before update ... "+ field);
			FieldRequest res = fieldRepository.save(field);
			LOGGER.debug(Logger.EVENT_SUCCESS, "After update ... "+ res);
			FieldResponse fieldResponse = convertEntityToDtoResponse.apply((res));
			response.setStatus("200", "Data is successfully added", fieldResponse, null, null);
			return response;
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "", ex);
		}
	}

	@Transactional
	@Override
	public BaseResponse deleteField(Integer id, String userId) {
		if (fieldRepository.countByIdAndIsActiveTrue(id) < 1) {
			throw new BadRequestParameterException(Errors.SVC_10_404_1, "Level not found for: {} " + id);
		}
		try {

			FieldRequest entity = new FieldRequest();
			entity.setUpdatedBy(userId);
			LOGGER.debug(Logger.EVENT_SUCCESS, "Before delete of ID: "+ id);
			int result = fieldRepository.deleteField(false, id, userId);
			LOGGER.debug(Logger.EVENT_SUCCESS, "After delete result: "+ result);
			BaseResponse response = new CommonResponse();
			response.setStatus("200", "Data successfully deleted for: " + id, result, null, null);
			return response;
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "", ex);
		}
	}

	
	Function<FieldRequestVo, FieldRequest> convert = new Function<FieldRequestVo, FieldRequest>() {

		@SuppressWarnings("static-access")
		@Override
		public FieldRequest apply(FieldRequestVo input) {
			
			
			FieldRequest entity = new FieldRequest()
					.builder()
						.category(input.getCategory())
						.createdBy(input.getCategory())
						.isActive(input.isActive())
						.isValidation(input.isValidation())
						.title(input.getTitle())
						.type(input.getType())
						.value(input.getValue())
						.levelRequest(input.getLevelRequest())
						.validationRequest(input.getValidationRequest())
						.updatedBy(input.getUpdatedBy())
					.build();
			
			return entity;
		}
	};
	
	@SuppressWarnings("unchecked")
	@Override
	protected T convertObjectToEntity(BaseRequest request, Action action) throws Exception {
		FieldRequest entity = null;
		switch (action) {
		case INSERT:
			entity = convert.apply((FieldRequestVo) request);
			break;
		case UPDATE:
		case SEARCH:
			entity = convert.apply((FieldRequestVo) request);
			entity.setId(request.getIdentifier());
			break;
		}
		return (T) entity;
	}
	
	Function<FieldRequest, FieldResponse> convertEntityToDtoResponse = new Function<FieldRequest, FieldResponse>() {

		@SuppressWarnings("static-access")
		@Override
		public FieldResponse apply(FieldRequest input) {
			FieldResponse response = new FieldResponse()
					.builder()
						.category(input.getCategory())
						.createdBy(input.getCategory())
						.id(input.getId())
						.isActive(input.isActive())
						.isValidation(input.isValidation())
						.title(input.getTitle())
						.type(input.getType())
						.updatedAt(input.getUpdatedAt())
						.updatedBy(input.getUpdatedBy())
					.build();
			return response;
		}
	};

	@SuppressWarnings({ "static-access", "unused" })
	private FieldDetailsList getEmptyFieldDetailsList() {
		FieldDetailsList fieldDetailsList = new FieldDetailsList()
				.builder()
					.count(0)
				.build();
		return fieldDetailsList;
	}

	@SuppressWarnings({ "static-access" })
	private FieldDetailsList getFieldDetailsList(List<FieldRequest> result, Pagination pagiantion) {

		FieldDetailsList ldList = new FieldDetailsList();
		try {
			ldList.setCount(result.size());
			List<FieldResponse> fieldList = new ArrayList<>();
			for (FieldRequest req : result) {
				FieldResponse response = new FieldResponse()
						.builder()
							.category(req.getCategory())
							.createdAt(req.getCreatedAt())
							.createdBy(req.getCategory())
							.id(req.getId())
							.isActive(req.isActive())
							.isValidation(req.isValidation())
							.title(req.getTitle())
							.type(req.getType())
							.updatedAt(req.getUpdatedAt())
							.updatedBy(req.getUpdatedBy())
						.build();
				fieldList.add(response);
			}
			ldList.setFieldResponse(fieldList);
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "", ex);
		}
		return ldList;
	}

	Function<FieldRequest, FieldRequestVo> convertEntityToRequestVo = new Function<FieldRequest, FieldRequestVo>() {

		@SuppressWarnings("static-access")
		@Override
		public FieldRequestVo apply(FieldRequest input) {
			FieldRequestVo entity = new FieldRequestVo()
					.builder()
						.id(input.getId())
						.title(input.getTitle())
						.value(input.getValue())
						.category(input.getCategory())
						.isActive(input.isActive())
						.isValidation(input.isValidation())
						.type(input.getType())
						.createdAt(input.getCreatedAt())
						.createdBy(input.getCategory())
						.updatedAt(input.getUpdatedAt())
						.updatedBy(input.getUpdatedBy())
					.build();

			return entity;
		}
	};
}
