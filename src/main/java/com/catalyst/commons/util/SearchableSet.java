/**
 * 
 */
package com.catalyst.commons.util;

import java.util.Set;

/**
 * Extends the {@link java.util.Set} Interface by specifying a get() method.
 * 
 * @author JDunn
 */
public interface SearchableSet<E> extends Set<E> {

	/**
	 * Gets the Entity from this SearchableSet that is equal to the Entity argument.
	 * @param entity
	 * @return
	 */
	E get(Object entity);
	
	SearchableSetIterable<E> iterable();
	
}
