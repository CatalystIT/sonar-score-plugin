package com.catalyst.sonar.score.api;


/**
 * {@code Trophy} extends {@code Award} and represents a Trophy as a unique kind of Award.
 * @author JDunn
 */
public class Trophy extends Award {
	
	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls {@code super()}.
	 */
	public Trophy(){
		super();
	}
	
	/**
	 * Constructs a {@code Trophy}, setting the name
	 * to equal the {@code String} name argument.
	 * @param name
	 */
	public Trophy(String name){
		super(name);
	}
}
