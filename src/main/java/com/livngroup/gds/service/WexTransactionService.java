package com.livngroup.gds.service;

import java.rmi.RemoteException;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DisputeTransaction;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DisputeTransactionRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DisputeTransactionResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DisputeTransactionResponseCodeEnum;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DisputeTransactionResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DisputedAccountActivityRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DisputedTransactionsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetDisputedTransactions;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetDisputedTransactionsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetRecentAccountActivity;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetRecentAccountActivityInternational;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetRecentAccountActivityInternationalResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetRecentAccountActivityInternationalResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetRecentAccountActivityRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetRecentAccountActivityResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetRecentAccountActivityResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetTransactions;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetTransactionsInternational;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetTransactionsInternationalResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetTransactionsInternationalResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetTransactionsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PurchaseLogResponseCodeEnum;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.TransactionsResponse;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.ExceptionFactory;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.response.CallResponse;

@Service("wexTransactionService")
public class WexTransactionService extends WexService {

	@Autowired
	private CallResponseService callResponseService;

	/*
	 * GetTransactions
	 */
	public CallResponse getTransactions(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetTransactionsResponse resEncap;
			TransactionsResponse result;
			
			GetRecentAccountActivityRequest reqObj = new GetRecentAccountActivityRequest();
			reqObj.setBankNumber(bankNo);
			reqObj.setCompanyNumber(compNo);
			reqObj.setPurchaseLogUniqueID(uniqueId);

			GetTransactions reqData = new GetTransactions();
			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			resEncap = purchaseLogServiceStub.getTransactions(reqData);
			if(resEncap != null && resEncap.getGetTransactionsResult() != null) {
				result = resEncap.getGetTransactionsResult();
				
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
	 * GetTransactionsInternational
	 */
	public CallResponse getTransactionsInternational(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetTransactionsInternationalResponseE resEncap;
			GetTransactionsInternationalResponse result;
			
			GetRecentAccountActivityRequest reqObj = new GetRecentAccountActivityRequest();
			reqObj.setBankNumber(bankNo);
			reqObj.setCompanyNumber(compNo);
			reqObj.setPurchaseLogUniqueID(uniqueId);

			GetTransactionsInternational reqData = new GetTransactionsInternational();
			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			resEncap = purchaseLogServiceStub.getTransactionsInternational(reqData);
			if(resEncap != null && resEncap.getGetTransactionsInternationalResult() != null) {
				result = resEncap.getGetTransactionsInternationalResult();
				
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
	 * GetRecentAccountActivity
	 */
	public CallResponse getRecentAccountActivity(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetRecentAccountActivityResponseE resEncap;
			GetRecentAccountActivityResponse result;
			
			GetRecentAccountActivityRequest reqObj = new GetRecentAccountActivityRequest();
			reqObj.setBankNumber(bankNo);
			reqObj.setCompanyNumber(compNo);
			reqObj.setPurchaseLogUniqueID(uniqueId);

			GetRecentAccountActivity reqData = new GetRecentAccountActivity();
			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			resEncap = purchaseLogServiceStub.getRecentAccountActivity(reqData);
			if(resEncap != null && resEncap.getGetRecentAccountActivityResult() != null) {
				result = resEncap.getGetRecentAccountActivityResult();
				
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
	 * GetRecentAccountActivityInternational
	 */
	public CallResponse getRecentAccountActivityInternational(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetRecentAccountActivityInternationalResponseE resEncap;
			GetRecentAccountActivityInternationalResponse result;

			GetRecentAccountActivityRequest reqObj = new GetRecentAccountActivityRequest();
			reqObj.setBankNumber(bankNo);
			reqObj.setCompanyNumber(compNo);
			reqObj.setPurchaseLogUniqueID(uniqueId);

			GetRecentAccountActivityInternational reqData = new GetRecentAccountActivityInternational();
			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			resEncap = purchaseLogServiceStub.getRecentAccountActivityInternational(reqData);
			if(resEncap != null && resEncap.getGetRecentAccountActivityInternationalResult() != null) {
				result = resEncap.getGetRecentAccountActivityInternationalResult();
				
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
	 * DisputeTransaction
	 */
	public CallResponse disputeTransaction(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			DisputeTransactionResponseE resEncap;
			DisputeTransactionResponse result;
			
			DisputeTransactionRequest reqObj = new DisputeTransactionRequest();
			reqObj.setBankNumber(bankNo);
			reqObj.setCompanyNumber(compNo);
			reqObj.setPurchaseLogUniqueId(uniqueId);

			DisputeTransaction reqData = new DisputeTransaction();
			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			resEncap = purchaseLogServiceStub.disputeTransaction(reqData);
			if(resEncap != null && resEncap.getDisputeTransactionResult() != null) {
				result = resEncap.getDisputeTransactionResult();
				
				DisputeTransactionResponseCodeEnum resultCode = result.getResponseCode();
				if(DisputeTransactionResponseCodeEnum.Success.equals(resultCode)) {
					response = callResponseService.getCallSuccessResponse(result);
				} else {
					response = callResponseService.getCallFailResponse(resultCode.getValue(), result.getDescription());
				}
			} else {
				response = callResponseService.getCallFailDefaultResponse();
			}
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.DISPUTED_TRANSACTION);
		}
		
		return response;
	}
	
	/*
	 * GetDisputeTransactions
	 */
	public CallResponse getDisputedTransactions(String bankNo, String compNo, String uniqueId, Calendar cDate) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetDisputedTransactionsResponse resEncap;
			DisputedTransactionsResponse result;
			
			DisputedAccountActivityRequest reqObj = new DisputedAccountActivityRequest();
			reqObj.setAccountNumber("12345678");
			reqObj.setBankNumber(bankNo);
			reqObj.setCompanyNumber(compNo);
			reqObj.setCreatedDate(cDate);

			GetDisputedTransactions reqData = new GetDisputedTransactions();
			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			resEncap = purchaseLogServiceStub.getDisputedTransactions(reqData);
			if(resEncap != null && resEncap.getGetDisputedTransactionsResult() != null) {
				result = resEncap.getGetDisputedTransactionsResult();
				
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
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.DISPUTED_TRANSACTION);
		}
		
		return response;
	}

}
