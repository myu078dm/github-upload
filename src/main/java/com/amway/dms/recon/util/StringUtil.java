package com.amway.dms.recon.util;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil
{
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

	// Get system-independent newline char String (\r\n for Win, \n for UNIX,
	// etc)
	private static final String NL = System.getProperty("line.separator");

	/**
	 * Compare two strings.
	 * 
	 * @returns boolean true if they are equal, and neither is null;
	 */
	public static final boolean equals(String str1, String str2)
	{
		if (str1 == null || str2 == null)
			return false;
		return str1.equals(str2);
	}

	/**
	 * Compare two strings - returns true if both null
	 */
	public static final boolean equalsOrBothNull(String str1, String str2) {
		return StringUtil.notNullTrim(str1).equals(StringUtil.notNullTrim(str2));
	}

	/**
	 * Very similar to equals() function, but provides antecedent for
	 * convenience.
	 */
	public static final boolean notEqual(String str1, String str2)
	{
		if (str1 == null || str2 == null)
			return false;
		return !(str1.equals(str2));
	}

	/**
	 * Convert problem symbols single quote, double quote, less-than symbol,
	 * greater-than symbol, and ampersand to Safe representation in HTML.
	 * 
	 * @param in
	 *            The initial string.
	 * @return <{String}>modified with HTML safe characters
	 */
	public static String toSafeHTML(String in)
	{
		if (in == null || "".equals(in))
			return "";
		int bufLength = in.length() + 16;
		StringBuffer out = new StringBuffer(bufLength);
		for (int i = 0; i < in.length(); i++)
		{
			char c = in.charAt(i);
			if (c == '\'')
			{
				out.append("&#39;");
			}
			else if (c == '\"')
			{
				out.append("&#34;");
			}
			else if (c == '<')
			{
				out.append("&lt;");
			}
			else if (c == '>')
			{
				out.append("&gt;");
			}
			else if (c == '&')
			{
				out.append("&amp;");
			}
			else
			{
				out.append(c);
			}
		}
		in = trim(out.toString());
		return in;
	}

	/**
	 * Convert a String to an int, if possible, else return the default value.
	 */
	public static int getInt(String s, int defaultIntValue)
	{
		if (s == null || s.trim().equals(""))
			return defaultIntValue;
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			logger.error("Error trying to convert String '" + s + " to int type.  Returning default value.  " + e);
//			logger.error(StackTrace.getStackTrace(e).toString());
		}
		return defaultIntValue;
	}

	public static int getInt(String s)
	{
		return Integer.parseInt(s);
	}

	/**
	 * Convert an Integer to an int, if possible, else return the default value.
	 */
	public static int getInt(Integer i, int defaultIntValue)
	{
		if (i == null)
			return defaultIntValue;
		try
		{
			return i.intValue();
		}
		catch (Throwable e)
		{
			logger.error("Error trying to convert Integer '" + i + " to int type.  Returning default value.  " + e);
		}
		return defaultIntValue;
	}

	// Trim the String of whitespace on both ends in a
	//  NullPointerException-safe way
	public static final String trim(String s)
	{
		if (null == s)
		{
			return null;
		}
		else
		{
			s = s.trim();
			if (0 == s.length() || "".equals(s))
			{
				return null;
			}
			else
			{
				return s;
			}
		}
	}

	/**
	 * @param s
	 *            The string to use
	 *
	 * @return String. If null  (" "), else return parm (S)
	 *
	 **/
	public static String notNullSpace(String s) {
		if (s == null || s.equals("")) {
			return " ";
		} else {
			return s;
		}
	}

	/**
	 * Trims a String if it is not null. Returns an empty String if it is null.
	 */
	public static final String notNullTrim(String s)
	{
		s = StringUtil.notNull(s);
		return s.trim();
	}

	/**
	 * 
	 * @param o
	 * @return String
	 */
	public static String notNullTrim(Object o)
	{
		String s = StringUtil.notNull(o);
		return s.trim();
	}

	/**
	 * Adds a '0' r '00' in front of the affiliate number if it is null.
	 */
	public static String affThree(String a)
	{
		if (a == null)
		{
			return "";
		}
		else if (a.length() == 2)
		{
			a = "0" + a;
		}
		else if (a.length() == 1)
		{
			a = "00" + a;
		}
		return a;
	}

	public static String affThree(int a)
	{
		return affThree(String.valueOf(a));
	}    

	public static String affThree(long a)
	{
		return affThree(String.valueOf(a));
	}    

	/**
	 * Convert a String to a boolean, if possible, else return the default
	 * value.
	 */
	public static boolean getBool(String s, boolean defaultBoolValue)
	{
		if (s == null)
			return defaultBoolValue;
		try
		{
			//Boolean booleanObject = new Boolean(s);
			return Boolean.valueOf(s);
		}
		catch (Exception e)
		{
			logger.error("Error trying to convert String '" + s + " to boolean type.  Returning default value." + e);
//			logger.error(StackTrace.getStackTrace(e).toString());
		}
		return defaultBoolValue;
	}

	/**
	 * @param s
	 *            The string to use
	 * @return String
	 */
	public static String notNull(String s)
	{
		return (s != null) ? s : "";
	}

	/**
	 * @param s
	 *            The string to use
	 * @param d
	 *            The default string if null
	 * @return String. If null or empty (''), return first parm (S), else parm (D)
	 *
	 **/
	public static String notNullEmpty(String s, String d) {
		if (s == null || s.equals("")) {
			return d;
		} else {
			return s;
		}
	}

	/**
	 * Return true if not null, and not blank either!
	 */
	public static boolean notBlank(String s)
	{
		if ((s != null) && (!s.equals("")))
		{
			return true;
		}
		return false;
	}

	/**
	 * @param s
	 *            The string to use
	 * @return String
	 */
	public static String notNull(Object o)
	{
		return (o != null) ? o.toString() : "";
	}

	public static boolean isNotNull(String s)
	{
		if ((s != null) && (!"".equals(s.trim())))
			return true;
		return false;
	}

	/**
	 * @param s
	 *            The string to use
	 * @param d
	 *            The default string if null
	 * @return String
	 */
	public static String notNull(String s, String d)
	{
		return (s != null) ? s : d;
	}

	/**
	 * @param i
	 *            The initial string
	 * @param s
	 *            The search string
	 * @param n
	 *            The new string
	 * @return <{String}>
	 */
	public static String replace(String i, String s, String n)
	{
		if (i == null || s == null || n == null)
			return "";
		StringBuffer tmp = new StringBuffer(i);
		int subLength = s.length();
		boolean found = true;
		int prevpos = 0;
		while (found)
		{
			int pos = tmp.toString().indexOf(s, prevpos);
			if (pos < 0)
			{
				found = false;
			}
			else
			{
				tmp.replace(pos, pos + subLength, n);
				prevpos = pos + subLength + 1;
			}
		}
		return tmp.toString();
	}

	public static String maxString(String s, int maxLength)
	{
		if ((s != null) && (s.length() >= maxLength))
		{
			return s.substring(0, maxLength);
		}
		return s;
	}

	/**
	 * Returns the first n chars if they exist
	 */
	public static String firstNChars(String s, int n)
	{
		if ((s != null) && (s.length() > n))
		{
			return s.substring(0, n);
		}
		else
		{
			return s;
		}
	}

	/**
	 * Wrapper method. Prints up to 'x' lines of the stack trace.
	 */
