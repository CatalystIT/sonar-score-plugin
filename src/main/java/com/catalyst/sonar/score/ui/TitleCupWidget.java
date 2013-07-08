package com.catalyst.sonar.score.ui;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.WidgetCategory;
/**
 * Sets up the TitleCup widget's properties
 * @author Team Build Meister
 *
 */
@UserRole(UserRole.USER)
@Description("Display the TitleCups a project holds.")
@WidgetCategory("Score")
public class TitleCupWidget extends AbstractRubyTemplate implements RubyRailsWidget{

	public String getId() {
		// retrieves the widget Id
		return "titlecups";
	}

	public String getTitle() {
		//retrieves the widget title
		return "TitleCups";
	}

	@Override
	protected String getTemplatePath() {
		// retrieves path of the widget
		return"/score/titlecup_widget.html.erb";
	}

	
}
