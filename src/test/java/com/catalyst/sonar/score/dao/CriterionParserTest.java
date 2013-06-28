/**
 * 
 */
package com.catalyst.sonar.score.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static com.catalyst.sonar.score.api.ApiTestConstants.*;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.database.DatabaseSession;
import org.sonar.jpa.dao.MeasuresDao;

/**
 * Test Class for {@link CriterionParser}.
 * 
 * @author JDunn
 */
public class CriterionParserTest {
	
	private static final String METRIC_NAME = "Metric";
	private static final double AMOUNT = 90.0;
	private static final int DAYS = 7;
	private static final String PERIOD = "w";
	private static final String D = ";";
	private static final String CRITERION_STRING = METRIC_NAME + D + AMOUNT + D + DAYS + PERIOD;
	private static final String CRITERION_STRING2 = METRIC_NAME + D + AMOUNT + D + DAYS;
	private static final String METRIC_DAO_FIELD = "metricDao";
	
	private DatabaseSession mockSession;
	private MeasuresDao mockMetricDao;
	private CriterionParser testParser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mockSession = mock(DatabaseSession.class);
		mockMetricDao = mock(MeasuresDao.class);
		testParser = new CriterionParser(mockSession, CRITERION_STRING);
	}

	/**
	 * Test method for {@link CriterionParser#CriterionParser(DatabaseSession, String)}.
	 */
	@Test
	public void testCriterionParserDatabaseSessionString() {
		assertEquals(mockSession, testParser.getSession());
		String[] criterionFields = (String[]) getField(testParser, "fields");
		assertArrayEquals(criterionFields, CRITERION_STRING.split(";"));
		MeasuresDao dao = (MeasuresDao) getField(testParser, METRIC_DAO_FIELD);
		assertEquals(mockSession, dao.getSession());
	}

	/**
	 * Test method for {@link CriterionParser#parse()}.
	 */
	@Test
	public void testParse() {
		setField(testParser, METRIC_DAO_FIELD, mockMetricDao);
		when(mockMetricDao.getMetric(METRIC_NAME)).thenReturn(METRIC_1);
		assertSame(METRIC_1, testParser.parse().getMetric());
		assertEquals(AMOUNT, testParser.parse().getAmount(), 0);
		assertEquals(DAYS * 7, testParser.parse().getDays());
	}

	/**
	 * Test method for {@link CriterionParser#parseMetric()}.
	 */
	@Test
	public void testParseMetric() {
		setField(testParser, METRIC_DAO_FIELD, mockMetricDao);
		when(mockMetricDao.getMetric(METRIC_NAME)).thenReturn(METRIC_1);
		assertSame(METRIC_1, testParser.parseMetric());
	}

	/**
	 * Test method for {@link CriterionParser#parseAmount()}.
	 */
	@Test
	public void testParseAmount() {
		assertEquals(AMOUNT, testParser.parseAmount(), 0);
	}

	/**
	 * Test method for {@link CriterionParser#parseDays()}.
	 */
	@Test
	public void testParseDays() {
		assertEquals(DAYS * 7, testParser.parseDays());
		testParser = new CriterionParser(mockSession, CRITERION_STRING2);
		assertEquals(DAYS, testParser.parseDays());
	}

}
