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
package com.catalyst.sonar.score.metrics;

import java.util.List;
import java.util.Arrays;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

/**
 * @author lsajeev
 * Creates SCORE's Points metric
 * 
 */
public class ScoreMetrics implements Metrics {
	/**
	 * Creates a new "Points" metric in the database setting the value type (value calculated as
	 * a double, but saved as an integer), name, short-name, description
	 * direction, qualitative/quantitative, and domain values 
	 */	
	public static final Metric POINTS = new Metric.Builder("points", "Points",
			Metric.ValueType.INT)
			.setDescription("Score's points value")
			.setDirection(Metric.DIRECTION_BETTER)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();
	
	
	/**
	 * Retrieves a list of specified metrics
	 */
	public List<Metric> getMetrics() { 
		return Arrays.asList(POINTS);
	}

}
