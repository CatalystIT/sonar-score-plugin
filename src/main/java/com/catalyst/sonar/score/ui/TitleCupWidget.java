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
