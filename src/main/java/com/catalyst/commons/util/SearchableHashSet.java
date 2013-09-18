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
package com.catalyst.commons.util;

import java.util.Collection;
import java.util.HashSet;

/**
 * {@link SearchableHashSet}{@code <E>} extends {@link HashSet}{@code <E>},
 * adding a get() method and overriding {@code add()} to disallow adding
 * {@code null}. It also adds a method to return an immutable copy of
 * {@code this}.
 */
public class SearchableHashSet<E> extends HashSet<E> implements SearchableSet<E> {

	/**
	 * @param o
	 * @return the {@code Object} of Type {@code <E>} from this {link
	 *         SearchableHashSet} that is meaningfully equal to the
	 *         {@code Object} argument, or {@code null} if no such
	 *         {@code Object} exists.
	 */
	public E get(Object o) {
		E entity = null;
		for (E entityInSet : this) {
			if (entityInSet.equals(o)) {
				entity = entityInSet;
			}
		}
		return entity;
	}

	/**
	 * Overrides {@link HashSet#add(E e)}, calling {@code super(e)} only if
	 * {@code e != null}. This prevents a {@code null} value from being added to
	 * this {link SearchableHashSet}.
	 * 
	 * @return true if this set did not already contain the specified element
	 *         <i>and</i> the specified element is not {@code null}.
	 * @see {@link java.util.HashSet#add(E e)}
	 */
	@Override
	public boolean add(E e) {
		return (e == null) ? false : super.add(e);
	}

	/**
	 * Returns an immutable copy of {@code this}.
	 * 
	 * @return
	 */
	public SearchableSetIterable<E> iterable() {
		return new SearchableSetIterable<E>(this);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6220028023304257059L;
}
