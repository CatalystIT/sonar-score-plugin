/**
 * 
 */
package com.catalyst.sonar.score.dao;

import static com.catalyst.sonar.score.api.ApiTestConstants.getField;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.database.DatabaseSession;

/**
 * Test Class for {@link Parser}.
 * 
 * @author JDunn
 */
public class ParserTest {
	
	private static final String STRING = "I'm a String.";
	
	private class TestParser extends Parser<String> {

		public TestParser(DatabaseSession session, String entityString) {
			super(session, entityString);
		}

		public TestParser(DatabaseSession session, String[] fields) {
			super(session, fields);
		}

		public String parse() {
			return get(0);
		}
		
	}

	private DatabaseSession mockSession;
	private Parser<String> testParser;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mockSession = mock(DatabaseSession.class);
		testParser = new TestParser(mockSession, STRING);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.dao.Parser#Parser(org.sonar.api.database.DatabaseSession, java.lang.String)}.
	 */
	@Test
	public void testParserDatabaseSessionString() {
		assertEquals(STRING, testParser.get(0));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.dao.Parser#get(int)}.
	 */
	@Test
	public void testGet() {
		String[] fields = (String[]) getField(testParser, "fields");
		assertEquals(fields[0], testParser.get(0));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.dao.Parser#parse()}.
	 */
	@Test
	public void testParse() {
		assertEquals(testParser.get(0), testParser.parse());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.dao.Parser#Parser(org.sonar.api.database.DatabaseSession, java.lang.String[])}.
	 */
	@Test
	public void testParserDatabaseSessionStringArray() {
		String[] array = {STRING};
		testParser = new TestParser(mockSession, array);
		String[] fields = (String[]) getField(testParser, "fields");
		assertArrayEquals(array, fields);
	}

}
