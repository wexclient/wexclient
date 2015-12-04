package com.livngroup.gds.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistory;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.User;
import com.google.gson.Gson;
import com.livngroup.gds.domain.WexAccount;
import com.livngroup.gds.domain.WexUser;
import com.livngroup.gds.response.GeneralResponse;
import com.livngroup.gds.service.WexClientService;

@RestController
@RequestMapping("/WEX")
public class PurchaseHistoryLog {

	@Autowired
	WexClientService wexService;
	
	@RequestMapping("/getPurchaseHistory")
	public @ResponseBody GeneralResponse call(@RequestParam String username, @RequestParam String password, @RequestParam String group) {
		GeneralResponse response = new GeneralResponse();
		StringBuffer logs = new StringBuffer();
		
		WexUser aUser = new WexUser();
		aUser.setOrgGroupLoginId(group);
		aUser.setUsername(username);
		aUser.setPassword(password);
		WexAccount aAccount = new WexAccount();
		aAccount.setBankNo("");
		aAccount.setComNo("");
		String uniquedId = "32";

		Map<String, String> historyLog = wexService.getPurchaseLogHistory(aUser, aAccount, uniquedId);
		for(String key : historyLog.keySet()) {
			logs.append(key + " : " + historyLog.get(key));
		}
		
		
		response.setOk(true);
		response.setMessage("Successful");
		response.setResult("");
		
		return response;
	}
}
