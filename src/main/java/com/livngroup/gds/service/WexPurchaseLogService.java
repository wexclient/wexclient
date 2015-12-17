package com.livngroup.gds.service;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

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
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PaymentScheduleItem;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PaymentTypeEnum;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PurchaseLogResponseCodeEnum;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogs;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogsRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogsResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.UpdatePurchaseLog;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.UpdatePurchaseLogRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.UpdatePurchaseLogResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.UpdatePurchaseLogResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.User;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.VendorInfo;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.ExceptionFactory;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.response.CallResponse;

@Service
public class WexPurchaseLogService extends WexService {
		
	/** CreatePurchaseLog */
	public CallResponse createPurchaseLog(String bankNo, String compNo, String amount) throws WexAppException {
		CallResponse result = new CallResponse();
		
		try {
			CreatePurchaseLogResponse response;
			CreatePurchaseLogResponseE resEncap;
			
			CreatePurchaseLog reqObj = new CreatePurchaseLog();
			reqObj.setUser((User)wexUser);
			
			CreatePurchaseLogRequest reqData = new CreatePurchaseLogRequest();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setAmount(new BigDecimal(amount));
			
			resEncap = purchaseLogServiceStub.createPurchaseLog(reqObj);
			if(resEncap != null) {
				response = resEncap.getCreatePurchaseLogResult();
				result.setResult((Object)response);
				result.setOk(true);
				result.setMessage("Success");
			}
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PURCHASE_LOG);
		}
		
		return result;
	}
	
	/*
	 * GetPurchaseLogHistory
	 */
	public CallResponse getPurchaseLogHistory(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse result = new CallResponse();
		
		try {
			GetPurchaseLogHistoryResponse response;
			GetPurchaseLogHistoryResponseE resEncap;
			
			GetPurchaseLogHistory reqObj = new GetPurchaseLogHistory();
			reqObj.setUser((User)wexUser);
			
			GetPurchaseLogHistoryRequest reqData = new GetPurchaseLogHistoryRequest();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setPurchaseLogUniqueID(uniqueId);
			
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.getPurchaseLogHistory(reqObj);
			if(resEncap != null) {
				response = resEncap.getGetPurchaseLogHistoryResult();
				result.setResult((Object)response);
				result.setOk(true);
				result.setMessage(CallResponse.SUCCESS);
			}
			
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PURCHASE_LOG);
		}
		
		return result;
	}

	/*
	 * CancelPurchaseLog
	 */
	public CallResponse cancelPurchaseLog(String bankNo, String compNo, String uniqueId) throws WexAppException {
		CallResponse result = new CallResponse();
		
		try {
			CancelPurchaseLogResponse response;
			CancelPurchaseLogResponseE resEncap;
			
			CancelPurchaseLog reqObj = new CancelPurchaseLog();
			reqObj.setUser((User)wexUser);
			
			CancelPurchaseLogRequest reqData = new CancelPurchaseLogRequest();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setPurchaseLogUniqueID(uniqueId);
			
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.cancelPurchaseLog(reqObj);
			if(resEncap != null) {
				response = resEncap.getCancelPurchaseLogResult();
				result.setResult((Object)response);
				result.setOk(true);
				result.setMessage(CallResponse.SUCCESS);
			}
			
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PURCHASE_LOG);
		}
		
		return result;
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
			reqData.setStatus(status);
			
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.queryPurchaseLogs(reqObj);
			if(resEncap != null && resEncap.getQueryPurchaseLogsResult() != null) {
				result = resEncap.getQueryPurchaseLogsResult();
				logger.debug(result.getDescription());
					
				PurchaseLogResponseCodeEnum resultCode = result.getResponseCode();
				if(PurchaseLogResponseCodeEnum.Success.equals(resultCode)) {
					response.setOk(true);
					response.setMessage("Successful call response");
					response.setStatus(HttpStatus.OK);
					response.setResult(result);
				} else {
					response.setOk(false);
					response.setMessage("WEX : [code] - " + resultCode.getValue() + " [description] - " + result.getDescription());
					response.setStatus(HttpStatus.BAD_REQUEST);
				}
			} else {
				response.setOk(false);
				response.setMessage("WEX server not responde : no response");
				response.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
			}
			
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PURCHASE_LOG);
		}
		
		return response;
	}

	/*
	 * UpdatesPurchaseLog
	 */
	public CallResponse updatePurchaseLog(String bankNo, String compNo, 
											String uniqueId, BigDecimal amount) throws WexAppException {
		CallResponse result = new CallResponse();
		
		try {
			UpdatePurchaseLogResponse response;
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
			aSchedule.setCreditLimit(new BigDecimal("1000"));
			
			UpdatePurchaseLogRequest reqData = new UpdatePurchaseLogRequest();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setPurchaseLogUniqueID(uniqueId);
			reqData.setAmount(amount);
			reqData.setVendor(aVendor);

			ArrayOfPaymentScheduleItem arraySchedule = new ArrayOfPaymentScheduleItem();
			arraySchedule.addPaymentScheduleItem(aSchedule);
			reqData.setPaymentSchedule(arraySchedule);
			
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.updatePurchaseLog(reqObj);
			if(resEncap != null) {
				response = resEncap.getUpdatePurchaseLogResult();
				result.setResult((Object)response);
				result.setOk(true);
				result.setMessage(CallResponse.SUCCESS);
			}
			
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PURCHASE_LOG);
		}
		
		return result;
	}

}
