package com.catalyst.sonar.reference.ui;
import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.WidgetCategory;
import org.sonar.api.web.WidgetProperties;
import org.sonar.api.web.WidgetProperty;

@UserRole(UserRole.USER)
@Description("How to use Ruby Widget Api")
@WidgetCategory("Score")
@WidgetProperties({
	  @WidgetProperty(key = "input1",
	    description = "This is a mandatory parameter",
	    optional = false
	  ),
	  @WidgetProperty(key = "input2",
	    description = "This is an optional parameter"
	  )
	})

public class ScoreRubyWidget extends AbstractRubyTemplate implements RubyRailsWidget{
	
	public String getId(){
		return "score";
	}
	
	public String getTitle(){
		return "Score";
	}
	
	@Override
	protected String getTemplatePath(){
		return "/score/score_widget.html.erb";
	}
}