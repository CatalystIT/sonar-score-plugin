/**
 * 
 */
package com.catalyst.sonar.score;

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
	
	
	public static final Metric POINTS = new Metric.Builder("points", "Points",
			Metric.ValueType.FLOAT)
			.setDescription("Score's points value")
			.setDirection(Metric.DIRECTION_NONE)
			.setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();

	/* getMetrics() method is defined in the Metrics interface and is used by
	Sonar to retrieve the list of new metrics
	 */
	public List<Metric> getMetrics() {
		//retrieves list of metrics
		return Arrays.asList(POINTS);
	}

}
