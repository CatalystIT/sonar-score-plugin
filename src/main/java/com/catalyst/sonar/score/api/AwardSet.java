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
 * {@code AwardSet} extends {@code SearchableHashSet<Award>}, overriding {@code add()}.
 */
public class AwardSet<A extends Award> extends SearchableHashSet<A> {

	/**
	 * If an {@code Award} with the same name is in this {@code AwardSet},
	 * and if the {@code Award} argument is not null, it adds the criteria
	 * and members to include and exclude of the {@code Award} argument
	 * to the {@code Award} that is already in this {@code AwardSet}. 
	 * Otherwise, it adds the {@code Award} to this {@code AwardSet}.
	 * If a new {@code Award} is not added, or if the {@code Award}
	 * already in this {@code AwardSet} receives no new {@code Criterion},
	 * false is returned.
	 * @see {@link com.catalyst.commons.util.SearchableHashSet#add(E e)}
	 */
	@Override
	public boolean add(A award) {
		boolean anyInfoAdded = true;
		//The method super.add(award) will run as part of the boolean expression in the evaluation.
		//Thus, if the expression evaluates to false, super.add returned true and the award was added.
		if(!super.add(award)) {
			anyInfoAdded = false;
			for(Criterion criterion : award.getCriteria()) {
				anyInfoAdded = (this.get(award).addCriterion(criterion)) ? true : anyInfoAdded;
			}
		}
		return anyInfoAdded;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6220028023304257059L;
}

