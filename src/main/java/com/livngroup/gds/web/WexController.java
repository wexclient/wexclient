package com.livngroup.gds.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.exception.WexException;
import com.livngroup.gds.exception.WexRuntimeException;
import com.livngroup.gds.response.ErrorResponse;
import com.livngroup.gds.util.Validator;

//@Secured("ROLE_USER")
public abstract class WexController {
	
	final protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected void assertNumber(String paramName, String paramValue) throws WexRuntimeException {
		if (!Validator.isNumber(paramValue)) {
			throw new WexRuntimeException(new ErrorResponse(
					HttpStatus.NOT_ACCEPTABLE, 
					HttpStatus.NOT_ACCEPTABLE.toString() + "-" + paramName,
					getEntytyType(),
					"Parameter: " + paramName + "=[" + paramValue + "] of input values should be a number.\nPlease check again the value of input parameter(s).", 
					ErrorResponse.URL_DEFAULT, 
					null, 
					null));
		}
	}

	protected abstract WexEntity getEntytyType();
	
	@ExceptionHandler({WexRuntimeException.class,WexAppException.class})
	public @ResponseBody ResponseEntity<ErrorResponse> handleRuntimeException(WexException exception) {
		logger.error("Exception cought: " + exception.getMessage(), exception);
	    return new ResponseEntity<ErrorResponse>(exception.getErrorResponse(), HttpStatus.valueOf(exception.getErrorResponse().getStatus()));
	}

}
