package com.catalyst.sonar.score.metrics;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.measures.Metric;
import org.sonar.api.database.DatabaseSession;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MetricsHelperTest {
	private String pointsName;
	private String pointsKey;
	private Metric pointsMetric;
	private MetricsHelper metricsHelper;
	private DatabaseSession mockSession;	
	private Metric.ValueType pointsType;
	private int pointsMetricId;
	private String noSuchMetricName;
	private int noSuchMetricId = 0;
			
	
	@Before
	public void testSetup(){
	mockSession = mock(DatabaseSession.class);
	metricsHelper = new MetricsHelper(mockSession);
	
	pointsKey = "Points";
	pointsName = "Points";
	pointsType = Metric.ValueType.INT;
	
	pointsMetric = new Metric.Builder(pointsKey, pointsName, pointsType).create();
	pointsMetric.setDirection(1);
	pointsMetric.setId(1);
	pointsMetricId = 1;
		
	}
	
	/**
	 * Testing that when the getMetricId is passed a metric name and the metric is found, 
	 * the metric's id is returned.
	 */
	@Test
	public void testGetMetricId() {		
	when(mockSession.getSingleResult(Metric.class, "name", pointsName, "enabled", true)).thenReturn(pointsMetric);
	assertEquals(metricsHelper.getMetricId(pointsName),pointsMetricId);	
	}
	
	/**
	 * Testing that when the getMetricId method id pass a metric name and the metric is not
	 * found, the method returns a zero
	 */
	@Test
	public void testGetMetricIdOfZero(){
	when(mockSession.getSingleResult(Metric.class, "name", pointsName, "enabled", true)).thenReturn(null); 	
	assertEquals(metricsHelper.getMetricId(noSuchMetricName), noSuchMetricId);
		
	}

}
