/**
 * 
 */
package com.catalyst.sonar.score.dao;

import org.sonar.api.database.DatabaseSession;
import org.sonar.jpa.dao.BaseDao;

/**
 * {@link Parser} defines a class for parsing Strings into objects specific to
 * the Score Plugin API. It has a Constructor and a {@code parse()} method.
 * Because Parsers often have to access database information, they are
 * considered a special kind of DAO, extending {@link BaseDao} and residing in
 * the package {@link com.catalyst.sonar.score.dao}.
 * 
 * @author JDunn
 */
public abstract class Parser<E> extends BaseDao {

	/**
	 * Constructs a {@link Parser}, instantiating the session. The entityString
	 * parameter sets the field for parsing.
	 * 
	 * @param session
	 * @param entityString
	 */
	public Parser(DatabaseSession session, String entityString) {
		super(session);
	}

	/**
	 * Parses the field for parsing into the {@code Object} of type {@code E}.
	 * 
	 * @return
	 */
	public abstract E parse();

}
