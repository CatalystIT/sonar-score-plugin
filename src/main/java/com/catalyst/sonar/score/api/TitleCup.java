/**
 * 
 */
package com.catalyst.sonar.score.api;

/**
 * {@code TitleCup} extends {@code Award} and represents a Title Cup as a unique kind of Award.
 * @author JDunn
 */
public class TitleCup extends Award {

	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls {@code super()}.
	 */
	public TitleCup() {
		super();
	}

	/**
	 * Constructs a {@code TitleCup}, setting the name
	 * to equal the {@code String} name argument.
	 * @param name
	 */
	public TitleCup(String name) {
		super(name);
	}

	
}
