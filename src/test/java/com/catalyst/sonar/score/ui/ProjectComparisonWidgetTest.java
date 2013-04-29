package com.catalyst.sonar.score.ui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProjectComparisonWidgetTest {
	
	private ProjectComparisonWidget testWidget;

	@Before
	public void setUp() throws Exception {
		testWidget = new ProjectComparisonWidget();
	}

	@Test
	public void testGetId() {
		assertEquals("projectComparison", testWidget.getId());
	}

	@Test
	public void testGetTitle() {
		assertEquals("Project comparison widget", testWidget.getTitle());
	}

	@Test
	public void testGetTemplatePath() {
		assertEquals("/score/projectComparison_widget.html.erb", testWidget.getTemplatePath());
	}

}
