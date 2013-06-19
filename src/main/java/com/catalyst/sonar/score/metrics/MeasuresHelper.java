package com.catalyst.sonar.score.metrics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.model.Snapshot;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Qualifiers;


import com.catalyst.sonar.score.util.SnapshotHistory;
/**
 * 
 * @author mwomack
 *
 */
public class MeasuresHelper {
	
	private DatabaseSession session;
	private MetricsHelper metricsHelper;
	private String resourceKey;
	private Project project;
	
	/**
	 * MeasuresHelper constructor
	 * @param session
	 * @param project
	 */
	public MeasuresHelper (DatabaseSession session, Project project){
		this.session = session;
		this.project = project;
	}

	/**
	 * 
	 * @param metricName
	 * @returns a snapshot history of build dates and measure values for a given
	 *  project and metric to be used to compare against a trophy's
	 *  criteria
	 */
	public List<SnapshotHistory> getMeasureCollection(String metricName) { 

		List<SnapshotHistory> entries = new ArrayList<SnapshotHistory>();
		metricsHelper = new MetricsHelper(session);
		// retrieve the metric's id
		int metricId = metricsHelper.getMetricId(metricName);
		
		resourceKey = project.getEffectiveKey();

		/*
		 * if the metric was found, retrieve all the measure values and build
		 * dates for a given metric and project
		 */
		if (metricId != 0) {

			List<Object[]> results = getPastMeasures(resourceKey, metricId); 

			for (Object[] result : results) {
				BigDecimal value = (BigDecimal) result[0];
				Date date = (Date) result[1];
				/*
				 * creates a snapshot history, with the corresponding build
				 * dates and measure values for a given project and metric
				 */
				SnapshotHistory entry = new SnapshotHistory(value, date);
				entries.add(entry);

			}

		}

		return entries;
	}

	/**
	 * 
	 * @param resourceKey: this is the resource key of the project, 
	 * used to retrieve the needed measure values
	 *            
	 * @param metricId: this is the metric id of the given metric
	 * 
	 * @returns a list of objects that contain a project's measure 
	 * values and  build dates for a given metric       
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getPastMeasures(String resourceKey, int metricId) {
		String sql = "select m.value, s.build_date from project_measures m, snapshots s"
				+ " where m.snapshot_id=s.id and m.metric_id in (:metricId) "
				+ " and s.status=:status and s.project_id=(select p.id from projects p where p.kee=:resourceKey and p.qualifier<>:lib)" 
				+ " order by s.build_date desc";
		return session.createNativeQuery(sql)
				.setParameter("metricId", metricId)
				.setParameter("resourceKey", resourceKey)
				.setParameter("lib", Qualifiers.LIBRARY)
				.setParameter("status", Snapshot.STATUS_PROCESSED)
				.getResultList();
	}

	
}
