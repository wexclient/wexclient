package com.livngroup.gds.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.livngroup.gds.exception.WexException;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.response.GeneralResponse;
import com.livngroup.gds.service.WexBackupCardService;

@RestController
@RequestMapping("/backupcard")
public class BackupCardController extends WexController {

	@Autowired
	WexBackupCardService backupCardService;
	
	@RequestMapping("/get")
	public @ResponseBody GeneralResponse getCard(@RequestParam String bankNo, 
												@RequestParam String compNo, 
												@RequestParam String orderId) throws WexException {
		CallResponse response = backupCardService.getBackupCards(bankNo, compNo, orderId);

		return (GeneralResponse)response;
	}

	@RequestMapping("/order")
	public @ResponseBody GeneralResponse orderCard(@RequestParam String bankNo, 
												@RequestParam String compNo, 
												@RequestParam String orderId) throws WexException {
		
		CallResponse response = backupCardService.orderBackupCards(bankNo, compNo, orderId);

		return (GeneralResponse)response;
	}

}
