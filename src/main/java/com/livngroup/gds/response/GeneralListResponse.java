package com.livngroup.gds.response;

import java.util.List;

public class GeneralListResponse {
	private Boolean ok;
	private String message;
	private List<Object> result;
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
	public List<Object> getResult() {
		return result;
	}
	public void setResult(List<Object> result) {
		this.result = result;
	}

}