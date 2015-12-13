package com.livngroup.gds.response;

import org.springframework.http.HttpStatus;

public class GeneralResponse {
	private Boolean ok;
	private String message;
	private HttpStatus status;
	private Object result;
	
	public GeneralResponse() {
		this.ok = false;
		this.message = "Fail";
	}
	
	public GeneralResponse(Boolean ok, String message) {
		this.ok = ok;
		this.message = message;
	}

	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
}
