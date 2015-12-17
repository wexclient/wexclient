package com.livngroup.gds.service;

import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentInformationUrl;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentInformationUrlRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentInformationUrlResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentInformationUrlResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentSchedule;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentScheduleRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentScheduleResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentScheduleResponseE;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.ExceptionFactory;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.response.CallResponse;

@Service
public class WexPaymentService extends WexService {

	public CallResponse getPaymentSchedule(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse result = new CallResponse();
		
		try {
			GetPaymentSchedule getPaymentSchedule = new GetPaymentSchedule();
			GetPaymentScheduleResponseE resEncap;
			GetPaymentScheduleResponse response;
			
			getPaymentSchedule.setUser(wexUser);
			
			GetPaymentScheduleRequest req = new GetPaymentScheduleRequest();
			req.setBankNumber(bankNo);
			req.setCompanyNumber(compNo);
			req.setPurchaseLogUniqueID(uniqueId);
			getPaymentSchedule.setRequest(req);
			
			resEncap = purchaseLogServiceStub.getPaymentSchedule(getPaymentSchedule);
			if(resEncap != null) {
				response = resEncap.getGetPaymentScheduleResult();
				result.setResult(response);
				result.setMessage("Success");
				result.setOk(true);
			}
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PAYMENT_SCHEDULE);
		}
		
		return result;
	}
	
	public CallResponse getPaymentInformationUrl(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse result = new CallResponse();
		
		try {
			GetPaymentInformationUrl getPaymentInformationUrl = new GetPaymentInformationUrl();
			GetPaymentInformationUrlResponseE resEncap;
			GetPaymentInformationUrlResponse response;
			
			getPaymentInformationUrl.setUser(wexUser);
			
			GetPaymentInformationUrlRequest req = new GetPaymentInformationUrlRequest();
			req.setBankNumber(bankNo);
			req.setCompanyNumber(compNo);
			req.setPurchaseLogUniqueID(uniqueId);
			getPaymentInformationUrl.setRequest(req);
			
			resEncap = purchaseLogServiceStub.getPaymentInformationUrl(getPaymentInformationUrl);
			if(resEncap != null) {
				response = resEncap.getGetPaymentInformationUrlResult();
				result.setResult(response);
				result.setMessage("Success");
				result.setOk(true);
			}
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PAYMENT_SCHEDULE);
		}
		
		return result;
	}
		
}
