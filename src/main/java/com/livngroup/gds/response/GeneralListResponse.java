package com.livngroup.gds.response;

import java.util.List;

public class GeneralListResponse implements IWexResponse<List<Object>> {
	private Boolean ok;
	private String message;
	private List<Object> result;
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
	public List<Object> getResult() {
		return result;
	}
	public void setResult(List<Object> result) {
		this.result = result;
	}

}