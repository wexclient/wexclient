package com.livngroup.gds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.BackupCardOrderResponse;
import com.livngroup.gds.domain.BackupCard;
import com.livngroup.gds.repositories.BackupCardRepository;

@Service
public class GdsDbService {

//	@Autowired
//	BackupCardRepository backupCardRepo;
	
	public void insertBackupCard(BackupCardOrderResponse issuedCard) {
		BackupCard newCardInfo = new BackupCard();

		/*
		 * Information of issued BackupCard stored into Database 
		 */
//		issuedCard.getCardsAvailable();
//		issuedCard.getPullParser(qName);
//		newCardInfo.setCardNumber();
//		backupCardRepo.save(newCardInfo);
	}
}
