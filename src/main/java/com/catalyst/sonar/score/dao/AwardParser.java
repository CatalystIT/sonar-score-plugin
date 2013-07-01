/**
 * 
 */
package com.catalyst.sonar.score.dao;

import org.sonar.api.database.DatabaseSession;

import com.catalyst.sonar.score.api.Award;

/**
 * The {@link AwardParser} parses a {@code String} into an {@link Award}.
 * 
 * @see {@link Parser}
 * 
 * @author JDunn
 */
public abstract class AwardParser<A extends Award> extends Parser<A> {

	/**
	 * Creates a String[] whose two fields are the {@link Award} name and the
	 * {@code String} representation of the {@link Criterion}. Intended only for
	 * use in the constructor (
	 * {@link AwardParser#AwardParser(DatabaseSession, String)}).
	 * 
	 * @param awardString
	 * @return
	 */
	private static String[] spliceAwardString(String awardString) {
		// Separates the award name from the Criterion/Group.
		String[] fields = awardString.split("\\{");
		// Trims the "}" off the end of the Criterion/Group.
		fields[1] = fields[1].replaceAll("\\}", "");
		return fields;
	}

	/**
	 * Overrides the equivalent constructor in {@code super}, passing in
	 * different logic in the way it calls its protected constructor (
	 * {@link Parser#Parser(DatabaseSession, String[])}).
	 * 
	 * @param session
	 * @param entityString
	 */
	public AwardParser(DatabaseSession session, String entityString) {
		super(session, spliceAwardString(entityString));
	}

	/**
	 * Parses the field for parsing into the {@link Award} of type {@code A}.
	 * When implementing, use the inherited method get(0) to get the
	 * {@link Award} name and get(1) to get the {@link Criterion}/{@link Group}.
	 * 
	 * @see {@link Parser#parse()}
	 */
	@Override
	public abstract A parse();
}
