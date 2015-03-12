/**
 * MloCommonTableTest.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.view;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.view.MloCommonTable;

/**
 * MloCommonTableTest
 *
 */
public class MloCommonTableTest {

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

	@Test
	public void testGetData_edit() {

		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		data.put("key1", "value1");
		data.put("key2", "value2");
		data.put("key3", "value3");
		
		MloCommonTable table = new MloCommonTable(data, 1000d, true);

		LinkedHashMap<String, String> ret = table.getData();
		assertEquals(ret.size(), 3);
		int i = 0;
		for(Map.Entry<String, String> e : ret.entrySet()) {
			i++;
			switch (i) {
			case 1:
				assertEquals("key1", e.getKey());
				assertEquals("value1", e.getValue());
				break;
			case 2:
				assertEquals("key2", e.getKey());
				assertEquals("value2", e.getValue());
				break;
			case 3:
				assertEquals("key3", e.getKey());
				assertEquals("value3", e.getValue());
				break;
			default:
				fail();
			}
		}
	}

	@Test
	public void testGetData_edit_false() {

		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		data.put("key1", "value1");
		data.put("key2", "value2");
		data.put("key3", "value3");
		
		MloCommonTable table = new MloCommonTable(data, 1000d, false);

		LinkedHashMap<String, String> ret = table.getData();
		assertEquals(ret.size(), 3);
		int i = 0;
		for(Map.Entry<String, String> e : ret.entrySet()) {
			i++;
			switch (i) {
			case 1:
				assertEquals("key1", e.getKey());
				assertEquals("value1", e.getValue());
				break;
			case 2:
				assertEquals("key2", e.getKey());
				assertEquals("value2", e.getValue());
				break;
			case 3:
				assertEquals("key3", e.getKey());
				assertEquals("value3", e.getValue());
				break;
			default:
				fail();
			}
		}
	}
}
