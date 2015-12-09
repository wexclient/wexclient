package com.livngroup.gds.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.livngroup.gds.exception.WexException;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.response.GeneralResponse;
import com.livngroup.gds.service.WexPurchaseLogService;

@RestController
@RequestMapping("/purchase")
public class PurchaseLogController extends WexController {
	
	@Autowired
	WexPurchaseLogService wexService;
	
	@RequestMapping(value="/historyLog", method=RequestMethod.GET)
	public @ResponseBody GeneralResponse getHistoryLog(@RequestParam String bankNo, 
														@RequestParam String compNo, 
														@RequestParam String uniqueId) throws WexException {
		CallResponse response = wexService.getPurchaseLogHistory(bankNo, compNo, uniqueId);

		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}
	
	@RequestMapping(value="/queryLog", method=RequestMethod.GET)
	public @ResponseBody GeneralResponse queryPurchaseLog(@RequestParam String bankNo, 
															@RequestParam String compNo, 
															@RequestParam String uniqueId) throws WexException {
		CallResponse response = wexService.queryPurchaseLog(bankNo, compNo, uniqueId);
		
		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}

}
