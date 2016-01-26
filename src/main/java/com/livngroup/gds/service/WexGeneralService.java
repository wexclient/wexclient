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
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.VendorInfo;
import com.livngroup.gds.domain.LivnBaseReq;
import com.livngroup.gds.domain.LivnInstantApprovalReq;
import com.livngroup.gds.domain.LivnPurchaseLog;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.ExceptionFactory;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.response.CallResponse;

@Service("wexSecurityService")
public class WexGeneralService extends WexService {

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
			DeleteAuthorizationRequest reqObj = new DeleteAuthorizationRequest();
			DeleteAuthorization reqData = new DeleteAuthorization();
			
			reqObj.setBankNumber(bankNo);
			reqObj.setCompanyNumber(compNo);
			reqObj.setAccountNumber(accNo);
			reqObj.setAuthorizationNumber(authNo);
			reqObj.setPurchaseLogUniqueID(uniqueId);
			reqObj.setAmount(invAmount);

			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			DeleteAuthorizationResponseE subResp = purchaseLogServiceStub.deleteAuthorization(reqData);
			if(subResp != null && subResp.getDeleteAuthorizationResult() != null) {
				DeleteAuthorizationResponse result = subResp.getDeleteAuthorizationResult();

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
	public CallResponse deleteInstantApproval(LivnInstantApprovalReq approvalReq) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			InstantApprovalRequest reqObj = new InstantApprovalRequest();
			DeleteInstantApproval reqData = new DeleteInstantApproval();
			
			reqObj.setBankNumber(approvalReq.getBankNumber());
			reqObj.setCompanyNumber(approvalReq.getCompanyNumber());
			reqObj.setAccountNumber(approvalReq.getAccountNumber());
			reqObj.setPurchaseLogUniqueID(approvalReq.getPurchaseLogUniqueID());
			reqObj.setUpperBound(approvalReq.getUppperBound());

			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			DeleteInstantApprovalResponse subResp = purchaseLogServiceStub.deleteInstantApproval(reqData);
			if(subResp != null && subResp.getDeleteInstantApprovalResult() != null) {
				InstantApprovalResponse result = subResp.getDeleteInstantApprovalResult();

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
	@Deprecated
	public CallResponse getInternalBackupInventoryInfo(String bankNo, String compNo, 
									String orderId, int lookBackDays) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			InternalBackupInventoryInfoRequest reqObj = new InternalBackupInventoryInfoRequest();
			GetInternalBackupInventoryInfo reqData = new GetInternalBackupInventoryInfo();
			
			reqObj.setBankNumber(bankNo);
			reqObj.setCompanyNumber(compNo);
			reqObj.setOrderID(orderId);
			reqObj.setLookBackDays(lookBackDays);

			reqData.setUser(wexUserToken);
			reqData.setRequest(reqObj);
			
			GetInternalBackupInventoryInfoResponse subResp = purchaseLogServiceStub.getInternalBackupInventoryInfo(reqData);
			if(subResp != null && subResp.getGetInternalBackupInventoryInfoResult() != null) {
				InternalBackupInventoryInfoResponse result = subResp.getGetInternalBackupInventoryInfoResult();

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
	public CallResponse instantApproval(LivnInstantApprovalReq approvalReq) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			InstantApprovalRequest reqObj = new InstantApprovalRequest();
			InstantApproval reqData = new InstantApproval();
			
			reqObj.setBankNumber(approvalReq.getBankNumber());
			reqObj.setCompanyNumber(approvalReq.getCompanyNumber());
			reqObj.setAccountNumber(approvalReq.getAccountNumber());
			reqObj.setPurchaseLogUniqueID(approvalReq.getPurchaseLogUniqueID());
			reqObj.setUpperBound(approvalReq.getUppperBound());

			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			InstantApprovalResponseE subResp = purchaseLogServiceStub.instantApproval(reqData);
			if(subResp != null && subResp.getInstantApprovalResult() != null) {
				InstantApprovalResponse result = subResp.getInstantApprovalResult();

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
	public CallResponse resendNotification(LivnBaseReq resendReq) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			ResendNotificationRequest reqObj = new ResendNotificationRequest();
			ResendNotification reqData = new ResendNotification();
			
			reqObj.setBankNumber(resendReq.getBankNumber());
			reqObj.setCompanyNumber(resendReq.getCompanyNumber());
			reqObj.setPurchaseLogUniqueID(resendReq.getPurchaseLogUniqueID());

			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			ResendNotificationResponseE subResp = purchaseLogServiceStub.resendNotification(reqData);
			if(subResp != null && subResp.getResendNotificationResult() != null) {
				ResendNotificationResponse result = subResp.getResendNotificationResult();

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
	public CallResponse retrieveSecureCodeAuthPin(LivnBaseReq retrieveReq) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			RetrieveSecureCodeAuthPinRequest reqObj = new RetrieveSecureCodeAuthPinRequest();
			RetrieveSecureCodeAuthPin reqData = new RetrieveSecureCodeAuthPin();
			
			reqObj.setBankNumber(retrieveReq.getBankNumber());
			reqObj.setCompanyNumber(retrieveReq.getCompanyNumber());
			reqObj.setPurchaseLogUniqueID(retrieveReq.getPurchaseLogUniqueID());

			reqData.setUser(wexUser);
			reqData.setRequest(reqObj);
			
			RetrieveSecureCodeAuthPinResponseE subResp = purchaseLogServiceStub.retrieveSecureCodeAuthPin(reqData);
			if(subResp != null && subResp.getRetrieveSecureCodeAuthPinResult() != null) {
				RetrieveSecureCodeAuthPinResponse result = subResp.getRetrieveSecureCodeAuthPinResult();

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
	@Deprecated
	public CallResponse submitCheckLog(LivnBaseReq checklogReq) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			SubmitCheckLog reqData = new SubmitCheckLog();
			reqData.setUser((UserToken)wexUserToken);
			
			PurchaseLog reqObj = new PurchaseLog();
			reqObj.setBankNumber(checklogReq.getBankNumber());
			reqObj.setCompanyNumber(checklogReq.getCompanyNumber());
			reqObj.setBillingCurrency("AUD");
			reqData.setPurchaseLog(reqObj);
			
			VendorInfo vendorInfo = new VendorInfo();
			vendorInfo.setName("");
			reqData.setVendorInfo(vendorInfo);
			
			SubmitCheckLogResponse subResp = purchaseLogServiceStub.submitCheckLog(reqData);
			if(subResp != null && subResp.getSubmitCheckLogResult() != null) {
				PurchaseLogResponse result = subResp.getSubmitCheckLogResult();

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
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.GENERAL);
		}
		
		return response;
	}

}
