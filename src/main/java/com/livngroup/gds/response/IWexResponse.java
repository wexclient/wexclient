package com.livngroup.gds.response;

public interface IWexResponse<T> {
	
	public static final String SUCCESS = "Success";
	public static final String FAILURE = "Failure";

	public Boolean getOk();
	public String getMessage();
	public T getResult();
	
}
