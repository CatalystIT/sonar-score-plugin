package com.catalyst.sonar.score.ui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TitleCupWidgetTest {

	/**
	 * Testing the title cup widget class
	 * 
	 * @author mwomack
	 * 
	 */

	TitleCupWidget titleCup;

	/**
	 * @throws java.lang.Exception
	 *             Creates a TitleCupWidget object
	 */
	@Before
	public void setup() throws Exception {
		titleCup = new TitleCupWidget();
	}

	/**
	 * Testing that the TitleCupWidget id can be retrieved
	 */
	@Test
	public void testGetTrophyId() {
		assertEquals(titleCup.getId(), "titlecups");
	}

	/**
	 * Testing that the TitleCupWidget title can be retrieved
	 */

	@Test
	public void testGetTrophyTitle() {
		assertEquals(titleCup.getTitle(), "Title Cups");
	}

	/**
	 * Testing that the TitleCupWidget html.erb template path can be retrieved
	 */
	@Test
	public void testGetTrophyTemplatePath() {
		assertEquals(titleCup.getTemplatePath(),
				"/score/titlecup_widget.html.erb");
	}

}
