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
 * {@link Group} extends {@link SearchableHashSet} where the type must extend
 * {@link Member}.
 * 
 * @author JDunn
 */
@SuppressWarnings("rawtypes")
public class Group<M extends Member> extends SearchableHashSet<M> implements
		Member, AssignableScoreEntity {

	private String name;
	private String description;

	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls
	 * {@code super()}.
	 */
	public Group() {
		super();
	}

	/**
	 * Constructs a {@code Group}, setting the name field with the name
	 * argument.
	 * 
	 * @param name
	 */
	public Group(String name) {
		this.name = name;
	}

	/**
	 * Constructs a {@code Group}, setting the name field with the name argument
	 * and populating the members with the {@code Member} arguments.
	 * 
	 * @param name
	 */
	@SafeVarargs
	public Group(String name, M... members) {
		this(name);
		addAll(Arrays.asList(members));
	}

	/**
	 * Returns a {@code hashCode} based on the name.
	 * 
	 * @see {@link Object#hashCode()}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Tests for meaningful equality based on the name.
	 * 
	 * @see {@link Object#equals(Object)}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (null == obj) {
			return false;
		}
		if (!(obj instanceof Group)) {
			return false;
		}
		Group<M> other = (Group<M>) obj;
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
	 * Calls {@code super.equals(obj)}
	 * 
	 * @see {@link HashSet#equals(Object)}
	 * @param obj
	 * @return
	 */
	public boolean equalsMembers(Object obj) {
		return super.equals(obj);
	}

	/**
	 * Gets the name.
	 * 
	 * @see {@link Member#getName()}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @see {@link Member#setName(String)}
	 */
	public Group setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Gets the uniqueId, which for a {@link Group} is the same as the name.
	 * 
	 * @see {@link Member#getUniqueId()}
	 */
	public String getUniqueId() {
		return this.name;
	}

	/**
	 * Gets the description.
	 * 
	 * @see {@link Member#getDescription()}
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @see {@link Member#setDescription(String)}
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1126059853127741286L;
}
