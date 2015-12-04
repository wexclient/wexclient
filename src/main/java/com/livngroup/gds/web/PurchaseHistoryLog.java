package com.livngroup.gds.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.livngroup.gds.domain.WexAccount;
import com.livngroup.gds.exception.WexException;
import com.livngroup.gds.response.GeneralResponse;
import com.livngroup.gds.service.WexClientService;

@RestController
@RequestMapping("/WEX")
public class PurchaseHistoryLog extends WexController {
	
	@Autowired
	WexClientService wexService;
	
	@RequestMapping("/getPurchaseHistory")
	public @ResponseBody GeneralResponse call() throws WexException {
		GeneralResponse response = new GeneralResponse();
		StringBuffer logs = new StringBuffer();
		
		WexAccount aAccount = new WexAccount();
		aAccount.setBankNo("");
		aAccount.setComNo("");
		String uniquedId = "32";

		Object historyLog = wexService.getPurchaseLogHistory(wexUser, aAccount, uniquedId);

		response.setOk(true);
		response.setMessage("Successful");
		response.setResult(historyLog);
		
		return response;
	}
}
