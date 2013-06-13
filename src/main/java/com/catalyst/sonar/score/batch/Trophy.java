package com.catalyst.sonar.score.batch;


import java.util.ArrayList;
import java.util.List;

public class Trophy {
	
	private String trophyName;
	private List<Criteria> criteriaList   = new ArrayList<Criteria>();
	
	public Trophy(){
		//no args constructor
	}
	
	public Trophy(String name){
		this.trophyName = name;
	}
	
	/**
	 * Overrides the equals method
	 */
	@Override
	public boolean equals(Object obj){
		if(this == obj){
			return true;
		}	
		if(obj == null){
			return false;
		}	
		if(!getClass().equals(obj.getClass())){
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
	 * Overrides the hashCode
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
