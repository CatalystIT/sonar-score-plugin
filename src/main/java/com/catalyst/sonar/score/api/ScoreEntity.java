/**
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
