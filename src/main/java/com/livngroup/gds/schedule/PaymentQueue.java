package com.livngroup.gds.schedule;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.livngroup.gds.exception.WexAppException;

@Component
public class PaymentQueue {

	private static Logger logger = LoggerFactory.getLogger(PaymentQueue.class); 

	@Autowired
    private MailSender mailSender;
     
    @Autowired
    private SimpleMailMessage templateMessage;

    @Value("${payment-queue.send-email}")
    private boolean sendEmail;
    
    @Value("${payment-queue.email-to}")
    private String emailTo;
    
    private final NumberFormat MONEY_FORMAT = DecimalFormat.getCurrencyInstance();
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyyy hh:mm"); 
    
	@Scheduled(cron="${payment-queue.cron-expression}")
	public void payToSupplier() throws WexAppException {
		Date now = new Date();
		
		// business logic here
		String supplierName = "SUPPLIER 001";
		BigDecimal payedAmount = BigDecimal.valueOf(1000000d);
		
		// send mail
		if (sendEmail) {
	        SimpleMailMessage mailMessage = new SimpleMailMessage(templateMessage);
	        mailMessage.setTo(StringUtils.split(emailTo, ","));
	        mailMessage.setSubject(createSubject(supplierName, payedAmount));
	        mailMessage.setText(createText(supplierName, payedAmount, now));
	        mailSender.send(mailMessage);
		}
		
		logger.debug("scheduller executed: " + now.toString());
	}
	
    private String createSubject(String supplierName, BigDecimal amount) {
    	return String.format("Supplier %s payed with amount %s", 
    			supplierName, 
    			MONEY_FORMAT.format(amount));
    }
	
    private String createText(String supplierName, BigDecimal amount, Date paymentDate) {
    	return String.format("Supplier %s payed with amount %s on %s", 
    			supplierName, 
    			MONEY_FORMAT.format(amount),
    			DATE_FORMAT.format(paymentDate));
    }
    
}
