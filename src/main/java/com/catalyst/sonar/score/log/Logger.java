/**
 * 
 */
package com.catalyst.sonar.score.log;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * The Logger class is used for printing useful information in the console of a
 * CI engine (like Hudson/Jenkins), and for general debugging.
 * 
 * @author JDunn
 * 
 */
public class Logger {

	public static final char BORDER1 = '*';
	public static final char BORDER2 = '>';
	public static final char BORDER3 = ':';
	public static final char BORDER4 = '-';
	public static final char[] BORDER = { BORDER1, BORDER2, BORDER3, BORDER4 };
	public static final char TAB = '\t';
	public static final int BORDER_LENGTH = 70;
	public static final int TAB_LENGTH = 4;
	public static final String START = "Start ";
	public static final String END = "End ";
	public static final int MAX_LENGTH = 50;

	public static final Logger LOG = new Logger();

	private PrintStream stream;
	private List<String> stack;

	/**
	 * The no-args constructor sets the stream to {@code System.out} and
	 * instantiates the {@code List<String> stack} as an {@link ArrayList}.
	 * {@code String}.
	 */
	public Logger() {
		this.stream = System.out;
		this.stack = new ArrayList<String>();
	}

	/**
	 * Prints a tab for every method name in the stack List, and then prints the
	 * object.
	 * 
	 * @param x
	 * @return this
	 */
	public Logger log(Object x) {
		stream.println(tab() + x);
		return this;
	}
	
	/**
	 * Prints a tab for every method name in the stack List, and then logs each
	 * object in Collection x on its own line.
	 * 
	 * @param x
	 * @return this
	 */
	public Logger log(@SuppressWarnings("rawtypes") Collection x) {
		String listDelimiter = ",  ";
		//characters left in line now
		int before = MAX_LENGTH;
		//characters that will be left in line after Object o is printed
		int after = before;
		int index = 0;
		for (Object o : x) {
			index++;
			if (index == x.size()) {
				listDelimiter = ".";
			}
			String objStr = o.toString() + listDelimiter;
			after = before - objStr.length();
			if(before == MAX_LENGTH) {
				stream.print(tab() + objStr);
			} else if (after >= 0) {
				stream.print("" + objStr);
			} else {
				stream.println();
				stream.print(tab() + objStr);
				before = MAX_LENGTH - objStr.length();
				continue;
			}
			before = after;			
		}
		stream.println();
		return this;
	}

	/**
	 * Prints the same as {@link Logger.log(Object x)} but adding an emphasis
	 * String.
	 * 
	 * @param x
	 * @return
	 */
	public Logger logEmf(Object x) {
		stream.println(tab() + "!!! " + x);
		return log(x);
	}
	
	/**
	 * Prints the same as {@link Logger.log(Object x)} but adding an emphasis
	 * String.
	 * 
	 * @param x
	 * @return
	 */
	public Logger warn(Object x) {
		if(x instanceof String[]) {
			x = Arrays.toString((String[]) x);
		}
		stream.println(tab() + "WARNING! " + x);
		return log(x);
	}

	/**
	 * Prints the methodName with a border, and then adds the methodName to the
	 * stack.
	 * 
	 * @param methodName
	 * @return
	 */
	public Logger beginMethod(final String methodName) {
		String message = border(TAB_LENGTH) + START + methodName;
		borderMessage(message);
		stack.add(methodName);
		return this;
	}

	/**
	 * Removes the last methodName from the stack and prints it with a border.
	 * 
	 * @return
	 */
	public Logger endMethod() {
		final String methodName = stack.remove(stack.size() - 1);
		String message = border(TAB_LENGTH) + END + methodName;
		return borderMessage(message);
	}
	
	private Logger borderMessage(String message) {
		stream.println(tab()
				+ message
				+ border(BORDER_LENGTH - (TAB_LENGTH * stack.size())
						- message.length()));
		return this;
	}

	/**
	 * Returns a String to be used as a border.
	 * 
	 * @param x
	 * @return
	 */
	private String border(final int x) {
		StringBuilder border = new StringBuilder();
		int index = stack.size();
		index = (index >= BORDER.length) ? BORDER.length - 1 : index;
		for (int y = 0; y < x; y++) {
			border.append(BORDER[index]);
		}
		return border.toString();
	}

	/**
	 * Returns a String consisting of the appropriate number of tabs based on
	 * the size of the stack List.
	 * 
	 * @return
	 */
	private String tab() {
		String tabs = "";
		for (int i = 0; i < stack.size(); i++) {
			tabs += '\t';
		}
		return tabs;
	}

	/**
	 * @return the stream
	 */
	public PrintStream getStream() {
		return stream;
	}

	/**
	 * @param stream
	 *            the stream to set
	 * @param stream
	 * @return this
	 */
	public Logger setStream(PrintStream stream) {
		this.stream = stream;
		return this;
	}
}
