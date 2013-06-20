package com.catalyst.sonar.score.ui;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author mwomack
 *
 */
public class TrophyWidgetTest {
	TrophyWidget trophyWidget;
	/**
	 * @throws java.lang.Exception
	 * Creates a TrophyWidget object
	 */
	@Before
	public void setup()  throws Exception{
		trophyWidget = new TrophyWidget();
	}
	
	/**
	 * Testing that the TrophyWidget id can be retrieved
	 */
	@Test
	public void testGetTrophyId() {
		assertEquals(trophyWidget.getId(),"trophies");
	}
	/**
	 * Testing that the TrophyWidget title can be retrieved
	 */
	
	@Test
	public void testGetTrophyTitle(){
		assertEquals(trophyWidget.getTitle(),"Trophies");
	}
	
	/**
	 * Testing that the TrophyWidget html.erb template path can be retrieved
	 */
	@Test
	public void testGetTrophyTemplatePath(){
		assertEquals(trophyWidget.getTemplatePath(),"/score/trophy_widget.html.erb");
	}
}
