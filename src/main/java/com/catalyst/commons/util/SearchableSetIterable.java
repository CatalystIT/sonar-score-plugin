/**
 * 
 */
package com.catalyst.commons.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * {@code SearchableSetIterable<E>} is intended to be used when it is desirable
 * to return an Iterable for the contents of the {@link SearchableSet} without
 * being able to modify it. This class itself is final so that it can't be
 * extended to modify its contained SearchableSet.
 * 
 * @author JDunn
 */
public final class SearchableSetIterable<E> implements Iterable<E> {

	private SearchableSet<E> set;

	/**
	 * Constructs a SearchableSetIterable for a set.
	 * 
	 * @param set
	 */
	public SearchableSetIterable(SearchableSet<E> set) {
		this.set = set;
	}

	/**
	 * @see {@link SearchableSet#get(Object)}
	 * @param entity
	 * @return
	 */
	public E get(E entity) {
		return set.get(entity);
	}

	/**
	 * @see {@link Collection#contains(Object)}
	 * @param entity
	 * @return
	 */
	public boolean contains(E entity) {
		return set.contains(entity);
	}

	/**
	 * @see {@link Collection#containsAll(Collection)}
	 * @param c
	 * @return
	 */
	public boolean containsAll(Collection<?> c) {
		return set.contains(c);
	}

	/**
	 * @see {@link Collection#size()}
	 * @return
	 */
	public int size() {
		return set.size();
	}

	/**
	 * @see {@link Iterator.iterator()}
	 */
	public Iterator<E> iterator() {
		return set.iterator();
	}
}
