package com.catalyst.sonar.score.metrics;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.model.Snapshot;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Qualifiers;
import org.sonar.api.resources.Resource;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.catalyst.sonar.score.util.SnapshotHistory;

public class MeasuresHelperTest {
	private String pointsName;
	private String pointsKey;
	private Metric pointsMetric;
	private Metric.ValueType pointsType;
	private int pointsMetricId;
	private String metricName;
	private Query query;
	
	private String coverageName;
	private String coverageKey;
	private Metric coverageMetric;
	private Metric.ValueType coverageType;
	private int coverageMetricId;
		
	List<SnapshotHistory> entries;
	List<SnapshotHistory> emptyEntries;
	
	List<Object[]> pastMeasures;
	private int metricId;
	private String resourceKey;
	private DatabaseSession mockSession; 
	private MetricsHelper metricsHelper;
	private Project mockProject;
	private SnapshotHistory measure1;
	private SnapshotHistory measure2;
	private MeasuresHelper measuresHelper;
	private Resource mockResource;
	private Project mockProject2;
	
	/////
	private SnapshotHistory sh1;
	private SnapshotHistory sh2;
	private BigDecimal bd1;
	private Date date1;
	private BigDecimal bd2;
	private Date date2;
	
	//
	private Object[] object1;
	private Object[] object2;
	
	private String sql;
	
	@Before
	public void setUp(){
	mockSession = mock(DatabaseSession.class);
	
	mockResource = mock (Resource.class);
	mockResource.setEffectiveKey("Points");
	query = mock(Query.class);
	mockProject = new Project("Points");
	entries = new ArrayList<SnapshotHistory>();
	emptyEntries = new ArrayList<SnapshotHistory>();
	metricsHelper = new MetricsHelper (mockSession);
	measuresHelper = new MeasuresHelper(mockSession, mockProject);
	metricName = "Points";
	sql = "select m.value, s.build_date from project_measures m, snapshots s"
			+ " where m.snapshot_id=s.id and m.metric_id in (:metricId) "
			+ " and s.status=:status and s.project_id=(select p.id from projects p where p.kee=:resourceKey and p.qualifier<>:lib)" 
			+ " order by s.build_date desc";
	resourceKey = "Points";
	
	//////
	bd1 = new BigDecimal(1200);
	bd2 = new BigDecimal(1500);
	date1 = new Date(1370464358000l); //6/5/2013
	date2 = new Date (1360099958000l); //2/15/2013
	sh1 = new SnapshotHistory(bd1,date1); //new snapshotHistory (1200,6/5/2013)
	sh2 = new SnapshotHistory(bd2,date2);//new snapshotHistory (1500, 2/5/2013)	
	
	entries.add(sh1);
	entries.add(sh2);
	
	pastMeasures = new ArrayList<Object[]>(); 
	object1 = new Object[2];
	object1[0] = bd1;
	object1[1] = date1;
	
	object2 = new Object[2];
	object2[0] = bd2;
	object2[1] = date2;
	
	pastMeasures.add(object1);
	pastMeasures.add(object2);
	
	pointsKey = "Points";
	pointsName = "Points";
	pointsType = Metric.ValueType.INT;
	
	pointsMetric = new Metric.Builder(pointsKey, pointsName, pointsType).create();
	pointsMetric.setDirection(1);
	pointsMetric.setId(1);
	pointsMetric.setKey(pointsKey);
	pointsMetricId = 1;
	
	coverageKey = "coverage";
	coverageName = "coverage";
	coverageType = Metric.ValueType.INT;
	
	coverageMetric = new Metric.Builder(coverageKey, coverageName, coverageType).create();
	coverageMetric.setDirection(1);
	coverageMetric.setId(0);
	coverageMetric.setKey(pointsKey);
	coverageMetricId = 0;
	
	
		
	}
		
	@Test
	public void testGetMeasureCollectionWhenMetricIdIsFound() {
		when(mockSession.getSingleResult(Metric.class, "name", pointsName, "enabled", true)).thenReturn(pointsMetric);
		when(mockSession.createNativeQuery(sql)).thenReturn(query);
		when(query.setParameter("metricId", pointsMetricId)).thenReturn(query);
		when(query.setParameter("resourceKey", "Points")).thenReturn(query);
		when(query.setParameter("lib",Qualifiers.LIBRARY)).thenReturn(query);
		when(query.setParameter("status", Snapshot.STATUS_PROCESSED)).thenReturn(query);
		when(query.getResultList()).thenReturn(pastMeasures);
		assertEquals(measuresHelper.getMeasureCollection("Points"), entries);
		
	}

	/**
	 * Testing that when a metric isn't found, the getMeasureCollection method
	 * returns an empty array list
	 */
	@Test
	public void testGetMeasureCollectionReturnsNullWhenMetricNotFound(){
	assertEquals(measuresHelper.getMeasureCollection(coverageName), emptyEntries);
		
	}

	/**
	 * Testing that when a resourceKey and metric id is passed to the 
	 * getPastMeasures method, a list of objects is returned
	 */
	@Test
	public void testGetPastMeasures(){	
	when(mockSession.createNativeQuery(sql)).thenReturn(query);
			when(query.setParameter("metricId", pointsMetricId)).thenReturn(query);
			when(query.setParameter("resourceKey", "Points")).thenReturn(query);
			when(query.setParameter("lib",Qualifiers.LIBRARY)).thenReturn(query);
			when(query.setParameter("status", Snapshot.STATUS_PROCESSED)).thenReturn(query);
			when(query.getResultList()).thenReturn(pastMeasures);
	assertEquals(measuresHelper.getPastMeasures(pointsKey, pointsMetricId), pastMeasures);
	}

}
