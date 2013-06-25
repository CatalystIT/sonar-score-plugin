/**
 * 
 */
package com.catalyst.sonar.score.api;

import org.sonar.api.database.model.User;

/**
 * @author James
 *
 */
public class ScoreUser extends User implements Member<User> {
	
	private String description;

	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls super().
	 */
	public ScoreUser() {
		super();
	}
	
	/**
	 * Constructs a ScoreUser, setting the name field with the name argument.
	 * @param name
	 */
	public ScoreUser(String name, String email, String login) {
		super.setName(name);
		super.setEmail(email);
		super.setLogin(login);
	}

	/* (non-Javadoc)
	 * @see com.catalyst.sonar.score.api.Member#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see com.catalyst.sonar.score.api.Member#setDescription(java.lang.String)
	 */
	public User setDescription(String description) {
		this.description = description;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.catalyst.sonar.score.api.Member#getUniqueId()
	 * in {@link com.catalyst.sonar.score.api.ScoreUser}, calls super.getLogin().
	 */
	public String getUniqueId() {
		return super.getLogin();
	}

}
