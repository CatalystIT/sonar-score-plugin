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
	@Override
	public String getId() {		
		return "projectAwards";
	}
	/**
	 * returns the widget's title represents the widget's "name" in the widgets
	 * table
	 */
	@Override
	public String getTitle() {
		return "Project Awards";
	}
	/**
	 * returns the path of the ruby widget
	 */
	@Override
	protected String getTemplatePath() {
		return "/score/project_awards_widget.html.erb";
	}


}
