package com.livngroup.gds.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class GdsDateUtil {

	private final static DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private final static DateTimeFormatter westernDayFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private final static DateTimeFormatter dayTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private final static DateTimeFormatter dayTimeMilFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
	private final static ZoneId sydneyTimeZone = ZoneId.of("Australia/Sydney");

	public static Calendar getCalendarFromString(String strDate) {
		
		Calendar cDate = Calendar.getInstance();
		
		LocalDate formatDate = LocalDate.parse(strDate, dayFormatter);
		
		Date convertToDate = Date.from(formatDate.atStartOfDay().atZone(sydneyTimeZone).toInstant());
			
		cDate.setTime(convertToDate);
		
		return cDate;
	}
	
	public static ZonedDateTime getSydneyDateTime() {
		LocalDateTime dateTime = LocalDateTime.now();
		ZonedDateTime sydneyDateTime = ZonedDateTime.of(dateTime, sydneyTimeZone);
		
		return sydneyDateTime;
	}

}
