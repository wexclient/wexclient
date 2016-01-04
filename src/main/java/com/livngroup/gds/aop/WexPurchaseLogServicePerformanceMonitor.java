package com.livngroup.gds.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class WexPurchaseLogServicePerformanceMonitor {
	
	@Value("${metrics.wex.performance}")
	private boolean wexPerformanceOn;
	
	@Autowired
	private GaugeService gaugeService;
	
	private static long callsAmount = 0L;
	private static long fastestCall = Long.MAX_VALUE;
	private static long slowestCall = Long.MIN_VALUE;
	private static long totalCallsDuration = 0L;
	
	private static final String MERTICS_WEX_FASTEST_DURATION = "wex.fastest.duration";
	private static final String MERTICS_WEX_SLOWEST_DURATION = "wex.slowest.duration";
	private static final String MERTICS_WEX_AVERAGE_DURATION = "wex.average.duration";
	
	@Around("execution(public * com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.*(..))")
	public final Object aroundCallingMethod(final ProceedingJoinPoint jp) throws Throwable {
		StopWatch sw = null;
		if (wexPerformanceOn) {
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
	            long callDuration = sw.getTotalTimeMillis();
	            callsAmount ++;
	            fastestCall = Math.min(fastestCall, callDuration);
	            slowestCall = Math.max(slowestCall, callDuration);
	            totalCallsDuration += callDuration;
	            
	            gaugeService.submit(MERTICS_WEX_FASTEST_DURATION, fastestCall);
	            gaugeService.submit(MERTICS_WEX_SLOWEST_DURATION, slowestCall);
	            gaugeService.submit(MERTICS_WEX_AVERAGE_DURATION, totalCallsDuration / callsAmount);

    		}
        }	
    }
}
