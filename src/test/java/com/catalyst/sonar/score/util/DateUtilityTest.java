package com.catalyst.sonar.score.util;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.junit.Test;

public class DateUtilityTest {

	private Date date; 
	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	private long dateAsLong = 1121410800000l;
	private DateUtility dateUtility = new DateUtility();
			
	/**
	 * Asserting that when a date is passed in, that date is returned
	 * as a long
	 */
	@Test
	public void testGetDateAsLong() {
		date = new Date(1121410800000l);
		
		dateUtility = new DateUtility();
		try {
			date = df.parse("07/15/2005");
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		assertEquals(dateUtility.getDateAsLong(date), dateAsLong);
	}

	/**
	 * Testing that when two dates are passed in to the getDaysBetweenDates
	 * in the DateUtility class, that the number of date between those
	 * two dates are returned
	 */
	@Test
	public void testGetDaysBetweenDates (){
		Date goodDate = new Date(1371193200000l);		
		Date nextDate = new Date(1387958400000l);//		
		int daysBetweenTwoDates = 194;
		
		assertEquals(dateUtility.getDaysBetweenDates(goodDate, nextDate), daysBetweenTwoDates);
	}



}
