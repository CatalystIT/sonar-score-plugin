/**
 * 
 */
package com.catalyst.sonar.score.api;

/**
 * An {@code AssignableScoreEntity} is a {@link ScoreEntity} that can be
 * assigned to another {@link Entity} by relating it via the Properties table in
 * SonarQube's database.
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
public interface AssignableScoreEntity extends ScoreEntity {

}
