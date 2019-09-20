package com.amway.dms.recon.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;



public class DateUtil
{
    private static final String AMWAY_DATE_SHOW_MILLIS = "dd-MMM-yyyy HH:mm:ss SSS";
    private static final String DB_DATE_FRMT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_DAY_MONTH_YEAR = "dd/MM/yyyy";
    private static final String DATE_MONTH_YEAR = "MM/yyyy";
    private static final String TIME_ONLY = "hh:mm a z";
    private static final String AMWAY_DATE_FORMAT = "dd-MMM-yyyy";
    private static final String DEFAULT_DATE_FORMATYYYY = "MM/dd/yyyy";
    private static final String DATE8_FORMAT = "yyyyMMdd";
    private static final char EMPTY_CHAR = ' ';
    private static final String DAY ="dd";
    private static final String ALPHA_MONTH ="mmm";
    private static final String NUM_MONTH ="mm";
    private static final String YEAR ="yyyy";
    private static final SimpleDateFormat amwayDateFormatter = new SimpleDateFormat(AMWAY_DATE_FORMAT);
    private static final SimpleDateFormat defaultDateFormatterYYYY = new SimpleDateFormat(DEFAULT_DATE_FORMATYYYY);
    private static final SimpleDateFormat date8Formatter = new SimpleDateFormat(DATE8_FORMAT);
    
    /**
     * Returns the elapsed time in hours, minutes & seconds for a time-based
     * function
     */
    public static String getElapsedTime(long start, long end) throws Exception
    {
        long timeInSeconds = (end - start) / 1000; // convert to seconds
        long hours, minutes, seconds;
        hours = timeInSeconds / 3600;
        timeInSeconds = timeInSeconds - (hours * 3600);
        minutes = timeInSeconds / 60;
        timeInSeconds = timeInSeconds - (minutes * 60);
        seconds = timeInSeconds;
        StringBuffer returnText = new StringBuffer();
        if (hours > 0)
        {
            returnText.append(hours);
            if (1 == hours)
            {
                returnText.append(" hour ");
            }
            else
            {
                returnText.append(" hours ");
            }
        }
        if (minutes > 0)
        {
            returnText.append(minutes);
            if (1 == minutes)
            {
                returnText.append(" minute ");
            }
            else
            {
                returnText.append(" minutes ");
            }
        }
        if (seconds > 0)
        {
            returnText.append(seconds);
            if (1 == seconds)
            {
                returnText.append(" second ");
            }
            else
            {
                returnText.append(" seconds ");
            }
        }
        return returnText.toString().trim();
    }

