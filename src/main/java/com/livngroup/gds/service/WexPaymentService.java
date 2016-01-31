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
import com.livngroup.gds.domain.LivnBaseReq;
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

	public CallResponse getPaymentInformationUrl(LivnBaseReq paymentReq) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetPaymentInformationUrl getPaymentInformationUrl = new GetPaymentInformationUrl();
			getPaymentInformationUrl.setUser(wexUser);
			
			GetPaymentInformationUrlRequest req = new GetPaymentInformationUrlRequest();
			req.setBankNumber(paymentReq.getBankNumber());
			req.setCompanyNumber(paymentReq.getCompanyNumber());
			req.setPurchaseLogUniqueID(paymentReq.getPurchaseLogUniqueID());
			getPaymentInformationUrl.setRequest(req);
			
			GetPaymentInformationUrlResponseE resEncap = purchaseLogServiceStub.getPaymentInformationUrl(getPaymentInformationUrl);
			if(resEncap != null && resEncap.getGetPaymentInformationUrlResult() != null) {
				GetPaymentInformationUrlResponse result = resEncap.getGetPaymentInformationUrlResult();
				
				PurchaseLogResponseCodeEnum resultCode = result.getResponseCode();
				if(PurchaseLogResponseCodeEnum.Success.equals(resultCode)) {
					response = getCallSuccessResponse(result);
				} else {
					response = getCallFailResponse(resultCode.getValue(), result.getDescription());
				}
			} else {
				response = getCallFailDefaultResponse();
			}
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PAYMENT_SCHEDULE);
		}
		
		return response;
	}
		
	/*
	 * GetPaymentSchedule
	 */
	public CallResponse getPaymentSchedule(LivnBaseReq paymentReq) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetPaymentSchedule reqObj = new GetPaymentSchedule();
			GetPaymentScheduleResponseE resEncap;
			GetPaymentScheduleResponse result;
			
			reqObj.setUser(wexUser);
			
			GetPaymentScheduleRequest reqData = new GetPaymentScheduleRequest();
			reqData.setBankNumber(paymentReq.getBankNumber());
			reqData.setCompanyNumber(paymentReq.getCompanyNumber());
			reqData.setPurchaseLogUniqueID(paymentReq.getPurchaseLogUniqueID());
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.getPaymentSchedule(reqObj);
			if(resEncap != null && resEncap.getGetPaymentScheduleResult() != null) {
				result = resEncap.getGetPaymentScheduleResult();
				
				PurchaseLogResponseCodeEnum resultCode = result.getResponseCode();
				if(PurchaseLogResponseCodeEnum.Success.equals(resultCode)) {
					response = getCallSuccessResponse(result);
				} else {
					response = getCallFailResponse(resultCode.getValue(), result.getDescription());
				}
			} else {
				response = getCallFailDefaultResponse();
			}
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PAYMENT_SCHEDULE);
		}
		
		return response;
	}
}
