/*
 * Copyright 2013 Catalyst IT Services
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.catalyst.sonar.score.util;

import java.util.Date;

/**
 * 
 * @author mwomack
 *
 */
public class DateUtility {

	public DateUtility(){
		
	}
	
	/**
	 * This method returns the number of days between two measure build
	 * dates. Used for trophy awarding.
	 * 
	 * @param goodMeasureDate :the date of the metric measure 
	 * that is above the threshold for the trophy	            
	 * 
	 * @param nextMeasureValueDate:the next metric measure that is 
	 * above the threshold for the  trophy	           
	 *
	 * @returns the number of days between the above to dates
	 * 
	 **/
	public int getDaysBetweenDates(Date goodMeasureDate, Date nextMeasureValueDate) {
	long nextDateAsALong = 0L;
	long goodDateAsALong = 0L;
	
	goodDateAsALong = getDateAsLong(goodMeasureDate);
	nextDateAsALong = getDateAsLong(nextMeasureValueDate);

		// calculate the number of days between the two dates
		final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
		int daysBetweenTwoDates = (int) ((nextDateAsALong - goodDateAsALong) / DAY_IN_MILLIS);
		return daysBetweenTwoDates;
	}

	/**
	 * Converts a date time value from the database into a long value
	 * @param date
	 * @returns a date time value into a long
	 */
	public long getDateAsLong (Date date){
		long dateAsLong = 0l;
		//convert date time to a long
		dateAsLong = date.getTime();
		return dateAsLong;
	}

}