    public static final String getTimeDisplayMillis(long dt)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(AMWAY_DATE_SHOW_MILLIS);
        return sdf.format(new Date(dt));
    }

    public static final String getFormattedDateTime(long dt, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(dt));
    }

    public static final String getTimeDisplay(long dt)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_ONLY);
        return sdf.format(new Date(dt));
    }

    public static final String formatDayMonthYear(Date theDate)
    {
        if (theDate != null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_DAY_MONTH_YEAR);
            return sdf.format(theDate);
        }
        else
        {
            return null;
        }
    }

    public static final String formatMonthYear(Date theDate)
    {
        if (theDate != null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_MONTH_YEAR);
            return sdf.format(theDate);
        }
        else
        {
            return null;
        }
    }

    public static final String formatMonthDateYear(Date theDate)
    {
        if (theDate != null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMATYYYY);
            return sdf.format(theDate);
        }
        else
        {
            return null;
        }
    }
    /*
     * This method converts a date format string retrieved from the database
     * into a form more easily usable by javascript. It first has the symbol
     * that separates month, day, and year, and then has the order in which the
     * day, month, and year come, represented by the order in which the letters
     * D, M, and Y come. Also, the M is lowercase if the month is to be stored
     * in letter form and capital if the month is to be stored in number form.
     */

    public static String convertDateFormatToJavascriptVersion(String origFormat)
    {

        StringBuffer returnValue = new StringBuffer();

        StringBuffer origFormatStrippedOfLetters = new StringBuffer();

        for (int i = 0; i < origFormat.length(); i++)
        {

            if (!Character.isLetter(origFormat.charAt(i)))
            {
                origFormatStrippedOfLetters.append(origFormat.charAt(i));
            }

        }
        String stripChar = origFormatStrippedOfLetters.charAt(0)+"";
        if(stripChar != null && stripChar.trim().equals("")) {
        	stripChar = "/";
        }
        returnValue.append(stripChar);
        String origFormatCaps = origFormat.toUpperCase();
        StringBuffer origFormatStrippedOfNonLetters = new StringBuffer();

        for (int i = 0; i < origFormatCaps.length(); i++)
        {
            if (Character.isLetter(origFormatCaps.charAt(i)))
            {
                origFormatStrippedOfNonLetters.append(origFormatCaps.charAt(i));
            }
        }

        boolean foundDays = false;
        boolean foundMonths = false;
        boolean decidingMonths = false;
        boolean foundYears = false;

        char currentChar;

        for (int i = 0; i < origFormatStrippedOfNonLetters.length(); i++)
        {

            currentChar = origFormatStrippedOfNonLetters.charAt(i);

            switch (currentChar)
            {

                case 'D':
                    if (foundDays)
                        continue;
                    else
                    {
                        foundDays = true;
                        returnValue.append('D');
                        continue;
                    }
                case 'M':
                    if (foundMonths)
                    {
                        if (decidingMonths)
                        {
                            returnValue.append('M');
                            decidingMonths = false;
                        }
                        continue;
                    }
                    else
                    {
                        foundMonths = true;
                        decidingMonths = true;
                        continue;
                    }
                case 'O':
                    decidingMonths = false;
                    returnValue.append('m');
                    continue;
                case 'Y':
                    if (foundYears)
                        continue;
                    else
                    {
                        foundYears = true;
                        returnValue.append('Y');
                        continue;
                    }

            }

        }

        return returnValue.toString();
    }

    /**
     * Converts date string to popup Calender
     * 
     * @param orig String
     * @return String
     */
    public static String convertDateFormatForPopUpCalendar(String orig)
    {
    	
        StringBuffer returnValue = new StringBuffer();
        char separator = orig.charAt(0);
        
        if(!StringUtil.isNullOrBlank(orig))
        {
	        // format orig.charAt(1)
	        returnValue = formatToDateString(orig.charAt(1), separator,DAY, ALPHA_MONTH, NUM_MONTH, YEAR, returnValue);
	        // format orig.charAt(2)
	        returnValue = formatToDateString(orig.charAt(2), separator, DAY, ALPHA_MONTH, NUM_MONTH, YEAR, returnValue);
	        // format orig.charAt(3)
	        returnValue = formatToDateString(orig.charAt(3),EMPTY_CHAR, DAY, ALPHA_MONTH, NUM_MONTH, YEAR, returnValue);
        }
        
        return returnValue.toString();

    }

   /**
    * 
    * @param ch
    * @param separator
    * @param day
    * @param alpMonth
    * @param numMonth
    * @param year
    * @param sb
    * @return StringBuffer
    */
    public static StringBuffer formatToDateString(char ch, char separator, String day, String alpMonth, String numMonth, String year, StringBuffer sb)
    {
        switch (ch)
        {
            case 'D':
                sb.append(day);                
                break;
            case 'm':
                sb.append(alpMonth);                
                break;
            case 'M':
                sb.append(numMonth);                
                break;
            case 'Y':
                sb.append(year);                
                break;
            default:
                return null;
        }
        
        if(separator != EMPTY_CHAR) 
             sb.append(separator);
        
        return sb;
    }

	/**
	 * Return the 'new' Amway Date Formatted Date dd-mon-yyyy as a java.util.Date
	 */
	public static final Date getAmwayFormatDateStringNullToDefault(String st)
	{
        try
		{
			if (null == st || st.equals(""))
			{
				st = "01-Jan-1900";
			}
            Date amwayDate = amwayDateFormatter.parse(st);
			return amwayDate;
		}
		catch (ParseException pe)
		{
            return null;
		}
	}
	
	/**
	 * Return the 'new' Amway Date Formatted Date dd-mon-yyyy as a java.util.Date
	 */
	public static final Date getDefaultDateFormat(String st)
	{
        try
		{
			if (null == st || st.equals(""))
			{
				st = "01-Jan-1900";
			}
            Date amwayDate = defaultDateFormatterYYYY.parse(st);
			return amwayDate;
		}
		catch (ParseException pe)
		{
            return null;
		}
	}
		
	public static final Date getFormat8DateStringNullToDefault(String st)
    {
        try
        {
            if (null == st || st.equals("") || st.equals("00000000") || st.equals("00000101"))
            {
                Date tbp8Date = getAmwayFormatDateStringNullToDefault("01-Jan-1900");
                return tbp8Date;
            }
            Date date8 = date8Formatter.parse(st);
            return date8;
        }
        catch (ParseException pe)
        {
            Date date8 = getAmwayFormatDateStringNullToDefault("01-Jan-1900");
            return date8;
        }
    }
	
	/**
	 * Return the 'new' Amway Date Formatted Date dd-mon-yyyy as a String
	 * If default date is 01-Jan-1900, return ""
	 */
	public static final String getFormattedDate(Date dt, String formatStr)
	{
		if (null == dt) { return ""; }
		if (compareDates(dt, getDefaultDate()) == null) { return ""; }
		formatStr = formatStr.replace('m', 'M');
		formatStr = formatStr.replace('Y', 'y');
		SimpleDateFormat dateFormatter = new SimpleDateFormat(formatStr);
		return dateFormatter.format(dt);
	}
	
	/**
	 * Gets the default Date value 
	 * @return Date - the default date
	 */
	public static Date getDefaultDate()
	{
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(1900, 00, 01);
		return cal.getTime();
	}
	
	/**
	 * Checks whether the date equals the default date (which is set
	 * when for empty columns by default).
	 * @param d - The date to use
	 * @return Date - the date or null (if default date equals the date)
	 */
	public static Date compareDates(Date d, Date d2)
	{
		Calendar date = Calendar.getInstance();
		if (date == null || d == null || d2 == null)
		{
			return null;
		}
		date.setTime(d);
		return (!date.getTime().equals(d2)) ? d : null;
	}
	
	public static boolean equalsOrBothNull(Date d1, Date d2) {
		return (d1 == null && d2 == null) || (d1 != null && d1.compareTo(d2) == 0);
	}
	/**
	 * 
	 * @param fdate
	 * @param uiObject
	 * @return
	 */
