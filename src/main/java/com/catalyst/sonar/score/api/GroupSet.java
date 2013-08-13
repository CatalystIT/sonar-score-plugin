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

/**
 * {@link GroupSet} extends {@link SearchableHashSet}{@code <}{@link Group}
 * {@code >}, overriding {@code add()}.
 */
@SuppressWarnings("rawtypes")
public class GroupSet<G extends Group> extends SearchableHashSet<G> {

	/**
	 * If a {@link Group} with the same name is in this {@code GroupSet}, and if
	 * the {@code Group} argument is not null, it adds the members of the
	 * {@code Group} argument to the {@code Group} that is already in this
	 * {@code GroupSet}. Otherwise, it adds the {@code Group} to the
	 * {@code GroupSet}. If a new {@code Group} is not added, or if the
	 * {@code Group} already in the {@code GroupSet} receives no new
	 * {@code Member}s, false is returned.
	 * 
	 * @see {@link SearchableHashSet#add(E e)}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean add(G group) {
		boolean anyMembersAdded = true;
		// The method super.add(group) will run as part of the boolean
		// expression in the evaluation.
		// Thus, if the expression evaluates to false, super.add returned true
		// and the group was added.
		if (!super.add(group)) {
			anyMembersAdded = false;
			for (Object member : group.immutableCopy()) {
				anyMembersAdded = (this.get(group).add(member)) ? true
						: anyMembersAdded;
			}
		}
		return anyMembersAdded;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6220028023304257059L;
}
