package com.livngroup.gds.response;

import org.springframework.http.HttpStatus;

public class CallResponse extends GeneralResponse {

	private HttpStatus status;
	
	public CallResponse() {
		super();
	}
	
	public CallResponse(Boolean ok, String message) {
		super(ok, message);
	}

	public CallResponse(Boolean ok, String message, HttpStatus httpStatus) {
		super(ok, message);
		this.status = httpStatus;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public static CallResponse forSuccess(Object result) {
		CallResponse response = new CallResponse();
		response.setOk(true);
		response.setMessage("Success");
		response.setStatus(HttpStatus.OK);
		response.setResult(result);
		return response;	
	}
	
	public static CallResponse forError(String errorCode, String errorDescription) {
		CallResponse response = new CallResponse();
		response.setOk(false);
		response.setMessage(String.format("WEX : [code] - %s [description] - %s", errorCode, errorDescription));
		response.setStatus(HttpStatus.BAD_REQUEST);
		return response;	
	}
	
}
