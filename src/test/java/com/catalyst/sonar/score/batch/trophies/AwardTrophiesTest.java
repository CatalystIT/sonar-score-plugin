package com.catalyst.sonar.score.batch.trophies;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.config.Settings;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.catalyst.sonar.score.batch.TrophiesDecorator;
import com.catalyst.sonar.score.util.MeasuresHelper;
import com.catalyst.sonar.score.util.TrophiesHelper;

public class AwardTrophiesTest {
	
	private DatabaseSession mockSession;
	private Project project;
	private Settings settings;
	private MeasuresHelper measuresHelper;
	private TrophiesHelper trophiesHelper;
	private Property newProperty;
	int numberOfListsOfCriteriaPerTrophy;
	private AwardTrophies awardTrophies;
	private List<Property> allProperties; 
	private Resource resource;
	private int resourceId;
	private Property property1;
	private Property property2;
	private TrophiesDecorator mockTrophiesDecorator;
	private DecoratorContext context;
	
	
	@Before
	public void setup(){
	awardTrophies = new AwardTrophies(mockSession, project,settings);
	mockTrophiesDecorator = mock(TrophiesDecorator.class);
	mockSession = mock(DatabaseSession.class);
	resource = mock(Resource.class);
	context = mock(DecoratorContext.class);
	settings = new Settings();
	allProperties =  new ArrayList<Property>();
	property1 = new Property("sonar.score.trophyProperty", "Trophy1,Trophy2");
	property2 = new Property("sonar.otherProperty", "other info");
	allProperties.add(property1);
	allProperties.add(property2);
	
	resource.setEffectiveKey("Points");
	resource.setId(1);
	
		
	}
	@Test
	public void testAwardTrophyWhenTrophyHasNotBeenEarnedYes() {
	//when(mockSession.getResults(Property.class,"resourceId",resourceId)).thenReturn(allProperties);
	//Mockito.verify(mockTrophiesDecorator,Mockito.atLeastOnce()).
	//awardTrophies.awardTrophy("Trophy3", resource);
	}

}
