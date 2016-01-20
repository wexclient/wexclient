package com.livngroup.gds.service;

import java.rmi.RemoteException;
import java.util.Calendar;

import org.springframework.http.HttpStatus;

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

public class WexTransactionService extends WexService {

	/*
	 * GetTransactions
	 */
	public CallResponse getTransactions(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetTransactionsResponse resEncap;
			TransactionsResponse result;
			
			GetRecentAccountActivityRequest req = new GetRecentAccountActivityRequest();
			req.setBankNumber(bankNo);
			req.setCompanyNumber(compNo);
			req.setPurchaseLogUniqueID(uniqueId);

			GetTransactions getTransactions = new GetTransactions();
			getTransactions.setUser(wexUser);
			getTransactions.setRequest(req);
			
			resEncap = purchaseLogServiceStub.getTransactions(getTransactions);
			if(resEncap != null && resEncap.getGetTransactionsResult() != null) {
				result = resEncap.getGetTransactionsResult();
				
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
	 * GetTransactionsInternational
	 */
	public CallResponse getTransactionsInternational(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetTransactionsInternationalResponseE resEncap;
			GetTransactionsInternationalResponse result;
			
			GetRecentAccountActivityRequest req = new GetRecentAccountActivityRequest();
			req.setBankNumber(bankNo);
			req.setCompanyNumber(compNo);
			req.setPurchaseLogUniqueID(uniqueId);

			GetTransactionsInternational getTransactions = new GetTransactionsInternational();
			getTransactions.setUser(wexUser);
			getTransactions.setRequest(req);
			
			resEncap = purchaseLogServiceStub.getTransactionsInternational(getTransactions);
			if(resEncap != null && resEncap.getGetTransactionsInternationalResult() != null) {
				result = resEncap.getGetTransactionsInternationalResult();
				
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
	 * GetRecentAccountActivity
	 */
	public CallResponse getRecentAccountActivity(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetRecentAccountActivityResponseE resEncap;
			GetRecentAccountActivityResponse result;
			
			GetRecentAccountActivityRequest req = new GetRecentAccountActivityRequest();
			req.setBankNumber(bankNo);
			req.setCompanyNumber(compNo);
			req.setPurchaseLogUniqueID(uniqueId);

			GetRecentAccountActivity getActivity = new GetRecentAccountActivity();
			getActivity.setUser(wexUser);
			getActivity.setRequest(req);
			
			resEncap = purchaseLogServiceStub.getRecentAccountActivity(getActivity);
			if(resEncap != null && resEncap.getGetRecentAccountActivityResult() != null) {
				result = resEncap.getGetRecentAccountActivityResult();
				
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
	 * GetRecentAccountActivityInternational
	 */
	public CallResponse getRecentAccountActivityInternational(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetRecentAccountActivityInternationalResponseE resEncap;
			GetRecentAccountActivityInternationalResponse result;

			GetRecentAccountActivityRequest req = new GetRecentAccountActivityRequest();
			req.setBankNumber(bankNo);
			req.setCompanyNumber(compNo);
			req.setPurchaseLogUniqueID(uniqueId);

			GetRecentAccountActivityInternational getActivity = new GetRecentAccountActivityInternational();
			getActivity.setUser(wexUser);
			getActivity.setRequest(req);
			
			resEncap = purchaseLogServiceStub.getRecentAccountActivityInternational(getActivity);
			if(resEncap != null && resEncap.getGetRecentAccountActivityInternationalResult() != null) {
				result = resEncap.getGetRecentAccountActivityInternationalResult();
				
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
	 * DisputeTransaction
	 */
	public CallResponse disputeTransaction(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			DisputeTransactionResponseE resEncap;
			DisputeTransactionResponse result;
			
			DisputeTransactionRequest req = new DisputeTransactionRequest();
			req.setBankNumber(bankNo);
			req.setCompanyNumber(compNo);
			req.setPurchaseLogUniqueId(uniqueId);

			DisputeTransaction getTransactions = new DisputeTransaction();
			getTransactions.setUser(wexUser);
			getTransactions.setRequest(req);
			
			resEncap = purchaseLogServiceStub.disputeTransaction(getTransactions);
			if(resEncap != null && resEncap.getDisputeTransactionResult() != null) {
				result = resEncap.getDisputeTransactionResult();
				
				DisputeTransactionResponseCodeEnum resultCode = result.getResponseCode();
				if(DisputeTransactionResponseCodeEnum.Success.equals(resultCode)) {
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
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.DISPUTED_TRANSACTION);
		}
		
		return response;
	}
	
	/*
	 * GetDisputeTransactions
	 */
	public CallResponse getDisputedTransactions(String accNo, String bankNo, String compNo, Calendar cDate) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetDisputedTransactionsResponse resEncap;
			DisputedTransactionsResponse result;
			
			DisputedAccountActivityRequest req = new DisputedAccountActivityRequest();
			req.setAccountNumber(accNo);
			req.setBankNumber(bankNo);
			req.setCompanyNumber(compNo);
			req.setCreatedDate(cDate);

			GetDisputedTransactions getTransactions = new GetDisputedTransactions();
			getTransactions.setUser(wexUser);
			getTransactions.setRequest(req);
			
			resEncap = purchaseLogServiceStub.getDisputedTransactions(getTransactions);
			if(resEncap != null && resEncap.getGetDisputedTransactionsResult() != null) {
				result = resEncap.getGetDisputedTransactionsResult();
				
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
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.DISPUTED_TRANSACTION);
		}
		
		return response;
	}

}
