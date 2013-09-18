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

import java.util.Arrays;
import java.util.Iterator;

import com.catalyst.commons.util.Mergeable;
import com.catalyst.commons.util.SearchableHashSet;
import com.catalyst.commons.util.SearchableSetIterable;

/**
 * The {@link Award} class represents an award in Sonar for projects to earn,
 * with a name and a {@link SearchableHashSet} of Criteria.
 * 
 * @author JDunn
 */
public abstract class Award implements Iterable<Criterion>, Mergeable<Award>,
		AssignableScoreEntity {

	private String name;
	private CriterionSet criteria;

	/**
	 * Constructs an {@code Award}, setting the name to equal the {@code String}
	 * name argument and instantiating the three other fields (which are all
	 * instances of {@link SearchableHashSet}).
	 * 
	 * @param name
	 */
	public Award(String name) {
		this.name = name;
		this.criteria = new CriterionSet();
	}

	/**
	 * @see Iterable#iterator()
	 */
	public Iterator<Criterion> iterator() {
		return this.criteria.iterator();
	}

	/**
	 * @see Mergeable#merge(Object)
	 */
	public boolean merge(Award other) {
		boolean added = false;
		if (this.equals(other)) {
			for (Criterion criterion : other) {
				if (this.criteria.add(criterion)) {
					added = true;
				}
			}
		}
		return added;
	}

	/**
	 * Adds a {@link Criterion} to the criteria.
	 * 
	 * @param criterion
	 */
	public boolean addCriterion(Criterion criterion) {
		return this.criteria.add(criterion);
	}

	/**
	 * Adds {@code Criteria} to the criteria.
	 * 
	 * @param criterion
	 * @return this.criteria.addAll(Arrays.asList(criteria));
	 */
	public boolean addCriteria(Criterion... criteria) {
		return this.criteria.addAll(Arrays.asList(criteria));
	}

	/**
	 * Overrides {@link java.lang.Object#equals(java.lang.Object)}, checking for
	 * meaningful equality based on the name field.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj.getClass().equals(this.getClass()))) {
			return false;
		}
		Award other = (Award) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/**
	 * Overrides {@link java.lang.Object#hashCode()}, calculating a
	 * {@code hashCode} based on the name field.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 1;
		hash += (prime * hash + ((name == null) ? 0 : name.hashCode()));
		return hash;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 * @return this
	 */
	public Award setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return a searchable iterable of the criteria
	 * @see {@link SearchableHashSet#immutableCopy()}
	 */
	public SearchableSetIterable<Criterion> getCriteria() {
		return criteria.iterable();
	}

	/**
	 * Gets the uniqueId, which for an {@link Award} is the same as the name.
	 * 
	 * @see {@link Member#getUniqueId()}
	 */
	public String getUniqueId() {
		return this.name;
	}

	/**
	 * @see {@link Object#toString()}
	 */
	@Override
	public String toString() {
		return this.name + " " + this.getClass().getSimpleName();
	}

}
