package com.livngroup.gds.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistory;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponseE;
import com.livngroup.gds.exception.WexException;

@RestController
@RequestMapping("/demo")
public class CallPurchase extends WexController {

	@RequestMapping("/call")
	public String hello(@RequestParam String name) throws WexException {
		String historyLog = "";
		
		try {
			
			GetPurchaseLogHistory obj = new GetPurchaseLogHistory();

			obj.setUser(wexUser);
			GetPurchaseLogHistoryRequest reqData = new GetPurchaseLogHistoryRequest();
			reqData.setBankNumber("1234");
			reqData.setCompanyNumber("1234567");
			reqData.setPurchaseLogUniqueID("32");
			obj.setRequest(reqData);
			
			GetPurchaseLogHistoryResponseE res = purchaseLogServiceStub.getPurchaseLogHistory(obj);
			
			GetPurchaseLogHistoryResponse result = res.getGetPurchaseLogHistoryResult();
			System.out.println("Response Code : " + result.getResponseCode());
			System.out.println("Description : " + result.getDescription());
			historyLog = result.getDescription();

			return historyLog;
			
		} catch(Exception exc) {
			throw new WexException(exc);
		}
		
	}
}