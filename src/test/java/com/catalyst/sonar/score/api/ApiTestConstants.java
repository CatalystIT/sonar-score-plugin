/**
 * 
 */
package com.catalyst.sonar.score.api;

import static org.mockito.Mockito.mock;

import java.lang.reflect.InvocationTargetException;

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
	
	public static final String USER1_EMAIL = "Jn@Smith.com";
	public static final String USER2_EMAIL = "Ma@Smith.com";
	public static final String USER3_EMAIL = "Ja@Smith.com";
	public static final String USER4_EMAIL = "Sa@Smith.com";
	
	public static final String USER1_LOGIN = "JnSmith";
	public static final String USER2_LOGIN = "SaSmith";
	public static final String USER3_LOGIN = "JaSmith";
	public static final String USER4_LOGIN = "SaSmith";

	public static final ScoreUser USER1 = new ScoreUser(USER1_NAME, USER1_EMAIL, USER1_LOGIN);
	public static final ScoreUser USER2 = new ScoreUser(USER2_NAME, USER2_EMAIL, USER2_LOGIN);
	public static final ScoreUser USER3 = new ScoreUser(USER3_NAME, USER3_EMAIL, USER3_LOGIN);
	public static final ScoreUser USER4 = new ScoreUser(USER4_NAME, USER4_EMAIL, USER4_LOGIN);
	
	//  G R O U P S  //
	public static final String GROUP1_NAME = "Group One";
	public static final String GROUP2_NAME = "Group Two";
	
	public static final Group<ScoreUser> GROUP1 = new Group<ScoreUser>(GROUP1_NAME, USER1, USER2);
	public static final Group<ScoreUser> GROUP2 = new Group<ScoreUser>(GROUP2_NAME, USER3, USER4);
	
	public static final Metric MOCK_METRIC_1 = mock(Metric.class);
	public static final Metric MOCK_METRIC_2 = mock(Metric.class);
	public static final Metric METRIC_1 = buildTestMetric("key1", "metric1", Metric.ValueType.INT);
	public static final Metric METRIC_2 = buildTestMetric("key2", "metric2", Metric.ValueType.BOOL);
	public static final Integer HASH_1 = 12345;
	public static final Integer HASH_2 = 67890;
	public static final Metric NULL_METRIC = null;
	public static final double AMOUNT_1 = 11.1;
	public static final double AMOUNT_2 = 22.2;
	public static final int DAYS_1 = 7;
	public static final int DAYS_2 = 14;
	
	public static Metric buildTestMetric(String key, String name, Metric.ValueType type) {
		Metric.Builder builder = new Metric.Builder(key, name, type);
		return buildTestMetric(builder);
	}
	
	
	public static Metric buildTestMetric(Metric.Builder builder) {
		Metric testMetric = null;
		try {
			java.lang.reflect.Constructor<Metric> constructor = Metric.class.getDeclaredConstructor(Metric.Builder.class);
			constructor.setAccessible(true);
			testMetric = constructor.newInstance(builder);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
        return testMetric;
	}

}
