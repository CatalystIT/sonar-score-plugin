/**
 * 
 */
package com.catalyst.sonar.score.api;

import org.sonar.api.BatchExtension;

/**
 * @author JDunn
 * Provides a common parent for most classes in the package
 * <code>com.catalyst.sonar.score.api</code>,
 * extending {@link org.sonar.api.BatchExtension}.
 *
 */
public interface ScoreEntity extends BatchExtension {
	
	static final String RAWTYPE_WARNING = "rawtypes";
	static final String UNCHECKED_WARNING = "unchecked";

}
