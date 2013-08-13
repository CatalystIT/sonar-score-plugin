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

import org.sonar.api.database.configuration.Property;

/**
 * A {@link ScoreEntity} is an object unique to the SCORE plugin and as such are
 * persisted in a roundabout fashion as a {@link Property} through the
 * Properties table.
 * 
 * <br/>
 * Examples include:
 * 
 * <br/>
 *  - {@link Award}
 * 
 * <br/>
 *  - {@link ProjectGroup}
 * 
 * @author JDunn
 */
public interface ScoreEntity extends Entity {

	String RAWTYPE_WARNING = "rawtypes";
	String UNCHECKED_WARNING = "unchecked";

}
