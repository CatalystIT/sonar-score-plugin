/**
 * 
 */
package com.catalyst.sonar.score.batch;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static com.catalyst.sonar.score.batch.ScoreDecorator.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Qualifiers;
import org.sonar.api.resources.Resource;

import com.catalyst.sonar.score.metrics.ScoreMetrics;
import com.google.common.collect.ImmutableList;

/**
 * @author JDunn
 *
 */
public class ScoreDecoratorTest {
//	abstract class MockResource extends Resource<Project> {		
//	};
	
	ScoreDecorator scoreDecorator;
	Project project;
	DecoratorContext mockContext;
	DecoratorContext mockContext2;
	Resource<Project> resource;
	Resource<Project> utsResource;
	Measure ncloc;
	Measure violations;
	Measure api;
	Measure coverage;
	Measure tangle;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		scoreDecorator = new ScoreDecorator();
		project = new Project("test");
		mockContext = mock(DecoratorContext.class);
		mockContext2 = mock(DecoratorContext.class);
		ncloc = mock(Measure.class);
		violations = mock(Measure.class);
		api = mock(Measure.class);
		coverage = mock(Measure.class);
		tangle = mock(Measure.class);
		resource = mock(Resource.class);
		when(resource.getQualifier()).thenReturn("NOT_" + Qualifiers.UNIT_TEST_FILE);
		utsResource = mock(Resource.class);
		when(utsResource.getQualifier()).thenReturn(Qualifiers.UNIT_TEST_FILE);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.ScoreDecorator#usedMetrics()}.
	 */
	@Test
	public void testUsedMetrics() {
		assertEquals(scoreDecorator.usedMetrics(), ImmutableList.of(
				CoreMetrics.NCLOC,
				CoreMetrics.VIOLATIONS_DENSITY,
				CoreMetrics.PUBLIC_DOCUMENTED_API_DENSITY,
				CoreMetrics.COVERAGE,
				CoreMetrics.PACKAGE_TANGLE_INDEX ));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.ScoreDecorator#generatedMetrics()}.
	 */
	@Test
	public void testGeneratedMetrics() {
		assertEquals(scoreDecorator.generatedMetrics(), Arrays.asList(ScoreMetrics.POINTS));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.ScoreDecorator#shouldExecuteOnProject(org.sonar.api.resources.Project)}.
	 */
	@Test
	public void testShouldExecuteOnProject() {
		assertEquals(scoreDecorator.shouldExecuteOnProject(project), true);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.ScoreDecorator#shouldDecorateResource(org.sonar.api.resources.Resource, org.sonar.api.batch.DecoratorContext)}.
	 * Tests for when the resource is a unit test resource.  It should return false.
	 */
	@Test
	public void testShouldDecorateResourceWhenResourceIsUnitTest() {
		assertEquals(scoreDecorator.shouldDecorateResource(utsResource, mockContext2), false);//!ResourceUtils.isUnitTestClass(resource));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.ScoreDecorator#shouldDecorateResource(org.sonar.api.resources.Resource, org.sonar.api.batch.DecoratorContext)}.
	 */
	@Test
	public void testShouldDecorateResource() {
		assertEquals(scoreDecorator.shouldDecorateResource(utsResource, mockContext), false);//!ResourceUtils.isUnitTestClass(resource));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.ScoreDecorator#decorate(org.sonar.api.resources.Resource, org.sonar.api.batch.DecoratorContext)}.
	 */
	@Test
	public void testDecorate() {
		when(mockContext.getMeasure(CoreMetrics.NCLOC)).thenReturn(ncloc);
		when(mockContext.getMeasure(CoreMetrics.VIOLATIONS_DENSITY)).thenReturn(violations);
		when(mockContext.getMeasure(CoreMetrics.PUBLIC_DOCUMENTED_API_DENSITY)).thenReturn(api);
		when(mockContext.getMeasure(CoreMetrics.COVERAGE)).thenReturn(coverage);
		when(mockContext.getMeasure(CoreMetrics.PACKAGE_TANGLE_INDEX)).thenReturn(tangle);
		when(ncloc.getValue()).thenReturn(1000.0);
		when(violations.getValue()).thenReturn(95.0);
		when(api.getValue()).thenReturn(80.0);
		when(coverage.getValue()).thenReturn(88.0);
		when(tangle.getValue()).thenReturn(0.0);
		double points = Math.round((1000.0 *(95.0/PERCENT) * (80.0/PERCENT) * (88.0/PERCENT)) - (0.0*MAGNIFY_PACKAGE_TANGLE));
		when(mockContext.saveMeasure(ScoreMetrics.POINTS, points)).thenReturn(mockContext);
		scoreDecorator.decorate(resource, mockContext);
		verify(mockContext).saveMeasure(ScoreMetrics.POINTS, points);
		scoreDecorator.decorate(utsResource, mockContext2);
		verify(mockContext2, never()).saveMeasure(ScoreMetrics.POINTS, points);
		
		
	}
/**
 * Testing that a point's value cannot be negative.  The lowest score possible is zero.
 */
	@Test
public void testDecorateWithNegativePoints(){
	
	when(mockContext.getMeasure(CoreMetrics.NCLOC)).thenReturn(ncloc);
	when(mockContext.getMeasure(CoreMetrics.VIOLATIONS_DENSITY)).thenReturn(violations);
	when(mockContext.getMeasure(CoreMetrics.PUBLIC_DOCUMENTED_API_DENSITY)).thenReturn(api);
	when(mockContext.getMeasure(CoreMetrics.COVERAGE)).thenReturn(coverage);
	when(mockContext.getMeasure(CoreMetrics.PACKAGE_TANGLE_INDEX)).thenReturn(tangle);
	when(ncloc.getValue()).thenReturn(1000.0);
	when(violations.getValue()).thenReturn(95.0);
	when(api.getValue()).thenReturn(80.0);
	when(coverage.getValue()).thenReturn(88.0);
	when(tangle.getValue()).thenReturn(500.0);
	//points calculated in the getPointsValue method is -49,331 but will return zero
	//because points be negative.  
	assertEquals((int)scoreDecorator.getPointsValue(mockContext),(int)LOWEST_POINTS);
}

}
