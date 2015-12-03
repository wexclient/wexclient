package com.livngroup.gds.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistory;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.User;
import com.livngroup.gds.domain.WexAccount;
import com.livngroup.gds.domain.WexUser;

@Service
public class WexClientService {
	@Autowired
	private WexClientFactory clientFactory;
//	final static private String WEX_WSDL_URL = "https://services.encompass-suite.com/services/PurchaseLogService.asmx?wsdl";
	
	public HashMap<String, String> getPurchaseLogHistory(WexUser aUser, WexAccount aAccount, String uniqueId) {
		HashMap<String, String> historyLog = new HashMap<>();
		
		PurchaseLogServiceStub wexService = clientFactory.getWexClient();
		try {
//			PurchaseLogServiceStub wexService = new PurchaseLogServiceStub(WEX_WSDL_URL);
			GetPurchaseLogHistoryResponseE res;
			
			GetPurchaseLogHistory reqObj = new GetPurchaseLogHistory();
			reqObj.setUser((User)aUser);
			GetPurchaseLogHistoryRequest reqData = new GetPurchaseLogHistoryRequest();
			reqData.setBankNumber(aAccount.getBankNo());
			reqData.setCompanyNumber(aAccount.getComNo());
			reqData.setPurchaseLogUniqueID(uniqueId);
			reqObj.setRequest(reqData);
			
			res = wexService.getPurchaseLogHistory(reqObj);
			GetPurchaseLogHistoryResponse result = res.getGetPurchaseLogHistoryResult();
			
			
			System.out.println("Response Code : " + result.getResponseCode());
			System.out.println("Description : " + result.getDescription());
			historyLog.put("repcode", result.getResponseCode().getValue());
			historyLog.put("description", result.getDescription());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return historyLog;
	}
}
