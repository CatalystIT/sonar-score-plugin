package com.catalyst.sonar.score.ui;

import static org.sonar.api.web.WidgetScope.GLOBAL;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.WidgetCategory;
import org.sonar.api.web.WidgetProperties;
import org.sonar.api.web.WidgetProperty;
import org.sonar.api.web.WidgetPropertyType;
import org.sonar.api.web.WidgetScope;



@UserRole(UserRole.USER)
@Description("Compares point values for projeccts in the same project group.")
@WidgetCategory("Points")
@WidgetScope(GLOBAL)

/**
 * Sets up the Enhanced Filter List widget's properties with keys, types and default values
 * 
 */
@WidgetProperties({
	 @WidgetProperty(key = ProjectComparisonWidget.PROJECT_COMPARISON_GROUP, type = WidgetPropertyType.STRING, optional = false),
	  @WidgetProperty(key = ProjectComparisonWidget.DISPLAY_WIDGET_DESCRIPTION, type = WidgetPropertyType.BOOLEAN, defaultValue = "false")
})

public class ProjectComparisonWidget extends AbstractRubyTemplate implements RubyRailsWidget{
	public static final String PROJECT_COMPARISON_GROUP = "groupId";
	public static final String DISPLAY_WIDGET_DESCRIPTION = "projectComparison ";
	  /**
		 * returns the widget's id
		 * represents the "widget_key" in the widgets table
		 */
	  public String getId() {
	    return "projectComparsion";
	  }
	/**
	 * returns the widget's title
	 * represents the widget's "name" in the widgets table
	 */
	  public String getTitle() {
	    return "Project comparison widget";
	  }
	/**
	 * returns the path of the ruby widget
	 */
	  @Override
	  public String getTemplatePath() {
	    return "/score/projectComparison_widget.html.erb";
	  }



}
