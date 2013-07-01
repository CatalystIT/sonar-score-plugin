/**
 * 
 */
package com.catalyst.sonar.score.api;

import org.sonar.api.BatchExtension;

/**
 * Provides a common parent for most classes in the package
 * {@code com.catalyst.sonar.score.api}, extending
 * {@link org.sonar.api.BatchExtension}.
 * 
 * @author JDunn
 */
public interface ScoreEntity extends BatchExtension {

	String RAWTYPE_WARNING = "rawtypes";
	String UNCHECKED_WARNING = "unchecked";

	/**
	 * Gets the field that functions as the uniqueId for this
	 * {@link ScoreEntity}. The name of this field will vary from implementation
	 * to implementation, and most likely the field will have its own getter as
	 * well.
	 * 
	 * @return
	 */
	String getUniqueId();

}
