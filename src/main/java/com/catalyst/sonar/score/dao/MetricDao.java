package com.catalyst.sonar.score.dao;

import org.sonar.api.database.DatabaseSession;
import org.sonar.api.measures.Metric;
import org.sonar.jpa.dao.BaseDao;
/**
 * 
 * @author mwomack
 *
 */
public class MetricDao extends BaseDao {

	/**
	 * Constructor with a parameter for the session to set the session.
	 * 
	 * @param session
	 */
	public MetricDao(DatabaseSession session){
		super(session);
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
		Metric metric = findMetricByName(metricName);
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
		return getSession().getSingleResult(Metric.class, "name", name, "enabled",
				true);
	}

	
}
