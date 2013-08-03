/**
 * 
 */
package com.catalyst.sonar.score;

import static com.catalyst.sonar.score.log.Logger.LOG;

/**
 * @author JDunn
 *
 */
public class ScoreTest {

	protected static void turnOffLogger() {
		LOG.on();
	}
	
	protected static void turnOnLogger() {
		LOG.off();
	}

}
