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

	private String[] fields;

	/**
	 * Constructs a {@link Parser}, instantiating the session. The entityString
	 * parameter sets the field for parsing.
	 * 
	 * @param session
	 * @param entityString
	 */
	public Parser(DatabaseSession session, String entityString) {
		this(session, new String[1]);
		this.fields[0] = entityString;
	}

	/**
	 * Returns the String at {@code fields[index]}.
	 * 
	 * @param index
	 * @return the String at fields[index].
	 */
	public String get(int index) {
		return fields[index];
	}

	/**
	 * Parses the field for parsing into the {@code Object} of type {@code E}.
	 * 
	 * @return
	 */
	public abstract E parse();

	/**
	 * For use only within the public constructor or the public constructors of
	 * subclasses.
	 * 
	 * @param session
	 */
	protected Parser(DatabaseSession session, String[] fields) {
		super(session);
		this.fields = new String[fields.length];
		System.arraycopy(fields, 0, this.fields, 0, fields.length);
	}

	/**
	 * 
	 * @return the length of the {@code String[] fields} field.
	 */
	protected int fieldsLength() {
		return fields.length;
	}

}