//	public static String formatStackTrace(Throwable t)
//	{
//		int NUM_LINES_TO_PRINT = 2; // configurable?
//		return StringUtil.formatStackTrace(t, NUM_LINES_TO_PRINT);
//	}

	/**
	 * Return a formatted String representing a prettier version of this
	 * Throwable's stack trace. Only return 'numLinesToPrint' of the stack
	 * trace. TTD: We should also examine whether it might be better/more
	 * helpful/ accurate to do a .toString() on the throwable, instead of just a
	 * .getMessage(), which seems to provide less info.
	 */
//	public static String formatStackTrace(Throwable t, int numLinesToPrint)
//	{
//
//		// How much to indent the stack trace
//		String INDENT = "\t\t\t\t\t";
//
//		// Create a return reference
//		String retString = ""; // init just to be safe
//
//		// Do some safety checking
//		if (t == null)
//			return "(null Throwable argument)";
//
//		// Get the message, first
//		retString = t.getMessage();
//
//		// Create a StackTrace object to do the dirty work
//		StackTrace st = new StackTrace(t);
//
//		// See how big the stacktrack is
//		int numLines = st.getStackDepth();
//
//		// Grab up to the first 'numLinesToPrint' lines
//		for (int i = 0; (i < numLines) && (i < numLinesToPrint); i++)
//		{
//			retString += NL + INDENT + st.getStackLine(i);
//		}
//
//		// Return the formatted Message + StackTrace snippet
//		return retString;
//	}

	/**
	 * Get the output of the 'toString()' method of this Throwable, and replace
	 * all of the newlines by newlines and an indent, so our debug output will
	 * be easier to read. This method does *not* provide a Stack Trace of the
	 * argument.
	 */
	public static String formatToString(Throwable t)
	{

		// How much to indent the stack trace
		String INDENT = "\t\t\t\t\t";

		// Create a return reference
		String retString = ""; // init just to be safe

		// Do some safety checking
		if (t == null)
			return "(null Throwable argument)";

		// Get the message
		retString = t.getMessage();

		// Search the message for newlines, and replace each newline
		//  sequence with a newline and the 'INDENT'
		return retString;

	}

	public static final boolean isNullOrBlank(String s)
	{
		if (s == null)
			return true;
		if (s.trim().equals(""))
			return true;
		return false;
	}

	/**
	 * Generic method to make a comma-delimited-list in a String.
	 */
	public static String makeCDL(List listIn)
	{
		if (listIn == null)
		{
			return "";
		}
		Iterator iter = listIn.iterator();
		String str = "";
		while (iter.hasNext())
		{
			str += (String) iter.next();
			if (iter.hasNext())
			{
				str += ",";
			}
		}
		return str;
	}

	/**
	 * Method to convert a List in a comma-delimited-String. Use withQuote = true if need comma-delimited-string in single quote.
	 * @param awards
	 * @return comma-delimited-String
	 */
	public static String makeCDL(List<? extends Object> listIn,boolean withQuote)
	{
        if (null == listIn || listIn.size() <= 0) {
            return "";
        }
        Iterator iter = listIn.iterator();
        String str = "";
        while (iter.hasNext()) {
            if (withQuote) {
                str += "'" + iter.next() + "'";
                if (iter.hasNext()) {
                    str += ", ";
                }
            } else {
                str += iter.next();
                if (iter.hasNext()) {
                    str += ", ";
                }
            }
        }
        return str;
    }

    public static String makeCDLString(List<String> valueList, boolean withQuote) {
        if (valueList == null || valueList.isEmpty()) {
            return "";
        }
        return valueList.stream().map(s -> (withQuote) ? "'" + s + "'" : s).collect(Collectors.joining(","));
    }

	/**
	 * Convert comma-delimited-String to a quote/comma-delimited-String
	 */
	public static String makeQuoteCommaDeLimited(String input){
		List wrkList = makeList(input);
		String returnValue = makeCDL(wrkList,true);

		return returnValue;
	}
	/**
	 * This method chops whitespace from elements!!!
	 */
	public static final List<String> makeList(String commaDelimitedString)
	{
		// Go home early?
		if (commaDelimitedString == null || "".equals(commaDelimitedString.trim()))
			return new ArrayList<String>();

		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(commaDelimitedString, ",");
		while (st.hasMoreTokens())
		{
			String token = st.nextToken();
			list.add(token);
		}
		return list;
	}

	/**
	 * @param str
	 *            The string to use
	 * @param maxlength
	 *            is the length of str
	 * @return str with padded spaces to the max length
	 */
	public static String padString(String str, int maxLength)
	{
		return padString(str, maxLength, " ");
	}

	/**
	 * Bret: uses PadString method but pads with something other then spaces
	 * 
	 * @param str
	 *            The string to use
	 * @param maxlength
	 *            is the length of str
	 * @return str with padded spaces to the max length
	 */
	public static String padString(String str, int maxlength, String padChar)
	{
		StringBuffer data = null;
		if (null == str)
		{
			str = "";
		}
		if (null == padChar)
		{
			padChar = " ";
		}
		int size = str.length();
		if (size < maxlength)
		{
			data = new StringBuffer(str);
			for (int i = size; i < maxlength; i++)
			{
				data.append(padChar);
			}
			return data.toString();
		}
		else
		{
			return str;
		}
	}

	public static String frontFillString(String str, int maxLength)
	{
		return frontFillString(str, maxLength, " ");
	}

	// Put fillCharacter on the front of a string to get it to a max length
	public static String frontFillString(String str, int maxLength, String fillChar)
	{
		StringBuffer data = null;
		if (null == str)
		{
			str = "";
		}
		if (null == fillChar)
		{
			fillChar = " ";
		}
		int size = str.length();
		if (size < maxLength)
		{
			data = new StringBuffer();
			for (int i = size; i < maxLength; i++)
			{
				data.append(fillChar);
			}
			data.append(str);
			return data.toString();
		}
		else
		{
			return str;
		}
	}

	public static String centerFillString(String str, int maxLength)
	{
		return centerFillString(str, maxLength, " ");
	}

	public static String centerFillString(String str, int maxLength, String fillChar)
	{
		StringBuffer data = null;
		if (null == str)
		{
			str = "";
		}
		if (null == fillChar)
		{
			fillChar = " ";
		}
		int size = str.length();
		int diff = maxLength - size;
		if (diff > 0)
		{
			data = new StringBuffer();
			int firstHalf = 0;
			//int secondHalf = 1;
			if (diff / 2 >= 1)
			{
				firstHalf = diff / 2;
				//secondHalf = (diff / 2) + (diff % 2);
			}
			for (int i = 0; i < firstHalf; i++)
			{
				data.append(fillChar);
			}
			data.append(str);
			for (int i = size + firstHalf; i < maxLength; i++)
			{
				data.append(fillChar);
			}
			return data.toString();
		}
		else
		{
			return str;
		}
	}

	/**
	 * @param str
	 *            The string to use
	 * @param maxlength
	 *            is the length of str
	 * @return str with padded zeroes to the max length
	 */
	public static String padZero(String str, int maxlength)
	{
		if (str == null)
		{
			str = "";
		}

		int size = str.length();
		String pad = "";
		StringBuffer mystr = new StringBuffer();
		if (size < maxlength)
		{
			for (int i = size; i < maxlength; i++)
			{
				pad = pad + "0";
			} // end for
		} //end if
		//this will add zeroes to the front of str
		mystr.append(pad);
		mystr.append(str);
		return (mystr.toString());

	} // padZero class

	/**
	 * @param input
	 *            The string/number to use
	 * @param maxlength
	 *            is the length of str
	 * @return str with padded spaces to the max length
	 */
	public static String padHTMLString(long input, int maxlength)
	{
		String str = Long.toString(input);
		int size = str.length();
		if (size < maxlength)
		{
			for (int i = size; i < maxlength; i++)
			{
				str = str + "&nbsp;";
			}
		}
		return str;
	}

	public static String toString(BigDecimal bd)
	{

		if (bd != null)
		{
			return bd.toString();
		}
		else
		{
			return "";
		}
	}

	public static final String getListAsString(List objectList)
	{
		return getListAsString(objectList, false);
	}

	public static final String getListAsStringNL(List objectList)
	{
		return getListAsString(objectList, true);
	}

	public static final String getListAsString(List objectList, boolean withNewLines)
	{
		if (objectList == null)
			return null;
		String s = "";
		Iterator iter = objectList.iterator();
		while (iter.hasNext())
		{
			if (withNewLines)
			{
				s += iter.next().toString() + "\n";
			}
			else
			{
				s += iter.next().toString();
			}
		}
		return s;
	}

	/**
	 * Convert a String to a long, if possible, else return the default value.
	 */
	public static long getLong(String s, long defaultValue)
	{
		if (s == null)
			return defaultValue;
		try
		{
			return Long.parseLong(s);
		}
		catch (NumberFormatException e)
		{
			System.err.println("Error trying to convert String '" + s + " to long type.  Returning default value.  " + e);
		}
		return defaultValue;
	}

	/**
	 * Simple String-matching utility method.
	 */
	public static boolean contains(String s, String fragment)
	{
		if ((s == null) || (fragment == null))
			return false;

		int index = s.indexOf(fragment);
		if (index > -1)
			return true;
		return false;
	}

	/*
	 * @author AIUV958 this method written by Tom Mellema, AIUV958, 08-30-06
	 * This method fixes the length of the given string so that its length
	 * equals the int you give it. If the string is shorter than the specified
	 * int, then this method pads the string with spaces. If the string is
	 * longer than the specified int, then the method uses substring. If you
	 * give it a null for the string, expect to see exceptions getting thrown
	 * left and right.
	 */
	public static String fixLength(String orig, int newLength)
	{
		if (orig.length() < newLength)
		{
			return padString(orig, newLength);
		}
		else
		{
			return orig.substring(0, newLength);
		}
	}

	/*
	 * @author AIUV958 this method written by Tom Mellema, AIUV958, 09-18-06
	 * This method returns a string based on the integer you give it. This
	 * String will be of the length specified, and it will be padded on the left
	 * with zeroes if necessary. However, if the given length is less than the
	 * length of the integer at its shortest, this method will return null.
	 */

	public static String fixLength(int original, int len)
	{

		String intString = original + "";
		if (intString.length() > len)
		{
			return null;
		}
		else if (intString.length() == len)
		{
			return intString;
		}
		else
		{
			while (intString.length() < len)
			{
				intString = "0" + intString;
			}
		}

		return intString;
	}

	/**
	 * Change the String to Initial Capital letters 
	 * @param inputString
	 * @param ignoreOriginalCase
	 * @return
	 */
	public static String toInitCaps(String inputString, boolean ignoreOriginalCase) {

		StringBuilder result = new StringBuilder();
		if (inputString != null && inputString.length() > 0) {
			for (String ini : inputString.split(" ")) {
				if (result.length() > 0) {
					result.append(" ");
				}
				if(ini.trim().length() > 0){
					if(ignoreOriginalCase){
						result.append(ini.substring(0, 1).toUpperCase()).append(ini.substring(1, ini.length()).toLowerCase());
					}else{
						result.append(ini.substring(0, 1).toUpperCase()).append(ini.substring(1, ini.length()));
					}
				}
			}
		}
		return result.toString();
	}

	/**
	 * Builds Comma separated String (with single quotes) to pass SQL Query In parameter  
	 * like VOL_TYPE in ('010','011','012'); 
	 * @param typeCds
	 * @return
	 */
	public static String toSQLSafeString(List<String> typeCds)
	{
		StringBuffer str = new StringBuffer();
		for (String typeCd :typeCds){
			str.append("'"+typeCd+"',");
		}	
		return ((str.length()> 0)? str.substring(0, str.length()-1): "");
	}

	public static boolean isNumeric(String s){
		boolean isNumeric = false;
		if(s != null && s.trim().length() > 0){
			try{
                Double.parseDouble(s.trim());
				isNumeric = true;
			}
			catch(Exception ex){
				isNumeric = false;
			}
		}    	
		return isNumeric;
	}

    public static boolean isNumericBigDecimal(String s) {
        boolean isNumeric = false;
        if (s != null && s.trim().length() > 0) {
            try {
                BigDecimal num = new BigDecimal(s.trim());
                isNumeric = true;
            } catch (Exception ex) {
                isNumeric = false;
            }
        }
        return isNumeric;
    }

	public static boolean isNotNullStringArray(String [] obj){
		if( obj !=null && obj.length >0){
			return true;
		}else{
			return false;
		}

	}
	public static final String modifyStringToFitResolution(String inputString, int position)
	{
		String output = "";

		for (int i=0; i<inputString.length(); i++) 
		{
			if (i>0 && i%position == 0)
				output += "<br>";

			output += inputString.charAt(i);
		} 	 

		return output;
	}

	public static String clearBlankSpaces(String input) {
		if (input == null) {
			return "";
		}
		return input.replaceAll("&nbsp;", "");
	}

    public static String removeAllCharsFromString(String inputStr) {
        if (inputStr == null) {
            return "";
        }

        return inputStr.replaceAll("[^0-9]", "");
    }

    public static String removeSpecialCharsFromString(String inputStr) {
        if (inputStr == null) {
            return "";
        }

        return inputStr.replaceAll("[^a-zA-Z0-9]", "");
    }

    public static boolean isCharYorN(String input) {
        boolean flag = false;

        if (!isNullOrBlank(input)) {
            if ("Y".equalsIgnoreCase(input) || "N".equalsIgnoreCase(input)) {
                flag = true;
            }
        }

        return flag;

    }

    /*
     * validate email address
     */

