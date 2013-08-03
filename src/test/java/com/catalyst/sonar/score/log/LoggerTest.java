/**
 * 
 */
package com.catalyst.sonar.score.log;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author JDunn
 *
 */
public class LoggerTest {
	
	Logger testLogger;
	public static final MockPrintStream stream = new MockPrintStream();
	
	static void throwException() {
		throw new RuntimeException();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testLogger = new Logger(stream);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.log.Logger#Logger()}.
	 */
	@Test
	public void testLogger() {
		testLogger = new Logger();
		assertSame(System.out, testLogger.getStream());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.log.Logger#log(java.lang.Object)}.
	 */
	@Test
	public void testLogObject() {
		testLogger.log(new Integer(1));
		assertEquals("1\n", stream.getOutput());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.log.Logger#log(java.lang.Exception)}.
	 */
	@Test
	public void testLogException() {
		RuntimeException exception = null;
		try{
			throwException();
		} catch (RuntimeException e) {
			testLogger.log(e);
			exception = e;
		}
		assertTrue(stream.getOutput().contains(exception.toString()));
		for(StackTraceElement element : exception.getStackTrace()) {
			assertTrue(stream.getOutput().contains(element.toString()));
		}
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.log.Logger#log(java.util.Collection)}.
	 */
	@Test
	public void testLogCollection() {
		Collection<String> testCol = new ArrayList<String>();
		testCol.add("Hi");
		testCol.add("Hello");
		testCol.add("Shalom");
		testLogger.log(testCol);
		assertTrue(stream.getOutput().contains("Hi,"));
		assertTrue(stream.getOutput().contains("Hello,"));
		assertTrue(stream.getOutput().contains("Shalom."));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.log.Logger#logEmf(java.lang.Object)}.
	 */
	@Test
	public void testLogEmf() {
		testLogger.logEmf("Hello");
		assertTrue(stream.getOutput().contains("!!! Hello"));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.log.Logger#warn(java.lang.Object)}.
	 */
	@Test
	public void testWarn() {
		testLogger.warn("Hello");
		assertTrue(stream.getOutput().contains("WARNING! Hello"));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.log.Logger#warn(java.lang.Object)}.
	 */
	@Test
	public void testWarnStringArray() {
		String[] strs = {"Hello", "Hi"};
		testLogger.warn(strs);
		assertTrue(stream.getOutput().contains("WARNING! "));
		for(String str : strs) {
			assertTrue(stream.getOutput().contains(str));
		}
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.log.Logger#beginMethod(java.lang.String)}.
	 */
	@Test
	public void testBeginMethod() {
		testLogger.beginMethod("Hi");
		stream.setOutput("");
		testLogger.log("Hi!");
		assertTrue(stream.getOutput().startsWith("\tHi!"));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.log.Logger#endMethod()}.
	 */
	@Test
	public void testEndMethod() {
		testLogger.beginMethod("Hi");
		testLogger.endMethod();
		assertTrue(stream.getOutput().contains("Hi"));
		stream.setOutput("");
		testLogger.log("Hi");
		assertTrue(stream.getOutput().startsWith("Hi"));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.log.Logger#getStream()}.
	 */
	@Test
	public void testGetStream() {
		assertSame(stream, testLogger.getStream());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.log.Logger#setOnStream(java.io.PrintStream)}.
	 */
	@Test
	public void testSetOnStream() {
		testLogger.on();
		testLogger.setOnStream(System.out);
		assertSame(System.out, testLogger.getOnStream());
		assertSame(testLogger.getOnStream(), testLogger.getStream());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.log.Logger#addTab(int)}.
	 */
	@Test
	public void testAddTab() {
		testLogger.addTab(1);
		testLogger.log("Hi");
		assertEquals("\tHi\n", stream.getOutput());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.log.Logger#removeTab(int)}.
	 */
	@Test
	public void testRemoveTab() {
		testLogger.addTab(1);
		testLogger.removeTab(1);
		testLogger.log("Hi");
		assertTrue(stream.getOutput().startsWith("Hi"));
	}
	
	@Test
	public void ensureBeginMethod_false_AndEndMethodWorkTogether() {
		testLogger.beginMethod("FIRST", false);
		testLogger.beginMethod("SECOND", false);
		assertEquals("", stream.getOutput());
		testLogger.log("HI");
		assertEquals("", stream.getOutput());
		testLogger.endMethod();
		testLogger.endMethod();
		assertEquals("", stream.getOutput());
		testLogger.log("HI");
		assertEquals("HI\n", stream.getOutput());
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		stream.setOutput("");
	}

}
