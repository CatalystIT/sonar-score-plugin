/**
 * 
 */
package com.catalyst.sonar.score.batch;

import java.util.HashSet;

/**
 * TrophySet extends HashSet<Trophy>, overriding the add() method and adding a get() method.
 */
public class TrophySet extends HashSet<Trophy> {

	/**
	 * if a Trophy with the same name is in the TrophySet, it adds the criteria of the Trophy argument
	 * to the trophy that is already in the TrophySet.  Otherwise, it adds the Trophy to the TrophySet.
	 * It always returns true.
	 */
	@Override
	public boolean add(Trophy trophy) {
		//The method super.add(trophy) will run as part of the boolean expression.
		//Thus, if the expression returns false, super.add returned true and the trophy was added.
		if(!super.add(trophy)) {			
			for(Criteria criteria : trophy.getCriteria()) {
				this.get(trophy).getCriteria().add(criteria);
			}						
		}		
		return true;
	}
	
	/**
	 * Gets a Trophy from the TrophySet that is meaningfully equal to the Trophy argument;
	 * that is, a Trophy with the same name.  If there is no such Trophy, null is returned.
	 * @param trophy
	 * @return
	 */
	public Trophy get(Trophy trophy) {
		Trophy trophyToReturn = null;
		for(Trophy trophyInSet : this) {
			if(trophyInSet.equals(trophy)) {
				trophyToReturn = trophyInSet;
			}
		}
		return trophyToReturn;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6220028023304257059L;
}

