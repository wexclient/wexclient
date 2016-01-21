package com.livngroup.gds.service;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.ArrayOfPaymentScheduleItem;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CancelPurchaseLog;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CancelPurchaseLogRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CancelPurchaseLogResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CancelPurchaseLogResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CreatePurchaseLog;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CreatePurchaseLogRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CreatePurchaseLogResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CreatePurchaseLogResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistory;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PLogResponseCode;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PaymentScheduleItem;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PaymentTypeEnum;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PurchaseLog;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PurchaseLogDeliveryMethod;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PurchaseLogResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PurchaseLogResponseCodeEnum;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PurchaseLogResponseWithImagePdf;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogs;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogsRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogsResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.RetrievePurchaseLog;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.RetrievePurchaseLogRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.RetrievePurchaseLogResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.RetrievePurchaseLogResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.SubmitBackupPurchaseLog;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.SubmitBackupPurchaseLogResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.SubmitBackupPurchaseLogWithFaxInfo;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.SubmitBackupPurchaseLogWithFaxInfoResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.SubmitPurchaseLog;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.SubmitPurchaseLogAndGetImagePdf;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.SubmitPurchaseLogAndGetImagePdfResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.SubmitPurchaseLogResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.SubmitPurchaseLogWithFaxInfo;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.SubmitPurchaseLogWithFaxInfoResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.UpdatePurchaseLog;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.UpdatePurchaseLogRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.UpdatePurchaseLogResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.UpdatePurchaseLogResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.User;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.UserToken;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.VendorInfo;
import com.livngroup.gds.domain.LivnPurchaseLog;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.ExceptionFactory;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.response.CallResponse;

@Service
public class WexPurchaseLogService extends WexService {
	
	@Autowired
	private CallResponseService callResponseService;
	
	@Override
	protected WexEntity getWexEntity() {
		return WexEntity.PURCHASE_LOG;
	}

