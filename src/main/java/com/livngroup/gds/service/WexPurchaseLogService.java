package com.livngroup.gds.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.livngroup.gds.exception.WexException;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.response.ErrorResponse;

@Service
public class WexPurchaseLogService extends WexService {
	
	/*
	 * CreatePurchaseLog
	 */
	public CallResponse createPurchaseLog(String bankNo, String compNo, String amount) throws WexException {
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
		} catch(java.rmi.RemoteException e) {
			logger.error("RmoteException Error Message : " + e.getMessage());
			throw new WexException("WEX has RMI exception. It could be caused by Server side and network.");
		}
		
		return result;
	}
	
	/*
	 * GetPurchaseLogHistory
	 */
	public CallResponse getPurchaseLogHistory(String bankNo, String compNo, String uniqueId) throws WexException {
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
				result.setMessage("Success");
			}
			
		} catch(java.rmi.RemoteException e) {
			logger.error("RmoteException Error Message : " + e.getMessage());
			throw new WexException("WEX has RMI exception. It could be caused by Server side and network.");
		}
		
		return result;
	}

	/*
	 * CancelPurchaseLog
	 */
	public CallResponse cancelPurchaseLog(String bankNo, String compNo, String uniqueId) throws WexException {
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
				result.setMessage("Success");
			}
			
		} catch(java.rmi.RemoteException e) {
			logger.error("RmoteException Error Message : " + e.getMessage());
			throw new WexException("WEX has RMI exception. It could be caused by Server side and network.");
		}
		
		return result;
	}
	
	/*
	 * QueryPurchaseLog
	 */
	public CallResponse queryPurchaseLog(String bankNo, String compNo, String status) throws WexException {
		CallResponse response = new CallResponse();
		
		try {
			QueryPurchaseLogsResponse result;
			QueryPurchaseLogsResponseE resEncap;
			
			QueryPurchaseLogs reqObj = new QueryPurchaseLogs();
			reqObj.setUser((User)wexUser);
			
			QueryPurchaseLogsRequest reqData = new QueryPurchaseLogsRequest();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setStatus(status);
			
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.queryPurchaseLogs(reqObj);
			if(resEncap != null) {
				result = resEncap.getQueryPurchaseLogsResult();
				
				if(result != null) {
					
					logger.debug(result.getDescription());
					PurchaseLogResponseCodeEnum resultCode = result.getResponseCode();
//					if(resultCode.getValue().equals(PurchaseLogResponseCodeEnum._Success)) {
//						resultCode.getValue();
//						re
//					} else {
//						ErrorResponse errorResp = new ErrorResponse(false, "The response of WEX server is failed. Please have a look at logs.");
//						return = new ResponseEntity<>(errorResp, HttpStatus.FAILED_DEPENDENCY);
//					}
//				} else {
//					ErrorResponse errorResp = new ErrorResponse(false, "WEX server no respond. Please contact to GDS administrator.");
//					return = new ResponseEntity<>(errorResp, HttpStatus.REQUEST_TIMEOUT);
				}

			}
			
		} catch(java.rmi.RemoteException e) {
			logger.error("RmoteException Error Message : " + e.getMessage());
			throw new WexException("WEX has RMI exception. It could be caused by Server side and network.");
		}
		
		return response;
	}

	/*
	 * UpdatesPurchaseLog
	 */
	public CallResponse updatePurchaseLog(String bankNo, String compNo, 
											String uniqueId, BigDecimal amount) throws WexException {
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
				result.setMessage("Success");
			}
			
		} catch(java.rmi.RemoteException e) {
			logger.error("RmoteException Error Message : " + e.getMessage());
			throw new WexException("WEX has RMI exception. It could be caused by Server side and network.");
		}
		
		return result;
	}

}
