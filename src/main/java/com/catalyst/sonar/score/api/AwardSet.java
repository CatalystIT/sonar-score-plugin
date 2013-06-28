/**
 * 
 */
package com.catalyst.sonar.score.api;

/**
 * {@code AwardSet} extends {@code SearchableHashSet<Award>}, overriding {@code add()}.
 */
public class AwardSet<A extends Award> extends SearchableHashSet<A> implements ScoreEntity {

	/**
	 * If an {@code Award} with the same name is in this {@code AwardSet},
	 * and if the {@code Award} argument is not null, it adds the criteria
	 * and members to include and exclude of the {@code Award} argument
	 * to the {@code Award} that is already in this {@code AwardSet}. 
	 * Otherwise, it adds the {@code Award} to this {@code AwardSet}.
	 * If a new {@code Award} is not added, or if the {@code Award}
	 * already in this {@code AwardSet} receives no new {@code Criterion},
	 * false is returned.
	 * @see {@link com.catalyst.sonar.score.api.SearchableHashSet#add(E e)}
	 */
	@Override
	public boolean add(A award) {
		boolean anyInfoAdded = true;
		//The method super.add(award) will run as part of the boolean expression in the evaluation.
		//Thus, if the expression evaluates to false, super.add returned true and the award was added.
		if(!super.add(award)) {
			anyInfoAdded = false;
			for(Criterion criterion : award.getCriteria()) {
				anyInfoAdded = (this.get(award).addCriterion(criterion)) ? true : anyInfoAdded;
			}
			for(Member member : award.getMembersToInclude()) {
				anyInfoAdded = (this.get(award).addMembersToInclude(member)) ? true : anyInfoAdded;
			}
			for(Member member : award.getMembersToExclude()) {
				anyInfoAdded = (this.get(award).addMembersToExclude(member)) ? true : anyInfoAdded;
			}
		}
		return anyInfoAdded;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6220028023304257059L;
}

