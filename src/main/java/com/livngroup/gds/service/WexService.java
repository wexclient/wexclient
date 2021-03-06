package com.livngroup.gds.service;

import org.apache.axis2.AxisFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.ArrayOfUserDefinedField;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.UserDefinedField;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.domain.WexUser;
import com.livngroup.gds.domain.WexUserToken;
import com.livngroup.gds.exception.WexRuntimeException;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.response.ErrorResponse;

@ManagedResource
public abstract class WexService {
	
	final protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${encompass.bank-number}")
	protected String BANK_NUMBER;

	@Value("${encompass.client-id}")
	protected String COMPANY_ID;
	
	@Autowired
	@Qualifier("wexUser")
	protected WexUser wexUser;
	
	@Autowired
	@Qualifier("wexUserToken")
	protected WexUserToken wexUserToken;
	
	@Autowired
	@Qualifier("purchaseLogServiceStub")
	protected PurchaseLogServiceStub purchaseLogServiceStub;

	private static final String SUCCESS_MESSAGE = "Successful call response";
	private static final String FAIL_MESSAGE = "WEX server not responde : no response";
	
	protected abstract WexEntity getWexEntity();

	protected UserDefinedField asUserDefinedField(String fieldName, String fieldValue) {
		UserDefinedField userDefinedField = new UserDefinedField();
		userDefinedField.setFieldName(fieldName);
		userDefinedField.setValueAsString(fieldValue);
		return userDefinedField;
	}

	protected ArrayOfUserDefinedField asArray(UserDefinedField ... fieldList) {
		ArrayOfUserDefinedField arrayOfFields = new ArrayOfUserDefinedField();
		for (UserDefinedField field: fieldList) {
			arrayOfFields.addUserDefinedField(field);
		}
		return arrayOfFields;
	}
	
	@ExceptionHandler(AxisFault.class)
	public void handleWexException(AxisFault exception) {
		throw new WexRuntimeException(new ErrorResponse(
	    				HttpStatus.NOT_ACCEPTABLE, 
	    				HttpStatus.NOT_ACCEPTABLE.toString() + getWexEntity(), 
	    				getWexEntity(), 
	    				exception.getMessage(), null, null, null));
	}

	protected CallResponse getCallSuccessResponse(Object result) {
		CallResponse callRes = new CallResponse();
		callRes.setOk(true);
		callRes.setMessage(SUCCESS_MESSAGE);
		callRes.setStatus(HttpStatus.OK);
		callRes.setResult(result);
		return callRes;
	}
	
	protected CallResponse getCallFailResponse(String resultCodeValue, String resultDesc) {
		CallResponse callRes = new CallResponse();
		callRes.setOk(false);
		callRes.setMessage(String.format("WEX : [code] - %s  [description] - %s", resultCodeValue, resultDesc));
		callRes.setStatus(HttpStatus.BAD_REQUEST);
		return callRes;
	}

	protected CallResponse getCallFailDefaultResponse() {
		CallResponse callRes = new CallResponse();
		callRes.setOk(false);
		callRes.setMessage(FAIL_MESSAGE);
		callRes.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
		return callRes;
	}

}
