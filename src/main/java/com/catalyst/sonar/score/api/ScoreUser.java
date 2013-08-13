/*
 * Copyright 2013 Catalyst IT Services
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.catalyst.sonar.score.api;

import org.sonar.api.database.model.User;

/**
 * ScoreUser extends {@link org.sonar.api.database.model.User}, adding a description field
 * and implementing {@link com.catalyst.sonar.score.api.Member}{@code <Project>}.
 * @author JDunn
 */
public class ScoreUser extends User implements Member {
	
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
	public void setDescription(String description) {
		this.description = description;
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
