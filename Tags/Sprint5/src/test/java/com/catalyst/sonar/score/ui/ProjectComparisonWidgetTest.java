package com.catalyst.sonar.score.ui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author mwomack
 *
 */
public class ProjectComparisonWidgetTest {
	
	private ProjectComparisonWidget testWidget;

	/**
	 * @throws java.lang.Exception
	 * creates a ProjectComparisonWidget object
	 */
	@Before
	public void setUp() throws Exception {
		testWidget = new ProjectComparisonWidget();
	}

	/**
	 * Testing that the ProjectComparisonWidget id can be retrieved
	 */
	@Test
	public void testGetId() {
		assertEquals("projectComparison", testWidget.getId());
	}
	/**
	 * Testing that the ProjectComparisonWidget title can be retrieved
	 */
	@Test
	public void testGetTitle() {
		assertEquals("Project comparison widget", testWidget.getTitle());
	}
	/**
	 * Testing that the ProjectComparisonWidget html.erb template path can be retrieved
	 */
	@Test
	public void testGetTemplatePath() {
		assertEquals("/score/projectComparison_widget.html.erb", testWidget.getTemplatePath());
	}

}
