package com.livngroup.gds.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.exception.WexException;
import com.livngroup.gds.exception.WexRuntimeException;
import com.livngroup.gds.response.ErrorResponse;

public abstract class WexController {
	
	final protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler({WexRuntimeException.class,WexAppException.class})
	public @ResponseBody ResponseEntity<ErrorResponse> handleRuntimeException(WexException exception) {
		logger.error("Exception cought: " + exception.getMessage(), exception);
	    return new ResponseEntity<ErrorResponse>(exception.getErrorResponse(), HttpStatus.valueOf(exception.getErrorResponse().getStatus()));
	}

}
