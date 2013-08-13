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
 * Sets up the TitleCup widget's properties
 * @author mWomack
 *
 */
@UserRole(UserRole.USER)
@Description("Display the TitleCups a project holds.")
@WidgetCategory("Score")
public class TitleCupWidget extends AbstractRubyTemplate implements RubyRailsWidget{

	/**
	 * @return the id of the ruby widget
	 */
	public String getId() {
		return "titlecups";
	}

	/**
	 * @return the Title of the widget
	 */
	public String getTitle() {
		return "Title Cups";
	}

	/**
	 * @return the URL of the ruby widget view/index .erb file
	 */
	@Override
	protected String getTemplatePath() {
		return"/score/titlecup_widget.html.erb";
	}

	
}
