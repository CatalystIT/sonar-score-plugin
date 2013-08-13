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
