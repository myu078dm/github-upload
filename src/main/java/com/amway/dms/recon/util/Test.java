package com.amway.dms.recon.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static boolean compareWithRegEx(String value, String regExpression, boolean ignoreCase) {
        Pattern p;
        if (ignoreCase) {
            p = Pattern.compile(regExpression, Pattern.CASE_INSENSITIVE);
        } else {
            p = Pattern.compile(regExpression);
        }
        Matcher m = p.matcher(value);
        return m.find();
    }
	
	public static void main(String[] args) {
		String regex = "^\\\\D+$";
		
		System.out.println(compareWithRegEx("KOM", regex, false));
	}
}
