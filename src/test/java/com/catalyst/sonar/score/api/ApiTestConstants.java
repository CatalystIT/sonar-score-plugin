/**
 * 
 */
package com.catalyst.sonar.score.api;

import static org.mockito.Mockito.mock;

import org.sonar.api.measures.Metric;

/**
 * @author James
 *
 */
public class ApiTestConstants {
	
	//  U S E R S  //
	public static final String USER1_NAME = "John";
	public static final String USER2_NAME = "Mary";
	public static final String USER3_NAME = "James";
	public static final String USER4_NAME = "Sarah";

	public static final ScoreUser USER1 = new ScoreUser(USER1_NAME, "Jn@Smith.com", USER1_NAME);
	public static final ScoreUser USER2 = new ScoreUser(USER2_NAME, "Ma@Smith.com", USER2_NAME);
	public static final ScoreUser USER3 = new ScoreUser(USER3_NAME, "Ja@Smith.com", USER3_NAME);
	public static final ScoreUser USER4 = new ScoreUser(USER4_NAME, "Sa@Smith.com", USER4_NAME);
	
	//  G R O U P S  //
	public static final String GROUP1_NAME = "Group One";
	public static final String GROUP2_NAME = "Group Two";
	
	public static final Group<ScoreUser> GROUP1 = new Group<ScoreUser>(GROUP1_NAME, USER1, USER2);
	public static final Group<ScoreUser> GROUP2 = new Group<ScoreUser>(GROUP2_NAME, USER3, USER4);
	
	public static final Metric MOCK_METRIC_1 = mock(Metric.class);
	public static final Metric MOCK_METRIC_2 = mock(Metric.class);
	public static final double AMOUNT_1 = 11.1;
	public static final double AMOUNT_2 = 22.2;
	public static final int DAYS_1 = 7;
	public static final int DAYS_2 = 14;

}
