package com.livngroup.gds.aop;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.livngroup.gds.domain.LivnRequestResponseLog;
import com.livngroup.gds.repositories.LivnRequestResponseLogRepository;
import com.livngroup.gds.response.IWexResponse;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

@Aspect
@Component
public class RequestResponseLogger {

	final private Logger requestResponseLogger = LoggerFactory.getLogger("request-response-logger");
	final private Logger commonLogger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LivnRequestResponseLogRepository requestResponseLogRepository;
	
	private static ThreadLocal<StringBuilder> wexInPayload = new ThreadLocal<StringBuilder>();
	private static ThreadLocal<StringBuilder> wexOutPayload = new ThreadLocal<StringBuilder>();
	
	public static void appendWexInPayload(String payload) {
		wexInPayload.get().append(payload);
	}
	
	public static void appendWexOutPayload(String payload) {
		wexOutPayload.get().append(payload);
	}
	
	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public final Object aroundCallingMethod(final ProceedingJoinPoint jp) throws Throwable {

		boolean logEnabled = requestResponseLogger.isTraceEnabled();
		LivnRequestResponseLog requestResponseLog = new LivnRequestResponseLog();
		
		final ObjectMapper JSON_MAPPER = new ObjectMapper();
		
		StopWatch sw = null;

		wexInPayload.set(new StringBuilder());
		wexOutPayload.set(new StringBuilder());
		
		try {
    		// start stop-watch
        	if (logEnabled) {
    			sw = new StopWatch(getClass().getSimpleName());
    			sw.start(jp.getSignature().getName());
    			requestResponseLog.setUrl(joinPointToString(jp));
    			if ((jp.getArgs() != null) && (jp.getArgs().length > 0)) {
                    requestResponseLog.setInputJsonParms(JSON_MAPPER.writeValueAsString(jp.getArgs()));
    			}
    		}
    		// run the real process
    		Object processResult = jp.proceed();
    		// stop stop-watch
    		if (logEnabled) {
    			try {
    	            sw.stop();
    	            boolean success = true;
    	            if (processResult instanceof ResponseEntity<?>) {
    	            	success = !((ResponseEntity<?>) processResult).getStatusCode().is4xxClientError() 
    	            			&& !((ResponseEntity<?>) processResult).getStatusCode().is5xxServerError();
    	            }
    	            if (processResult instanceof IWexResponse<?>) {
    	            	success = ((IWexResponse<?>) processResult).getOk();
    	            }
        			requestResponseLog.setSuccess(success); 
        			requestResponseLog.setExecutionTime(sw.getTotalTimeMillis());
        			requestResponseLog.setInputSoapBody(trimXml(wexInPayload.get().toString()));
        			requestResponseLog.setResultSoap(trimXml(wexOutPayload.get().toString()));
        			requestResponseLog.setResultJson(JSON_MAPPER.writeValueAsString(processResult));
        			requestResponseLogRepository.save(requestResponseLog);
    			} catch (Throwable exc) {
    				commonLogger.error(exc.getMessage(), exc);
    			}
    		}
    		// return the result
    		return processResult;
        } catch (Exception exc) {
    		// stop stop-watch
    		if (logEnabled) {
    			try {
    	            sw.stop();
        			requestResponseLog.setSuccess(false); 
        			requestResponseLog.setExecutionTime(sw.getTotalTimeMillis());
        			requestResponseLog.setInputSoapBody(trimXml(wexInPayload.get().toString()));
        			requestResponseLog.setResultSoap(trimXml(wexOutPayload.get().toString()));
        			requestResponseLog.setResultJson(exc.getMessage());
        			requestResponseLogRepository.save(requestResponseLog);
    			} catch (Throwable excInner) {
    				commonLogger.error(excInner.getMessage(), exc);
    			}
    		}
    		// re-throw the exception
    		throw exc;
        } finally {
    		wexInPayload.set(new StringBuilder());
    		wexOutPayload.set(new StringBuilder());
        }	
	}

	private String trimXml(final String dirtyXml) {
		int openBraket = StringUtils.indexOf(dirtyXml, "<");
		int closeBraket = StringUtils.lastIndexOf(dirtyXml, ">");
		return StringUtils.substring(dirtyXml, openBraket, closeBraket+1);
	}
	
    private String joinPointToString(JoinPoint jp) {
    	String shortJoinPointName = jp.toString();//.replace("com.parrimark.epv.service.", "").replace("com.parrimark.epv.vo.", "").replace("com.priava.", "");
    //	int posPreFunctionName = shortJoinPointName.lastIndexOf("."); 
    //	int posPostFunctionName = shortJoinPointName.indexOf("(", posPreFunctionName);
    //	shortJoinPointName = shortJoinPointName.substring(0, posPreFunctionName+1) + "<i><font color=\"blue\">" + 
    //			shortJoinPointName.substring(posPreFunctionName+1, posPostFunctionName) + "</font></i>" + shortJoinPointName.substring(posPostFunctionName); 
    	return shortJoinPointName;
    }

}



