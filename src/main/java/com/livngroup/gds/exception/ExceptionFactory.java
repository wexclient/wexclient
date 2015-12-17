package com.livngroup.gds.exception;

import org.springframework.http.HttpStatus;

import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.response.ErrorResponse;

public class ExceptionFactory {

	public static WexAppException createServiceUnavailableForEntityException(Throwable cause, WexEntity wexEntity) {
		return new WexAppException(cause, 
				new ErrorResponse(
						HttpStatus.SERVICE_UNAVAILABLE, 
						ErrorResponse.REMOTE_CALL_ERROR_CODE, 
						wexEntity, 
						WexException.MESSAGE_REMOTE_CALL_ERROR, 
						ErrorResponse.URL_DEFAULT, 
						"",
						null));
	}
	
}
