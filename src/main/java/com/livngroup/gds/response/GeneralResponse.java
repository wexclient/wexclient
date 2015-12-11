package com.livngroup.gds.response;

import java.io.Serializable;

public class GeneralResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String SUCCESS = "Success";
	public static final String FAILURE = "Failure";
	
	private Boolean ok;
	private String message;
	private Object result;
	
	public GeneralResponse() {
		this.ok = false;
		this.message = FAILURE;
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
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}

}
