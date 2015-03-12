/**
 * FXMLUtilTest.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.control;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.o3project.mlo.client.control.FXMLUtil;
import org.o3project.mlo.client.control.ResultDialogboxController;
import org.o3project.mlo.client.impl.control.ResultDialogboxControllerImpl;
import org.o3project.mlo.client.test.JavaFxJUnitRunner;

/**
 * FXMLUtilTest
 *
 */
@RunWith(JavaFxJUnitRunner.class)
public class FXMLUtilTest {


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		System.out.println("setUp called.");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown called.");
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.control.FXMLUtil#createController(java.lang.Class, java.lang.String)}.
	 */
	@Test
	public void testCreateController_normal() {
		ResultDialogboxController controller = FXMLUtil.createController(ResultDialogboxControllerImpl.class, "resultDialogbox.fxml");
		assertNotNull(controller);
	}


	/**
	 * Test method for {@link org.o3project.mlo.client.control.FXMLUtil#createController(java.lang.Class, java.lang.String)}.
	 */
	@Test
	public void testCreateController_invalidClass() {
		ResultDialogboxController controller = null;
		try {
			controller = FXMLUtil.createController(ResultDialogboxController.class, "resultDialogbox.fxml");
			fail();
		} catch (RuntimeException e) {
			assertNotNull(e);
		}
		assertNull(controller);
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.control.FXMLUtil#createController(java.lang.Class, java.lang.String)}.
	 */
	@Test
	public void testCreateController_noFile() {
		ResultDialogboxController controller = null;
		try {
			controller = FXMLUtil.createController(ResultDialogboxControllerImpl.class, "../control");
			fail();
		} catch (RuntimeException e) {
			assertNotNull(e);
		}
		assertNull(controller);
	}
}
