package com.livngroup.gds.util;

public class Validator {
	public static Boolean isNumber(String in) {
		try { new Integer(in);} catch(Exception e) {return false;}
		return true;
	}

	public static Boolean isBoolean(String in) {
		try { new Boolean(in);} catch(Exception e) {return false;}
		return true;
	}
}
