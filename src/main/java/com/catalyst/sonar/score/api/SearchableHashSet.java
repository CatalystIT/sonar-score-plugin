/**
 * 
 */
package com.catalyst.sonar.score.api;

import java.util.Collection;
import java.util.HashSet;

/**
 * {@link SearchableHashSet}{@code <E>} extends {@link HashSet}{@code <E>},
 * adding a get() method and overriding {@code add()} to disallow adding
 * {@code null}. It also adds a method to return an immutable copy of
 * {@code this}.
 */
public class SearchableHashSet<E> extends HashSet<E> {

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
	public ImmutableSearchableHashSet<E> immutableCopy() {
		return new ImmutableSearchableHashSet<E>().addAllAnyway(this);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6220028023304257059L;

	/**
	 * Extends {@link SearchableHashSet}{@code <E>}, enforcing immutability by
	 * overriding all methods that would modify this Set.
	 * 
	 * @author JDunn
	 * 
	 * @param <E>
	 */
	private static final class ImmutableSearchableHashSet<E> extends
			SearchableHashSet<E> {

		public static final String EXCEPTION_MESSAGE = "This SearchableHashSet is immutable and cannot be modified.";

		/**
		 * @throws {@link UnsupportedOperationException} to enforce
		 *         immutability.
		 */
		@Override
		public boolean add(E e) {
			throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
		}

		/**
		 * @throws {@link UnsupportedOperationException} to enforce
		 *         immutability.
		 */
		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
		}

		/**
		 * @throws {@link UnsupportedOperationException} to enforce
		 *         immutability.
		 */
		@Override
		public void clear() {
			throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
		}

		/**
		 * @throws {@link UnsupportedOperationException} to enforce
		 *         immutability.
		 */
		@Override
		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
		}

		/**
		 * @throws {@link UnsupportedOperationException} to enforce
		 *         immutability.
		 */
		@Override
		public boolean addAll(Collection<? extends E> c) {
			throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
		}

		/**
		 * @throws {@link UnsupportedOperationException} to enforce
		 *         immutability.
		 */
		@Override
		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
		}

		/**
		 * calls {@code super.addAll(set)} to allow the contents to be set
		 * within the {@link SearchableHashSet} class.
		 * 
		 * @param set
		 * 
		 * @return this
		 */
		private ImmutableSearchableHashSet<E> addAllAnyway(
				SearchableHashSet<E> set) {
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
