/**
 * 
 */
package com.catalyst.sonar.score.batch;

import static com.catalyst.sonar.score.batch.PointsCalculator.MAGNIFY_PACKAGE_TANGLE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
import com.catalyst.sonar.score.util.CalculationComponent;
import com.catalyst.sonar.score.util.CalculationComponent.CalculationComponentList;
import com.google.common.collect.ImmutableList;

/**
 * @author JDunn
 * 
 */
public class PointsDecoratorTest {
	// abstract class MockResource extends Resource<Project> {
	// };

	PointsDecorator scoreDecorator;
	Project project;
	DecoratorContext mockContext;
	DecoratorContext mockContext2;
	Resource<Project> resource;
	Resource<Project> utsResource;
	Measure ncloc;
	Measure classes;
	Measure rulesCompliance;
	Measure api;
	Measure coverage;
	Measure tangle;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		scoreDecorator = new PointsDecorator();
		project = new Project("test");
		mockContext = mock(DecoratorContext.class);
		mockContext2 = mock(DecoratorContext.class);
		ncloc = mock(Measure.class);
		classes = mock(Measure.class);
		rulesCompliance = mock(Measure.class);
		api = mock(Measure.class);
		coverage = mock(Measure.class);
		tangle = mock(Measure.class);
		resource = mock(Resource.class);
		when(resource.getQualifier()).thenReturn(
				"NOT_" + Qualifiers.UNIT_TEST_FILE);
		utsResource = mock(Resource.class);
		when(utsResource.getQualifier()).thenReturn(Qualifiers.UNIT_TEST_FILE);
	}

	/**
	 * Test method for
	 * {@link com.catalyst.sonar.score.batch.PointsDecorator#usedMetrics()}.
	 */
	@Test
	public void testUsedMetrics() {
		assertEquals(scoreDecorator.usedMetrics(), ImmutableList.of(
				CoreMetrics.PACKAGES, CoreMetrics.CLASSES,
				CoreMetrics.NCLOC, CoreMetrics.VIOLATIONS_DENSITY,
				CoreMetrics.PUBLIC_DOCUMENTED_API_DENSITY,
				CoreMetrics.COVERAGE, CoreMetrics.PACKAGE_TANGLE_INDEX));
	}

	/**
	 * Test method for
	 * {@link com.catalyst.sonar.score.batch.PointsDecorator#generatedMetrics()}.
	 */
	@Test
	public void testGeneratedMetrics() {
		assertEquals(scoreDecorator.generatedMetrics(),
				Arrays.asList(ScoreMetrics.POINTS));
	}

	/**
	 * Test method for
	 * {@link com.catalyst.sonar.score.batch.PointsDecorator#shouldExecuteOnProject(org.sonar.api.resources.Project)}
	 * .
	 */
	@Test
	public void testShouldExecuteOnProject() {
		assertEquals(scoreDecorator.shouldExecuteOnProject(project), true);
	}

	/**
	 * Test method for
	 * {@link com.catalyst.sonar.score.batch.PointsDecorator#shouldDecorateResource(org.sonar.api.resources.Resource, org.sonar.api.batch.DecoratorContext)}
	 * . Tests for when the resource is a unit test resource. It should return
	 * false.
	 */
	@Test
	public void testShouldDecorateResourceWhenResourceIsUnitTest() {
		assertEquals(scoreDecorator.shouldDecorateResource(utsResource,
				mockContext2), false);// !ResourceUtils.isUnitTestClass(resource));
	}

	/**
	 * Test method for
	 * {@link com.catalyst.sonar.score.batch.PointsDecorator#shouldDecorateResource(org.sonar.api.resources.Resource, org.sonar.api.batch.DecoratorContext)}
	 * .
	 */
	@Test
	public void testShouldDecorateResource() {
		assertEquals(
				scoreDecorator.shouldDecorateResource(utsResource, mockContext),
				false);// !ResourceUtils.isUnitTestClass(resource));
	}

	/**
	 * Test method for
	 * {@link com.catalyst.sonar.score.batch.PointsDecorator#decorate(org.sonar.api.resources.Resource, org.sonar.api.batch.DecoratorContext)}
	 * .
	 */
	@Test
	public void testDecorate() {
		when(mockContext.getMeasure(CoreMetrics.NCLOC)).thenReturn(ncloc);
		when(mockContext.getMeasure(CoreMetrics.CLASSES)).thenReturn(classes);
		when(mockContext.getMeasure(CoreMetrics.VIOLATIONS_DENSITY))
				.thenReturn(rulesCompliance);
		when(mockContext.getMeasure(CoreMetrics.PUBLIC_DOCUMENTED_API_DENSITY))
				.thenReturn(api);
		when(mockContext.getMeasure(CoreMetrics.COVERAGE)).thenReturn(coverage);
		when(mockContext.getMeasure(CoreMetrics.PACKAGE_TANGLE_INDEX))
				.thenReturn(tangle);
		when(ncloc.getValue()).thenReturn(1000.0);
		when(classes.getValue()).thenReturn(20.0);
		when(rulesCompliance.getValue()).thenReturn(95.0);
		when(api.getValue()).thenReturn(80.0);
		when(coverage.getValue()).thenReturn(88.0);
		when(tangle.getValue()).thenReturn(0.0);
		CalculationComponentList componentList = new CalculationComponentList();
		CalculationComponent tangleComponent = new CalculationComponent(tangle.getValue(), MAGNIFY_PACKAGE_TANGLE);
		componentList.add(tangleComponent);
		double points = new PointsCalculator(componentList , null).calculateTotalPoints(5, 20, 1000, 95, 80, 88, 0);
		when(mockContext.saveMeasure(ScoreMetrics.POINTS, points)).thenReturn(
				mockContext);
		scoreDecorator.decorate(resource, mockContext);
		//verify(mockContext).saveMeasure(ScoreMetrics.POINTS, points);
		scoreDecorator.decorate(utsResource, mockContext2);
		//verify(mockContext2, never()).saveMeasure(ScoreMetrics.POINTS, points);

	}

}
