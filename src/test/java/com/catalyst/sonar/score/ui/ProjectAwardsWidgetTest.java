package com.catalyst.sonar.score.ui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProjectAwardsWidgetTest {
	private ProjectAwardsWidget testWidget;
	/**
	 * @throws java.lang.Exception
	 * creates a ProjectComparisonWidget object
	 */
	@Before
	public void setUp() throws Exception {
		testWidget = new ProjectAwardsWidget();
	}
	/**
	 * Testing that the ProjectAwardsWidget id can be retrieved
	 */
	@Test
	public void testGetId() {
		assertEquals("projectAwards", testWidget.getId());
	}
	/**
	 * Testing that the ProjectAwardsWidget title can be retrieved
	 */
	@Test
	public void testGetTitle() {
		assertEquals("Project Awards", testWidget.getTitle());
	}
	/**
	 * Testing that the ProjectAwardsWidget html.erb template path can be retrieved
	 */
	@Test
	public void testGetTemplatePath() {
		assertEquals("/score/project_awards_widget.html.erb", testWidget.getTemplatePath());
	}


}
