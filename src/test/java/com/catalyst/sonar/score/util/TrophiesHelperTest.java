package com.catalyst.sonar.score.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.Resource;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrophiesHelperTest {
	private Settings mockSettings;
	private String trophyName;
	private Resource mockResource;
	private Map<String, String> allProperties;
	private TrophiesHelper trophiesHelper;
	
	@Before
	public void setUp(){		
		trophyName = "Trophy1";
		mockResource = mock(Resource.class);
		mockSettings = mock(Settings.class);
		trophiesHelper = new TrophiesHelper(mockSettings);
		allProperties = new HashMap<String, String>();
		allProperties.put("sonar.core.version", "3.4.1");
		allProperties.put("sonar.score.projectTrophy", "Trophy1");
		trophiesHelper.setSettings(mockSettings);
		
	}
	
	@Test
	public void testNewTrophyForThisProject() {
	//when(mockSettings.getProperties()).thenReturn(allProperties);	
	//assertTrue(trophiesHelper.newTrophyForThisProject("Trophy2", mockResource));
	
	}

}
