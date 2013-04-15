/**
 * 
 */
package com.catalyst.sonar.score.model;

import java.io.Serializable;

/**
 * @author JDunn
 *
 */
public abstract class Entity implements Serializable {
	
	/**
	 * This field is intended as a unique identifier per type of entity.
	 */
	private String name;
	
	/**
	 * returns a String concatenating name + the classname.
	 * @return
	 */
	protected String fileName() {
		String className =  this.getClass().getName();
		className = className.substring(className.lastIndexOf('.') + 1);
		return name + className;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 136843162032769943L;
}
