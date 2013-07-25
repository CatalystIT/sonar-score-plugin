package com.catalyst.sonar.score.ui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ImageUploadPageTest {

	ImageUploadPage imageUpload;

	/**
	 * creates an ImageUpload Page object
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		imageUpload = new ImageUploadPage();

	}

	/**
	 * Testing that the URL of the controller is returned
	 */
	@Test
	public void testGetId() {
		assertEquals(imageUpload.getId(), "/images/index");
	}

	/**
	 * Testing that the Title is returned 
	 */
	@Test
	public void testGetTitle() {
		assertEquals(imageUpload.getTitle(), "Edit Project Profile");
	}

}
