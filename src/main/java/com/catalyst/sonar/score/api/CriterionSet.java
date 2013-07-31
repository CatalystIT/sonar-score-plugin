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
