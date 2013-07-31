/**
 * 
 */
package com.catalyst.sonar.score.api;

/**
 * @author JDunn
 *
 */
public class CriterionSet extends SearchableHashSet<Criterion> {

	/**
	 * Ensures that no Criterion will be added if its Metric is null.
	 * @See {@link SearchableHashSet#add(Object)}
	 */
	@Override
	public boolean add(Criterion criterion) {
		if (criterion == null || criterion.getMetric() == null) {
			return false;
		}
		return super.add(criterion);
	}
	
	/**
	 * Prints out the CriterionSet as it will be represented in the database.
	 * @see {@link AbstractCollection#toString()}
	 * @see {@link Object#toString()}
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (Criterion criterion: this) {
			buffer.append(criterion.toValueString() + ',');
		}
		return buffer.subSequence(0, buffer.length() - 1).toString();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8665990041148078349L;

}
