package com.livngroup.gds.service;

import java.rmi.RemoteException;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DisputeTransaction;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DisputeTransactionReasonEnum;
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
import com.livngroup.gds.domain.LivnBaseReq;
import com.livngroup.gds.domain.LivnDisputeTransactionReq;
import com.livngroup.gds.domain.LivnTransactionReq;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.ExceptionFactory;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.util.GdsDateUtil;
import com.livngroup.gds.util.Validator;

@Service("wexTransactionService")
public class WexTransactionService extends WexService {

	@Override
	protected WexEntity getWexEntity() {
		return WexEntity.TRANSACTION;
	}

	@Autowired
	private CallResponseService callResponseService;

	private final static int MAX_RETURN_OF_TRASACT = 10;

	/*
	 * GetTransactions
	 */
	public CallResponse getTransactions(LivnTransactionReq transactReq) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetRecentAccountActivityRequest reqObj = new GetRecentAccountActivityRequest();
			reqObj.setBankNumber(transactReq.getBankNumber());
			reqObj.setCompanyNumber(transactReq.getCompanyNumber());
//			reqObj.setPurchaseLogUniqueID(transactReq.getPurchaseLogUniqueID());
			reqObj.setMaxReturned(MAX_RETURN_OF_TRASACT);

			GetTransactions reqData = new GetTransactions();
			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			GetTransactionsResponse resEncap = purchaseLogServiceStub.getTransactions(reqData);
			if(resEncap != null && resEncap.getGetTransactionsResult() != null) {
				TransactionsResponse result = resEncap.getGetTransactionsResult();
				
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
	public CallResponse getTransactionsInternational(LivnTransactionReq transactReq) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetRecentAccountActivityRequest reqObj = new GetRecentAccountActivityRequest();
			reqObj.setBankNumber(transactReq.getBankNumber());
			reqObj.setCompanyNumber(transactReq.getCompanyNumber());
			reqObj.setPurchaseLogUniqueID(transactReq.getPurchaseLogUniqueID());

			GetTransactionsInternational reqData = new GetTransactionsInternational();
			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			GetTransactionsInternationalResponseE resEncap = purchaseLogServiceStub.getTransactionsInternational(reqData);
			if(resEncap != null && resEncap.getGetTransactionsInternationalResult() != null) {
				GetTransactionsInternationalResponse result = resEncap.getGetTransactionsInternationalResult();
				
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
	public CallResponse getRecentAccountActivity(LivnTransactionReq transactReq) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetRecentAccountActivityRequest reqObj = new GetRecentAccountActivityRequest();
			reqObj.setBankNumber(transactReq.getBankNumber());
			reqObj.setCompanyNumber(transactReq.getCompanyNumber());
			reqObj.setPurchaseLogUniqueID(transactReq.getPurchaseLogUniqueID());
			reqObj.setMaxReturned(MAX_RETURN_OF_TRASACT);

			GetRecentAccountActivity reqData = new GetRecentAccountActivity();
			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			GetRecentAccountActivityResponseE resEncap = purchaseLogServiceStub.getRecentAccountActivity(reqData);
			if(resEncap != null && resEncap.getGetRecentAccountActivityResult() != null) {
				GetRecentAccountActivityResponse result = resEncap.getGetRecentAccountActivityResult();
				
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
	public CallResponse getRecentAccountActivityInternational(LivnTransactionReq transactReq) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetRecentAccountActivityRequest reqObj = new GetRecentAccountActivityRequest();
			reqObj.setBankNumber(transactReq.getBankNumber());
			reqObj.setCompanyNumber(transactReq.getCompanyNumber());
			reqObj.setPurchaseLogUniqueID(transactReq.getPurchaseLogUniqueID());

			GetRecentAccountActivityInternational reqData = new GetRecentAccountActivityInternational();
			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			GetRecentAccountActivityInternationalResponseE resEncap = purchaseLogServiceStub.getRecentAccountActivityInternational(reqData);
			if(resEncap != null && resEncap.getGetRecentAccountActivityInternationalResult() != null) {
				GetRecentAccountActivityInternationalResponse result = resEncap.getGetRecentAccountActivityInternationalResult();
				
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
	public CallResponse disputeTransaction(LivnDisputeTransactionReq disputeReq) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			DisputeTransactionRequest reqObj = new DisputeTransactionRequest();
			reqObj.setBankNumber(disputeReq.getBankNumber());
			reqObj.setCompanyNumber(disputeReq.getCompanyNumber());
			reqObj.setPurchaseLogUniqueId(disputeReq.getPurchaseLogUniqueID());
			reqObj.setDisputeAmount(disputeReq.getDisputeAmount());
			reqObj.setDisputeReason(DisputeTransactionReasonEnum.IncorrectAmountBilled);

			DisputeTransaction reqData = new DisputeTransaction();
			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			DisputeTransactionResponseE resEncap = purchaseLogServiceStub.disputeTransaction(reqData);
			if(resEncap != null && resEncap.getDisputeTransactionResult() != null) {
				DisputeTransactionResponse result = resEncap.getDisputeTransactionResult();
				
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
	public CallResponse getDisputedTransactions(String bankNo, String compNo, String uniqueId, 
								String openOnly, String fromDate, String toDate) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			DisputedAccountActivityRequest reqObj = new DisputedAccountActivityRequest();
			reqObj.setBankNumber(bankNo);
			reqObj.setCompanyNumber(compNo);

			if(openOnly != null && Validator.isBoolean(openOnly))
				reqObj.setOpenDisputesOnly(new Boolean(openOnly));
			if(fromDate != null)
				reqObj.setCreatedDate(GdsDateUtil.getCalendarFromString(fromDate));
			if(toDate != null)
				reqObj.setCreatedDate(GdsDateUtil.getCalendarFromString(toDate));

			GetDisputedTransactions reqData = new GetDisputedTransactions();
			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			GetDisputedTransactionsResponse resEncap = purchaseLogServiceStub.getDisputedTransactions(reqData);
			if(resEncap != null && resEncap.getGetDisputedTransactionsResult() != null) {
				DisputedTransactionsResponse result = resEncap.getGetDisputedTransactionsResult();
				
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
