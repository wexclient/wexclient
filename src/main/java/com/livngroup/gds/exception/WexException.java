package com.livngroup.gds.exception;

import java.util.Optional;

import com.livngroup.gds.response.ErrorResponse;

public interface WexException {

	public String MESSAGE_DEFAULT = "Default error";
	
	public String MESSAGE_REMOTE_CALL_ERROR = "WEX has RMI exception. It could be caused by Server side and network";
	public String MESSAGE_INVALID_CREDENTIALS = "Invalid credentials";
	
	public static ErrorResponse defaultIfNull(ErrorResponse errorResponse) {
		return Optional.ofNullable(errorResponse).orElse(ErrorResponse.DEAULT);
	}
	
 	public ErrorResponse getErrorResponse();
 	
 	public String getMessage();
	
}
