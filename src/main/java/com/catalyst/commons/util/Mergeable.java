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

/**
 * <p>The {@code Mergeable<E>} Interface defines a method merge(). It is intended
 * for use by Entities, where a field in the Entity is used to determine
 * equality, and a field that is a Collection or array is not used to determine
 * equality; and in the case of this second field, it is desirable for the
 * values to be merged.
 * </p>
 * 
 * @author JDunn
 */
public interface Mergeable<E> {

	/**
	 * Merges the values of one Entity with the values of another Entity. When
	 * implemented, merge() should do nothing and immediately return false if
	 * {@code !this.equals(entity)}, and should also return false if no values
	 * were added.
	 * 
	 * @param entity
	 * @return whether or not any values were added.
	 */
	boolean merge(E entity);

}
