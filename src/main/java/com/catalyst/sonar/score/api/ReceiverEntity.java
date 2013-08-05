/**
 * 
 */
package com.catalyst.sonar.score.api;

/**
 * A {@code ReceiverEntity} is an {@link Entity} to which an
 * {@link AssignableScoreEntity} can be assigned, by relating them via the
 * Properties table in SonarQube's database.
 * 
 * <br/>
 * Examples include:
 * 
 * <br/>
 * - All classes implementing {@link Member}
 * 
 * <br/>
 * - {@link ProjectGroup}
 * 
 * @author JDunn
 */
public interface ReceiverEntity extends Entity {

}
