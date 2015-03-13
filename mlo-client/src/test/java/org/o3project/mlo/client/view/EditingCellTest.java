/**
 * EditingCellTest.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.view;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.view.EditingCell;

/**
 * SliceDataTest
 *
 */
public class EditingCellTest {

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
	 * Test method for {@link org.o3project.mlo.client.view.EditingCell#updateItem(java.lang.String, boolean)}.
	 */
	@Test
	public void testUpdateItem_notEmpty_notEditing() {

		EditingCell cell = new EditingCell();
		
		cell.updateItem("test", false);
		
		assertEquals("test", cell.getText());
		assertNull(cell.getGraphic());
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.view.EditingCell#updateItem(java.lang.String, boolean)}.
	 */
	@Test
	public void testUpdateItem_empty() {

		EditingCell cell = new EditingCell();
		
		cell.updateItem("test", true);
		
		assertNull(cell.getText());
		assertNull(cell.getGraphic());
	}

}
