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
package com.catalyst.sonar.score.api;

import com.catalyst.commons.util.SearchableHashSet;

/**
 * CriterionSet extends {@link SearchableHashSet}{@code <}{@link Criterion}
 * {@code >}, overriding add such that any {@code Criterion} with a null metric
 * cannot be added, and overriding {@code toString()} to return a String
 * representation of the contained Criteria as they would appear in the value
 * column of the Properties table.
 * 
 * @author JDunn
 * 
 */
public class CriterionSet extends SearchableHashSet<Criterion> {

	/**
	 * Ensures that no Criterion will be added if its Metric is null.
	 * 
	 * @See {@link SearchableHashSet#add(Object)}
	 */
	@Override
	public boolean add(Criterion criterion) {
		if (criterion == null || criterion.getMetric() == null) {
			return false;
		}
		return super.add(criterion);
	}

	/**
	 * Prints out the CriterionSet as it will be represented in the Properties
	 * table in the database.
	 * 
	 * @see {@link AbstractCollection#toString()}
	 * @see {@link Object#toString()}
	 */
	@Override
	public String toString() {
		String toString = "";
		for (Criterion criterion : this) {
			toString += criterion.toValueString() + ',';
		}
		return toString.substring(0, toString.length() - 1);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8665990041148078349L;

}
