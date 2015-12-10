package com.livngroup.gds.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.livngroup.gds.LivnDemoApplication;
import com.livngroup.gds.domain.BackupCard;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LivnDemoApplication.class)
@WebIntegrationTest("server.port=8080")
public class BackupCardRepositoryTest {
 
    @Autowired
    private BackupCardRepository backupCardRepository;
 
    @Test
    public void testBackupCardCRUD(){

		BackupCard backupCard = new BackupCard();
		backupCard.setCardNumber("1111222223333344444");
		backupCard.setCvc2("123");
		backupCard.setNameLine1("name line one");
		backupCard.setCreateDate(new Date());
		Calendar expirationDate = Calendar.getInstance();
		expirationDate.add(Calendar.YEAR, 1);
		backupCard.setCurrency(Currency.getInstance("AUD"));
    	backupCard.setCreditLimit(new BigDecimal("10000.00"));
    	
        assertNull(backupCard.getUuid()); 
        backupCardRepository.save(backupCard);
        assertNotNull(backupCard.getUuid()); 
 
        BackupCard fetchedBackupCard = backupCardRepository.findOne(backupCard.getUuid());
        assertNotNull(fetchedBackupCard);
        assertEquals(backupCard.getUuid(), fetchedBackupCard.getUuid());
        assertEquals(backupCard.getCardNumber(), fetchedBackupCard.getCardNumber());
        
        fetchedBackupCard.setNameLine2("name line two");
        backupCardRepository.save(fetchedBackupCard);
 
        BackupCard fetchedUpdatedBackupCard = backupCardRepository.findOne(fetchedBackupCard.getUuid());
        assertEquals(fetchedBackupCard.getNameLine2(), fetchedUpdatedBackupCard.getNameLine2());
 
        //verify count of products in DB
        long backupCardCount = backupCardRepository.count();
        assertEquals(backupCardCount, 1);
 
        //get all products, list should only have one
        List<BackupCard> products = backupCardRepository.findAll();
        assertEquals(products.size(), 1);
    }
}