package com.catalyst.sonar.score.ui;
import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.NavigationSection;
import org.sonar.api.web.Page;
import org.sonar.api.web.RubyRailsPage;
import org.sonar.api.web.UserRole;

@NavigationSection({NavigationSection.RESOURCE_TAB, NavigationSection.RESOURCE})
//@UserRole(UserRole.USER)
public final class UserProfilePage implements Page{
	
	public String getId() {
		    // URL of the controller
		   return "/userprofiles/index";
	}
	
	public String getTitle() {
		  return "User Profile";
	}

//	@Override
//	protected String getTemplatePath(){
//		return "/score/trophy_page.html.erb";
//	}

}
