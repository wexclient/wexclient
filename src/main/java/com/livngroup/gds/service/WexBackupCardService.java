package com.livngroup.gds.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.ArrayOfBackupCardOrderBlock;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.BackupCardOrderBlock;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.BackupCardOrderRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.BackupCardRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCards;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCardsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.OrderBackupCards;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.OrderBackupCardsResponse;
import com.livngroup.gds.domain.BackupCard;
import com.livngroup.gds.exception.WexException;
import com.livngroup.gds.repositories.BackupCardRepository;
import com.livngroup.gds.response.CallResponse;

@Service
public class WexBackupCardService extends WexService {

	@Autowired
	private BackupCardRepository backupCardRepository;
	
	@Transactional()
	public CallResponse getBackupCards(String bankNo, String compNo, String orderId) throws WexException {
		CallResponse result = new CallResponse();
		
		try {
			GetBackupCardsResponse response;
			GetBackupCards reqObj = new GetBackupCards();
			
			BackupCardRequest reqData = new BackupCardRequest();
			
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setOrderID(orderId);

			reqObj.setUser(wexUserToken);
			reqObj.setRequest(reqData);
			
			response = purchaseLogServiceStub.getBackupCards(reqObj);
			
		} catch(java.rmi.RemoteException e) {
			logger.debug(e);
			throw new WexException("WEX has RMI exception. It could be caused by Server side and network.");
		}
		
		// dummy code to test persistence
		// in future "getBackupCards" will have to go to local database
		// instead of calling WEX for data
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
		
		return result;
	}

	public CallResponse orderBackupCards(String bankNo, String compNo, String orderId) throws WexException {
		CallResponse result = new CallResponse();
		
		try {
			OrderBackupCardsResponse response;
			OrderBackupCards reqObj = new OrderBackupCards();
			
			BackupCardOrderRequest reqData = new BackupCardOrderRequest();
			
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setOrderID(orderId);

			ArrayOfBackupCardOrderBlock orderArray = new ArrayOfBackupCardOrderBlock();
			BackupCardOrderBlock aOrder = new BackupCardOrderBlock();
			/*
			 * 
			 */
			aOrder.setBillingCurrency("");
			aOrder.setCreditLimit(new BigDecimal(5000));
			aOrder.setQuantity(150);
			BackupCardOrderBlock[] orderBlocks = {aOrder};
			orderArray.setBackupCardOrderBlock(orderBlocks);

			reqData.setOrderBlocks(orderArray);

			reqObj.setUser(wexUserToken);
			reqObj.setRequest(reqData);
			
			response = purchaseLogServiceStub.orderBackupCards(reqObj);
			if(response != null) {
				
			}
			
		} catch(java.rmi.RemoteException e) {
			logger.debug(e);
			throw new WexException("WEX has RMI exception. It could be caused by Server side and network.");
		}
		
		return result;
	}

}
