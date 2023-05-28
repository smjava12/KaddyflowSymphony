/**
 * 
 */
package com.labs.kaddy.flow.system.service.level;

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
import com.labs.kaddy.flow.system.dto.request.LevelRequestVo;
import com.labs.kaddy.flow.system.dto.response.BaseResponse;
import com.labs.kaddy.flow.system.dto.response.CommonResponse;
import com.labs.kaddy.flow.system.dto.response.LevelDetailsList;
import com.labs.kaddy.flow.system.dto.response.LevelResponse;
import com.labs.kaddy.flow.system.dto.response.Pagination;
import com.labs.kaddy.flow.system.entity.LevelRequest;
import com.labs.kaddy.flow.system.entity.QLevelRequest;
import com.labs.kaddy.flow.system.entity.WorkflowRequest;
import com.labs.kaddy.flow.system.enums.Action;
import com.labs.kaddy.flow.system.enums.Errors;
import com.labs.kaddy.flow.system.repository.LevelRepository;
import com.labs.kaddy.flow.system.repository.WorkflowRepository;
import com.labs.kaddy.flow.system.service.base.BaseServiceImpl;
import com.labs.kaddy.flow.system.util.CopySourceEntityToDestinationEntity;

/**
 * @author Surjyakanta
 *
 */
@Transactional
@Service
public class LevelServiceImpl<T, I extends Serializable> extends BaseServiceImpl<T, I> implements LevelService<T, I> {

	private static final Logger LOGGER = ESAPI.getLogger(LevelServiceImpl.class);
	private final QLevelRequest qLevelRequest = QLevelRequest.levelRequest;

	@Autowired
	LevelRepository levelRepository;
	@Autowired
	WorkflowRepository workflowRepository;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected CrudRepository getRepository() {
		return levelRepository;
	}

