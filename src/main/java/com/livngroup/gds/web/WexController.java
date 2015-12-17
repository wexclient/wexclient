package com.livngroup.gds.web;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.exception.WexException;
import com.livngroup.gds.exception.WexRuntimeException;
import com.livngroup.gds.response.ErrorResponse;

public abstract class WexController {
	
	final protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(WexRuntimeException.class)
	public ErrorResponse handleRuntimeException(HttpServletResponse httpResponse, Exception exception) {
		return handleException(httpResponse, exception);
	}

	@ExceptionHandler(WexAppException.class)
	public ErrorResponse handleCheckedException(HttpServletResponse httpResponse, Exception exception) {
		return handleException(httpResponse, exception);
	}

	private ErrorResponse handleException(HttpServletResponse httpResponse, Exception exception) {
		logger.error("Exception cought: " + exception.getMessage(), exception);
		httpResponse.setStatus(((WexException) exception).getErrorResponse().getStatus());
		if (exception instanceof WexException) {
		    return ((WexException) exception).getErrorResponse();
		} else {
			return ErrorResponse.DEAULT;
		}
	}

}
