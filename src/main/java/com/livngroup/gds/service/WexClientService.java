package com.livngroup.gds.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistory;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponseE;
import com.livngroup.gds.domain.WexAccount;
import com.livngroup.gds.domain.WexUser;
import com.livngroup.gds.exception.WexException;

@Service
public class WexClientService {
	
	@Autowired
	@Qualifier("purchaseLogServiceStub")
	protected PurchaseLogServiceStub purchaseLogServiceStub;
	
	public Map<String, String> getPurchaseLogHistory(WexUser aUser, WexAccount aAccount, String uniqueId) throws WexException {
		
		try {
			Map<String, String> historyLog = new HashMap<>();
			GetPurchaseLogHistoryResponseE res;
			
			GetPurchaseLogHistory reqObj = new GetPurchaseLogHistory();
			reqObj.setUser(aUser);
			GetPurchaseLogHistoryRequest reqData = new GetPurchaseLogHistoryRequest();
			reqData.setBankNumber(aAccount.getBankNo());
			reqData.setCompanyNumber(aAccount.getComNo());
			reqData.setPurchaseLogUniqueID(uniqueId);
			reqObj.setRequest(reqData);
			
			res = purchaseLogServiceStub.getPurchaseLogHistory(reqObj);
			GetPurchaseLogHistoryResponse result = res.getGetPurchaseLogHistoryResult();
			
			
			System.out.println("Response Code : " + result.getResponseCode());
			System.out.println("Description : " + result.getDescription());
			historyLog.put("repcode", result.getResponseCode().getValue());
			historyLog.put("description", result.getDescription());
			return historyLog;

		} catch(Exception exc) {
			throw new WexException(exc);
		}
		
	}
}
