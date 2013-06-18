package com.catalyst.sonar.score.ui;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class EnhancedListFilterTest {
	EnhancedListFilterWidget enhancedWidget;

	/**
	 * @throws java.lang.Exception
	 * Creates an Enhanced List Filter object
	 */
	@Before
	public void setUp() throws Exception {
		enhancedWidget = new EnhancedListFilterWidget();
	}

	/**
	 * Testing that the Enhanced List Filter widget's id is returned
	 */
	@Test
	public void testGetWidgetId() {
		assertEquals(enhancedWidget.getId(), "enhancedList");
	}

	/**
	 * Testing that the Enhanced List Filter widget's title is returned
	 */
	@Test
	public void testGetWidgetTitle() {
		assertEquals(enhancedWidget.getTitle(), "Enhanced List");

	}

	/**
	 * Testing that the Enhanced Filter List widget's template path is returned
	 */
	@Test
	public void testGetWidgetTemplatePath() {
		assertEquals(enhancedWidget.getTemplatePath(),
				"/score/enhancedList_widget.html.erb");
	}
}
