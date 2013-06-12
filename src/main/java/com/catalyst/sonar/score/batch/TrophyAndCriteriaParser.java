package com.catalyst.sonar.score.batch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sonar.api.config.Settings;
import org.sonar.api.database.configuration.Property;

/**
 * 
 * @author Team Build Meister
 * 
 */

public class TrophyAndCriteriaParser {
	private Property property;
	private Settings settings;
	private static final String GLOBALPROPERTYKEY = "sonar.score.Trophy";
	public static HashMap<String, String> testMap = new HashMap<String, String>();
	// Regular Expressions
	private static String regExpOne = "[\\;%}]";
	private static String regExpTwo = "^[a-zA-Z]+$";
	private static String regExpThree = "^[0-9]+$";
	private static String regExpFour = "^[0-9a-zA-Z]+$";
	private static String regExpFive = "[\\,]";
	private static String regExpSix = "{";

	/**
	 * Gets all the properties and saves them to a hashmap.Then iterates through
	 * the hashmap and finds the value for the global property key for trophy
	 * 
	 * @param settings
	 * @return value
	 */
	public String getGlobalProperty(Settings settings) {
		String key = "";
		String value = "";
		Map<String, String> propertyMap = new HashMap<String, String>();
		propertyMap = settings.getProperties();
		for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			if (key.equalsIgnoreCase(GLOBALPROPERTYKEY)) {
				return value;
			}
		}
		return value;

	}

	/**
	 * Returns a set of trophies with criteria from an array of
	 * trophPropertyStrings
	 * 
	 * @param trophyPropertyStringList
	 * @return
	 */
	public static TrophySet parseTrophies(String trophyPropertyStringList) {
		TrophySet trophies = new TrophySet();
		try {
			// Split the global property string at the ',' to separate between
			// each property
			String[] value = trophyPropertyStringList.split(regExpFive);
			for (String tPropertyString : value) {
				// go through each property and extract the trophy name to
				// trophy
				Trophy trophy = extractTrophyName(tPropertyString);
				int indexNum = tPropertyString.indexOf(regExpSix);
				String criteriaString = tPropertyString.substring(++indexNum);
				// parse the rest of the string and get the criteria
				Criteria criteria = parseCriteria(criteriaString);
				// add the criteria to the trophy
				trophy.addCriteria(criteria);
				// add the trophies to the trophy set
				trophies.add(trophy);
			}
		} catch (IndexOutOfBoundsException ie) {
			ie.printStackTrace();
			System.out.println("Inside IndexOutOfBoundException");
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			System.out.println("InsideNullPointerException");
		}

		return trophies;

	}

	/**
	 * extracts a Trophy name from a global trophy property string
	 * 
	 * @param globalPropertyValue
	 * @return
	 */
	public static Trophy extractTrophyName(String globalPropertyValue) {
		int indexNum = globalPropertyValue.indexOf("{");
		String trophyName = globalPropertyValue.substring(0, indexNum);
		return new Trophy(trophyName);
	}

	/**
	 * parses the string and extracts the criteria from it
	 * 
	 * @param propertyString
	 * @return
	 */
	public static Criteria parseCriteria(String propertyString) {
		String metric = "";
		double requiredAmt = 0;
		int days = 0;
		String value = propertyString.trim();
		try {
			// split the criteria string into an array of strings
			String[] valueArray = value.split(regExpOne);
			for (String string : valueArray) {
				if (hasOnlyLetters(string)) {
					// metric value
					metric = string;
				} else if (hasOnlyNumbers(string)) {
					// required Amt
					requiredAmt = parseInt(string);
				} else if (hasAlphaNumericCharacters(string)) {
					// extract the number from the string
					String numOne = string.substring(0, string.length() - 1);
					String numTwo = string.substring(string.length() - 1);
					try {
						// checks if the string ends with 'd' for number of days
						if (numTwo.equalsIgnoreCase("d")) {
							int number = parseInt(numOne);
							days = number;
						}
						// checks if the string ends with 'w' for number of
						// weeks
						else if (numTwo.equalsIgnoreCase("w")) {
							int secondNum = parseInt(numOne);
							// converts the weeks to days
							secondNum = secondNum * 7;
							days = secondNum;
						}
					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
					}
				}
			}
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		}

		return new Criteria(metric, requiredAmt, days);

	}

	/**
	 * Checks to see if the string contains only letters
	 * 
	 * @param value
	 * @return
	 */
	public static boolean hasOnlyLetters(String value) {
		return value.matches(regExpTwo);
	}

	/**
	 * Checks to see if the string contains only numbers
	 * 
	 * @param value
	 * @return
	 */
	public static boolean hasOnlyNumbers(String value) {
		return value.matches(regExpThree);
	}

	/**
	 * checks to see if the string contains both letters and numbers
	 * 
	 * @param value
	 * @return
	 */
	public static boolean hasAlphaNumericCharacters(String value) {
		return value.matches(regExpFour);
	}

	/**
	 * parses a string to an Integer
	 * 
	 * @param first
	 * @return
	 */
	private static Integer parseInt(String first) {
		return Integer.parseInt(first);
	}

	/**
	 * getter for property
	 * 
	 * @return
	 */
	public Property getProperty() {
		return property;
	}

	/**
	 * setter for property
	 * 
	 * @param property
	 */
	public void setProperty(Property property) {
		this.property = property;
	}

	/**
	 * getter for settings
	 * 
	 * @return
	 */
	public Settings getSettings() {
		return settings;
	}

	/**
	 * setter for settings
	 * 
	 * @param settings
	 */
	public void setSettings(Settings settings) {
		this.settings = settings;
	}

}
