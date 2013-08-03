package com.catalyst.sonar.score.ui;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.WidgetCategory;
/**
 * Sets up the Trophy widget's properties
 * @author Team Build Meister
 *
 */
@UserRole(UserRole.USER)
@Description("Calculate the trophies earned for a project.")
@WidgetCategory("Score")
public class TrophyWidget extends AbstractRubyTemplate implements RubyRailsWidget{

	/**
	 * @return the id of the ruby widget
	 */
	public String getId() {
		return "trophies";
	}

	/**
	 * @return the Title of the widget
	 */
	public String getTitle() {
		return "Trophies";
	}

	/**
	 * @return the URL of the ruby widget view/index .erb file
	 */
	@Override
	protected String getTemplatePath() {
		// retrieves path of the widget
		return"/score/trophy_widget.html.erb";
	}

	
}
