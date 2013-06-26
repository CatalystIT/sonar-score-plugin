/**
 * 
 */
package com.catalyst.sonar.score.api;

import org.sonar.api.resources.Project;

/**
 * @author James
 *
 */
public class ScoreProject extends Project implements Member<Project> {

	/**
	 * @param key
	 */
	public ScoreProject(String key) {
		super(key);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param key
	 * @param branch
	 * @param name
	 */
	public ScoreProject(String key, String branch, String name) {
		super(key, branch, name);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.catalyst.sonar.score.api.Member#getUniqueId()
	 * in {@link com.catalyst.sonar.score.api.ScoreProject}, calls super.getKey().
	 */
	public String getUniqueId() {
		return super.getKey();
	}

}
