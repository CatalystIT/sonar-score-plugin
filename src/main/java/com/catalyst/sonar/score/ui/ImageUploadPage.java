package com.catalyst.sonar.score.ui;

import org.sonar.api.web.NavigationSection;
import org.sonar.api.web.Page;
/**
 * ImageUpload Page adds a page to the sidebar. The url directs the page to the index.html.erb file
 * @author Team Build Meister
 *
 */
@NavigationSection({NavigationSection.RESOURCE_TAB, NavigationSection.CONFIGURATION})
public class ImageUploadPage implements Page{
	public String getId(){
		//returns the URL of the ruby widget view/index file
		return "/images/index";
	}
	
	public String getTitle(){
		//returns the title
		return "Image Upload";
	}
}
