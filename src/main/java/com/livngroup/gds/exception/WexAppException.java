package com.livngroup.gds.exception;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.livngroup.gds.response.ErrorResponse;

public class WexAppException extends Exception implements WexException {

	private static final long serialVersionUID = 1L;

	private ErrorResponse errorResponse;

	public WexAppException(Throwable cause, ErrorResponse errorResponse) {
		super(ObjectUtils.defaultIfNull(errorResponse, ErrorResponse.DEAULT).getMessage(), cause);
		this.errorResponse = ObjectUtils.defaultIfNull(errorResponse, ErrorResponse.DEAULT);
	}

	public WexAppException(ErrorResponse errorDetail) {
		super(ObjectUtils.defaultIfNull(errorDetail, ErrorResponse.DEAULT).getMessage());
		this.errorResponse = ObjectUtils.defaultIfNull(errorDetail, ErrorResponse.DEAULT);
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
