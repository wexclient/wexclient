package com.livngroup.gds.service;

import java.rmi.RemoteException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentInformationUrl;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentInformationUrlRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentInformationUrlResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentInformationUrlResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentSchedule;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentScheduleRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentScheduleResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentScheduleResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PurchaseLogResponseCodeEnum;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.ExceptionFactory;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.response.CallResponse;

@Service
public class WexPaymentService extends WexService {

	/*
	 * GetPaymentInformationUrl
	 */
	public CallResponse getPaymentInformationUrl(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetPaymentInformationUrl getPaymentInformationUrl = new GetPaymentInformationUrl();
			GetPaymentInformationUrlResponseE resEncap;
			GetPaymentInformationUrlResponse result;
			
			getPaymentInformationUrl.setUser(wexUser);
			
			GetPaymentInformationUrlRequest req = new GetPaymentInformationUrlRequest();
			req.setBankNumber(bankNo);
			req.setCompanyNumber(compNo);
			req.setPurchaseLogUniqueID(uniqueId);
			getPaymentInformationUrl.setRequest(req);
			
			resEncap = purchaseLogServiceStub.getPaymentInformationUrl(getPaymentInformationUrl);
			if(resEncap != null && resEncap.getGetPaymentInformationUrlResult() != null) {
				result = resEncap.getGetPaymentInformationUrlResult();
				
				PurchaseLogResponseCodeEnum resultCode = result.getResponseCode();
				if(PurchaseLogResponseCodeEnum.Success.equals(resultCode)) {
					response.setOk(true);
					response.setMessage("Successful call response");
					response.setStatus(HttpStatus.OK);
					response.setResult(result);
				} else {
					response.setOk(false);
					response.setMessage("WEX : [code] - " 
												+ resultCode.getValue() 
												+ " [description] - " 
												+ result.getDescription());
					response.setStatus(HttpStatus.BAD_REQUEST);
				}
			} else {
				response.setOk(false);
				response.setMessage("WEX server not responde : no response");
				response.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
			}
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PAYMENT_SCHEDULE);
		}
		
		return response;
	}
		
	/*
	 * GetPaymentSchedule
	 */
	public CallResponse getPaymentSchedule(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetPaymentSchedule getPaymentSchedule = new GetPaymentSchedule();
			GetPaymentScheduleResponseE resEncap;
			GetPaymentScheduleResponse result;
			
			getPaymentSchedule.setUser(wexUser);
			
			GetPaymentScheduleRequest req = new GetPaymentScheduleRequest();
			req.setBankNumber(bankNo);
			req.setCompanyNumber(compNo);
			req.setPurchaseLogUniqueID(uniqueId);
			getPaymentSchedule.setRequest(req);
			
			resEncap = purchaseLogServiceStub.getPaymentSchedule(getPaymentSchedule);
			if(resEncap != null && resEncap.getGetPaymentScheduleResult() != null) {
				result = resEncap.getGetPaymentScheduleResult();
				
				PurchaseLogResponseCodeEnum resultCode = result.getResponseCode();
				if(PurchaseLogResponseCodeEnum.Success.equals(resultCode)) {
					response.setOk(true);
					response.setMessage("Successful call response");
					response.setStatus(HttpStatus.OK);
					response.setResult(result);
				} else {
					response.setOk(false);
					response.setMessage("WEX : [code] - " 
												+ resultCode.getValue() 
												+ " [description] - " 
												+ result.getDescription());
					response.setStatus(HttpStatus.BAD_REQUEST);
				}
			} else {
				response.setOk(false);
				response.setMessage("WEX server not responde : no response");
				response.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
			}
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PAYMENT_SCHEDULE);
		}
		
		return response;
	}
	
}
