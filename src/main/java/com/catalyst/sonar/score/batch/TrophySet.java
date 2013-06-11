/**
 * 
 */
package com.catalyst.sonar.score.batch;

import java.util.HashSet;

/**
 * @author James
 * extends HashSet<Trophy>, overriding the add() method.
 */
public class TrophySet extends HashSet<Trophy> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6220028023304257059L;

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
			TrophySet deepCopy = deepCopy();
			for (Trophy trophyInDeepCopyOfSet : deepCopy) {
				if(trophyInDeepCopyOfSet.equals(trophy)) {
					for(Criteria criteria : trophy.getCriteria()) {
						this.get(trophyInDeepCopyOfSet).getCriteria().add(criteria);
					}
				}
			}			
		}		
		return true;
	}
	
	public Trophy get(Trophy trophy) {
		Trophy trophyToReturn = null;
		for(Trophy trophyInSet : this) {
			if(trophyInSet.equals(trophy)) {
				trophyToReturn = trophyInSet;
			}
		}
		return trophyToReturn;
	}
	
	public TrophySet deepCopy() {
		TrophySet trophySet = new TrophySet();
		for(Trophy trophy : this) {
			trophySet.add(new Trophy(trophy.getName(), trophy.getCriteria()));
		}
		return trophySet;
	}
}
