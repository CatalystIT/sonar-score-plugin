/**
 * 
 */
package com.catalyst.sonar.score.api;

import java.util.HashSet;


/**
 * SearchableHashSet<E> extends HashSet<E>, adding a get() method
 * and overriding the add() method to disallow adding <code>null</code>.
 */
public class SearchableHashSet<E> extends HashSet<E> {
	
	/**
	 * @param o
	 * @return the <code>Object</code> of Type <code>E</code> from the <code>SearchableHashSet</code>
	 * that is meaningfully equal to the <code>Object</code> argument,
	 * or <code>null</code> if no such <code>Object</code> exists.
	 */
	public E get(Object o) {
		E entity = null;
		for(E entityInSet : this) {
			if(entityInSet.equals(o)) {
				entity = entityInSet;
			}
		}
		return entity;
	}
	
	/**
	 * Overrides {@link java.util.HashSet#add(E e)}, calling super(e) only if <code>e != null</code>.
	 * This prevents a <code>null</code> value from ever being added to any <code>SearchableHashSet</code>
	 * @return true if this set did not already contain the specified
	 * element <i>and</i> the specified element is not <code>null</code>.
	 * @see {@link java.util.HashSet#add(E e)}

	 */
	@Override
	public boolean add(E e) {
		return (e == null) ? false : super.add(e);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6220028023304257059L;
}

