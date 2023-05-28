/**
 * 
 */
package com.labs.kaddy.flow.system.dto.response;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.labs.kaddy.flow.infra.constants.Constants;

/**
 * @author mak
 *
 */
public abstract class BaseResponse implements Constants {

	private StatusObject output;

	public void setStatus(Map<String,Object> responseData) {
		output = new StatusObject();
		output.setStatusCode((String) responseData.get(STATUS_CODE));
		output.setStatusMessage((String) responseData.get(STATUS_MESSAGE));
		
		output.setData(responseData.get(DATA));
		
		ErrorObject errorObject = new ErrorObject();
		errorObject.setErrorCode((String) responseData.get(ERROR_CODE));
		errorObject.setErrorMessage((String) responseData.get(ERROR_MESSAGE));
		errorObject.setTimestamp(new Timestamp(new Date().getTime()));
		
		output.setError(errorObject);
	}
	
	public StatusObject getOutput() {
		return output;
	}

	public void setOutput(StatusObject output) {
		this.output = output;
	}

	public void setStatus(String statusCode, String statusMessage, Object data, String errorCode, String errorMessage) {
		Map<String,Object> statusObjectMap = new HashMap<>();
		
		statusObjectMap.put(STATUS_CODE, statusCode);
		statusObjectMap.put(STATUS_MESSAGE, statusMessage);
		statusObjectMap.put(DATA, data);
		statusObjectMap.put(ERROR_CODE, errorCode);
		statusObjectMap.put(ERROR_MESSAGE, errorMessage);
		
		setStatus(statusObjectMap);		
	}
}
