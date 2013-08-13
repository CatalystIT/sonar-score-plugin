/*
 * Copyright 2013 Catalyst IT Services
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
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
