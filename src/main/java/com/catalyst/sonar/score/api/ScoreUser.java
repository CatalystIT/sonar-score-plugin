/**
 * 
 */
package com.catalyst.sonar.score.api;

import org.sonar.api.database.model.User;

/**
 * ScoreUser extends {@link org.sonar.api.database.model.User}, adding a description field
 * and implementing {@link com.catalyst.sonar.score.api.Member}{@code <Project>}.
 * @author JDunn
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

	/**
	 * Gets the description.
	 * @see com.catalyst.sonar.score.api.Member#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * @see com.catalyst.sonar.score.api.Member#setDescription(java.lang.String)
	 */
	public User setDescription(String description) {
		this.description = description;
		return this;
	}
	
	/**
	 * Gets the uniqueId, which for a ScoreUser is the login.
	 * In {@link com.catalyst.sonar.score.api.ScoreUser}, calls super.getLogin().
	 * @see com.catalyst.sonar.score.api.Member#getUniqueId()
	 */
	public String getUniqueId() {
		return super.getLogin();
	}

}
