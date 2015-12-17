package com.livngroup.gds.response;

public class GeneralResponse implements IWexResponse<Object> {
	
	private Boolean ok;
	private String message;
	private Object result;
	
	public GeneralResponse() {
		this.ok = false;
		this.message = FAILURE;
	}
	
	public GeneralResponse(Boolean ok, String message) {
		this.ok = ok;
		this.message = message;
	}

	@Override
	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
}
