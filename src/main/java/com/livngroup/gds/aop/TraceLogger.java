package com.livngroup.gds.aop;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.livngroup.gds.response.CallResponse;

@Aspect
@Component
public class TraceLogger {

	final protected Logger logger = LoggerFactory.getLogger(this.getClass());
	final static Gson gson = new GsonBuilder().create();

	@AfterReturning(
			pointcut="execution(public * com.livngroup.gds.service.Wex*.*(..))",
			returning="result")
	public void traceWexApiCall(JoinPoint joinPoint, Object result) {
		if(logger.isInfoEnabled() && (result instanceof CallResponse) ) {
			LocalDateTime now = LocalDateTime.now();
			StringBuffer inputArgs = new StringBuffer();
			
			for(Object arg : joinPoint.getArgs()) {
				inputArgs.append(gson.toJson(arg));
			}
			
			String formatTimestamp = "[" + now +"] ";
			CallResponse callResoponse = (CallResponse) result;
			
			logger.info("{} {} Call({}) Result : {}", 
					formatTimestamp, 
					joinPoint.toString(),
					inputArgs.toString(),
					callResoponse == null ? "EMPTY" : gson.toJson(callResoponse));
		}
	}

}
