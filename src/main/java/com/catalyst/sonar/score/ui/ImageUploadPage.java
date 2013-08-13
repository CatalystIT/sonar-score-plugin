/*
 * Copyright 2013 Catalyst IT Services
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
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
