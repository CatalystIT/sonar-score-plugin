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
package com.catalyst.sonar.score.ui;

import org.sonar.api.web.NavigationSection;
import org.sonar.api.web.Page;
import org.sonar.api.web.UserRole;

/**
 * Page that allows an admin to configure Awards, such as Trophies and Title
 * Cups.
 * 
 * @author mWomack
 * 
 */
@NavigationSection({ NavigationSection.RESOURCE_TAB,
		NavigationSection.CONFIGURATION })
@UserRole(UserRole.ADMIN)
public final class TrophyPage implements Page {

	/**
	 * @return the URL of the Awards Page
	 */
	public String getId() {
		return "/awards/index";
	}

	/**
	 * @return the title of the Awards Page
	 */
	public String getTitle() {
		return "Awards Page";
	}

}
