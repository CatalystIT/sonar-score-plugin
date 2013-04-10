package com.catalyst.sonar.score.ui;
import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.WidgetCategory;


@UserRole(UserRole.USER)
@Description("Add the point widget here!")
@WidgetCategory("Points")
public class ScoreRubyWidget extends AbstractRubyTemplate implements RubyRailsWidget{
	
	public String getId(){
		return "points";
	}
	
	public String getTitle(){
		return "Points";
	}
	
	@Override
	public String getTemplatePath(){
		return "/score/score_widget.html.erb";
	}
}