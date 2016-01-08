package com.livngroup.gds;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventNotifier {

	@Autowired
    private MailSender mailSender;
     
    @Autowired
    private SimpleMailMessage templateMessage;

    @Value("${on-app-event.send-email}")
    private boolean sendEmail;
    
    @Value("${on-app-event.email-to}")
    private String emailTo;
    
    private final static long NOTIFICATION_TRESHOLD = 600000L;
    
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyyy hh:mm:ss"); 

	final private static Logger logger = LoggerFactory.getLogger(ApplicationEventNotifier.class);

	private long startEventNotificationLastSent = 0L;
	private long stopEventNotificationLastSent = 0L;
	
	@EventListener
	public void onContextRefreshedEvent(ContextRefreshedEvent event) {
		logger.info("ContextRefreshedEvent Received");
		long now = new Date().getTime();
		if (sendEmail && (now - startEventNotificationLastSent > NOTIFICATION_TRESHOLD)) {
			sendEmail(String.format("WexClient REST app started at %s", DATE_FORMAT.format(new Date())));
			startEventNotificationLastSent = now;
		}
	}

	@EventListener
	public void onContextClosedEvent(ContextClosedEvent event) {
		logger.info("ContextClosedEvent Received");
		long now = new Date().getTime();
		if (sendEmail && (now - stopEventNotificationLastSent > NOTIFICATION_TRESHOLD)) {
			sendEmail(String.format("WexClient REST app shutted down at %s", DATE_FORMAT.format(new Date())));
			stopEventNotificationLastSent = now;
		}
	}
	
	private void sendEmail(String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage(templateMessage);
        mailMessage.setTo(StringUtils.split(emailTo, ","));
        mailMessage.setSubject(text);
        mailMessage.setText(text);
        mailSender.send(mailMessage);
	}
}