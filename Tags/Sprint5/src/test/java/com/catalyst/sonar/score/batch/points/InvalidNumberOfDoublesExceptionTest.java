/**
 * 
 */
package com.catalyst.sonar.score.batch.points;

import static org.junit.Assert.*;
import static com.catalyst.sonar.score.batch.points.InvalidNumberOfDoublesException.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link com.catalyst.sonar.score.batch.points.InvalidNumberOfDoublesException}.
 *
 */
public class InvalidNumberOfDoublesExceptionTest {
	
	InvalidNumberOfDoublesException testException;
	private static final String TEST_STRING = "This is a test.";
	private static final int TEST_PLURAL_INT = 5;
	private static final int TEST_SINGULAR_INT = 1;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testException = new InvalidNumberOfDoublesException(TEST_STRING, TEST_PLURAL_INT);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.InvalidNumberOfDoublesException#InvalidNumberOfDoublesException(java.lang.String, int)}.
	 */
	@Test
	public void testInvalidNumberOfDoublesException() {
		testException = new InvalidNumberOfDoublesException();
		assertEquals(null, testException.getMessage());
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.InvalidNumberOfDoublesException#InvalidNumberOfDoublesException(java.lang.String, int)}.
	 */
	@Test
	public void testInvalidNumberOfDoublesExceptionString() {
		assertEquals(makeDetailMessage(TEST_STRING, TEST_PLURAL_INT), testException.getMessage());
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.InvalidNumberOfDoublesException#makeDetailMessage(java.lang.String, int)}.
	 */
	@Test
	public void testMakeDetailMessage_Plural() {
		String result = makeDetailMessage(TEST_STRING, TEST_PLURAL_INT);
		String message = LINE + LINE + INDIVISIBLE_MESSAGE + LINE
				+ TEST_PLURAL_INT + PLURAL + INDIVISIBLE_EXPLANATION
				+ QUOTE + TEST_STRING + QUOTE + LINE;
		assertTrue(result.contains(PLURAL));
		assertFalse(result.contains(SINGULAR));
		assertEquals(message, result);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.InvalidNumberOfDoublesException#makeDetailMessage(java.lang.String, int)}.
	 */
	@Test
	public void testMakeDetailMessage_Singular() {
		String result = makeDetailMessage(TEST_STRING, TEST_SINGULAR_INT);
		String message = LINE + LINE + INDIVISIBLE_MESSAGE + LINE
				+ TEST_SINGULAR_INT + SINGULAR + INDIVISIBLE_EXPLANATION
				+ QUOTE + TEST_STRING + QUOTE + LINE;
		assertFalse(result.contains(PLURAL));
		assertTrue(result.contains(SINGULAR));
		assertEquals(message, result);
	}

}
