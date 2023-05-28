package com.labs.kaddy.flow.system.dto.response;

import java.util.Date;

public class StatusObject {

	
	private String statusCode;
	private String statusMessage;
	private Object data;
	private ErrorObject error;
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public ErrorObject getError() {
		return error;
	}
	public void setError(ErrorObject error) {
		this.error = error;
	}	
}

class ErrorObject {
	private String errorCode;
	private String errorMessage;
	private Date timestamp;
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}