package com.livngroup.gds.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.livngroup.gds.domain.GeneralListResponse;
import com.livngroup.gds.domain.WexAccount;
import com.livngroup.gds.exception.WexException;
import com.livngroup.gds.service.WexClientService;

@RestController
@RequestMapping("/WEX")
public class PurchaseHistoryLog extends WexController {

	@Autowired
	WexClientService wexService;
	
	@RequestMapping(value = "/getPurchaseHistory", method = RequestMethod.GET)
	public @ResponseBody GeneralListResponse call() throws WexException {
		GeneralListResponse response = new GeneralListResponse();
		StringBuffer logs = new StringBuffer();
		
		WexAccount aAccount = new WexAccount();
		aAccount.setBankNo("");
		aAccount.setComNo("");
		String uniquedId = "32";

		Map<String, String> historyLog = wexService.getPurchaseLogHistory(wexUser, aAccount, uniquedId);
		for(String key : historyLog.keySet()) {
			logs.append(key + " : " + historyLog.get(key));
		}
		
//		response.setOk(true);
//		response.setMessage("Successful");
//		response.setResult("");
		
		return response;
	}

}
