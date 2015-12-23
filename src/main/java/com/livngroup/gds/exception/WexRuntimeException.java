package com.livngroup.gds.exception;

import org.apache.commons.lang3.StringUtils;

import com.livngroup.gds.response.ErrorResponse;

public class WexRuntimeException extends RuntimeException implements WexException {

	private static final long serialVersionUID = 1L;

	private ErrorResponse errorResponse;

	public WexRuntimeException(Throwable cause, ErrorResponse errorResponse) {
		super(WexException.defaultIfNull(errorResponse).getMessage(), cause);
		this.errorResponse = WexException.defaultIfNull(errorResponse);
	}

	public WexRuntimeException(ErrorResponse errorResponse) {
		super(WexException.defaultIfNull(errorResponse).getMessage());
		this.errorResponse = WexException.defaultIfNull(errorResponse);
	}

	@Override
	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	@Override
	public String getMessage() {
		return errorResponse.toString() + ": " +  StringUtils.trimToEmpty(super.getMessage());
	}
	
}
