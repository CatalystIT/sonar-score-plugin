/**
 * 
 */
package com.catalyst.sonar.score;

import static com.catalyst.sonar.score.log.Logger.LOG;
import static org.mockito.Mockito.mock;

import java.io.PrintStream;

/**
 * @author JDunn
 *
 */
public class ScoreTest {

	protected static void turnOffLogger() {
		PrintStream mockStream = mock(PrintStream.class);
		LOG.setStream(mockStream);
	}
	
	protected static void turnOnLogger() {
		LOG.setStream(System.out);
	}

}
