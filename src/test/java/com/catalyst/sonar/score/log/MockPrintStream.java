/**
 * 
 */
package com.catalyst.sonar.score.log;

import static org.mockito.Mockito.mock;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author JDunn
 *
 */
public class MockPrintStream extends PrintStream {
	
	private static final OutputStream MOCK = mock(OutputStream.class);
	private static final String LN = "\n";
	
	private String output;

	public MockPrintStream() {
		super(MOCK);
		output = "";
	}
	
	@Override
	public void print(Object x) {
		output += x.toString();
	}
	
	@Override
	public void print(boolean b) {
		output += b;
	}

	@Override
	public void print(char c) {
		output += c;
	}

	@Override
	public void print(int i) {
		output += i;
	}

	@Override
	public void print(long l) {
		output += l;
	}

	@Override
	public void print(float f) {
		output += f;
	}

	@Override
	public void print(double d) {
		output += d;
	}

	@Override
	public void print(char[] s) {
		output += s;
	}

	@Override
	public void print(String s) {
		output += s;
	}
	
	@Override
	public void println(Object x) {
		print(x + LN);
	}
	
	@Override
	public void println() {
		print(LN);
	}

	@Override
	public void println(boolean x) {
		print(x + LN);
	}

	@Override
	public void println(char x) {
		print(x + LN);
	}

	@Override
	public void println(int x) {
		print(x + LN);
	}

	@Override
	public void println(long x) {
		print(x + LN);
	}

	@Override
	public void println(float x) {
		print(x + LN);
	}

	@Override
	public void println(double x) {
		print(x + LN);
	}

	@Override
	public void println(char[] x) {
		print(x);
		println();
	}

	@Override
	public void println(String x) {
		print(x + LN);
	}

//	public static void main(String[] args) {
//		MockPrintStream stream = new MockPrintStream();
//		stream.println(false);
//		System.out.println((stream.getOutput().equals("false\n")));
//		System.out.println((stream.getOutput()));
//	}

	
	
	/**
	 * @return the output
	 */
	public String getOutput() {
		return output;
	}
	
	/**
	 * @param output the output to set
	 */
	public void setOutput(String output) {
		this.output = output;
	}

}
