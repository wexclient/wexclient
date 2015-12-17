package com.livngroup.gds.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistory;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponseE;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.domain.WexUser;
import com.livngroup.gds.exception.ExceptionFactory;
import com.livngroup.gds.exception.WexAppException;

@RestController
@RequestMapping("/demo")
public class CallPurchaseDemo extends WexController {
	@Autowired
	@Qualifier("wexUser")
	protected WexUser wexUser;
	
	@Autowired
	@Qualifier("purchaseLogServiceStub")
	protected PurchaseLogServiceStub purchaseLogServiceStub;

	@RequestMapping("/call")
	public String call() throws WexAppException {
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
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PURCHASE_LOG);
		}
	}
}