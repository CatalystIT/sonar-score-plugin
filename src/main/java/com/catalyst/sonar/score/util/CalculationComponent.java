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
package com.catalyst.sonar.score.util;

import java.util.ArrayList;

import org.sonar.api.BatchExtension;

/**
 * CalculationComponent is a double tuplet that combines a metric and a double for use in calculations.
 *
 */
public class CalculationComponent implements BatchExtension {

	private final double metricAmount;
	private final double factor;
	
	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls this(0, 0).
	 */
	public CalculationComponent() {
		this(0, 0);
	}
	
	/**
	 * Constructs a CalculationComponent, setting the fields accordingly.
	 * @param metricAmount
	 * @param factor
	 */
	public CalculationComponent(double metricAmount, double factor) {
		this.metricAmount = metricAmount;
		this.factor = factor;
	}
	
	/**
	 * returns the product of the metricAmount and the factor.
	 * @return
	 */
	public double factoredAmount() {
		return metricAmount*factor;
	}

	/**
	 * @return the metricAmount
	 */
	public double getMetricAmount() {
		return metricAmount;
	}

	/**
	 * @return the factor
	 */
	public double getFactor() {
		return factor;
	}
	
	/**
	 * 
	 * CalculationComponentList is an ArrayList of CalculationComponents,
	 * providing an extra method to calculate the sum of all the factoredAmounts of its contents.
	 *
	 */
	public static class CalculationComponentList extends ArrayList<CalculationComponent> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6978099613310725807L;

		/**
		 * returns the sum of all the factoredTotals of the contents.
		 * @return
		 */
		public double factoredTotal() {
			double factoredTotal = 0;
			for(CalculationComponent component : this) {
				if(component != null) {					
					factoredTotal += component.factoredAmount();
				}
			}
			return factoredTotal;
		}		
	}
}