//	public static final String getDateAndTime(String fdate,UIDataObject uiObject)
//	{
//		String date = "";
//		try
//		{
//			DateFormat df = new SimpleDateFormat(uiObject.getDateFormat());
//			Date ddd = df.parse(fdate);				
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(ddd);
//			cal.set(Calendar.HOUR_OF_DAY,0);
//			cal.set(Calendar.SECOND,01);
//			//cal.set(Calendar.AM_PM,Calendar.AM);
//			ddd = cal.getTime();				
//			df = new SimpleDateFormat(uiObject.getDataTimeFormat());
//			date = df.format(ddd);
//		}
//		catch (ParseException pe)
//		{
//			date = "";
//		}
//
//		return date;
//	}
	
	/**
	 * Returns the elapsed time in days, hours, minutes & seconds for a time-based
	 * function
	 */
	public static String getFormattedElapsedTimeDHMS(Date currentTime, Date startupTime) throws Exception
	{
		long elapsedSeconds = (currentTime.getTime() - startupTime.getTime())/1000;

		String elapsedTime = null;

		if(elapsedSeconds < 60) 
		{
			elapsedTime = Long.toString(elapsedSeconds)+" seconds";
		}
		else
			if((elapsedSeconds >60) && (elapsedSeconds <3600))
			{
				elapsedTime = Long.toString(elapsedSeconds/60)+" minutes";
			}
			else
				if((elapsedSeconds >3600) && (elapsedSeconds<86400))
				{
					elapsedTime = Long.toString(elapsedSeconds/3600)+" hours";
				}
				else
					if(elapsedSeconds>=86400)
					{
						long numDays = elapsedSeconds/86400;
						String elapsedDays = Long.toString(numDays)+" days";
						long numExtraSeconds = elapsedSeconds%86400;
						long numHours = numExtraSeconds/3600;
						if(numHours>=1)
						{
							String elapsedHours = Long.toString(numHours)+ " hours";
							elapsedTime = elapsedDays+", "+elapsedHours;
						}
						else
						{
							elapsedTime = elapsedDays;
						}
					}
		return elapsedTime.trim();
	}
