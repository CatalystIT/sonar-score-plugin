/**
 * 
 */
package com.catalyst.sonar.score.api;

import static org.junit.Assert.*;
import static com.catalyst.sonar.score.api.ApiTestConstants.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Cases for all possible outcomes of
 * {@link com.catalyst.sonar.score.api.SearchableHashSet#immutableCopy()}.
 * @author JDunn
 */
public class SearchableHashSetTest_ImmutableCopy {
	SearchableHashSet<ScoreUser> testSet;
	SearchableHashSet<ScoreUser> immutableCopy;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testSet = new SearchableHashSet<ScoreUser>();
		testSet.add(USER1);
		testSet.add(USER2);
		immutableCopy = testSet.immutableCopy();
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.SearchableHashSet#immutableCopy()}.
	 * Asserts that immutable copy of the testSet has the same members.
	 */
	@Test
	public void testImmutableCopy_AssertMembersEqual() {
		assertEquals(testSet, testSet.immutableCopy());
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.SearchableHashSet#immutableCopy()}.
	 * Asserts that {@code contains()} does not throw an exception.
	 */
	@Test
	public void testImmutableCopy_Contains_Works() {
		assertEquals(testSet.contains(USER1), immutableCopy.contains(USER1));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.SearchableHashSet#immutableCopy()}.
	 * Asserts that {@code get()} does not throw an exception.
	 */
	@Test
	public void testImmutableCopy_Get_Works() {
		assertEquals(testSet.get(USER1), immutableCopy.get(USER1));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.SearchableHashSet#immutableCopy()}.
	 * Asserts that {@code add()} throws an {@code UnsupportedOperationException}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testImmutableCopy_Add_ThrowsException() {
		immutableCopy.add(USER3);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.SearchableHashSet#immutableCopy()}.
	 * Asserts that {@code remove()} throws an {@code UnsupportedOperationException}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testImmutableCopy_Remove_ThrowsException() {
		immutableCopy.remove(USER2);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.SearchableHashSet#immutableCopy()}.
	 * Asserts that {@code clear()} throws an {@code UnsupportedOperationException}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testImmutableCopy_Clear_ThrowsException() {
		immutableCopy.clear();
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.SearchableHashSet#immutableCopy()}.
	 * Asserts that {@code removeAll()} throws an {@code UnsupportedOperationException}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testImmutableCopy_AddAll_ThrowsException() {
		immutableCopy.addAll(testSet);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.SearchableHashSet#immutableCopy()}.
	 * Asserts that {@code removeAll()} throws an {@code UnsupportedOperationException}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testImmutableCopy_RemoveAll_ThrowsException() {
		immutableCopy.removeAll(testSet);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.SearchableHashSet#immutableCopy()}.
	 * Asserts that {@code retainAll()} throws an {@code UnsupportedOperationException}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testImmutableCopy_RetainAll_ThrowsException() {
		immutableCopy.retainAll(testSet);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testThis() {
		throw new UnsupportedOperationException("hi");
	}


}
