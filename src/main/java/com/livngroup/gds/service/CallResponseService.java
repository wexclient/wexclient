package com.livngroup.gds.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.livngroup.gds.response.CallResponse;

@Service("callResponseService")
public class CallResponseService {

	public CallResponse getCallSuccessResponse(Object result) {
		CallResponse callRes = new CallResponse();

		callRes.setOk(true);
		callRes.setMessage("Successful call response");
		callRes.setStatus(HttpStatus.OK);
		callRes.setResult(result);

		return callRes;
	}
	
	public CallResponse getCallFailResponse(String resultCodeValue, String resultDesc) {
		CallResponse callRes = new CallResponse();

		callRes.setOk(false);
		callRes.setMessage("WEX : [code] - " + resultCodeValue + " [description] - " + resultDesc);
		callRes.setStatus(HttpStatus.BAD_REQUEST);
		
		return callRes;
	}

	public CallResponse getCallFailDefaultResponse() {
		CallResponse callRes = new CallResponse();

		callRes.setOk(false);
		callRes.setMessage("WEX server not responde : no response");
		callRes.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
		
		return callRes;
	}

}
