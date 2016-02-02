package com.livngroup.gds.aop;

import org.apache.commons.lang3.StringUtils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class WexPayloadTreadlocalAppender extends AppenderBase<ILoggingEvent> {

	@Override
	protected void append(ILoggingEvent eventObject) {
		String message = eventObject.getMessage();
		if (StringUtils.startsWith(message, ">> ")) {
			RequestResponseLogger.appendWexInPayload(cleanUp(message));
		} else {
			RequestResponseLogger.appendWexOutPayload(cleanUp(message));
		}
		
	}
	
	private String cleanUp(final String inString) {
		String outString = StringUtils.trim(inString);
		outString = StringUtils.removeStart(outString, ">> \"");
		outString = StringUtils.removeStart(outString, "<< \"");
		outString = StringUtils.removeEnd(outString, "\"");
		outString = StringUtils.removeEnd(outString, "[\\r][\\n]");
		return outString.trim(); 
	}
}
