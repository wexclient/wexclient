package com.livngroup.gds.aop;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.livngroup.gds.response.CallResponse;

@Aspect
@Component
public class TraceLogger {

	final protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@AfterReturning(
			pointcut="execution(* com.livngroup.gds.service..*(..))",
			returning="result")
	public void tracePurchaseLog(JoinPoint joinPoint, Object result) {
		LocalDateTime now = LocalDateTime.now();
		
		String formatTimestamp = "[" + now +"] ";
		CallResponse callResoponse = (CallResponse)result;
		
		logger.info("{} {} Call Result : {}", formatTimestamp, joinPoint.getTarget(), callResoponse.getMessage());
	}

}
