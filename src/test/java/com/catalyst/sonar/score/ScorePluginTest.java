/**
 * 
 */
package com.catalyst.sonar.score;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.catalyst.sonar.score.ScoreMetrics;
import com.catalyst.sonar.score.ScorePlugin;
import com.catalyst.sonar.score.batch.ScoreDecorator;
import com.catalyst.sonar.score.ui.ScoreRubyWidget;

/**
 * @author JDunn
 *
 */
public class ScorePluginTest {
	
	private ScorePlugin scorePlugin;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		scorePlugin = new ScorePlugin();
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.ScorePlugin#getExtensions()}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetExtensions() {
		assertEquals(scorePlugin.getExtensions(),
				Arrays.asList(
						ScoreMetrics.class,
						ScoreDecorator.class,
						ScoreRubyWidget.class
		));
	}

}
