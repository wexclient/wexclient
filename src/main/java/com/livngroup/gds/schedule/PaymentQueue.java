package com.livngroup.gds.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.livngroup.gds.exception.WexException;

@Component
public class PaymentQueue {

	private static Logger logger = Logger.getLogger(PaymentQueue.class); 
	
	@Scheduled(cron="${payment-queue-cron-expression}")
	public void payToSupplier() throws WexException {
		Date now = new Date();
		
		// business logic here
		
		logger.debug("scheduller executed: " + now.toString());
	}
	
}
