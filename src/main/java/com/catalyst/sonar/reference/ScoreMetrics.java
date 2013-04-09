/**
 * 
 */
package com.catalyst.sonar.reference;

import java.util.List;
import java.util.Arrays;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

/**
 * @author lsajeev
 * 
 */
public class ScoreMetrics implements Metrics {
	
	public static final String GENERATED_SCORE_KEY = "score_key";

	public static final Metric SCORE = new Metric.Builder(GENERATED_SCORE_KEY, "Generated Score",
			Metric.ValueType.INT)
			.setDescription("This gives test score")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(true)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();

	public List<Metric> getMetrics() {
		//retrieves list of metrics
		return Arrays.asList(SCORE);
	}

}
