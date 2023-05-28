/**
 * 
 */
package com.labs.kaddy.flow.system.service.status;

import java.io.Serializable;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.labs.kaddy.flow.system.dto.request.BaseRequest;
import com.labs.kaddy.flow.system.dto.request.StatusRequestVo;
import com.labs.kaddy.flow.system.entity.StatusRequest;
import com.labs.kaddy.flow.system.enums.Action;
import com.labs.kaddy.flow.system.repository.StatusRepository;
import com.labs.kaddy.flow.system.service.base.BaseServiceImpl;

/**
 * @author Surjyakanta
 *
 */
@Service
public class StatusServiceImpl<T, I extends Serializable> extends BaseServiceImpl<T, I>
			implements StatusService<T, I> {

	@Autowired
	StatusRepository statusRepository;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected CrudRepository getRepository() {
		return statusRepository;
	}

	Function<StatusRequestVo, StatusRequest> convert = new Function<StatusRequestVo, StatusRequest>() {

		@Override
		public StatusRequest apply(StatusRequestVo input) {
			StatusRequest entity = new StatusRequest();
			entity.setLabelAliasName(input.getLabelAliasName());
			return entity;
		}
	};
	
	@SuppressWarnings("unchecked")
	@Override
	protected T convertObjectToEntity(BaseRequest request, Action action) throws Exception {
		StatusRequest entity = null;
		switch (action) {
		case INSERT:
			entity = convert.apply((StatusRequestVo) request);
			break;
		case UPDATE:
		case SEARCH:
			entity = convert.apply((StatusRequestVo) request);
			entity.setId(request.getIdentifier());
			break;
		}
		return (T) entity;
	}
	
}
