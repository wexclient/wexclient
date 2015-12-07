package com.livngroup.gds.response;

public class GeneralResponse {
	private Boolean ok;
	private String message;
	private Object result;
	
	public GeneralResponse() {
		this.ok = false;
		this.message = "Fail";
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
