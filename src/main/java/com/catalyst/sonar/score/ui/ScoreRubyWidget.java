package com.catalyst.sonar.score.ui;
import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.WidgetCategory;

/**
 * @UserRole:  Assigns the user role to "User" on Sonar
 * @Description: Adds a description of the Points Widget on Sonar under the "Points" category when configuring widgets
 * @WidgetCategory:  Creates a new category called "Points" on Sonar when configuring widgets
 */
@UserRole(UserRole.USER)
@Description("Add the point widget here!")
@WidgetCategory("Points")

/**
 * @author - Team Build Meister
 * Class that retrieves the Points Widget id, title and ruby template path
  */

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