/**
 * 
 */
package com.catalyst.sonar.score.api;

import java.util.Collection;
import java.util.HashSet;


/**
 * {@code SearchableHashSet<E>} extends {@code HashSet<E>}, adding a get() method
 * and overriding {@code add()} to disallow adding {@code null}.
 * It also adds a method to return an immutable copy of {@code this}.
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
	 * Returns an immutable copy of {@code this}.
	 * @return
	 */
	public ImmutableSearchableHashSet<E> immutableCopy() {
		return new ImmutableSearchableHashSet<E>().addAllAnyway(this);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6220028023304257059L;
	
	/**
	 * Extends {@code SearchableHashSet<E>}, enforcing immutability by overriding all methods that would modify the list.
	 * @author JDunn
	 *
	 * @param <E>
	 */
	private static final class ImmutableSearchableHashSet<E> extends SearchableHashSet<E> {
		
		public static final String EXCEPTION_MESSAGE = "This SearchableHashSet is immutable and cannot be modified.";

		/**
		 * @throws {@code UnsupportedOperationException} to enforce immutability.
		 */
		@Override
		public boolean add(E e) {
			throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
		}

		/**
		 * @throws {@code UnsupportedOperationException} to enforce immutability.
		 */
		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
		}

		/**
		 * @throws {@code UnsupportedOperationException} to enforce immutability.
		 */
		@Override
		public void clear() {
			throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
		}

		/**
		 * @throws {@code UnsupportedOperationException} to enforce immutability.
		 */
		@Override
		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
		}

		/**
		 * @throws {@code UnsupportedOperationException} to enforce immutability.
		 */
		@Override
		public boolean addAll(Collection<? extends E> c) {
			throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
		}

		/**
		 * @throws {@code UnsupportedOperationException} to enforce immutability.
		 */
		@Override
		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
		}
		
		/**
		 * calls {@code super.addAll(set)} to allow the contents to be set within the {@code SearchableHashSet} class.
		 * @param set;
		 * @return this;
		 */
		private ImmutableSearchableHashSet<E> addAllAnyway(SearchableHashSet<E> set) {
			for (E e : set) {
	            super.add(e);
			}
			return this;
		}
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 2323258698323274104L;
	}
}