//    public static boolean isValidEmailAddress(String emailAddress, String ecommTypCd) {
//
//        if (!isNullOrBlank(emailAddress)) {
//            if (null == ecommTypCd || Constants.ECOMM_TYP_EMAIL.equalsIgnoreCase(notNullTrim(ecommTypCd))) {
//            /*
//             * String EMAIL_PATTERN =
//             * "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*[@]" +
//             * "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; isValid =
//             * emailAddress.matches(EMAIL_PATTERN);
//             */
//            Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
//                    Pattern.CASE_INSENSITIVE);
//            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailAddress);
//            return matcher.find();
//            } else {
//                return true;
//            }
//        }
//        return false;
//    }

    public static String padTwoChar(String month) {
        if (month == null || month.equalsIgnoreCase("")) {
            return "";
        } else if (month.length() == 2) {
            return month;
        } else if (month.length() == 1) {
            month = "0" + month;
        }
        return month;
    }

    public static boolean isBlankOrNotNull(String s) {
        boolean isflag = false;
        if (s != null) {
            isflag = true;
        }
        return isflag;
    }

    /**
     * It masks the given string with * and show only last length characters.
     * 
     * @param info is input to apply mask
     * @param length is number of last character want to show
     * @return 
     */
//    public static String maskNumber(String info, int length) {
//        if (isNotNull(info) && info.length() > length) {
//            int start = 0;
//            int end = info.length() - length;
//            String overlay = StringUtils.repeat("*", end - start);
//            return StringUtils.overlay(info, overlay, start, end);
//        } else {
//            return notNull(info);
//        }
//    }

    /**
     * check special char excluding white space
     */
    public static boolean isSpecialChar(String str) {
        boolean isSpecialChar = false;
        if (str != null) {
        Pattern p = Pattern.compile("[^A-Za-z0-9\\s]");
        Matcher m = p.matcher(str);
            isSpecialChar = m.find();
        }
        return isSpecialChar;
    }

    /**
     * remove consecutive special characters
     */
    public static String removeConsecutivetSpChars(String input) {
        if (input != null) {
            for (int i = 0; i < input.length(); i++) {

                if (isSpecialChar(input.charAt(i) + "")) {
                    if ((i + 1 < input.length()) && isSpecialChar(input.charAt(i + 1) + "")) {
                        input = input.replace(input.charAt(i + 1) + "", "");
                        input = input.replace(input.charAt(i) + "", "");
                    }

                }

            }
            input = input.replace("+ ", "");
        }
        return input;
    }

    /**
     * Returns the last n chars if they exist
     */
    public static String lastNChars(String s, int n) {
        if ((s != null) && (s.length() > n)) {
            return s.substring(s.length() - n, s.length());
        } else {
            return s;
        }
    }

    /*
     * replace special chars including white spaces
     */
    public static String replaceSpecialChars(String inputString) {
        String resultString = null;
        if (inputString != null && inputString.length() > 0) {
            resultString = inputString.replaceAll("[^A-Za-z0-9]", "");
        }
        return resultString;
    }

    /**
     * Returns the first/last n chars if they exist
     */
    public static String truncateAuditUser(String s, int n) {
        return lastNChars(s, n);
    }

    /**
     * check if long
     * 
     * @param s
     * @return
     */
    public static boolean isLong(String s) {
        boolean isLong = false;
        if (s != null && s.trim().length() > 0) {
            try {
                Long.parseLong(s.trim());
                isLong = true;
            } catch (Exception ex) {
                isLong = false;
            }
        }
        return isLong;
    }

    /*
     * Utility to generate bind variables for SQL injection for a list
     */
    public static String generateBindVariableString(List<?> objList) {

        StringBuffer str = new StringBuffer();
        if (objList == null || objList.isEmpty())
            return str.toString();

        for (Object s : objList) {
            str.append("?" + ",");
        }
        return ((str.length() > 0) ? str.substring(0, str.length() - 1) : "");

    }

    /*
     * Utility to generate bind variables for SQL injection for a list
     */
    public static String genBindVarStringForCommaSeparatedStr(String commaSeparatedStr) {

        StringBuffer str = new StringBuffer();
        if (StringUtil.isNullOrBlank(commaSeparatedStr))
            return str.toString();

        List<String> aboList = Arrays.asList(commaSeparatedStr.split(","));

        for (String s : aboList) {
            str.append("?" + ",");
        }
        return ((str.length() > 0) ? str.substring(0, str.length() - 1) : "");

    }

    public static String removeCharsFromPhone(String phoneStr) {
        if (StringUtil.isNotNull(phoneStr)) {
            try {
                phoneStr = phoneStr.replaceAll("[^0-9]", "");
                Long.parseLong(phoneStr.trim());
            } catch (NumberFormatException ex) {
                phoneStr = null;
            }
        } else {
            phoneStr = null;
        }
        return phoneStr;
    }

    /**
     * Trims a String if it is not null or not blank.
     */
    public static final String isNotNullTrim(String s) {
        if (null == s || "".equals(s.trim())) {
            return null;
        } else {
            return s.trim();
        }
    }

    public static final int convertInt(String s) {
        int value;
        if (null != s && !"".equals(s.trim())) {
            try {
                value = Integer.parseInt(s.trim());
            } catch (NumberFormatException ex) {
                value = 0;
            }
        } else {
            value = 0;
        }
        return value;
    }

    public static final boolean convertToBoolean(String s) {
        boolean value;
        if (null != s) {
            value = "Y".equals(s.trim()) ? true : false;
        } else {
            value = false;
        }
        return value;
    }

    public static final String removeLastPeriod(String val){
        if (StringUtil.isNotNull(val) && val.endsWith(".")) {
            return val.substring(0, val.length() - 1);
        }else {
            return val;
        }        
    }

    public static final boolean isBlankorZero(String s) {
        if (s == null || "".equals(s.trim()) || "0".equals(s.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isImage(String imageFileName) {
        //String image_pattern = "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp))$)";
        String image_pattern = "(.*)*.+\\.(png|jpg|gif|bmp|jpeg|JPEG|PNG|JPG|GIF|BMP)$";
        Pattern pattern = Pattern.compile(image_pattern);
        Matcher matcher = pattern.matcher(imageFileName);
        return matcher.matches();
    }

    /*
     * Utility to generate bind variables for SQL injection for a list
     */
    public static String generateBindVarString(List<?> objList) {

        StringBuilder str = new StringBuilder();
        if (objList == null || objList.isEmpty())
            return str.toString();
        str.append("(");
        for (Object s : objList) {
            str.append("(" + "?" + "," + "?" + ")" + ",");
        }
        String str1 = (str.length() > 0) ? str.substring(0, str.length() - 1) : "";
        str1.concat(")");
        return ((str.length() > 0) ? str.substring(0, str.length() - 1) : "");

    }
	
	/*
     * Utility to generate bind variables for SQL injection for a list
     */
    public static String generateBindVarString(int size) {

        StringBuilder str = new StringBuilder();

        str.append("(");
        for (int i = 0; i < size; i++) {
            str.append("?" + ",");
        }
        String str1 = (str.length() > 0) ? str.substring(0, str.length() - 1) : "";

        return str1.concat(")");

    }

    public static final long convertToLong(String s) {
        long value;
        if (null != s && !"".equals(s.trim())) {
            try {
                value = Long.parseLong(s.trim());
            } catch (NumberFormatException ex) {
                value = 0;
            }
        } else {
            value = 0;
        }
        return value;
    }

    public static void main(String[] args) {
        System.out.println(" isImage = " + isImage("werwer.test!@#$%^&*()   .JPEG"));
        String s = null;
        System.out.println(clearLeadingZeros(s));
    }

    /**
     * 
     * @param s
     * @param size
     * @return
     */
    public static String[] splitByNumber(String s, int size) {
        if (s == null || size <= 0)
            return null;
        int chunks = s.length() / size + ((s.length() % size > 0) ? 1 : 0);
        String[] arr = new String[chunks];
        for (int i = 0, j = 0, l = s.length(); i < l; i += size, j++)
            arr[j] = s.substring(i, Math.min(l, i + size));
        return arr;

    }

    public static String unaccent(String src) {
        return Normalizer.normalize(src, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public static String clearLeadingZeros(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceFirst("^0+(?!$)", "");
    }

    public static boolean isFile(String fileName){
        String filepattern = "(.*)*.+\\.(xlsx|pdf)$";
        Pattern pattern = Pattern.compile(filepattern);
        Matcher matcher = pattern.matcher(fileName);
        return matcher.matches();
    }

    public static String removeSpace(String str) {
        return str.replaceAll(" ", "");
    }

}
