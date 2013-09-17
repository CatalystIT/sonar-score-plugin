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
 * Sets up the Project Awards Widget's properties with keys, types and default values
 * 
 */
@UserRole(UserRole.USER)
@Description("Displays all the awards earned by a project.")
@WidgetCategory("Score")
public class ProjectAwardsWidget extends AbstractRubyTemplate implements RubyRailsWidget{
	
	public static final String PROJECT_AWARDS_ = "projectAwards";
	/**
	 * returns the widget's id represents the "widget_key" in the widgets table
	 */
	public String getId() {		
		return "projectAwards";
	}
	/**
	 * returns the widget's title represents the widget's "name" in the widgets
	 * table
	 */
	public String getTitle() {
		return "Project Awards";
	}
	/**
	 * returns the path of the ruby widget
	 */
	@Override
	protected String getTemplatePath() {
		return "/score/project_awards_widget.html.erb";
		//return "C:/Users/JDunn/Dropbox/workspace/SonarScore/src/main/resources/score/project_awards_widget.html.erb";
	}


}
