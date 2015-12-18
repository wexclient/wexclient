package com.livngroup.gds.exception;

import com.livngroup.gds.response.ErrorResponse;

public interface WexException {

	public String MESSAGE_DEFAULT = "Default error";
	
	public String MESSAGE_REMOTE_CALL_ERROR = "WEX has RMI exception. It could be caused by Server side and network";
	public String MESSAGE_INVALID_CREDENTIALS = "Invalid credentials";
	
 	public ErrorResponse getErrorResponse();
 	
 	public String getMessage();
	
}
