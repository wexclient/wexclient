package com.livngroup.gds.response;

import org.springframework.http.HttpStatus;

public class CallResponse extends GeneralResponse {

	private static final long serialVersionUID = 1L;
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

}
