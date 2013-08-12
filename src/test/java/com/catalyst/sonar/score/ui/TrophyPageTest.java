package com.catalyst.sonar.score.ui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TrophyPageTest {

	TrophyPage trophypage;

	/**
	 * creates an TrophyPage object
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		trophypage = new TrophyPage();

	}

	/**
	 * Testing that the URL of the controller is returned
	 */
	@Test
	public void testGetId() {
		assertEquals(trophypage.getId(), "/awards/index");
	}

	/**
	 * Testing that the Title is returned 
	 */
	@Test
	public void testGetTitle() {
		assertEquals(trophypage.getTitle(), "Awards Page");
	}

}
