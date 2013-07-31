package com.catalyst.sonar.score.batch;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.config.Settings;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Qualifiers;
import org.sonar.api.resources.Resource;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.catalyst.sonar.score.metrics.ScoreMetrics;

public class TrophiesDecoratorTest {
	
	private DatabaseSession mockSession;
	private Project project;
	private Settings mockSetting;
	private TrophiesDecorator trophiesDecorator;
	private Resource<Project> resource;
	private Resource<Project> utsResource;
	private DecoratorContext mockContext;
	private DecoratorContext mockContext2;
		
	@SuppressWarnings("unchecked")
	@Before
	public void setup(){
	mockSession = mock(DatabaseSession.class);
	mockSetting = mock(Settings.class);
	mockContext = mock(DecoratorContext.class);
	mockContext2 = mock(DecoratorContext.class);
	project = new Project("Project test");
	project.setId(1);
	resource = mock(Resource.class);
	when(resource.getQualifier()).thenReturn(
			"NOT_" + Qualifiers.UNIT_TEST_FILE);
	when(resource.getScope()).thenReturn("PRJ");
	
			
	trophiesDecorator = new TrophiesDecorator(mockSession, project, mockSetting);
	utsResource = mock(Resource.class);
	when(utsResource.getQualifier()).thenReturn(Qualifiers.UNIT_TEST_FILE);	
	}
	/**
	 * Testing that shouldExecuteOnProject method returns true
	 */
	@Test
	public void testShouldExecuteOnProject() {
		assertEquals(trophiesDecorator.shouldExecuteOnProject(project), true);
	}

	/**
	 * Testing that if a resource is not a unit test, then the shouldDecorateResource
	 * method returns true
	 */
	@Test
	public void testShouldDecorateResource(){
		assertEquals(
				trophiesDecorator.shouldDecorateResource(resource, mockContext),true);
	}
	/**
	 * Testing that if a resource is a unit test, then the shouldDecorateResource method
	 * returns false
	 */
	@Test 
	public void testShouldNotDecorateResource(){
		assertEquals(trophiesDecorator.shouldDecorateResource(utsResource, mockContext2), false);
	}

	/**
	 * Testing that if a resource scope is a project, then the shouldCheckTrophyStatusForResource
	 * returns true
	 */
	@Test
	public void testShouldCheckTrophyStatusForResource(){
		assertEquals(trophiesDecorator.shouldCheckTrophyStatusForResource(resource), true);
	}

//	@Test
//	public void testDecorate(){
//		DecoratorContext context = mock(DecoratorContext.class);
//		AwardTrophies awardTrophies = mock(AwardTrophies.class);
//		Resource resource1  = mock(Resource.class);
//		Project project1 = new Project("Points");
//		project1.setEffectiveKey("Points");
//		project1.setId(1);
//		resource1.setEffectiveKey("Points");
//		resource1.setId(1);
//		when(resource1.getQualifier()).thenReturn(
//				"NOT_" + Qualifiers.UNIT_TEST_FILE);
//		when(resource1.getScope()).thenReturn("PRJ");
//		when(resource1.getId()).thenReturn(new Integer(1));
//		Settings settings = new Settings();
//		TrophiesDecorator trophiesDecorator = new TrophiesDecorator(mockSession, project1, settings);
//		trophiesDecorator.decorate(resource1, context);
//		
//		Property property1 = new Property("sonar.score.trophyProperty", "trophy1");
//		Property property2 = new Property("sonar.otherProperty", "information");
//		List<Property>theProperties = new ArrayList<Property>();
//		theProperties.add(property1);
//		theProperties.add(property2);		
//		
//		verify(awardTrophies).awardTrophies(context, project1);
//	
//	}


}
