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

	public String getId() {
		// retrieves the widget Id
		return "trophies";
	}

	public String getTitle() {
		//retrieves the widget title
		return "Trophies";
	}

	@Override
	protected String getTemplatePath() {
		// retrieves path of the widget
		return"/score/trophy_widget.html.erb";
	}

	
}
