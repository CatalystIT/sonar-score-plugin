/**
 * 
 */
package com.catalyst.sonar.score.dao;

import static com.catalyst.sonar.score.log.Logger.LOG;

import org.sonar.api.database.DatabaseSession;

import com.catalyst.sonar.score.api.TitleCup;

/**
 * An implementation of {@link AwardParser}{@code <}{@link TitleCup}{@code >},
 * specifically for parsing a {@link TitleCup}.
 * 
 * @author JDunn
 */
public class TitleCupParser extends AwardParser<TitleCup> {

	/**
	 * @see {@link AwardParser#AwardParser(DatabaseSession, String)}
	 * 
	 * @param session
	 * @param entityString
	 */
	// TODO update Javadoc
	public TitleCupParser(DatabaseSession session, String key, String value) {
		super(session, key, value);
	}

	/**
	 * Parses a {@link TitleCup} from a {@code String}.
	 * 
	 * @see {@link AwardParser#parse()}
	 */
	@Override
	public TitleCup parse() {
		LOG.beginMethod("PARSING TITLECUP");
		TitleCup cup = new TitleCup(getName());
		for (int index = 0; index < fieldsLength(); index++) {			
			CriterionParser cParser = new CriterionParser(getSession(), get(index));
			cup.addCriterion(cParser.parse());
		}
		LOG.log("CUP = " + cup + "; CRITERIA = " + cup.getCriteria())
				.endMethod();
		return cup;
	}
}
