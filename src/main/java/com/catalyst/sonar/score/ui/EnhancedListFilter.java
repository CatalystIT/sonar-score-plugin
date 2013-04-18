package com.catalyst.sonar.score.ui;
import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.WidgetCategory;
import org.sonar.api.web.WidgetProperties;
import org.sonar.api.web.WidgetProperty;
import org.sonar.api.web.WidgetPropertyType;
import org.sonar.api.web.WidgetScope;
import static org.sonar.api.web.WidgetScope.GLOBAL;

/**
 * 
 * Sets up the the Enhanced Filter List's settings:  @UserRole defines the user role as "user", @Description defines
 * and displays the description for the widget, @WidgetCategory puts the Enhanced List Filter in the "Points" and
 * "Filters" category and the @WidgetScope sets the widget scope to "GLOBAL" as opposed to "PROJECT" 
 *
 */

@UserRole(UserRole.USER)
@Description("Displays the result of a pre-configured measure filter as a list with the project logo.")
@WidgetCategory({"Filters","Points"})
@WidgetScope(GLOBAL)

/**
 * Sets up the Enhanced Filter List widget's properties with keys, types and default values
 * 
 */
@WidgetProperties({
	 @WidgetProperty(key = EnhancedListFilter.FILTER_PROPERTY, type = WidgetPropertyType.FILTER, optional = false),
	  @WidgetProperty(key = EnhancedListFilter.PAGE_SIZE_PROPERTY, type = WidgetPropertyType.INTEGER, defaultValue = "30"),
	  @WidgetProperty(key = EnhancedListFilter.DISPLAY_FILTER_DESCRIPTION, type = WidgetPropertyType.BOOLEAN, defaultValue = "false")
})

/**
 *  Class that retrieves the Enhanced Filter List widget's id, title and ruby template path
 * @author Team Build Meister
 *
 */
public class EnhancedListFilter extends AbstractRubyTemplate implements RubyRailsWidget {
	 public static final String FILTER_PROPERTY = "filter";
	  public static final String PAGE_SIZE_PROPERTY = "pageSize";
	  public static final String DISPLAY_FILTER_DESCRIPTION = "displayFilterDescription";
	  
	/**
	 * returns the widget's id
	 */
  public String getId() {
    return "enhancedList";
  }
/**
 * returns the widget's title
 */
  public String getTitle() {
    return "Enhanced List";
  }
/**
 * returns the path of the ruby widget
 */
  @Override
  public String getTemplatePath() {
    return "/score/enhancedList_widget.html.erb";
  }
}

