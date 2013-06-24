package com.catalyst.sonar.score.ui;
import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.NavigationSection;
import org.sonar.api.web.Page;
import org.sonar.api.web.RubyRailsPage;
import org.sonar.api.web.UserRole;

@NavigationSection({NavigationSection.RESOURCE_TAB, NavigationSection.CONFIGURATION})
//@NavigationSection(NavigationSection.CONFIGURATION)
//@UserRole(UserRole.ADMIN)
public final class TrophyPage implements Page{
	
	public String getId() {
		    // URL of the controller
		   return "/trophies/index";
		//return "trophy_page";
	}
	
	public String getTitle() {
		  return "Trophy Page";
	}

//	@Override
//	protected String getTemplatePath(){
//		return "/score/trophy_page.html.erb";
//	}

}
