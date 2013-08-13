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

/**
 * The {@link Award} class represents an award in Sonar for projects to earn,
 * with a name and a {@link SearchableHashSet} of Criteria.
 * 
 * @author JDunn
 */
public abstract class Award implements AssignableScoreEntity {

	public static final String UNNAMED_AWARD = "Unnamed Award";

	private String name;
	private CriterionSet criteria;
	@SuppressWarnings(RAWTYPE_WARNING)
	private Group membersToInclude;
	@SuppressWarnings(RAWTYPE_WARNING)
	private Group membersToExclude;

	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls
	 * {@code this({@link UNNAMED_AWARD})}, preventing any fields from being
	 * {@code null}.
	 */
	public Award() {
		this(UNNAMED_AWARD);
	}

	/**
	 * Constructs an {@code Award}, setting the name to equal the {@code String}
	 * name argument and instantiating the three other fields (which are all
	 * instances of {@link SearchableHashSet}).
	 * 
	 * @param name
	 */
	@SuppressWarnings(RAWTYPE_WARNING)
	public Award(String name) {
		this.name = name;
		this.criteria = new CriterionSet();
		this.membersToInclude = new Group();
		this.membersToExclude = new Group();
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
	 * Adds {@link Member}s to include in receiving this {@code Award}. When
	 * using this field, to determine who should be eligible to receive this
	 * award, implement your logic in such a way that all {@link Member}s are
	 * included by default if the {@link SearchableHashSet} of {@link Member}s
	 * is empty.
	 * 
	 * @param members
	 * @return this.membersToInclude.addAll(Arrays.asList(members));
	 */
	@SuppressWarnings(UNCHECKED_WARNING)
	public boolean addMembersToInclude(Member... members) {
		return this.membersToInclude.addAll(Arrays.asList(members));
	}

	/**
	 * Adds {@link Member}s to exclude from receiving this {@code Award}.
	 * 
	 * @param members
	 * @return this.membersToExclude.addAll(Arrays.asList(members));
	 */
	@SuppressWarnings(UNCHECKED_WARNING)
	public boolean addMembersToExclude(Member... members) {
		return this.membersToExclude.addAll(Arrays.asList(members));
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
	 * @return an immutable copy of the criteria
	 * @see {@link SearchableHashSet#immutableCopy()}
	 */
	public SearchableHashSet<Criterion> getCriteria() {
		return criteria.immutableCopy();
	}

	/**
	 * @return an immutable copy of the membersToInclude
	 * @see {@link SearchableHashSet#immutableCopy()}
	 */
	@SuppressWarnings(UNCHECKED_WARNING)
	public SearchableHashSet<Member> getMembersToInclude() {
		return membersToInclude.immutableCopy();
	}

	/**
	 * @return an immutable copy of the membersToExclude
	 * @see {@link SearchableHashSet#immutableCopy()}
	 */
	@SuppressWarnings(UNCHECKED_WARNING)
	public SearchableHashSet<Member> getMembersToExclude() {
		return membersToExclude.immutableCopy();
	}
	
	/**
	 * Gets the uniqueId, which for an {@link Award} is the same as the name.
	 * 
	 * @see {@link Member#getUniqueId()}
	 */
	public String getUniqueId() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return "The " + this.name + " " + this.getClass().getSimpleName(); 
	}

}