	@Override
	public LevelDetailsList getLevelListById(Integer workflowId, Integer offset, Integer limit) {

		Long totalRecords = levelRepository.countByWorkflowRequestId(workflowId);
		Integer totalCount = totalRecords.intValue();
		if (totalCount == 0) {
			return getEmptyLevelDetailsList();
		}
		if (offset >= totalCount) {
			throw new BadRequestParameterException(Errors.SVC_10_400_3, "offset");
		}
		List<LevelRequest> result = new ArrayList<>();
		try {
			result = ((List<LevelRequest>) levelRepository.findByWorkflowRequestIdAndIsActiveTrue(workflowId)).stream()
					.skip(offset).limit(limit).collect(Collectors.toList());
			Pagination pagiantion = new Pagination(offset, limit, totalCount);
			return getLevelDetailsList(result, pagiantion);
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "", ex);
		}
	}

	@SuppressWarnings({ "deprecation" })
	@Transactional
	@Override
	public BaseResponse saveLevel(LevelRequestVo levelRequestVo, Integer workflowId) {

		if(null == levelRequestVo.getTitle() || StringUtils.isEmpty(levelRequestVo)) {
			throw new BadRequestParameterException(Errors.SVC_10_400_3,"LevelRequestVo Entity", null);
		}
		Optional<WorkflowRequest> optionalWorkflowRequest = workflowRepository.findById(workflowId);
		if (!optionalWorkflowRequest.isPresent()) {
			throw new WorkflowNotFoundException(Errors.SVC_10_404_1, workflowId.toString());
		}
		if (levelRepository.findByWorkflowRequestIdAndIsActiveTrue(workflowId).stream()
				.anyMatch(o -> o.getTitle().equals(levelRequestVo.getTitle()))) {
			throw new BadRequestParameterException(Errors.SVC_10_500_3, "Level Title already Exists: "+levelRequestVo.getTitle());
		}

		levelRequestVo.setWorkflowEntity(optionalWorkflowRequest.get());
		LOGGER.debug(Logger.EVENT_SUCCESS, "Before save ... "+ levelRequestVo);
		BaseResponse response = insert(levelRequestVo);
		LOGGER.debug(Logger.EVENT_SUCCESS, "After save ... "+ response);
		LevelResponse levelResponse = convertEntityToDtoResponse.apply((LevelRequest) response.getOutput().getData());
		response.setStatus("200", "Data is successfully added", levelResponse, null, null);
		return response;

	}

	@Transactional
	@Override
	public BaseResponse update(Map<String, Object> changes, Integer id, String userId) {

		if (levelRepository.countByIdAndIsActiveTrue(id) < 1) {
			throw new BadRequestParameterException(Errors.SVC_10_404_1, "workflowId not found for: {} " + id);
		}

		try {

			LevelRequest levels = levelRepository.findByIdAndIsActiveTrue(id).get();

			LevelRequestVo levelRequestVo = convertEntityToRequestVo.apply(levels);

			changes.forEach((change, value) -> {

				switch (change) {
				case "category":
					levelRequestVo.setCategory((String) value);
					break;
				case "isActive":
					levelRequestVo.setActive((boolean) value);
					break;
				case "isValidation":
					levelRequestVo.setValidation((boolean) value);
					break;
				case "title":
					levelRequestVo.setTitle((String) value);
					break;
				case "type":
					levelRequestVo.setType((String) value);
					break;

				}
			});

			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

			Set<ConstraintViolation<LevelRequestVo>> violations = validator.validate(levelRequestVo, PostUpdate.class);

			if (!violations.isEmpty()) {
				throw new ApiAbortedException(Errors.SVC_10_500_1, "", null);
			}
			CopySourceEntityToDestinationEntity.levelVoRequestToEntityRequestMapper(levelRequestVo, levels);
			BaseResponse response = new CommonResponse();
			LOGGER.debug(Logger.EVENT_SUCCESS, "Before update ... "+ levels);
			LevelRequest res = levelRepository.save(levels);
			LOGGER.debug(Logger.EVENT_SUCCESS, "After update ... "+ res);
			LevelResponse levelResponse = convertEntityToDtoResponse.apply((res));
			response.setStatus("200", "Data is successfully added", levelResponse, null, null);
			return response;
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "", ex);
		}
	}

	@Transactional
	@Override
	public BaseResponse deleteLevelRequest(Integer id, String userId) {
		if (levelRepository.countByIdAndIsActiveTrue(id) < 1) {
			throw new BadRequestParameterException(Errors.SVC_10_404_1, "Level not found for: {} " + id);
		}
		try {

			LevelRequest entity = new LevelRequest();
			entity.setUpdatedBy(userId);
			LOGGER.debug(Logger.EVENT_SUCCESS, "Before delete of ID: "+ id);
			int result = levelRepository.deleteLevel(false, id, userId);
			LOGGER.debug(Logger.EVENT_SUCCESS, "After delete result: "+ result);
			BaseResponse response = new CommonResponse();
			response.setStatus("200", "Data successfully deleted for: " + id, result, null, null);
			return response;
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "", ex);
		}
	}

	@SuppressWarnings({ "static-access" })
	private LevelDetailsList getLevelDetailsList(List<LevelRequest> result, Pagination pagiantion) {

		LevelDetailsList ldList = new LevelDetailsList();
		try {
			ldList.setCount(result.size());
			List<LevelResponse> levelList = new ArrayList<>();
			for (LevelRequest req : result) {
				LevelResponse response = new LevelResponse()
						.builder()
							.category(req.getCategory())
							.createdAt(req.getCreatedAt())
							.createdBy(req.getCategory())
							.fieldEntity(req.getFieldEntity())
							.id(req.getId())
							.isActive(req.isActive())
							.isValidation(req.isValidation())
							.title(req.getTitle())
							.type(req.getType())
							.updatedAt(req.getUpdatedAt())
							.updatedBy(req.getUpdatedBy())
							.workflowId(req.getWorkflowId())
							.workflowEntity(req.getWorkflowRequest())
						.build();
				levelList.add(response);
			}
			ldList.setLevelResponse(levelList);
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "", ex);
		}
		return ldList;
	}

	@SuppressWarnings("static-access")
	private LevelDetailsList getEmptyLevelDetailsList() {
		LevelDetailsList levelDetailsList = new LevelDetailsList()
				.builder()
					.count(0)
				.build();
		return levelDetailsList;
	}

	Function<LevelRequestVo, LevelRequest> convert = new Function<LevelRequestVo, LevelRequest>() {

		@SuppressWarnings("static-access")
		@Override
		public LevelRequest apply(LevelRequestVo input) {
			LevelRequest entity = new LevelRequest()
					.builder()
						.workflowId(input.getWorkflowEntity().getWorkflowId())
						.title(input.getTitle())
						.type(input.getType())
						.isActive(input.isActive())
						.isValidation(input.isValidation())
						.category(input.getCategory())
						.createdBy(input.getCategory())
						.fieldEntity(input.getFieldEntity())
						.workflowRequest(input.getWorkflowEntity())
						.updatedBy(input.getUpdatedBy())
					.build();
			return entity;
		}
	};

	Function<LevelRequest, LevelResponse> convertEntityToDtoResponse = new Function<LevelRequest, LevelResponse>() {

		@SuppressWarnings("static-access")
		@Override
		public LevelResponse apply(LevelRequest input) {
			LevelResponse response = new LevelResponse()
					.builder()
						.category(input.getCategory())
						.createdAt(input.getCreatedAt())
						.createdBy(input.getCategory())
						.fieldEntity(input.getFieldEntity())
						.id(input.getId())
						.isActive(input.isActive())
						.isValidation(input.isValidation())
						.title(input.getTitle())
						.type(input.getType())
						.updatedAt(input.getUpdatedAt())
						.updatedBy(input.getUpdatedBy())
						.workflowEntity(input.getWorkflowRequest())
					.build();
			return response;
		}
	};

	Function<LevelRequest, LevelRequestVo> convertEntityToRequestVo = new Function<LevelRequest, LevelRequestVo>() {

		@SuppressWarnings("static-access")
		@Override
		public LevelRequestVo apply(LevelRequest input) {
			LevelRequestVo entity = new LevelRequestVo()
					.builder()
						.category(input.getCategory())
						.createdAt(input.getCreatedAt())
						.createdBy(input.getCategory())
						.fieldEntity(input.getFieldEntity())
						.id(input.getId())
						.isActive(input.isActive())
						.isValidation(input.isValidation())
						.title(input.getTitle())
						.type(input.getType())
						.updatedAt(input.getUpdatedAt())
						.updatedBy(input.getUpdatedBy())
						.workflowId(input.getWorkflowId())
					.build();

			return entity;
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	protected T convertObjectToEntity(BaseRequest request, Action action) throws Exception {
		LevelRequest entity = null;
		switch (action) {
		case INSERT:
			entity = convert.apply((LevelRequestVo) request);
			break;
		case UPDATE:
			entity = convert.apply((LevelRequestVo) request);
			break;
		case SEARCH:
			entity = convert.apply((LevelRequestVo) request);
			entity.setId(request.getIdentifier());
			break;
		}
		return (T) entity;
	}

}