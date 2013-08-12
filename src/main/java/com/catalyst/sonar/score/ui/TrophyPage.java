package com.catalyst.sonar.score.ui;

import org.sonar.api.web.NavigationSection;
import org.sonar.api.web.Page;
import org.sonar.api.web.UserRole;

/**
 * Page that allows an admin to configure Awards, such as Trophies and Title
 * Cups.
 * 
 * @author mWomack
 * 
 */
@NavigationSection({ NavigationSection.RESOURCE_TAB,
		NavigationSection.CONFIGURATION })
@UserRole(UserRole.ADMIN)
public final class TrophyPage implements Page {

	/**
	 * @return the URL of the Awards Page
	 */
	public String getId() {
		return "/awards/index";
	}

	/**
	 * @return the title of the Awards Page
	 */
	public String getTitle() {
		return "Awards Page";
	}

}
