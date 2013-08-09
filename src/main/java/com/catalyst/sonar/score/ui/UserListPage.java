package com.catalyst.sonar.score.ui;
import org.sonar.api.web.NavigationSection;
import org.sonar.api.web.Page;

@NavigationSection({NavigationSection.HOME, NavigationSection.RESOURCE_TAB, NavigationSection.RESOURCE, NavigationSection.CONFIGURATION})
public final class UserListPage implements Page{
	
	public String getId() {
		    // URL of the controller
		   return "/userlist/index";
	}
	
	public String getTitle() {
		  return "Users";
	}

}
