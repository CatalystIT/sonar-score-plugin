package com.catalyst.sonar.score.batch.trophies;


import java.util.ArrayList;
import java.util.List;

/**
 * The Trophy class represents a Trophy in Sonar for projects to earn,
 * with a name and a List of Criteria.
 */
public class Trophy {
	
	public static final String UNNAMED_TROPHY = "Unnamed Trophy";
	
	private String trophyName;
	private List<Criteria> criteriaList   = new ArrayList<Criteria>();
	
	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls this(UNNAMED_TROPHY).
	 */
	public Trophy(){
		this(UNNAMED_TROPHY);
	}
	
	/**
	 * Constructs a Trophy, setting the trophyName
	 * to equal the String name argument.
	 * @param name
	 */
	public Trophy(String name){
		this.trophyName = name;
	}
	
	/**
	 * Overrides {@link java.lang.Object#equals(java.lang.Object)},
	 * checking for meaningful equality based on the trophyName field.
	 */
	@Override
	public boolean equals(Object obj){
		if(this == obj){
			return true;
		}	
		if(obj == null){
			return false;
		}	
		if(!(obj instanceof Trophy)){
			return false;
		}	
		Trophy trophy = (Trophy) obj;
		if(trophyName == null){
			if(trophy.trophyName != null){
				return false;
			}	
		}
		else if(!trophyName.equals(trophy.trophyName)){
			return false;
		}		
		return true;		
	}
	
	/**
	 * Overrides {@link java.lang.Object#hashcode()},
	 * calculating a hashcode based on the trophyName field.
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int hash = 1;		
		hash+= (prime * hash + ((trophyName == null) ? 0 : trophyName.hashCode()));
		return hash;
		
	}
	
	/**
	 * getter for trophyName
	 * @return
	 */
	public String getTrophyName() {
		return trophyName;
	}

	/**
	 * setter for trophy name
	 * @param name
	 */
	public void setTrophyName(String name) {
		this.trophyName = name;
	}

	
	/**
	 * Gets list of Criteria
	 * @return
	 */
	protected List<Criteria> getCriteria() {
		return criteriaList;
	}

	/**
	 * Adds criteria to the List of Criteria
	 * @param criteria
	 */
	public void addCriteria(Criteria criteria) {
		criteriaList.add(criteria);
	}
}
