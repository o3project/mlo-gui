/**
 * ResultDialogboxControllerImplTest.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import static org.junit.Assert.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.o3project.mlo.client.control.FXMLUtil;
import org.o3project.mlo.client.impl.control.ResultDialogboxControllerImpl;
import org.o3project.mlo.client.test.JavaFxJUnitRunner;

/**
 * ResultDialogboxControllerImplTest
 *
 */
@RunWith(JavaFxJUnitRunner.class)
public class ResultDialogboxControllerImplTest {
	
	private ResultDialogboxControllerImpl obj;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		obj = FXMLUtil.createController(ResultDialogboxControllerImpl.class, "resultDialogbox.fxml");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		obj = null;
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ResultDialogboxControllerImpl#getStage()}.
	 */
	@Test
	public void testGetStage() {
		Stage stage = obj.getStage();
		assertNotNull(stage);
		assertEquals(Modality.APPLICATION_MODAL, stage.getModality());
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ResultDialogboxControllerImpl#getOkButton()}.
	 */
	@Test
	public void testGetOkButton() {
		Button okButton = obj.getOkButton();
		assertNotNull(okButton);
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ResultDialogboxControllerImpl#getMsgLabel()}.
	 */
	@Test
	public void testGetMsgLabel() {
		Label msgLabel = obj.getMsgLabel();
		assertNotNull(msgLabel);
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ResultDialogboxControllerImpl#showDialog(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testShowDialog() {
		obj.showDialog("myTitle", "myMessage", "myDetail");
		assertEquals("myTitle", obj.getStage().getTitle());
		assertEquals("myMessage" + System.lineSeparator() + "myDetail", obj.getMsgLabel().getText());
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ResultDialogboxControllerImpl#closeDialog()}.
	 */
	@Test
	public void testCloseDialog() {
		obj.showDialog("myTitle", "myMessage", "myDetail");
		assertEquals(Boolean.TRUE, obj.getStage().isShowing());
		obj.closeDialog();
		assertEquals(Boolean.FALSE, obj.getStage().isShowing());
	}

}
