package com.livngroup.gds.aop;

import java.time.LocalDateTime;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.livngroup.gds.response.CallResponse;

@Aspect
@Component
public class TraceLogger {

	final protected Logger logger = Logger.getLogger(this.getClass());
	
	@AfterReturning(
			pointcut="execution(* com.livngroup.gds.service..*(..))",
			returning="result")
	public void tracePurchaseLog(JoinPoint joinPoint, Object result) {
		LocalDateTime now = LocalDateTime.now();
		
		String formatTimestamp = "[" + now +"] ";
		CallResponse callResoponse = (CallResponse)result;
		
		logger.info(formatTimestamp + "WexPurchaseLogService" + joinPoint.getSourceLocation() + " Call Result : " + callResoponse.getMessage());
	}

}
