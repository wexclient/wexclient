package com.livngroup.gds.exception;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.livngroup.gds.response.ErrorResponse;

public class WexRuntimeException extends RuntimeException implements WexException {

	private static final long serialVersionUID = 1L;

	private ErrorResponse errorResponse;

	public WexRuntimeException(Throwable cause, ErrorResponse errorResponse) {
		super(ObjectUtils.defaultIfNull(errorResponse, ErrorResponse.DEAULT).getMessage(), cause);
		this.errorResponse = ObjectUtils.defaultIfNull(errorResponse, ErrorResponse.DEAULT);
	}

	public WexRuntimeException(ErrorResponse errorResponse) {
		super(ObjectUtils.defaultIfNull(errorResponse, ErrorResponse.DEAULT).getMessage());
		this.errorResponse = ObjectUtils.defaultIfNull(errorResponse, ErrorResponse.DEAULT);
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
