/**
 * 
 */
package com.catalyst.sonar.score.log;

import static org.mockito.Mockito.mock;

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
	private int extraTabs = 0;
	private static final PrintStream offStream = mock(PrintStream.class);

	private PrintStream currentStream;
	private PrintStream onStream;
	private List<String> stack;
	private int offAt;

	/**
	 * The no-args constructor sets the currentStream and onStream to
	 * {@code System.out} and instantiates the {@code List<String> stack} as an
	 * {@link ArrayList} {@code <String>}.
	 */
	public Logger() {
		this(System.out);
	}

	/**
	 * Constructs a Logger and sets its currentStream and onStream to
	 * {@code onStream} and instantiates the {@code List<String> stack} as an
	 * {@link ArrayList} {@code <String>}.
	 * 
	 * @param onStream
	 */
	public Logger(PrintStream onStream) {
		this.onStream = onStream;
		this.currentStream = this.onStream;
		this.stack = new ArrayList<String>();
		offAt = 0;
	}

	/**
	 * Prints a tab for every method name in the stack List, and then prints the
	 * object.
	 * 
	 * @param x
	 * @return this
	 */
	public Logger log(Object x) {
		currentStream.println(tab() + x);
		return this;
	}

	/**
	 * Logs an Exception.
	 * 
	 * @param e
	 * @return
	 */
	public Logger log(Exception e) {
		final boolean wasOff = !isOn();
		onIf(wasOff);
		log(e.toString());
		addTab(1);
		log(Arrays.asList(e.getStackTrace()));
		return offIf(wasOff);
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
		// characters left in line now
		int before = MAX_LENGTH;
		// characters that will be left in line after Object o is printed
		int after = before;
		int index = 0;
		for (Object o : x) {
			index++;
			if (index == x.size()) {
				listDelimiter = ".";
			}
			String objStr = o.toString() + listDelimiter;
			after = before - objStr.length();
			if (before == MAX_LENGTH) {
				currentStream.print(tab() + objStr);
			} else if (after >= 0) {
				currentStream.print("" + objStr);
			} else {
				currentStream.println();
				currentStream.print(tab() + objStr);
				before = MAX_LENGTH - objStr.length();
				continue;
			}
			before = after;
		}
		currentStream.println();
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
		currentStream.println(tab() + "!!! " + x);
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
		Object objectToPrint = x;
		if (x instanceof String[]) {
			objectToPrint = Arrays.toString((String[]) x);
		}
		currentStream.println(tab() + "WARNING! " + objectToPrint);
		return this;
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
	 * Logs the beginning of a method only if logIf is true; if not, the Logger
	 * is turned off until endMethod() is called.
	 * 
	 * @param methodName
	 * @param logIf
	 * @return
	 */
	public Logger beginMethod(final String methodName, boolean logIf) {
		if (!logIf) {
			off();
		}
		return beginMethod(methodName);
	}

	/**
	 * Removes the last methodName from the stack and prints it with a border.
	 * 
	 * @return
	 */
	public Logger endMethod() {
		try {
			final String methodName = stack.remove(stack.size() - 1);
			String message = border(TAB_LENGTH) + END + methodName;
			return borderMessage(message).onIf(offAt == stack.size());
		} catch (ArrayIndexOutOfBoundsException methodLoggingOutOfSync) {
			final boolean wasOff = !isOn();
			onIf(wasOff);
			return LOG.log("METHOD LOGGING OUT OF SYNC!!!")
					.log(methodLoggingOutOfSync).offIf(wasOff);
		}
	}

	/**
	 * Logs "Returning " + the object.
	 * 
	 * @param o
	 * @return
	 */
	public Logger returning(Object o) {
		return log("Returning " + o);
	}

	/**
	 * prints a message with a "border". The characters used in the Border will
	 * be based on the stack size.
	 * 
	 * @param message
	 * @return
	 */
	private Logger borderMessage(String message) {
		currentStream.println(tab()
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
		for (int i = 0; i < stack.size() + extraTabs; i++) {
			tabs += '\t';
		}
		return tabs;
	}

	/**
	 * @return the currentStream
	 */
	public PrintStream getStream() {
		return currentStream;
	}

	/**
	 * @param stream
	 *            the currentStream to set
	 * @param stream
	 * @return this
	 */
	private Logger setStream(PrintStream stream) {
		this.currentStream = stream;
		return this;
	}

	/**
	 * Gets the onStream -- that is, the {@link PrintStream} when this Logger is
	 * turned on.
	 * 
	 * @return the onStream
	 */
	public PrintStream getOnStream() {
		return onStream;
	}

	/**
	 * Sets the onStream -- that is, the {@link PrintStream} when this Logger is
	 * turned on. If this Logger is on, also sets the currentStream to the
	 * onStream arg.
	 * 
	 * @param onStream
	 *            the onStream to set
	 */
	public void setOnStream(PrintStream onStream) {
		if (isOn()) {
			currentStream = onStream;
		}
		this.onStream = onStream;
	}

	/**
	 * Adds the specified number of tabs to the indentation of what is being
	 * logged.
	 * 
	 * @param amount
	 * @return
	 */
	public Logger addTab(int amount) {
		extraTabs += amount;
		return this;
	}

	/**
	 * Removes the specified number of tabs from the indentation of what is
	 * being logged.
	 * 
	 * @param amount
	 * @return
	 */
	public Logger removeTab(int amount) {
		extraTabs -= amount;
		return this;
	}

	/**
	 * Turns on this Logger by setting {@code this.currentStream} to
	 * {@code this.onStream}.
	 * 
	 * @return
	 */
	public Logger on() {
		offAt = 0;
		return setStream(onStream);
	}

	/**
	 * Turns off this Logger by setting {@code this.currentStream} to
	 * {@code this.offStream}.
	 * 
	 * @return
	 */
	public Logger off() {
		if(isOn()) {			
			offAt = stack.size();
			return setStream(offStream);
		} else {
			return this;
		}
	}

	/**
	 * Turns this Logger off if the boolean argument offIf is true.
	 * 
	 * @param offIf
	 * @return
	 */
	private Logger offIf(boolean offIf) {
		return (offIf) ? off() : this;
	}

	private boolean isOn() {
		return currentStream == onStream;
	}

	/**
	 * Turns this Logger on if the boolean argument onIf is true.
	 * 
	 * @param onIf
	 * @return
	 */
	private Logger onIf(boolean onIf) {
		return (onIf) ? on() : this;
	}
}
