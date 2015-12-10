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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/purchase")
@Api(value="/purchase")
public class PurchaseLogController extends WexController {
	
	@Autowired
	WexPurchaseLogService wexService;
	
	@ApiResponses(@ApiResponse(code=402, message="Input amount is not number", response=CallResponse.class))
	@RequestMapping(value="/createLog", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody GeneralResponse createPurchaseLog(@RequestParam String bankNo, 
														@RequestParam String compNo, 
														@RequestParam String amount) throws WexException {
		CallResponse response = wexService.createPurchaseLog(bankNo, compNo, amount);

		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}
	
	@RequestMapping(value="/cancelLog", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody GeneralResponse cancelPurchaseLog(@RequestParam String bankNo, 
														@RequestParam String compNo, 
														@RequestParam String uniqueId) throws WexException {
		CallResponse response = wexService.cancelPurchaseLog(bankNo, compNo, uniqueId);

		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}
	
	@RequestMapping(value="/historyLog", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody GeneralResponse getHistoryLog(@RequestParam String bankNo, 
														@RequestParam String compNo, 
														@RequestParam String uniqueId) throws WexException {
		CallResponse response = wexService.getPurchaseLogHistory(bankNo, compNo, uniqueId);

		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}
	
	@RequestMapping(value="/queryLog", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody GeneralResponse queryPurchaseLog(@RequestParam String bankNo, 
															@RequestParam String compNo, 
															@RequestParam String uniqueId) throws WexException {
		CallResponse response = wexService.queryPurchaseLog(bankNo, compNo, uniqueId);
		
		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}

}
