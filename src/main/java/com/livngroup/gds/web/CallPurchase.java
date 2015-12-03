package com.livngroup.gds.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistory;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.User;

@RestController
@RequestMapping("/demo")
public class CallPurchase {

	final static private String WEX_WSDL_URL = "https://services.encompass-suite.com/services/PurchaseLogService.asmx?wsdl";

	@RequestMapping("/call")
	public String hello(@RequestParam String name) {
		String historyLog = "";
		
		try {
			PurchaseLogServiceStub wexService = new PurchaseLogServiceStub(WEX_WSDL_URL);
			
			GetPurchaseLogHistory obj = new GetPurchaseLogHistory();

			User credential = new User();
			credential.setOrgGroupLoginId("AOCTEST");
			credential.setUsername(name);
			credential.setPassword("plogtestpw");
			obj.setUser(credential);
			GetPurchaseLogHistoryRequest reqData = new GetPurchaseLogHistoryRequest();
			reqData.setBankNumber("1234");
			reqData.setCompanyNumber("1234567");
			reqData.setPurchaseLogUniqueID("32");
			obj.setRequest(reqData);
			
			GetPurchaseLogHistoryResponseE res = wexService.getPurchaseLogHistory(obj);
			
			GetPurchaseLogHistoryResponse result = res.getGetPurchaseLogHistoryResult();
			System.out.println("Response Code : " + result.getResponseCode());
			System.out.println("Description : " + result.getDescription());
			historyLog = result.getDescription();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return historyLog;
	}
}