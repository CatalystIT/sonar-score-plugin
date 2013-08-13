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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.model.Snapshot;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Qualifiers;
import org.sonar.jpa.dao.BaseDao;

import com.catalyst.sonar.score.api.ScoreProject;
import com.catalyst.sonar.score.util.SnapshotValue;

/**
 * 
 * @author mwomack
 * 
 */
public class SnapShotDao extends BaseDao{

	private MetricDao metricsHelper;
	private String resourceKey;
	private Project project;

	/**
	 * MeasuresHelper constructor
	 * 
	 * @param session
	 * @param project
	 */
	public SnapShotDao(DatabaseSession session, Project project) {
		super(session);
		this.project = project;
	}
	
	/**
	 * MeasuresHelper constructor
	 * 
	 * @param session
	 * @param project
	 */
	public SnapShotDao(DatabaseSession session, ScoreProject project) {
		super(session);
		this.project = new Project(project.getKey(), "", project.getName());
	}

	/**
	 * 
	 * @param metricName
	 * @returns a snapshot history of build dates and measure values for a given
	 *          project and metric to be used to compare against a trophy's
	 *          criteria
	 */
	public List<SnapshotValue> getMeasureCollection(String metricName) {

		List<SnapshotValue> entries = new ArrayList<SnapshotValue>();
		metricsHelper = new MetricDao(getSession());
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
				SnapshotValue entry = new SnapshotValue(value, date);
				entries.add(entry);

			}

		}

		return entries;
	}

	/**
	 * 
	 * @param resourceKey
	 *            : this is the resource key of the project, used to retrieve
	 *            the needed measure values
	 * 
	 * @param metricId
	 *            : this is the metric id of the given metric
	 * 
	 * @returns a list of objects that contain a project's measure values and
	 *          build dates for a given metric
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getPastMeasures(String resourceKey, int metricId) {
		String sql = "select m.value, s.build_date from project_measures m, snapshots s"
				+ " where m.snapshot_id=s.id and m.metric_id in (:metricId) "
				+ " and s.status=:status and s.project_id=(select p.id from projects p where p.kee=:resourceKey and p.qualifier<>:lib)"
				+ " order by s.build_date desc";
		return getSession().createNativeQuery(sql)
				.setParameter("metricId", metricId)
				.setParameter("resourceKey", resourceKey)
				.setParameter("lib", Qualifiers.LIBRARY)
				.setParameter("status", Snapshot.STATUS_PROCESSED)
				.getResultList();
	}

}
