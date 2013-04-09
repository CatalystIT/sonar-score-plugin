package com.catalyst.sonar.score.batch;
import com.catalyst.sonar.score.metrics.ScoreMetrics;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;
import java.util.Arrays;
import java.util.Collection;
import com.google.common.collect.ImmutableList;
import org.sonar.api.batch.DependsUpon;

public class ScoreDecorator implements Decorator {
	
 /*The points metric depends upon the non-commented lines of code, the rules compliance (violations density), unit test coverage,
 documented API, and package tangle measures to be calculated first before the points measure can be calaculated.
 */
 @DependsUpon
  public Collection<Metric> usedMetrics(){
  return ImmutableList.of(CoreMetrics.NCLOC, CoreMetrics.VIOLATIONS_DENSITY, CoreMetrics.PUBLIC_DOCUMENTED_API_DENSITY,
  CoreMetrics.COVERAGE, CoreMetrics.PACKAGE_TANGLE_INDEX );   
  }
  /*The points metric is calculated after the other metrics that is depends upon are calculated first.
  */
  @DependedUpon
  public Collection<Metric> generatedMetrics() {   
  return Arrays.asList(ScoreMetrics.POINTS);
  }

  public boolean shouldExecuteOnProject(Project project) {
   // return !Project.AnalysisType.STATIC.equals(project.getAnalysisType());
	return true;
  }
/*returns whether or not a resource is a unit test class
*/
  public boolean shouldDecorateResource( final Resource resource, final DecoratorContext context ) {
    return !ResourceUtils.isUnitTestClass(resource);
  }
/*calculates the points metric and saves it to the database.  First all the necessary measures are retrieved in order to 
calculate the points metric for a particular project.
*/
  public void decorate( final Resource resource, final DecoratorContext context ) {
    if (shouldDecorateResource (resource, context)){
	double lines = MeasureUtils.getValue(context.getMeasure(CoreMetrics.NCLOC),0.0);
	double rulesComplexity = MeasureUtils.getValue(context.getMeasure(CoreMetrics.VIOLATIONS_DENSITY), 0.0);
	double docAPI = MeasureUtils.getValue(context.getMeasure(CoreMetrics.PUBLIC_DOCUMENTED_API_DENSITY), 0.0);
	double coverage = MeasureUtils.getValue(context.getMeasure(CoreMetrics.COVERAGE), 0.0);
	double packageTangle = MeasureUtils.getValue(context.getMeasure(CoreMetrics.PACKAGE_TANGLE_INDEX), 0.0);
	
	double value = (lines * (rulesComplexity/100.0) * (docAPI /100.0) * (coverage/100.0)) - packageTangle *100.0;	
	
	context.saveMeasure(ScoreMetrics.POINTS, value);  
	}
  
  }
    
}
