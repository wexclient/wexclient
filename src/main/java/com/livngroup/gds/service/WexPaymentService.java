package com.livngroup.gds.service;

import java.rmi.RemoteException;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	protected WexEntity getWexEntity() {
		return WexEntity.PAYMENT_SCHEDULE;
	}

	@Autowired
	private CallResponseService callResponseService;

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
					response = callResponseService.getCallSuccessResponse(result);
				} else {
					response = callResponseService.getCallFailResponse(resultCode.getValue(), result.getDescription());
				}
			} else {
				response = callResponseService.getCallFailDefaultResponse();
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
					response = callResponseService.getCallSuccessResponse(result);
				} else {
					response = callResponseService.getCallFailResponse(resultCode.getValue(), result.getDescription());
				}
			} else {
				response = callResponseService.getCallFailDefaultResponse();
			}
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PAYMENT_SCHEDULE);
		}
		
		return response;
	}
	
}
