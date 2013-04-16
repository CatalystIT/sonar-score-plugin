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
public class ProjectGroup extends Entity {
	
	private final Set<String> tags;
	
	private Filter filter;
	
	/**
	 * constructs the ProjectGroup, sets the name, sets filter to null and instantiates tags.
	 * @param name
	 */
	public ProjectGroup(String name) {
		super(name);
		this.tags = new HashSet<String>();
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
	 * Removes a tag from the tags set.
	 * @param tag
	 * @return
	 */
	public boolean contains(String tag) {
		return tags.contains(tag);
	}

	/**
	 * @return the tags
	 */
	public Set<String> getTags() {
		return tags;
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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
