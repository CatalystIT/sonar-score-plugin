/**
 * 
 */
package com.catalyst.sonar.score.dao;

import static com.catalyst.sonar.score.log.Logger.LOG;

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
	
	private final String name;
	
	/**
	 * Creates a String[] whose two fields are the {@link Award} name and the
	 * {@code String} representation of the {@link Criterion}. Intended only for
	 * use in the constructor (
	 * {@link AwardParser#AwardParser(DatabaseSession, String)}).
	 * 
	 * @param awardString
	 * @return
	 */
	private static String[] spliceValueToCriteria(String value) {
		String[] criteriaStrings = value.split("\\{|,|\\}");
		LOG.log(criteriaStrings);
		return criteriaStrings;
	}

	/**
	 * Overrides the equivalent constructor in {@code super}, passing in
	 * different logic in the way it calls its protected constructor (
	 * {@link Parser#Parser(DatabaseSession, String[])}).
	 * 
	 * @param session
	 * @param entityString
	 *///TODO: update Javadoc description
	public AwardParser(DatabaseSession session, String key, String value) {
		super(session, spliceValueToCriteria(value));
		this.name = key.substring(key.lastIndexOf(':') + 1);
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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
