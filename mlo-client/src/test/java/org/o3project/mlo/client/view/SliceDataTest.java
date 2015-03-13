/**
 * SliceDataTest.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.view;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.view.SliceData;

/**
 * SliceDataTest
 *
 */
public class SliceDataTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.view.SliceData#SliceData(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSliceData() {

		SliceData data = new SliceData("testKey1", "testValue1");
		
		assertEquals("testKey1", data.getKey());
		assertEquals("testValue1", data.getValue());
		assertEquals("testKey1", data.keyProperty().getValue());
		assertEquals("testValue1", data.valueProperty().getValue());

		data.setKey("testKey2");
		data.setValue("testValue2");
		assertEquals("testKey2", data.getKey());
		assertEquals("testValue2", data.getValue());
		assertEquals("testKey2", data.keyProperty().getValue());
		assertEquals("testValue2", data.valueProperty().getValue());
	}

}
