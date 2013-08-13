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
 * Sets up the Enhanced Filter List widget's properties with keys, types and default values
 * 
 */
@UserRole(UserRole.USER)
@Description("Compares point values for projects in the same project group.")
@WidgetCategory("Score")
public class ProjectComparisonWidget extends AbstractRubyTemplate implements
		RubyRailsWidget {
	public static final String PROJECT_COMPARISON_GROUP = "groupId";

	/**
	 * returns the widget's id represents the "widget_key" in the widgets table
	 */
	public String getId() {
		return "projectComparison";
	}

	/**
	 * returns the widget's title represents the widget's "name" in the widgets
	 * table
	 */
	public String getTitle() {
		return "Project comparison widget";
	}

	/**
	 * returns the path of the ruby widget
	 */
	@Override
	public String getTemplatePath() {
		return "/score/projectComparison_widget.html.erb";
	}

}
