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

@RestController
@RequestMapping("/backupcard")
public class BackupCardController extends WexController {

	@Autowired
	WexBackupCardService backupCardService;
	
	@RequestMapping(value="/get", method=RequestMethod.GET)
	public @ResponseBody GeneralResponse getCard(@RequestParam String bankNo, 
												@RequestParam String compNo, 
												@RequestParam String orderId) throws WexException {
		CallResponse response = backupCardService.getBackupCards(bankNo, compNo, orderId);
//
//		BackupCard backupCard = new BackupCard();
//		backupCard.setCardNumber(new Date().toString());
//		backupCard.setCvc2("123");
//		backupCard.setNameLine1("name line one");
//		backupCard.setCreateDate(new Date());
//		Calendar expirationDate = Calendar.getInstance();
//		expirationDate.add(Calendar.YEAR, 1);
//		backupCard.setCurrency(Currency.getInstance("AUD"));
//    	backupCard.setCreditLimit(new BigDecimal("10000.00"));
//        backupCardRepository.save(backupCard);			
//        
//        List<BackupCard> cardList = backupCardRepository.findAll();
//        logger.debug("Card list:");
//        cardList.forEach(n -> logger.debug(n.getUuid() + ":" + n.getCardNumber()));
		
		
		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}

	@RequestMapping(value="/order", method=RequestMethod.GET)
	public @ResponseBody GeneralResponse orderCard(@RequestParam String bankNo, 
												@RequestParam String compNo, 
												@RequestParam String orderId) throws WexException {
		CallResponse response = backupCardService.orderBackupCards(bankNo, compNo, orderId);

		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}

}
