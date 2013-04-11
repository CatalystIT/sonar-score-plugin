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
	 * Creates a new "Points" metric in the database setting the value type, name, short-name, description
	 * direction, qualitative/quantitative, and domain values 
	 */	
	public static final Metric POINTS = new Metric.Builder("points", "Points",
			Metric.ValueType.FLOAT)
			.setDescription("Score's points value")
			.setDirection(Metric.DIRECTION_NONE)
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
