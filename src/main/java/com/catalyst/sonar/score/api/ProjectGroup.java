/**
 * 
 */
package com.catalyst.sonar.score.api;

/**
 * Defines a {@link Group} of {@link ScoreProject}s.
 * 
 * @author JDunn
 */
public class ProjectGroup extends Group<ScoreProject> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2004714738878257027L;

	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls
	 * {@code super()}.
	 * @see {@link Group#Group()}
	 */
	public ProjectGroup() {
		super();
	}

	/**
	 * @param name
	 * @param members
	 */
	public ProjectGroup(String name, ScoreProject... members) {
		super(name, members);
	}

	/**
	 * @param name
	 */
	public ProjectGroup(String name) {
		super(name);
	}

}
