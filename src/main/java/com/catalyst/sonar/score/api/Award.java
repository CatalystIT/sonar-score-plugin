package com.catalyst.sonar.score.api;

import java.util.Arrays;

/**
 * The <code>Award</code> class represents an award in Sonar for
 * projects to earn, with a name and a <code>SearchableHashSet</code> of
 * Criteria.
 * @author JDunn
 */
public abstract class Award implements ScoreEntity {

	public static final String UNNAMED_AWARD = "Unnamed Award";

	private String name;
	private SearchableHashSet<Criterion> criteria;
	@SuppressWarnings(RAWTYPE_WARNING)
	private Group membersToInclude;
	@SuppressWarnings(RAWTYPE_WARNING)
	private Group membersToExclude;

	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls
	 * {@code this(UNNAMED_AWARD)}, preventing any fields from being {@code null}.
	 */
	public Award() {
		this(UNNAMED_AWARD);
	}

	/**
	 * Constructs an {@code Award}, setting the name to equal the
	 * {@code String} name argument.
	 * 
	 * @param name
	 */
	@SuppressWarnings(RAWTYPE_WARNING)
	public Award(String name) {
		this.name = name;
		this.criteria = new SearchableHashSet<Criterion>();
		this.membersToInclude = new Group();
		this.membersToExclude = new Group();
	}

	/**
	 * Adds a {@code Criterion} to the criteria.
	 * 
	 * @param criterion
	 */
	public boolean addCriterion(Criterion criterion) {
		return this.criteria.add(criterion);
	}
	
	/**
	 * Adds {@code Criteria} to the criteria.
	 * 
	 * @param criterion
	 */
	public Award addCriteria(Criterion... criteria) {
		this.criteria.addAll(Arrays.asList(criteria));
		return this;
	}

	/**
	 * Adds Members to include from receiving the {@code Award}.
	 * Code elsewhere should include all members by default if this list is empty.
	 * @param members
	 * @return this
	 */
	@SuppressWarnings({ RAWTYPE_WARNING, UNCHECKED_WARNING })
	public Award addMembersToInclude(Member... members) {
		this.membersToInclude.addAll(Arrays.asList(members));
		return this;
	}

	/**
	 * Adds Members to exclude from receiving the {@code Award}.
	 * @param members
	 * @return this
	 */
	@SuppressWarnings({ RAWTYPE_WARNING, UNCHECKED_WARNING })
	public Award addMembersToExclude(Member... members) {
		this.membersToExclude.addAll(Arrays.asList(members));
		return this;
	}

	/**
	 * Overrides {@link java.lang.Object#equals(java.lang.Object)}, checking for
	 * meaningful equality based on the name field.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj.getClass().equals(this.getClass()))) {
			return false;
		}
		Award other = (Award) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/**
	 * Overrides {@link java.lang.Object#hashCode()}, calculating a hashCode
	 * based on the name field.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 1;
		hash += (prime * hash + ((name == null) ? 0 : name.hashCode()));
		return hash;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 * @return this
	 */
	public Award setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return an immutable copy of the criteria
	 */
	public SearchableHashSet<Criterion> getCriteria() {
		return criteria.immutableCopy();
	}
	
	/**
	 * @return an immutable copy of the membersToInclude
	 */
	@SuppressWarnings(UNCHECKED_WARNING)
	public SearchableHashSet<Criterion> getMembersToInclude() {
		return membersToInclude.immutableCopy();
	}
	
	/**
	 * @return an immutable copy of the membersToExclude
	 */
	@SuppressWarnings(UNCHECKED_WARNING)
	public SearchableHashSet<Criterion> getMembersToExclude() {
		return membersToExclude.immutableCopy();
	}

}
