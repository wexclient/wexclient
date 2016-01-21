package com.livngroup.gds.service;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.BackupCardResponseCode;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DeleteAuthorization;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DeleteAuthorizationRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DeleteAuthorizationResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DeleteAuthorizationResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DeleteInstantApproval;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DeleteInstantApprovalResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetInternalBackupInventoryInfo;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetInternalBackupInventoryInfoResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.InstantApproval;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.InstantApprovalRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.InstantApprovalResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.InstantApprovalResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.InternalBackupInventoryInfoRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.InternalBackupInventoryInfoResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PLogResponseCode;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PurchaseLog;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PurchaseLogResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PurchaseLogResponseCodeEnum;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.ResendNotification;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.ResendNotificationRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.ResendNotificationResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.ResendNotificationResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.RetrieveSecureCodeAuthPin;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.RetrieveSecureCodeAuthPinRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.RetrieveSecureCodeAuthPinResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.RetrieveSecureCodeAuthPinResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.SubmitCheckLog;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.SubmitCheckLogResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.UserToken;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.ExceptionFactory;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.response.CallResponse;

@Service("wexSecurityService")
public class WexSecurityService extends WexService {

	@Override
	protected WexEntity getWexEntity() {
		return WexEntity.GENERAL;
	}

	@Autowired
	private CallResponseService callResponseService;

	/*
	 * DeleteAuthorization
	 */
	public CallResponse deleteAuthorization(String bankNo, String compNo, String accNo,  
								String authNo, String uniqueId, BigDecimal invAmount) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			DeleteAuthorizationResponseE subResp;
			DeleteAuthorizationResponse result;
			
			DeleteAuthorizationRequest reqObj = new DeleteAuthorizationRequest();
			DeleteAuthorization reqData = new DeleteAuthorization();
			
			reqObj.setBankNumber(bankNo);
			reqObj.setCompanyNumber(compNo);
			reqObj.setAccountNumber(accNo);
			reqObj.setAuthorizationNumber(authNo);
			reqObj.setPurchaseLogUniqueID(uniqueId);
			reqObj.setAmount(invAmount);

			reqData.setRequest(reqObj);
			
