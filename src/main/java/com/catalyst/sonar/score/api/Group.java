/**
 * 
 */
package com.catalyst.sonar.score.api;

import java.util.Arrays;

/**
 * @author JDunn
 *
 */
@SuppressWarnings("rawtypes")
public class Group<M extends Member> extends SearchableHashSet<M> implements Member<Group> {
	
	private String name;
	private String description;
	
	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls super().
	 */
	public Group() {
		super();
	}
	
	/**
	 * Constructs a <code>Group</code>, setting the name field with the name argument.
	 * @param name
	 */
	public Group(String name) {
		this.name = name;
	}
	
	/**
	 * Constructs a <code>Group</code>, setting the name field with the name argument
	 * and populating the members with the <code>Member</code> arguments.
	 * @param name
	 */
	public Group(String name, M... members) {
		this(name);
		addAll(Arrays.asList(members));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (null == obj) {
			return false;
		}
		if (!(obj instanceof Group)) {
			return false;
		}
		Group<M> other = (Group<M>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	/**
	 * Calls <code>super.equals(obj)</code>
	 * @see java.util.HashSet#equals(java.lang.Object)
	 * @param obj
	 * @return
	 */
	public boolean equalsMembers(Object obj) {
		return super.equals(obj);
	}	

	/* (non-Javadoc)
	 * @see com.catalyst.sonar.score.api.Member#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.catalyst.sonar.score.api.Member#setName(java.lang.String)
	 */
	public Group setName(String name) {
		this.name = name;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.catalyst.sonar.score.api.Member#getUniqueId()
	 */
	public String getUniqueId() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see com.catalyst.sonar.score.api.Member#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see com.catalyst.sonar.score.api.Member#setDescription(java.lang.String)
	 */
	public Group setDescription(String description) {
		this.description = description;
		return this;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = -1126059853127741286L;
}
