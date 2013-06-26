/**
 * 
 */
package com.catalyst.sonar.score.api;

import org.sonar.api.resources.Project;

/**
 * ScoreProject extends {@link org.sonar.api.resources.Project}, adding a description field
 * and implementing {@link com.catalyst.sonar.score.api.Member}{@code <Project>}.
 * @author JDunn
 */
public class ScoreProject extends Project implements Member<Project> {

	/**
	 * Calls {@code super(key)}.  It should be noted that as of this writing,
	 * {@code super(key, branch, name)} sets the key to {@code += (":" + branch)}.
	 * This needs to be mentioned because Sonar failed to document this.
	 * @see {@link org.sonar.api.resources.Project#Project(String key)}
	 * @param key
	 */
	public ScoreProject(String key) {
		super(key);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Calls {@code super(key, branch, name)}.  It should be noted that as of this writing,
	 * {@code super(key, branch, name)} sets the key and the name to {@code += (":" + branch)}.
	 * This needs to be mentioned because Sonar failed to document this.
	 * @see {@link org.sonar.api.resources.Project#Project(String key, String name, String branch)}
	 * @param key
	 * @param branch
	 * @param name
	 */
	public ScoreProject(String key, String branch, String name) {
		super(key, branch, name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the uniqueId, which for a ScoreProject is the key.
	 * In {@link com.catalyst.sonar.score.api.ScoreProject}, calls super.getKey().
	 * @see com.catalyst.sonar.score.api.Member#getUniqueId()
	 */
	public String getUniqueId() {
		return super.getKey();
	}

}
