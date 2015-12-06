package com.livngroup.gds.aop;

import java.time.LocalDateTime;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponse;

@Aspect
@Component
public class TraceLogger {

	final protected Logger logger = Logger.getLogger(this.getClass());
	
	@AfterReturning(
			pointcut="execution(* com.livngroup.gds.service.WexPurchaseLogService.*(..))",
			returning="result")
	public void truncateToLog(JoinPoint joinPoint, Object result) {
		LocalDateTime now = LocalDateTime.now();
		
		String formatTimestamp = "[" + now +"] ";
		GetPurchaseLogHistoryResponse truncatedMessage = (GetPurchaseLogHistoryResponse)result;
		logger.info(formatTimestamp + "WexPurchaseLogService.getPurchaseLogHistory() Call Result : " + truncatedMessage.getResponseCode());
	}

}
