/*
 * Copyright 2013 Catalyst IT Services
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
			trophy.addCriterion(criterion);
		}
		LOG.log("Name = " + trophy + "; Criteria = ").log(trophy.getCriteria())
				.endMethod();
		return trophy;
	}
}
