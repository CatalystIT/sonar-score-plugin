/**
 * 
 */
package com.catalyst.commons.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * Test Cases for all possible outcomes of
 * {@link SearchableHashSet#immutableCopy()}.
 * 
 * @author JDunn
 */
public class SearchableHashSet_ImmutableCopyTest {
	
	private static final String STRING1 = "String One";
	private static final String STRING2 = "String Two";
	private static final String STRING3 = "String Three";
	
	SearchableHashSet<String> testSet;
	SearchableHashSet<String> immutableCopy;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testSet = new SearchableHashSet<String>();
		testSet.add(STRING1);
		testSet.add(STRING2);
		immutableCopy = testSet.immutableCopy();
	}

	/**
	 * Test method for {@link SearchableHashSet#immutableCopy()}. Asserts that
	 * immutable copy of the testSet has the same members.
	 */
	@Test
	public void testImmutableCopy_AssertMembersEqual() {
		assertEquals(testSet, testSet.immutableCopy());
	}

	/**
	 * Test method for {@link SearchableHashSet#immutableCopy()}. Asserts that
	 * {@code contains()} does not throw an exception.
	 */
	@Test
	public void testImmutableCopy_Contains_Works() {
		assertEquals(testSet.contains(STRING1), immutableCopy.contains(STRING1));
	}

	/**
	 * Test method for {@link SearchableHashSet#immutableCopy()}. Asserts that
	 * {@code get()} does not throw an exception.
	 */
	@Test
	public void testImmutableCopy_Get_Works() {
		assertEquals(testSet.get(STRING1), immutableCopy.get(STRING1));
	}

	/**
	 * Test method for {@link SearchableHashSet#immutableCopy()}. Asserts that
	 * {@code add()} throws an {@link UnsupportedOperationException}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testImmutableCopy_Add_ThrowsException() {
		immutableCopy.add(STRING3);
	}

	/**
	 * Test method for {@link SearchableHashSet#immutableCopy()}. Asserts that
	 * {@code remove()} throws an {@link UnsupportedOperationException}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testImmutableCopy_Remove_ThrowsException() {
		immutableCopy.remove(STRING2);
	}

	/**
	 * Test method for {@link SearchableHashSet#immutableCopy()}. Asserts that
	 * {@code clear()} throws an {@link UnsupportedOperationException}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testImmutableCopy_Clear_ThrowsException() {
		immutableCopy.clear();
	}

	/**
	 * Test method for {@link SearchableHashSet#immutableCopy()}. Asserts that
	 * {@code removeAll()} throws an {@link UnsupportedOperationException}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testImmutableCopy_AddAll_ThrowsException() {
		immutableCopy.addAll(testSet);
	}

	/**
	 * Test method for {@link SearchableHashSet#immutableCopy()}. Asserts that
	 * {@code removeAll()} throws an {@link UnsupportedOperationException}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testImmutableCopy_RemoveAll_ThrowsException() {
		immutableCopy.removeAll(testSet);
	}

	/**
	 * Test method for {@link SearchableHashSet#immutableCopy()}. Asserts that
	 * {@code retainAll()} throws an {@link UnsupportedOperationException}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testImmutableCopy_RetainAll_ThrowsException() {
		immutableCopy.retainAll(testSet);
	}

}
