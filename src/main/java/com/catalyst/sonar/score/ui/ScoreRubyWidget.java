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

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.WidgetCategory;
/**
 * @author - Team Build Meister
 * Class that retrieves the Points Widget id, title and ruby template path
 */

/**
 * @UserRole:  Assigns the user role to "User" on Sonar
 * @Description: Adds a description of the Points Widget on Sonar under the "Points" category when configuring widgets
 * @WidgetCategory:  Creates a new category called "Points" on Sonar when configuring widgets
 */
@UserRole(UserRole.USER)
@Description("Calculate the points earned for a project.")
@WidgetCategory("Score")
public class ScoreRubyWidget extends AbstractRubyTemplate implements RubyRailsWidget{
	/**
	 * retrieves the widget id
	 */
	public String getId(){
		return "points";
	}
	/**
	 * retrieves the widget title
	 */
	public String getTitle(){
		return "Points";
	}
	/**
	 * returns the path of the ruby widget
	 */
	@Override
	public String getTemplatePath(){
		return "/score/score_widget.html.erb";
	}
}