			subResp = purchaseLogServiceStub.deleteAuthorization(reqData);
			if(subResp != null && subResp.getDeleteAuthorizationResult() != null) {
				result = subResp.getDeleteAuthorizationResult();

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
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PURCHASE_LOG);
		}
		
		return response;
	}

	/*
	 * DeleteInstantApproval
	 */
	public CallResponse deleteInstantApproval(String bankNo, String compNo, String accNo,  
								String authNo, String uniqueId, BigDecimal invAmount) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			DeleteInstantApprovalResponse subResp;
			InstantApprovalResponse result;
			
			InstantApprovalRequest reqObj = new InstantApprovalRequest();
			DeleteInstantApproval reqData = new DeleteInstantApproval();
			
			reqObj.setBankNumber(bankNo);
			reqObj.setCompanyNumber(compNo);
			reqObj.setAccountNumber(accNo);
			reqObj.setPurchaseLogUniqueID(uniqueId);

			reqData.setRequest(reqObj);
			
			subResp = purchaseLogServiceStub.deleteInstantApproval(reqData);
			if(subResp != null && subResp.getDeleteInstantApprovalResult() != null) {
				result = subResp.getDeleteInstantApprovalResult();

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
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PURCHASE_LOG);
		}
		
		return response;
	}

	/*
	 * GetInternalBackupInventoryInfo
	 */
	public CallResponse getInternalBackupInventoryInfo(String bankNo, String compNo, 
									String orderId, String lookBackDays) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetInternalBackupInventoryInfoResponse subResp;
			InternalBackupInventoryInfoResponse result;
			
			InternalBackupInventoryInfoRequest reqObj = new InternalBackupInventoryInfoRequest();
			GetInternalBackupInventoryInfo reqData = new GetInternalBackupInventoryInfo();
			
			reqObj.setBankNumber(bankNo);
			reqObj.setCompanyNumber(compNo);

			reqData.setRequest(reqObj);
			
			subResp = purchaseLogServiceStub.getInternalBackupInventoryInfo(reqData);
			if(subResp != null && subResp.getGetInternalBackupInventoryInfoResult() != null) {
				result = subResp.getGetInternalBackupInventoryInfoResult();

				BackupCardResponseCode resultCode = result.getResponseCode();
				if(BackupCardResponseCode.Success.equals(resultCode)) {
					response = callResponseService.getCallSuccessResponse(result);
				} else {
					response = callResponseService.getCallFailResponse(resultCode.getValue(), result.getDescription());
				}
			} else {
				response = callResponseService.getCallFailDefaultResponse();
			}
			
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PURCHASE_LOG);
		}
		
		return response;
	}

	/*
	 * InstantApproval
	 */
	public CallResponse instantApproval(String bankNo, String compNo, String accNo,  
								String authNo, String uniqueId, BigDecimal invAmount) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			InstantApprovalResponseE subResp;
			InstantApprovalResponse result;
			
			InstantApprovalRequest reqObj = new InstantApprovalRequest();
			InstantApproval reqData = new InstantApproval();
			
			reqObj.setBankNumber(bankNo);
			reqObj.setCompanyNumber(compNo);
			reqObj.setAccountNumber(accNo);
			reqObj.setPurchaseLogUniqueID(uniqueId);

			reqData.setRequest(reqObj);
			
			subResp = purchaseLogServiceStub.instantApproval(reqData);
			if(subResp != null && subResp.getInstantApprovalResult() != null) {
				result = subResp.getInstantApprovalResult();

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
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PURCHASE_LOG);
		}
		
		return response;
	}

	/*
	 * ResendNotification
	 */
	public CallResponse resendNotification(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			ResendNotificationResponseE subResp;
			ResendNotificationResponse result;
			
			ResendNotificationRequest reqObj = new ResendNotificationRequest();
			ResendNotification reqData = new ResendNotification();
			
			reqObj.setBankNumber(bankNo);
			reqObj.setCompanyNumber(compNo);
			reqObj.setPurchaseLogUniqueID(uniqueId);

			reqData.setRequest(reqObj);
			
			subResp = purchaseLogServiceStub.resendNotification(reqData);
			if(subResp != null && subResp.getResendNotificationResult() != null) {
				result = subResp.getResendNotificationResult();

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
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PURCHASE_LOG);
		}
		
		return response;
	}

	/*
	 * RetrieveSecureCodeAuthPin
	 */
	public CallResponse retrieveSecureCodeAuthPin(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			RetrieveSecureCodeAuthPinResponseE subResp;
			RetrieveSecureCodeAuthPinResponse result;
			
			RetrieveSecureCodeAuthPinRequest reqObj = new RetrieveSecureCodeAuthPinRequest();
			RetrieveSecureCodeAuthPin reqData = new RetrieveSecureCodeAuthPin();
			
			reqObj.setBankNumber(bankNo);
			reqObj.setCompanyNumber(compNo);
			reqObj.setPurchaseLogUniqueID(uniqueId);

			reqData.setRequest(reqObj);
			
			subResp = purchaseLogServiceStub.retrieveSecureCodeAuthPin(reqData);
			if(subResp != null && subResp.getRetrieveSecureCodeAuthPinResult() != null) {
				result = subResp.getRetrieveSecureCodeAuthPinResult();

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
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PURCHASE_LOG);
		}
		
		return response;
	}

	/*
	 * SubmitCheckLog
	 */
	public CallResponse submitCheckLog(String bankNo, String compNo, BigDecimal invAmount) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			SubmitCheckLogResponse subResp;
			PurchaseLogResponse result;
			
			SubmitCheckLog reqObj = new SubmitCheckLog();
			reqObj.setUser((UserToken)wexUserToken);
//			reqObj.setVendorInfo(param);
			
			PurchaseLog reqData = new PurchaseLog();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setInvoiceAmount(invAmount);
			reqData.setBillingCurrency("AUD");
			
			reqObj.setPurchaseLog(reqData);
			
			subResp = purchaseLogServiceStub.submitCheckLog(reqObj);
			result = subResp.getSubmitCheckLogResult();
			if(result != null && result.getValidationResults() != null) {

				PLogResponseCode resultCode = result.getResponseCode();
				if(PLogResponseCode.Success.equals(resultCode)) {
					response = callResponseService.getCallSuccessResponse(result);
				} else {
					response = callResponseService.getCallFailResponse(resultCode.getValue(), result.getDescription());
				}
			} else {
				response = callResponseService.getCallFailDefaultResponse();
			}
			
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PURCHASE_LOG);
		}
		
		return response;
	}

}
