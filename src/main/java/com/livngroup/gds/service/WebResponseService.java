package com.livngroup.gds.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.response.ErrorResponse;

@Service("webResponseService")
public class WebResponseService {
	
	public ErrorResponse getErrorResponse(CallResponse result) {
		ErrorResponse warnRes = new ErrorResponse();
		
		warnRes.setStatus(result.getStatus());
		warnRes.setCode(result.getStatus().toString());
		warnRes.setWexEntity(null);
		warnRes.setMessage(result.getMessage());
		warnRes.setLink(ErrorResponse.URL_DEFAULT);
		warnRes.setDeveloperMessage(null);
		warnRes.setResult(result.getResult());
		
		return warnRes;
	}
	
	public ErrorResponse getErrorResponse(CallResponse result, WexEntity entity) {
		ErrorResponse warnRes = new ErrorResponse();
		
		warnRes.setStatus(result.getStatus());
		warnRes.setCode(result.getStatus().toString());
		warnRes.setWexEntity(entity);
		warnRes.setMessage(result.getMessage());
		warnRes.setLink(ErrorResponse.URL_DEFAULT);
		warnRes.setDeveloperMessage(null);
		warnRes.setResult(result.getResult());
		
		return warnRes;
	}

	public ErrorResponse getErrorResponse(CallResponse result, WexEntity entity, String customMessage) {
		ErrorResponse warnRes = new ErrorResponse();
		
		warnRes.setStatus(result.getStatus());
		warnRes.setCode(result.getStatus().toString());
		warnRes.setWexEntity(entity);
		warnRes.setMessage(customMessage);
		warnRes.setLink(ErrorResponse.URL_DEFAULT);
		warnRes.setDeveloperMessage(null);
		warnRes.setResult(result.getResult());
		
		return warnRes;
	}
	
	public ErrorResponse getErrorResponseDefault(String message) {
		ErrorResponse warnRes = new ErrorResponse();
		
		warnRes.setStatus(HttpStatus.NOT_ACCEPTABLE);
		warnRes.setCode(HttpStatus.NOT_ACCEPTABLE.toString());
		warnRes.setWexEntity(null);
		warnRes.setMessage(message);
		warnRes.setLink(ErrorResponse.URL_DEFAULT);
		warnRes.setDeveloperMessage(null);
		warnRes.setResult(null);
		
		return warnRes;
	}

}