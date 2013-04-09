/**
 * 
 */
package com.catalyst.sonar.score.ui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.catalyst.sonar.reference.ui.ScoreRubyWidget;

/**
 * @author JDunn
 *
 */
public class ScoreRubyWidgetTest {
	
	ScoreRubyWidget widget;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		widget = new ScoreRubyWidget();
	}

	/**
	 * Test method for {@link com.catalyst.sonar.reference.ui.ScoreRubyWidget#getId()}.
	 */
	@Test
	public void testGetId() {
		assertEquals(widget.getId(), "points");
	}

	/**
	 * Test method for {@link com.catalyst.sonar.reference.ui.ScoreRubyWidget#getTitle()}.
	 */
	@Test
	public void testGetTitle() {
		assertEquals(widget.getTitle(), "Points");
	}

	/**
	 * Test method for {@link com.catalyst.sonar.reference.ui.ScoreRubyWidget#getTemplatePath()}.
	 */
	@Test
	public void testGetTemplatePath() {
		assertEquals(widget.getTemplatePath(), "/score/score_widget.html.erb");
	}

}