/**
 * Formats a period into the output format passed into the method.  outputFormat
 * must be a format string that SimpleDateFormat can use.  Period must be in yyyymm 
 * format.
 * @param period
 * @param outputFormant
 * @return
 */
//	public static String formatPeriod(String period, String outputFormat) throws ParseException{
//		SimpleDateFormat periodIn = new SimpleDateFormat(SystemConstants.PERIOD_FORMAT);
//		SimpleDateFormat periodOut = new SimpleDateFormat(outputFormat);
//		
//		try{
//			Date workDate = periodIn.parse(period);
//			return periodOut.format(workDate);
//		}catch(ParseException ex){
//			throw ex;
//		}
//	}

	public static long getTimestampMillis(Timestamp ts) {
		// The following code was disabled as we have found that with the new
		// release
		// the getTime is now calculating the millis correctly. This caused
		// problems
		// when we were trying to match dates and getting millis that were
		// doubled. C.G. FEB 10, 2005.

		// long dateTime = ts.getTime();
		// dateTime += (ts.getNanos() / 1000000);
		// logger.debug("Timestamp time: " + ts.getTime() + " + " +
		// ts.getNanos() +
		// " / 1000000 = " + dateTime);
		return ts.getTime();
	}

	/**
	 * Takes a Timestamp date/time and returns it as a java.util.Date with the
	 * seperate nano field from Timestamp, converted to milliseconds and added
	 * to the total. Without using this method Timestamp will 'lose' it's
	 * millisecond value (it will be zeroed out).
	 * 
	 * Using getTime() in Timestamp will return an improper value as that method
	 * is inherited off java.util.Date but it is NOT aware of the seperate nanos
	 * field in Timestamp.
	 * 
	 * This method should be used instead of getTime().
	 * 
	 * Example usage: Timestamp ts = new Timestamp(); Date foo =
	 * DateUtil.getTimestampDate(ts);
	 * 
	 * @param ts
	 *            - The Timestamp value
	 * @return Date - java.util.Date value
	 * @see http://java.sun.com/j2se/1.3/docs/api/java/sql/Timestamp.html
	 */
	public static Date getTimestampDate(Timestamp ts) {
		return new Date(DateUtil.getTimestampMillis(ts));
	}

    /***
     * Method will take the input date in MMM DD, YYYY format and will return
     * the time stamp with current time
     * 
     * @param inputDate
     * @return
     */
    public static Timestamp getTimeStampWithInputDate(String inputDate) {
        if (inputDate != null && inputDate.length() > 0) {
            Timestamp inputDateTimeStamp = new Timestamp(new java.util.Date(inputDate).getTime());
            Timestamp currentTimeStamp = new Timestamp(new java.util.Date().getTime());
            currentTimeStamp.setDate(inputDateTimeStamp.getDate());
            currentTimeStamp.setMonth(inputDateTimeStamp.getMonth());
            currentTimeStamp.setYear(inputDateTimeStamp.getYear());
            currentTimeStamp.setDate(inputDateTimeStamp.getDate());
            return currentTimeStamp;
        }
        return null;

    }

    /***
     * Method will take the input date in MMM DD, YYYY format and will return
     * the time stamp with end time of the day: 23:59:59
     * 
     * @param inputDate
     * @return
     */
    public static Timestamp getLastTimeStampWithInputDate(String inputDate) {
        if (inputDate != null && inputDate.length() > 0) {
            Timestamp inputDateTimeStamp = new Timestamp(new java.util.Date(inputDate).getTime());
            inputDateTimeStamp.setHours(23);
            inputDateTimeStamp.setMinutes(59);
            inputDateTimeStamp.setSeconds(59);
            return inputDateTimeStamp;
        }
        return null;

    }
    /***
     * Method will take the input java.util.date and will return
     * the time stamp with end time of the day: 23:59:59
     * 
     * @param inputDate
     * @return
     */
    public static Timestamp getLastTimeStampWithInputDate(java.util.Date inputDate) {
        if (inputDate != null ) {
            Timestamp inputDateTimeStamp = new Timestamp(inputDate.getTime());
            inputDateTimeStamp.setHours(23);
            inputDateTimeStamp.setMinutes(59);
            inputDateTimeStamp.setSeconds(59);
            return inputDateTimeStamp;
        }
        return null;

    }
    
    /***
     * Method will take the input date in MMM DD, YYYY format and will return
     * the time stamp with start time of day: 00:00:00
     * 
     * @param inputDate
     * @return
     */
    public static Timestamp getFirstTimeStampWithInputDate(String inputDate) {
        if (inputDate != null && inputDate.length() > 0) {
            Timestamp inputDateTimeStamp = new Timestamp(new java.util.Date(inputDate).getTime());
            inputDateTimeStamp.setHours(00);
            inputDateTimeStamp.setMinutes(00);
            inputDateTimeStamp.setSeconds(00);
            return inputDateTimeStamp;
        }
        return null;

    }
    
    /***
     * Method will take the input java.util.date and will return the time stamp
     * with start time of day: 00:00:00
     * 
     * @param inputDate
     * @return
     */
    public static Timestamp getFirstTimeStampWithInputDate(java.util.Date inputDate) {
        if (inputDate != null ) {
            Timestamp inputDateTimeStamp = new Timestamp(inputDate.getTime());
            inputDateTimeStamp.setHours(00);
            inputDateTimeStamp.setMinutes(00);
            inputDateTimeStamp.setSeconds(00);
            return inputDateTimeStamp;
        }
        return null;

    }

    public static Timestamp getTimeStampWithInputDate(java.util.Date inputDate) {
        if (inputDate != null) {
            Timestamp inputDateTimeStamp = new Timestamp(inputDate.getTime());
            Timestamp newTimeStamp = new Timestamp(new Date().getTime());
            inputDateTimeStamp.setHours(newTimeStamp.getHours());
            inputDateTimeStamp.setMinutes(newTimeStamp.getMinutes());
            inputDateTimeStamp.setSeconds(newTimeStamp.getSeconds());
            return inputDateTimeStamp;
        }
        return null;

    }

    public static Date convertStringToDate(String dateString) {
        Date dateObj = null;
        try {
            DateFormat df = new SimpleDateFormat(DB_DATE_FRMT);
            dateObj = df.parse(dateString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dateObj;
    }

    public static java.sql.Date convertFileDateToDBDate(int orig) throws IllegalArgumentException {

        if (orig <= 0) {
            return null;
        }
        int century = (orig / 1000000) + 19;
        int year = orig % 1000000 / 10000;
        int month = orig % 10000 / 100;
        if (month == 0 || month > 12) {
            return null;
        }
        int day = orig % 100;
        // If Day is Invalid in Date then taking the last date of the month
        if (day > 31) {
            Calendar c = GregorianCalendar.getInstance();
            c.set(Calendar.MONTH, month - 1);
            day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        } else if (day < 1) {
            Calendar c = GregorianCalendar.getInstance();
            c.set(Calendar.MONTH, month - 1);
            day = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        }

        String toBeConverted = StringUtil.fixLength(century, 2) + StringUtil.fixLength(year, 2) + "-"
                + StringUtil.fixLength(month, 2) + "-" + StringUtil.fixLength(day, 2);
        // System.out.println(">>>>toBeConverted>>>>>" + toBeConverted);
        java.sql.Date returnValue = java.sql.Date.valueOf(toBeConverted);

        return returnValue;
    }

    public static Timestamp convertFileTimestampToDBTimeStamp(int dateValue, int timeValue)
            throws IllegalArgumentException {
        if (dateValue <= 0) {
            return null;
        }
        int century = (dateValue / 1000000) + 19;
        int year = dateValue % 1000000 / 10000;
        int month = dateValue % 10000 / 100;
        if (month == 0) {
            return null;
        }
        int day = dateValue % 100;
        Calendar c = GregorianCalendar.getInstance();
        c.set(Calendar.MONTH, month - 1);

        // If Day is Invalid in Date then taking the last date of the month
        if (day > 31) {
            day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        } else if (day < 1) {
            day = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        }

        int seconds = timeValue % 100;
        int minutes = timeValue % 10000 / 100;
        int hours24 = timeValue % 1000000 / 10000;

        String toBeConverted = StringUtil.fixLength(century, 2) + StringUtil.fixLength(year, 2) + "-"
                + StringUtil.fixLength(month, 2) + "-" + StringUtil.fixLength(day, 2) + " "
                + StringUtil.fixLength(hours24, 2) + ":" + StringUtil.fixLength(minutes, 2) + ":"
                + StringUtil.fixLength(seconds, 2);
        // System.out.println(">>>>toBeConverted>>>>>" + toBeConverted);
        Timestamp returnValue = Timestamp.valueOf(toBeConverted);

        return returnValue;
    }

    public static Timestamp convertStringToTimeStamp(String strDate, String format) {

        if (StringUtil.isNullOrBlank(strDate)) {
            return null;
        }
        try {
            DateFormat formatter = new SimpleDateFormat(format);
            Date date = formatter.parse(strDate);
            java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
            return timeStampDate;
        } catch (ParseException e) {
            return null;
        }
    }


    public static Date getZeroTimeDate(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();

    }


    public static final String getFormattedDateTime(Date dt, String formatStr) {
        if (null == dt) {
            return "";
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat(formatStr);
        return dateFormatter.format(dt);
    }

    public static Date getConvertDateByZone(Date date, String fromTimeZoneId, String toTimeZoneId) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        TimeZone fromTimeZone = TimeZone.getTimeZone(fromTimeZoneId);
        TimeZone toTimeZone = TimeZone.getTimeZone(toTimeZoneId);

        cal.setTimeZone(fromTimeZone);
        cal.add(Calendar.MILLISECOND, fromTimeZone.getRawOffset() * -1);
        if (fromTimeZone.inDaylightTime(cal.getTime())) {
            cal.add(Calendar.MILLISECOND, cal.getTimeZone().getDSTSavings() * -1);
        }

        cal.add(Calendar.MILLISECOND, toTimeZone.getRawOffset());
        if (toTimeZone.inDaylightTime(cal.getTime())) {
            cal.add(Calendar.MILLISECOND, toTimeZone.getDSTSavings());
        }
        return cal.getTime();

    }

    public static Date addDayStartTime(Date date) {

        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            return cal.getTime();
        } else {
            return null;
        }
    }

    public static Date addDayEndTime(Date date) {

        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            return cal.getTime();
        } else {
            return null;
        }
    }

    public static java.sql.Date convertToSQLDate(String dateStr) {

        if (StringUtil.isNullOrBlank(dateStr) || dateStr.length() != 8) {
            return null;
        }
        int year = Integer.parseInt(dateStr.substring(0, 4));
        int month = Integer.parseInt(dateStr.substring(4, 6));
        int day = Integer.parseInt(dateStr.substring(6, 8));

        if (month == 0 || month > 12) {
            return null;
        }
        // If Day is Invalid in Date then taking the last date of the month
        if (day > 31) {
            Calendar c = GregorianCalendar.getInstance();
            c.set(Calendar.MONTH, month - 1);
            day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        } else if (day < 1) {
            Calendar c = GregorianCalendar.getInstance();
            c.set(Calendar.MONTH, month - 1);
            day = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        }

        String toBeConverted = StringUtil.fixLength(year, 4) + "-"
                + StringUtil.fixLength(month, 2) + "-" + StringUtil.fixLength(day, 2);
        // System.out.println(">>>>toBeConverted>>>>>" + toBeConverted);
        java.sql.Date returnValue = java.sql.Date.valueOf(toBeConverted);

        return returnValue;
    }

    public static Date addMonthsToDate(Date inputDate, int numOfMonths) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(inputDate.getTime());
        cal.add(Calendar.MONTH, numOfMonths);
        return new Timestamp(cal.getTime().getTime());
    }

    public static java.util.Date parseDate(String date) throws ParseException {

        SimpleDateFormat sdfrmt = new SimpleDateFormat("MMddyyyy");
        sdfrmt.setLenient(false);
        java.util.Date javaDate = null;
        try {
            javaDate = sdfrmt.parse(date);
        }
        catch (ParseException e) {
            return null;
        }
        return javaDate;
    }

    public static Date parseDate(String dateStr, String format) {
        Date date = null;
        if (StringUtil.isNotNull(dateStr)) {
            DateFormat sdf = new SimpleDateFormat(format);
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e) {
            }
        }
        return date;
    }

    public static Date addDays(Date date, int days) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, days);
            return calendar.getTime();
        }
        return date;
    }

    public static String formatDate(Date date, String format) {
        String dateStr = null;
        if (date != null) {
            DateFormat sdf = new SimpleDateFormat(format);
            dateStr = sdf.format(date);
        }
        return dateStr;
    }

    public static Date parseMonthYearToDate(String monthYear) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MMyyyy");
        sdf.setLenient(false);
        return sdf.parse(monthYear);

    }

    public static boolean isValidMonthYear(String monthYear) {
        try {
            DateUtil.parseMonthYearToDate(monthYear);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static long getDaysDifference(Date d1, Date d2) throws IllegalArgumentException {
        if (d1 == null || d2 == null) {
            throw new IllegalArgumentException();
        }
        return TimeUnit.MILLISECONDS.toDays(d1.getTime() - d2.getTime());
    }

    /**
     * 
     * @param dateStr
     * @return
     */
//    public static Date parseISO8601Date(String dateStr) {
//
//        Date parsedDate = null;
//        if (StringUtil.isNotNull(dateStr) && dateStr.length() >= 20) {
//            // Added below check as date is being received as
//            // "=2017-05-28T00:00:00 02:00"
//            // when user send "=2017-05-28T00:00:00+02:00"
//            if (dateStr.contains(" ")) {
//                dateStr = dateStr.replace(" ", "+");
//            }
//            DateFormat formatter = new SimpleDateFormat(Constants.TIME_ZONE_FORMAT_ISO_8601);
//            formatter.setLenient(false);
//            try {
//                parsedDate = formatter.parse(dateStr);
//                return parsedDate;
//            } catch (ParseException e) {
//                // Do Nothing
//            }
//        }
//        return parsedDate;
//    }

    public static Timestamp convertDateToTimestamp(Date inputDate) {
        if (inputDate != null) {
            return new Timestamp(inputDate.getTime());
        }
        return null;
    }

    public static LocalDateTime getLocalDate(String dateStr, String formatStr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatStr, Locale.ENGLISH);
        return LocalDateTime.parse(dateStr, formatter);

    }


    public static final String formatMonthYear(Date theDate, String format) {
        if (theDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(theDate);
        } else {
            return null;
        }
    }

    public static String getLastMonthDateAndYear() {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-YYYY");

            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, -1); // one year back

            return dateFormat.format(c.getTime());

        } catch (Exception exp) {
            return null;
        }
    }

    /*
     * add calendar unit amounts to existing Date
     */
    public static Date addToDate(Date date, int calenderField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The Date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calenderField, amount);
        return c.getTime();
    }



    public static java.util.Date convertAS400DateTime(int date, int time) {
        Timestamp dbProcDtm = DateUtil.convertFileTimestampToDBTimeStamp(date, time);
        if (dbProcDtm != null) {
            return DateUtil.getTimestampDate(dbProcDtm);
        }
        return null;
    }

    public static String convertDate8format(Date date) {
        String amwayDateStr;
        try {
            amwayDateStr = date8Formatter.format(date);
        } catch (Exception e) {
            amwayDateStr = "00000000";
        }

        return amwayDateStr;
    }

    public static String convertDBDateToFileDate(Date date) throws IllegalArgumentException {

        if (date == null) {
            return "0000000";
        }

        String as400Date = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String yearStr = String.valueOf(year);
        String monthStr = String.valueOf(month);
        String dayStr = String.valueOf(day);
        // String
        if (yearStr.startsWith("20")) {
            as400Date = "1";
        }

        yearStr = yearStr.substring(2, 4);

        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }

        if (dayStr.length() == 1) {
            dayStr = "0" + 1;
        }
        as400Date = as400Date + yearStr + monthStr + dayStr;

        if (as400Date.length() == 6) {
            as400Date = "0" + as400Date;
        }

        return as400Date;

    }

    public static void main(String[] args) throws ParseException {

        // System.out.println(convertFileDateToDBDate(1171207).toString());

        // int as400Date = 1160718;
        // int as400Date = 721029;

        System.out.println(convertAS400DateTime(1140810, 70001));

        // int as400Date = 0;
        // String dateStr = convertFileDateToDBDate(as400Date) != null ?
        // convertFileDateToDBDate(as400Date).toString()
        // : null;
        // System.out.println("Date from AS400_Date to DB_Date: " + dateStr);
        // Date date = dateStr != null ? new
        // SimpleDateFormat("yyyy-MM-dd").parse(dateStr) : null;
        // System.out.println("Date from DB_Date to AS400_Date: " +
        // convertDBDateToFileDate(date));

    }

}

