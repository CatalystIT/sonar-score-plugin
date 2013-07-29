package com.catalyst.sonar.score.ui;
import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.NavigationSection;
import org.sonar.api.web.Page;
import org.sonar.api.web.RubyRailsPage;
import org.sonar.api.web.UserRole;

/**
 * Page that allows an admin to create trophies and title cups.
 * @author team Build Meister
 *
 */
@NavigationSection({NavigationSection.RESOURCE_TAB, NavigationSection.CONFIGURATION})
@UserRole(UserRole.ADMIN)
public final class TrophyPage implements Page{
	/**
	 * retrieves the URL of the trophy page
	 */
	public String getId() {		    
		   return "/trophies/index";
		
	}
	
	/**
	 * retrieves the title of the Awards Page
	 */
	public String getTitle() {
		  return "Awards Page";
	}



}
