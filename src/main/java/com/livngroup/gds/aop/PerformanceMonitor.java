package com.livngroup.gds.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class PerformanceMonitor {
	
	final private Logger logger = LoggerFactory.getLogger("performance-monitor");

	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public final Object aroundCallingMethod(final ProceedingJoinPoint jp) throws Throwable {
		StopWatch sw = null;
		if (logger.isTraceEnabled()) {
			sw = new StopWatch(getClass().getSimpleName());
		}
        try {
    		if (sw != null) {
    			sw.start(jp.getSignature().getName());
    		}
    		// run the real process
    		return jp.proceed();
        } finally {
    		if (sw != null) {
	            sw.stop();
	            logger.trace(String.format("\"%s\",%s,mills", joinPointToString(jp), sw.getTotalTimeMillis()));
    		}
        }	
    }
	
	private String joinPointToString(JoinPoint jp) {
		return jp.toString().replaceFirst("^execution\\(", "").replace("com.livngroup.gds.", "").replaceFirst("\\)$", "");
	}
}
