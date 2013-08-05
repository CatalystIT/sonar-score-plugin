/**
 * 
 */
package com.catalyst.sonar.score.api;

/**
 * A member can be added to a {@link Group}.
 * 
 * @author JDunn
 */
public interface Member extends ReceiverEntity {

	/**
	 * @return the name
	 */
	String getName();

	/**
	 * @return the uniqueId
	 */
	String getUniqueId();

	/**
	 * @return the description
	 */
	String getDescription();

	/**
	 * @param description
	 *            the description to set
	 */
	void setDescription(String description);

}