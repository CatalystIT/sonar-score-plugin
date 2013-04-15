/**
 * 
 */
package com.catalyst.sonar.score.model;

import java.util.Set;
import java.util.HashSet;
import org.sonar.api.web.Filter;

/**
 * @author JDunn
 * 
 * This class represents a group of projects, grouped together for comparison.
 * They are grouped together either by tags, by a filter, or by both.
 *
 */
public class ProjectGroup {
	
	private Set<String> tags;
	
	private Filter filter;
	
	public ProjectGroup() {
		tags = new HashSet<String>();
		this.filter = null;
	}
	
	/**
	 * Adds a tag to the tags set.
	 * @param tag
	 * @return
	 */
	public boolean add(String tag) {
		return tags.add(tag);
	}
	
	/**
	 * Removes a tag from the tags set.
	 * @param tag
	 * @return
	 */
	public boolean remove(String tag) {
		return tags.remove(tag);
	}

	/**
	 * @return the filter
	 */
	public Filter getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(Filter filter) {
		this.filter = filter;
	}

}
