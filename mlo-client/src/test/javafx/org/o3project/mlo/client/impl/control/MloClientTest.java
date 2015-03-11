/**
 * MloClientTest.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import static org.junit.Assert.*;
import javafx.stage.Stage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.o3project.mlo.client.impl.control.MloClient;
import org.o3project.mlo.client.test.JavaFxJUnitRunner;

/**
 * MloClientTest
 *
 */
@RunWith(JavaFxJUnitRunner.class)
public class MloClientTest {
	
	private MloClient obj;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Stage primaryStage = new Stage();
		obj = MloClientBuilder.builSimpleInstance(primaryStage);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		obj = null;
	}
	
	/**
	 * 
	 */
	@Test
	public void testStop() {
		try {
			obj.stop();
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloClient#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		assertNotNull(MloClient.getInstance());
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloClient#changeInfoView()}.
	 */
	//@Test
	public void testChangeInfoView() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloClient#changeUpdateView()}.
	 */
	//@Test
	public void testChangeUpdateView() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloClient#changeCreateView()}.
	 */
	//@Test
	public void testChangeCreateView() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloClient#createIndicatorStage()}.
	 */
	@Test
	public void testCreateIndicatorStage() {
		Stage stage = MloClient.createIndicatorStage();
		assertNotNull(stage);
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloClient#showResultAndGoBackToInfoView(org.o3project.mlo.client.control.MloClientException)}.
	 */
	//@Test
	public void testShowResultAndGoBackToInfoView() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloClient#getIndicatorStage()}.
	 */
	@Test
	public void testGetIndicatorStage() {
		assertNotNull(obj.getIndicatorStage());
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloClient#closeDialog()}.
	 */
	@Test
	public void testCloseDialog() {
		Stage stage = obj.getIndicatorStage();
		stage.show();
		assertEquals(Boolean.TRUE, stage.isShowing());
		obj.closeDialog();
		assertEquals(Boolean.FALSE, stage.isShowing());
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloClient#setSelectSliceDto(org.o3project.mlo.server.dto.SliceDto)}.
	 */
	//@Test
	public void testSetSelectSliceDto() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloClient#getSliceDataManager()}.
	 */
	@Test
	public void testGetSliceDataManager() {
		assertNotNull(obj.getSliceDataManager());
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloClient#getResultDialogboxController()}.
	 */
	@Test
	public void testGetResultDialogboxController() {
		assertNotNull(obj.getResultDialogboxController());
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloClient#getSliceTaskFactory()}.
	 */
	@Test
	public void testGetSliceTaskFactory() {
		assertNotNull(obj.getSliceTaskFactory());
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloClient#getExecutorService()}.
	 */
	@Test
	public void testGetExecutorService() {
		assertNotNull(obj.getExecutorService());
	}

}
