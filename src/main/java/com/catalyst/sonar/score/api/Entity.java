/**
 * 
 */
package com.catalyst.sonar.score.api;

//imported for the link in the javadoc
import org.sonar.api.database.configuration.Property;

/**
 * Entity represents an object that is persisted either directly or indirectly
 * to Sonar's database. Two interfaces directly extend Entity:
 * {@link ScoreEntity} and {@link SonarEntity}.
 * 
 * <br/>
 * <br/>
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
 * <br/>
 * <br/>
 * A {@link SonarEntity} is an object derived from Sonar's database
 * models, and as such are persisted directly to the respective table in Sonar's
 * database.
 * 
 * <br/>
 * Examples include:
 * 
 * <br/>
 *  - {@link ScoreProject}
 * 
 * <br/>
 *  - {@link ScoreUser}
 * 
 * @author JDunn
 * 
 */
public interface Entity {
	
	/**
	 * Gets the field that functions as the uniqueId for this
	 * {@link Entity}. The name of this field will vary from implementation
	 * to implementation, and most likely the field will have its own getter as
	 * well.
	 * 
	 * @return
	 */
	String getUniqueId();

}
