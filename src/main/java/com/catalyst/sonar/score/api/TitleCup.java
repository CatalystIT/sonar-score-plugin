/**
 * 
 */
package com.catalyst.sonar.score.api;

/**
 * @author James
 *
 */
public class TitleCup extends Award {

	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls <code>super()</code>.
	 */
	public TitleCup() {
		super();
	}

	/**
	 * Constructs a <code>TitleCup</code>, setting the name
	 * to equal the <code>String</code> name argument.
	 * @param name
	 */
	public TitleCup(String name) {
		super(name);
	}

	
}