	/* 
	 * CreatePurchaseLog 
	 */
	public CallResponse createPurchaseLog(LivnPurchaseLog purchaseLog) throws WexAppException {
		try {
			CreatePurchaseLogRequest requestParam = new CreatePurchaseLogRequest();
			requestParam.setBankNumber(BANK_NUMBER);
			requestParam.setCompanyNumber(COMPANY_ID);
			requestParam.setAmount(purchaseLog.getAmount());
			
			requestParam.setDeliveryMethod(PurchaseLogDeliveryMethod.None);
			requestParam.setUserDefinedFields(asArray(
					asUserDefinedField(LivnPurchaseLog.UDF_RESERVATION_ID,purchaseLog.getReservationId()),
					asUserDefinedField(LivnPurchaseLog.UDF_LEAD_PASSENGER_NAME,purchaseLog.getLeadPassengerName()),
					asUserDefinedField(LivnPurchaseLog.UDF_INVOICE_NUMBER,purchaseLog.getInvoiceNumber())
			));
			
			CreatePurchaseLog reqObj = new CreatePurchaseLog();
			reqObj.setUser(wexUser);
			reqObj.setRequest(requestParam);

			CreatePurchaseLogResponseE resEncap = purchaseLogServiceStub.createPurchaseLog(reqObj);
			if ((resEncap != null) && (resEncap.getCreatePurchaseLogResult() != null)) {
				CreatePurchaseLogResponse result = resEncap.getCreatePurchaseLogResult();

				PurchaseLogResponseCodeEnum resultCode = result.getResponseCode();
				CallResponse response = PurchaseLogResponseCodeEnum.Success.equals(resultCode) ?
					CallResponse.forSuccess(result) : CallResponse.forError(resultCode.getValue(), result.getDescription());
				return response;
			} else {
				throw ExceptionFactory.createServiceUnavailableForEntityException(null, WexEntity.PURCHASE_LOG);
			}

		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PURCHASE_LOG);
		}
	}

	/*
	 * GetPurchaseLogHistory
	 */
	public CallResponse getPurchaseLogHistory(String uniqueId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetPurchaseLogHistoryResponse result;
			GetPurchaseLogHistoryResponseE resEncap;
			
			GetPurchaseLogHistory reqObj = new GetPurchaseLogHistory();
			reqObj.setUser((User)wexUser);
			
			GetPurchaseLogHistoryRequest reqData = new GetPurchaseLogHistoryRequest();
			reqData.setBankNumber(BANK_NUMBER);
			reqData.setCompanyNumber(COMPANY_ID);
			reqData.setPurchaseLogUniqueID(uniqueId);
			
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.getPurchaseLogHistory(reqObj);
			if(resEncap != null && resEncap.getGetPurchaseLogHistoryResult() != null) {
				result = resEncap.getGetPurchaseLogHistoryResult();

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

	/* CancelPurchaseLog */
	public CallResponse cancelPurchaseLog(String uniqueId) throws WexAppException {
		
		try {
						
			CancelPurchaseLogRequest reqData = new CancelPurchaseLogRequest();
			reqData.setBankNumber(BANK_NUMBER);
			reqData.setCompanyNumber(COMPANY_ID);
			reqData.setPurchaseLogUniqueID(uniqueId);
			
			CancelPurchaseLog reqObj = new CancelPurchaseLog();
			reqObj.setUser(wexUser);
			reqObj.setRequest(reqData);
			
			CancelPurchaseLogResponseE resEncap = purchaseLogServiceStub.cancelPurchaseLog(reqObj);
			if (resEncap != null && resEncap.getCancelPurchaseLogResult() != null) {
				CancelPurchaseLogResponse result = resEncap.getCancelPurchaseLogResult();

				PurchaseLogResponseCodeEnum resultCode = result.getResponseCode();
				CallResponse response = (PurchaseLogResponseCodeEnum.Success.equals(resultCode)) ? 
						CallResponse.forSuccess(result) : CallResponse.forError(resultCode.getValue(), result.getDescription());
				return response;
			} else {
				throw ExceptionFactory.createServiceUnavailableForEntityException(null, WexEntity.PURCHASE_LOG);
			}
			
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PURCHASE_LOG);
		}
	}
	
	/*
	 * QueryPurchaseLog
	 */
	public CallResponse queryPurchaseLogs(String bankNo, String compNo, String status) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			QueryPurchaseLogsResponse result;
			QueryPurchaseLogsResponseE resEncap;
			
			QueryPurchaseLogs reqObj = new QueryPurchaseLogs();
			logger.debug("OrgGroupLoginId : {} ", wexUser.getOrgGroupLoginId());
			logger.debug("Username : {} ", wexUser.getUsername());
			logger.debug("Password : {} ", wexUser.getPassword());
			reqObj.setUser((User)wexUser);
			
			QueryPurchaseLogsRequest reqData = new QueryPurchaseLogsRequest();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			logger.debug("BankNo : {} ", bankNo);
			logger.debug("CompanyNo : {} ", compNo);
			logger.debug("Status : {} ", status);
			reqData.setStatus(status);
			
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.queryPurchaseLogs(reqObj);
			if(resEncap != null && resEncap.getQueryPurchaseLogsResult() != null) {
				result = resEncap.getQueryPurchaseLogsResult();
				logger.debug(result.getDescription());
					
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
	 * RetrievePurchaseLog
	 */
	public CallResponse retrievePurchaseLog(String bankNo, String compNo, String uniqueId)  throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			RetrievePurchaseLogResponse result;
			RetrievePurchaseLogResponseE resEncap;
			
			RetrievePurchaseLog reqObj = new RetrievePurchaseLog();
			reqObj.setUser((User)wexUser);
			
			RetrievePurchaseLogRequest reqData = new RetrievePurchaseLogRequest();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setPurchaseLogUniqueID(uniqueId);
			
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.retrievePurchaseLog(reqObj);
			if(resEncap != null && resEncap.getRetrievePurchaseLogResult() != null) {
				result = resEncap.getRetrievePurchaseLogResult();

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
	 * SubmitBackupPurchaseLog
	 */
	public CallResponse submitBackupPurchaseLog(String bankNo, String compNo, BigDecimal invAmount)  throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			SubmitBackupPurchaseLogResponse subResp;
			PurchaseLogResponse result;
			
			SubmitBackupPurchaseLog reqObj = new SubmitBackupPurchaseLog();
			reqObj.setUser((UserToken)wexUserToken);
			
			PurchaseLog reqData = new PurchaseLog();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setInvoiceAmount(invAmount);
			reqData.setBillingCurrency("AUD");
			
			reqObj.setPurchaseLog(reqData);
			
			subResp = purchaseLogServiceStub.submitBackupPurchaseLog(reqObj);
			result = subResp.getSubmitBackupPurchaseLogResult();
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

	/*
	 * SubmitBackupPurchaseLogWithFaxInfo
	 */
	public CallResponse submitBackupPurchaseLogWithFaxInfo(String bankNo, String compNo, 
												BigDecimal invAmount, String currency)  throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			SubmitBackupPurchaseLogWithFaxInfoResponse subResp;
			PurchaseLogResponse result;
			
			SubmitBackupPurchaseLogWithFaxInfo reqObj = new SubmitBackupPurchaseLogWithFaxInfo();
			reqObj.setUser((UserToken)wexUserToken);
			
			PurchaseLog reqData = new PurchaseLog();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setInvoiceAmount(invAmount);
			reqData.setBillingCurrency(currency);
			
			reqObj.setPurchaseLog(reqData);
			
			subResp = purchaseLogServiceStub.submitBackupPurchaseLogWithFaxInfo(reqObj);
			result = subResp.getSubmitBackupPurchaseLogWithFaxInfoResult();
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

	/*
	 * SubmitPurchaseLog
	 */
	public CallResponse submitPurchaseLog(String bankNo, String compNo, BigDecimal invAmount)  throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			SubmitPurchaseLogResponse subResp;
			PurchaseLogResponse result;
			
			SubmitPurchaseLog reqObj = new SubmitPurchaseLog();
			reqObj.setUser((UserToken)wexUserToken);
			
			PurchaseLog reqData = new PurchaseLog();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setInvoiceAmount(invAmount);
			reqData.setBillingCurrency("AUD");
			
			reqObj.setPurchaseLog(reqData);
			
			subResp = purchaseLogServiceStub.submitPurchaseLog(reqObj);
			result = subResp.getSubmitPurchaseLogResult();
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

	/*
	 * SubmitPurchaseLogAndGetImagePdf
	 */
	public CallResponse submitPurchaseLogAndGetImagePdf(String bankNo, String compNo, BigDecimal invAmount)  throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			SubmitPurchaseLogAndGetImagePdfResponse subResp;
			PurchaseLogResponseWithImagePdf result;
			
			SubmitPurchaseLogAndGetImagePdf reqObj = new SubmitPurchaseLogAndGetImagePdf();
			reqObj.setUser((UserToken)wexUserToken);
			
			PurchaseLog reqData = new PurchaseLog();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setInvoiceAmount(invAmount);
			reqData.setBillingCurrency("AUD");
			
			reqObj.setPurchaseLog(reqData);
			
			subResp = purchaseLogServiceStub.submitPurchaseLogAndGetImagePdf(reqObj);
			result = subResp.getSubmitPurchaseLogAndGetImagePdfResult();
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
	
	/*
	 * SubmitPurchaseLogWithFaxInfo
	 */
	public CallResponse submitPurchaseLogWithFaxInfo(String bankNo, String compNo, 
												BigDecimal invAmount, String currency)  throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			SubmitPurchaseLogWithFaxInfoResponse subResp;
			PurchaseLogResponse result;
			
			SubmitPurchaseLogWithFaxInfo reqObj = new SubmitPurchaseLogWithFaxInfo();
			reqObj.setUser((UserToken)wexUserToken);
			
			PurchaseLog reqData = new PurchaseLog();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setInvoiceAmount(invAmount);
			reqData.setBillingCurrency(currency);
			
			reqObj.setPurchaseLog(reqData);
			
			subResp = purchaseLogServiceStub.submitPurchaseLogWithFaxInfo(reqObj);
			result = subResp.getSubmitPurchaseLogWithFaxInfoResult();
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
	
	/*
	 * UpdatesPurchaseLog
	 */
	public CallResponse updatePurchaseLog(String bankNo, String compNo, String uniqueId, BigDecimal amount) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			UpdatePurchaseLogResponse result;
			UpdatePurchaseLogResponseE resEncap;
			
			UpdatePurchaseLog reqObj = new UpdatePurchaseLog();
			reqObj.setUser((User)wexUser);
			
			VendorInfo aVendor = new VendorInfo();
			aVendor.setID("");
			aVendor.setName("");
			aVendor.setPaymentType(PaymentTypeEnum.Card);

			PaymentScheduleItem aSchedule = new PaymentScheduleItem();
			Calendar fromCalendar = Calendar.getInstance();
			Calendar toCalendar = Calendar.getInstance();
			LocalDate fromDate = LocalDate.of(2015, 12, 31);
			LocalDate toDate = LocalDate.of(2017, 1, 1);
			fromCalendar.setTime(Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			toCalendar.setTime(Date.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			aSchedule.setActiveFromDate(fromCalendar);
			aSchedule.setActiveToDate(toCalendar);
			aSchedule.setAmount(new BigDecimal("120"));
			aSchedule.setCreditLimit(amount);
			
			UpdatePurchaseLogRequest reqData = new UpdatePurchaseLogRequest();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setPurchaseLogUniqueID(uniqueId);
			reqData.setVendor(aVendor);

			ArrayOfPaymentScheduleItem arraySchedule = new ArrayOfPaymentScheduleItem();
			arraySchedule.addPaymentScheduleItem(aSchedule);
			reqData.setPaymentSchedule(arraySchedule);
			
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.updatePurchaseLog(reqObj);
			if(resEncap != null) {
				result = resEncap.getUpdatePurchaseLogResult();
				
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

}
