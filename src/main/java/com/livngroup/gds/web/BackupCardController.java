package com.livngroup.gds.web;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.BackupCardRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCards;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCardsResponse;
import com.livngroup.gds.domain.BackupCard;
import com.livngroup.gds.exception.WexException;
import com.livngroup.gds.repositories.BackupCardRepository;

@RestController
@RequestMapping("/WEX")
public class BackupCardController extends WexController {
	
	@Autowired
	BackupCardRepository backupCardRepository;
	
	@RequestMapping(value = "/backupCard", method = RequestMethod.GET)
	public String getBackupCards(@RequestParam String bankNumber, @RequestParam String companyNumber, @RequestParam String orderId) throws WexException {
		
		try {
			
			GetBackupCards requestObj = new GetBackupCards();
			requestObj.setUser(wexUser.convertToUserToken());
			
			BackupCardRequest requestData = new BackupCardRequest();
			
			requestData.setBankNumber(bankNumber); 
			requestData.setCompanyNumber(companyNumber);
			requestData.setOrderID(orderId);
			
			requestObj.setRequest(requestData);
			
			GetBackupCardsResponse responceObj = purchaseLogServiceStub.getBackupCards(requestObj);
			
//			GetPurchaseLogHistoryResponse result = res.getGetPurchaseLogHistoryResult();
//			System.out.println("Response Code : " + result.getResponseCode());
//			System.out.println("Description : " + result.getDescription());
			String description = responceObj.getGetBackupCardsResult().getDescription();

			// dummy code to test persistence
			BackupCard backupCard = new BackupCard();
			backupCard.setCardNumber(new Date().toString());
			backupCard.setCvc2("123");
			backupCard.setNameLine1("name line one");
			backupCard.setCreateDate(new Date());
			Calendar expirationDate = Calendar.getInstance();
			expirationDate.add(Calendar.YEAR, 1);
			backupCard.setCurrency(Currency.getInstance("AUD"));
	    	backupCard.setCreditLimit(new BigDecimal("10000.00"));
	        backupCardRepository.save(backupCard);			
	        
	        List<BackupCard> cardList = backupCardRepository.findAll();
	        logger.debug("Card list:");
	        cardList.forEach(n -> logger.debug(n.getUuid() + ":" + n.getCardNumber()));
			
			return description;
			
		} catch(Exception exc) {
			throw new WexException(exc);
		}
		
	}
}