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
