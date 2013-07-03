package com.catalyst.sonar.score.metrics;

import org.sonar.api.database.DatabaseSession;
import org.sonar.api.measures.Metric;
/**
 * 
 * @author mwomack
 *
 */
public class MetricsHelper {

	private Metric metric;
	private DatabaseSession session;	
	public MetricsHelper(DatabaseSession session){
		this.session = session;
	}
	
	/**
	 * @return the session
	 */
	public DatabaseSession getSession() {
		return session;
	}

	/**
	 * Retrieve the metric id for a given metric name
	 * 
	 * @param metric name
	 * @returns the metric id, returns zero if the metric was not found
	 */
	public int getMetricId(String metricName) {
		// retrieve the metric by name in order to retrieve the metric id
		int metricId = 0;
		metric = findMetricByName(metricName);
			if (metric != null) {
				metricId = metric.getId();
			}
		return metricId; 

	}

	/**
	 * 
	 * @param name: name of the metric to query
	 * @returns the Metric object by name
	 */
	public Metric findMetricByName(String name) {
		return session.getSingleResult(Metric.class, "name", name, "enabled",
				true);
	}

	
}
