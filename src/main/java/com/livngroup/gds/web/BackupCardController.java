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
import com.livngroup.gds.service.WexBackupCardService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/backupcard")
@Api(value="/backupcard")
public class BackupCardController extends WexController {

	@Autowired
	private WexBackupCardService backupCardService;
	
	@RequestMapping(value="/get", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody GeneralResponse getCard(@RequestParam String bankNo, 
												@RequestParam String compNo, 
												@RequestParam String orderId) throws WexException {
		
		CallResponse response = backupCardService.getBackupCards(bankNo, compNo, orderId);		
		
		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}

	@RequestMapping(value="/order", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody GeneralResponse orderCard(@RequestParam String bankNo, 
												@RequestParam String compNo, 
												@RequestParam(value="orderId", required=false) String orderId) throws WexException {
		CallResponse response = backupCardService.orderBackupCards(bankNo, compNo, orderId);

		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}

}
