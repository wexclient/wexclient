package com.livngroup.gds.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CreatePurchaseLog;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CreatePurchaseLogRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CreatePurchaseLogResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CreatePurchaseLogResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistory;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogs;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogsRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogsResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.User;
import com.livngroup.gds.exception.WexException;
import com.livngroup.gds.response.CallResponse;

@Service
public class WexPurchaseLogService extends WexService {

	public CallResponse createPurchaseLog(String bankNo, String compNo, String uniqueId) throws WexException {
		CallResponse result = new CallResponse();
		
		try {
			CreatePurchaseLogResponse response;
			CreatePurchaseLogResponseE resEncap;
			
			CreatePurchaseLog reqObj = new CreatePurchaseLog();
			reqObj.setUser((User)wexUser);
			
			CreatePurchaseLogRequest reqData = new CreatePurchaseLogRequest();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setAmount(new BigDecimal(150));
			
			resEncap = purchaseLogServiceStub.createPurchaseLog(reqObj);
			if(resEncap != null) {
				response = resEncap.getCreatePurchaseLogResult();
				result.setResult((Object)response);
				result.setOk(true);
				result.setMessage("Success");
			}
		} catch(java.rmi.RemoteException e) {
//			logger.debug(e);
			throw new WexException("WEX has RMI exception. It could be caused by Server side and network.");
		}
		
		return result;
	}
	
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
//			logger.debug(e);
			throw new WexException("WEX has RMI exception. It could be caused by Server side and network.");
		}
		
		return result;
	}
	
	public CallResponse queryPurchaseLog(String bankNo, String compNo, String status) throws WexException {
		CallResponse result = new CallResponse();
		
		try {
			QueryPurchaseLogsResponse response;
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
				response = resEncap.getQueryPurchaseLogsResult();
				result.setResult((Object)response);
				result.setOk(true);
				result.setMessage("Success");
			}
			
		} catch(java.rmi.RemoteException e) {
//			logger.debug(e);
			throw new WexException("WEX has RMI exception. It could be caused by Server side and network.");
		}
		
		return result;
	}
	
}
