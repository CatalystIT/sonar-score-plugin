package com.catalyst.sonar.score.ui;

import org.sonar.api.web.NavigationSection;
import org.sonar.api.web.Page;
/**
 * ImageUpload Page adds a page to the sidebar. The url directs the page to the index.html.erb file.
 * @author Team Build Meister
 *
 */
@NavigationSection({NavigationSection.RESOURCE_TAB, NavigationSection.RESOURCE})
public class ImageUploadPage implements Page{
	
	/**
	 * @return the URL of the ruby view/index .erb file
	 */
	public String getId(){
		return "/images/index";
	}
	
	/**
	 * @return the Title of the page
	 */
	public String getTitle(){
		return "Edit Project Profile";
	}
}
