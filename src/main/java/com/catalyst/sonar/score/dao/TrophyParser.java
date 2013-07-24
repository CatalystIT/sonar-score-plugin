/**
 * 
 */
package com.catalyst.sonar.score.dao;

import static com.catalyst.sonar.score.log.Logger.LOG;

import org.sonar.api.database.DatabaseSession;

import com.catalyst.sonar.score.api.Criterion;
import com.catalyst.sonar.score.api.Trophy;

/**
 * An implementation of {@link AwardParser}{@code <}{@link Trophy}{@code >},
 * specifically for parsing a {@link Trophy}.
 * 
 * @author JDunn
 */
public class TrophyParser extends AwardParser<Trophy> {

	/**
	 * @see {@link AwardParser#AwardParser(DatabaseSession, String)}
	 * 
	 * @param session
	 * @param entityString
	 */
	// TODO update Javadoc
	public TrophyParser(DatabaseSession session, String key, String value) {
		super(session, key, value);
	}

	/**
	 * Parses a {@link Trophy} from a {@code String}.
	 * 
	 * @see {@link AwardParser#parse()}
	 */
	@Override
	public Trophy parse() {
		LOG.beginMethod("PARSING TROPHY");
		Trophy trophy = new Trophy(getName());
		for (int index = 0; index < fieldsLength(); index++) {			
			CriterionParser cParser = new CriterionParser(getSession(), get(index));
			Criterion criterion = cParser.parse();
			if (criterion.getMetric() != null) {
				LOG.log("adding criterion: " + criterion);
				trophy.addCriterion(criterion);
			} else {
				LOG.log("Metric = " + criterion.getMetric() + ", so not adding.");
			}
		}
		LOG.log("Name = " + trophy + "; Criteria = ").log(trophy.getCriteria())
				.endMethod();
		return trophy;
	}
}
