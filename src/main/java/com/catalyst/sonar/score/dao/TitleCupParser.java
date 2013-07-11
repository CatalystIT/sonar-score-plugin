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
	public TitleCupParser(DatabaseSession session, String entityString) {
		super(session, entityString);
	}

	/**
	 * Parses a {@link TitleCup} from a {@code String}.
	 * 
	 * @see {@link AwardParser#parse()}
	 */
	@Override
	public TitleCup parse() {
		LOG.beginMethod("PARSING TITLECUP");
		CriterionParser cParser = new CriterionParser(getSession(), get(1));
		TitleCup cup = new TitleCup(get(0));
		cup.addCriterion(cParser.parse());
		LOG.log("CUP = " + cup + "; CRITERIA = " + cup.getCriteria())
				.endMethod();
		return cup;
	}

}
