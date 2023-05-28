/**
 * 
 */
package com.labs.kaddy.flow.system.service.validation;

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
import com.labs.kaddy.flow.system.dto.request.ValidationRequestVo;
import com.labs.kaddy.flow.system.dto.response.BaseResponse;
import com.labs.kaddy.flow.system.dto.response.CommonResponse;
import com.labs.kaddy.flow.system.dto.response.Pagination;
import com.labs.kaddy.flow.system.dto.response.ValidationDetailsList;
import com.labs.kaddy.flow.system.dto.response.ValidationResponse;
import com.labs.kaddy.flow.system.entity.FieldRequest;
import com.labs.kaddy.flow.system.entity.ValidationRequest;
import com.labs.kaddy.flow.system.enums.Action;
import com.labs.kaddy.flow.system.enums.Errors;
import com.labs.kaddy.flow.system.repository.FieldRepository;
import com.labs.kaddy.flow.system.repository.ValidationRepository;
import com.labs.kaddy.flow.system.service.base.BaseServiceImpl;
import com.labs.kaddy.flow.system.util.CopySourceEntityToDestinationEntity;

/**
 * @author Surjyakanta
 *
 */
@Service
public class ValidationServiceImpl<T, I extends Serializable> extends BaseServiceImpl<T, I>
		implements ValidationService<T, I> {

	private static final Logger LOGGER = ESAPI.getLogger(ValidationServiceImpl.class);

	@Autowired
	ValidationRepository validationRepository;
	
	@Autowired
	FieldRepository fieldRepository;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected CrudRepository getRepository() {
		return validationRepository;
	}


	@SuppressWarnings("deprecation")
	@Transactional
	@Override
	public BaseResponse saveValidation(ValidationRequestVo validationRequestVo, Integer fieldRequestId) {

		if(null == validationRequestVo.getTitle() || StringUtils.isEmpty(validationRequestVo)) {
			throw new BadRequestParameterException(Errors.SVC_10_400_3,"LevelRequestVo Entity", null);
		}
		Optional<FieldRequest> optionalFieldRequest = fieldRepository.findByIdAndIsActiveTrue(fieldRequestId);
		if (!optionalFieldRequest.isPresent()) {
			throw new WorkflowNotFoundException(Errors.SVC_10_404_1, fieldRequestId.toString());
		}
		if (((ValidationRepository) getRepository()).findByFieldRequestIdAndIsActiveTrue(fieldRequestId).get().stream()
				.anyMatch(o -> o.getTitle().equals(validationRequestVo.getTitle()))) {
			throw new BadRequestParameterException(Errors.SVC_10_500_3, "Validation Title already Exists: "+validationRequestVo.getTitle());
		}
		if (((ValidationRepository) getRepository()).findByFieldRequestIdAndIsActiveTrue(fieldRequestId).isEmpty()) {
			throw new BadRequestParameterException(Errors.SVC_10_500_4, ""+validationRequestVo.getTitle());
		}

		validationRequestVo.setFieldRequest(optionalFieldRequest.get());
		LOGGER.debug(Logger.EVENT_SUCCESS, "Before save ... ");
		BaseResponse response = insert(validationRequestVo);
		LOGGER.debug(Logger.EVENT_SUCCESS, "After save ... ");
		ValidationResponse validationResponse = convertEntityToDtoResponse.apply((ValidationRequest) response.getOutput().getData());
		response.setStatus("200", "Data is successfully added", validationResponse, null, null);
		return response;
	}

	@Override
	public ValidationDetailsList getFieldListById(Integer fieldRequestId, Integer offset, Integer limit) {
		Long totalRecords = ((ValidationRepository) getRepository()).countByFieldRequestId(fieldRequestId);
		Integer totalCount = totalRecords.intValue();
		if (totalCount == 0) {
			return getEmptyValidationDetailsList();
		}
		if (offset >= totalCount) {
			throw new BadRequestParameterException(Errors.SVC_10_400_3, "offset");
		}
		List<ValidationRequest> result = new ArrayList<>();
		try {
			result = validationRepository.findByFieldRequestIdAndIsActiveTrue(fieldRequestId).get().stream()
					.skip(offset).limit(limit).collect(Collectors.toList());
			Pagination pagiantion = new Pagination(offset, limit, totalCount);
			return getValidationDetailsList(result, pagiantion);
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "", ex);
		}
	}

	@Transactional
	@Override
	public BaseResponse updateValidation(@Valid Map<String, Object> changes, Integer id, String user) {

		if (validationRepository.countByIdAndIsActiveTrue(id) < 1) {
			throw new BadRequestParameterException(Errors.SVC_10_404_1, "Validation not found for: {} " + id);
		}
		try {
			ValidationRequest field = validationRepository.findByIdAndIsActiveTrue(id).get();
			ValidationRequestVo validationRequestVo = convertEntityToRequestVo.apply(field);

			changes.forEach((change, value) -> {
				switch (change) {
				case "title":
					validationRequestVo.setTitle((String) value);
					break;
				case "type":
					validationRequestVo.setType((String) value);
					break;
				case "value":
					validationRequestVo.setType((String) value);
					break;
				case "isEnforce":
					validationRequestVo.setEnforce((boolean) value);
					break;
				case "isVisibility":
					validationRequestVo.setVisibility((boolean) value);
					break;
				case "isActive":
					validationRequestVo.setActive((boolean) value);
					break;
				}
			});

			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

			Set<ConstraintViolation<ValidationRequestVo>> violations = validator.validate(validationRequestVo, PostUpdate.class);

			if (!violations.isEmpty()) {
				throw new ApiAbortedException(Errors.SVC_10_500_1, "", null);
			}
			CopySourceEntityToDestinationEntity.validationVoRequestToEntityRequestMapper(validationRequestVo, field);
			field.setUpdatedBy(user);
			BaseResponse response = new CommonResponse();
			LOGGER.debug(Logger.EVENT_SUCCESS, "Before update ... "+ field);
			ValidationRequest res = validationRepository.save(field);
			LOGGER.debug(Logger.EVENT_SUCCESS, "After update ... "+ res);
			ValidationResponse validationResponse = convertEntityToDtoResponse.apply((res));
			response.setStatus("200", "Data is successfully added", validationResponse, null, null);
			return response;
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "", ex);
		}
	}

	@Transactional
	@Override
	public BaseResponse deleteField(Integer id, String user) {
		if (validationRepository.countByIdAndIsActiveTrue(id) < 1) {
			throw new BadRequestParameterException(Errors.SVC_10_404_1, "Validation not found for: {} " + id);
		}
		try {

			FieldRequest entity = new FieldRequest();
			entity.setUpdatedBy(user);
			LOGGER.debug(Logger.EVENT_SUCCESS, "Before delete of ID: "+ id);
			int result = validationRepository.deleteField(false, id, user);
			LOGGER.debug(Logger.EVENT_SUCCESS, "After delete result: "+ result);
			BaseResponse response = new CommonResponse();
			response.setStatus("200", "Data successfully deleted for: " + id, result, null, null);
			return response;
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "", ex);
		}
	}

	Function<ValidationRequestVo, ValidationRequest> convert = new Function<ValidationRequestVo, ValidationRequest>() {

		@Override
		public ValidationRequest apply(ValidationRequestVo input) {
			ValidationRequest entity = new ValidationRequest();
			entity.setTitle(input.getTitle());
			entity.setType(input.getType());
			entity.setActive(input.isActive());
			entity.setEnforce(input.isEnforce());
			entity.setVisibility(input.isVisibility());
			entity.setValue(input.getValue());
			entity.setFieldRequest(input.getFieldRequest());
			return entity;
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	protected T convertObjectToEntity(BaseRequest request, Action action) throws Exception {
		ValidationRequest entity = null;
		switch (action) {
		case INSERT:
			entity = convert.apply((ValidationRequestVo) request);
			break;
		case UPDATE:
		case SEARCH:
			entity = convert.apply((ValidationRequestVo) request);
			entity.setId(request.getIdentifier());
			break;
		}
		return (T) entity;
	}

	
	Function<ValidationRequest, ValidationResponse> convertEntityToDtoResponse = new Function<ValidationRequest, ValidationResponse>() {

		@SuppressWarnings("static-access")
		@Override
		public ValidationResponse apply(ValidationRequest input) {
			ValidationResponse response = new ValidationResponse()
					.builder()
						.id(input.getId())
						.title(input.getTitle())
						.type(input.getType())
						.value(input.getValue())
						.isEnforce(input.isEnforce())
						.isVisibility(input.isVisibility())
						.fieldRequest(input.getFieldRequest())
						.isActive(input.isActive())
						.updatedAt(input.getUpdatedAt())
						.updatedBy(input.getUpdatedBy())
					.build();
			return response;
		}
	};

	@SuppressWarnings({ "static-access", "unused" })
	private ValidationDetailsList getEmptyValidationDetailsList() {
		ValidationDetailsList validationDetailsList = new ValidationDetailsList()
				.builder()
					.count(0)
				.build();
		return validationDetailsList;
	}

	
	@SuppressWarnings({ "static-access" })
	private ValidationDetailsList getValidationDetailsList(List<ValidationRequest> result, Pagination pagiantion) {

		ValidationDetailsList ldList = new ValidationDetailsList();
		try {
			ldList.setCount(result.size());
			List<ValidationResponse> fieldList = new ArrayList<>();
			for (ValidationRequest req : result) {
				ValidationResponse response = new ValidationResponse()
						.builder()
							.id(req.getId())
							.title(req.getTitle())
							.type(req.getType())
							.value(req.getValue())
							.isEnforce(req.isEnforce())
							.isVisibility(req.isVisibility())
							.fieldRequest(req.getFieldRequest())
							.isActive(req.isActive())
							.createdAt(req.getCreatedAt())
							.updatedAt(req.getUpdatedAt())
							.updatedBy(req.getUpdatedBy())
						.build();
				fieldList.add(response);
			}
			ldList.setValidationResponse(fieldList);
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "", ex);
		}
		return ldList;
	}

	Function<ValidationRequest, ValidationRequestVo> convertEntityToRequestVo = new Function<ValidationRequest, ValidationRequestVo>() {

		@SuppressWarnings("static-access")
		@Override
		public ValidationRequestVo apply(ValidationRequest input) {
			ValidationRequestVo entity = new ValidationRequestVo()
						.builder()
							.id(input.getId())
							.title(input.getTitle())
							.type(input.getType())
							.value(input.getValue())
							.isEnforce(input.isEnforce())
							.isVisibility(input.isVisibility())
							.fieldRequest(input.getFieldRequest())
							.isActive(input.isActive())
							.createdAt(input.getCreatedAt())
							.updatedAt(input.getUpdatedAt())
							.updatedBy(input.getUpdatedBy())
						.build();

			return entity;
		}
	};
}
