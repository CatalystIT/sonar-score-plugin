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
package com.catalyst.sonar.score.batch.points;

import org.sonar.api.BatchExtension;

/**
 * InvalidNumberOfDoublesException extends IllegalArgumentException, and has a special constructor
 * {@link com.catalyst.sonar.score.batch.points.InvalidNumberOfDoublesException#makeDetailMessage(java.lang.String, int)},
 * which generates an applicable detail message to aid in debugging.  It is designed to be used by
 * {@link com.catalyst.sonar.score.batch.points.MetricBracketsParser} and is thrown by
 * {@link com.catalyst.sonar.score.batch.points.MetricBracketsParser#parseDoubles()}.
 *
 */
public class InvalidNumberOfDoublesException extends IllegalArgumentException implements BatchExtension {
	
	protected static final String INDIVISIBLE_MESSAGE =
			"The number of doubles parsed from the metricBracketsString is not divisible by " + MetricBracketsParser.DIVISOR + ".";
	protected static final String SINGULAR = " double was ";
	protected static final String PLURAL = " doubles were ";
	protected static final String INDIVISIBLE_EXPLANATION = "found in the metricBracketsString: ";
	protected static final String QUOTE = "\"";
	protected static final String LINE = "\n";
	
	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls super().
	 */
	public InvalidNumberOfDoublesException() {
		super();
	}
	
	/**
	 * Constructs a InvalidNumberOfDoublesException with a detailMessage containing the metricBracketsString and the numberOfDoubles.
	 * @param metricBracketsString
	 * @param numberOfDoubles
	 */
	public InvalidNumberOfDoublesException(String metricBracketsString, int numberOfDoubles) {		
		super(makeDetailMessage(metricBracketsString, numberOfDoubles));
	}
	
	/**
	 * Builds a detailMessage containing the metricBracketsString and the numberOfDoubles.
	 * @param metricBracketsString
	 * @param numberOfDoubles
	 * @return
	 */
	protected static String makeDetailMessage(String metricBracketsString, int numberOfDoubles) {
		String number = (numberOfDoubles == 1) ? SINGULAR : PLURAL;
		return LINE + LINE + INDIVISIBLE_MESSAGE + LINE
				+ numberOfDoubles + number + INDIVISIBLE_EXPLANATION
				+ QUOTE + metricBracketsString + QUOTE + LINE;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3852925032801521188L;

}
