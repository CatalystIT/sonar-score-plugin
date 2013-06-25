/**
 * 
 */
package com.catalyst.sonar.score.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author James
 *
 */
public class SearchableHashSetTest {
	
	private SearchableHashSet<String> testSet;
	private String testString = new String("Hi!");
	private Object equalString = new String("Hi!");
	private Object unequalString = new String("Yo!");
	private String testString2 = new String("Hello!");

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testSet = new SearchableHashSet<String>();
		testSet.add(testString);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.SearchableHashSet#get(java.lang.Object)}.
	 * Asserts that the returned element is the same one as the one in the list when a different but
	 * meaningfully equal object is used as the argument.
	 */
	@Test
	public void testGet_isEqualToObjectInSet_sameObject() {
		assertSame(testString, testSet.get(equalString));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.SearchableHashSet#get(java.lang.Object)}.
	 * Asserts that the returned element is the same one as the one in the list when a different but
	 * meaningfully equal object is used as the argument.
	 */
	@Test
	public void testGet_isNotEqualToObjectInSet_null() {
		assertNull(testSet.get(unequalString));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.SearchableHashSet#add(java.lang.Object)}.
	 * Asserts that null cannot be added.
	 */
	@Test
	public void testAddE_isNull_false() {
		assertFalse(testSet.add(null));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.SearchableHashSet#add(java.lang.Object)}.
	 * Asserts that equal object cannot be added.
	 */
	@Test
	public void testAddE_isEqualToObjectInSet_false() {
		assertFalse(testSet.add((String)equalString));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.SearchableHashSet#add(java.lang.Object)}.
	 * Asserts that unequal object can be added.
	 */
	@Test
	public void testAddE_isNotEqualToObjectInSet_true() {
		assertTrue(testSet.add((String)unequalString));
	}

}
