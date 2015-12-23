package com.livngroup.gds.exception;

import org.apache.commons.lang3.StringUtils;

import com.livngroup.gds.response.ErrorResponse;

public class WexAppException extends Exception implements WexException {

	private static final long serialVersionUID = 1L;

	private ErrorResponse errorResponse;

	public WexAppException(Throwable cause, ErrorResponse errorResponse) {
		super(WexException.defaultIfNull(errorResponse).getMessage(), cause);
		this.errorResponse = WexException.defaultIfNull(errorResponse);
	}

	public WexAppException(ErrorResponse errorResponse) {
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
