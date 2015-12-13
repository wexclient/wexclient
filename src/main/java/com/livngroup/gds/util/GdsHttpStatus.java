package com.livngroup.gds.util;

public enum GdsHttpStatus {

	NOT_NUMBER_VALUE(441, "Not number value"), 
	WEX_NOT_RESPONDING(541, "WEX server not responding"), 
	NOT_DEFINED_ERROR(901, "Not defined error");
	
	private final int value;

	private final String reasonPhrase;


	private GdsHttpStatus(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}

	public int value() {
		return this.value;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

}
