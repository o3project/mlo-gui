/**
 * MloCreateViewControllerTest
 */
package org.o3project.mlo.client.impl.control;

import static org.junit.Assert.*;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.o3project.mlo.server.dto.FlowDto;
import org.o3project.mlo.server.dto.SliceDto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.control.FXMLUtil;
import org.o3project.mlo.client.control.MloInputDataException;
import org.o3project.mlo.client.impl.control.MloCreateViewController;

/**
 *
 */
public class MloCreateViewControllerTest {

	private MloCreateViewController obj;
	private FlowDto defaultFlowDto;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		obj = FXMLUtil.createController(MloCreateViewController.class, "create.fxml");
		defaultFlowDto = new FlowDto();
		defaultFlowDto.name = "";
		defaultFlowDto.protectionLevel = "0";
		defaultFlowDto.srcCENodeName = "tokyo";
		defaultFlowDto.srcCEPortNo = "00000001";
		defaultFlowDto.dstCENodeName = "osaka";
		defaultFlowDto.dstCEPortNo = "00000004";
		defaultFlowDto.reqBandWidth = 10000;
		defaultFlowDto.reqDelay = 9999;
		obj.setDefaultFlowDto(defaultFlowDto);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		obj = null;
		defaultFlowDto = null;
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloCreateViewController#setUpView()}.
	 */
	@Test
	public void testSetUpView() {
		obj.setUpView();
		assertTrue(obj != null);
		assertTrue(obj.slicePropsDispTable != null);
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloCreateViewController#getRoot()}.
	 */
	@Test
	public void testGetRoot() {
		obj.setUpView();
		Parent pne = obj.getRoot();
		
		ScrollPane sPne = (ScrollPane)pne.lookup("#scrollPane");
		Pane slicePane = (Pane) pne.lookup("#sliceInfoPanel");
		
		assertTrue(sPne != null);
		assertTrue(slicePane != null);
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloCreateViewController#getData()}.
	 */
	@Test
	public void testGetData() {
		obj.setUpView();
		try {
			SliceDto sliceDto = obj.getData();
			
			// 初期値の値が取れることを確認
			assertEquals(sliceDto.name, "");
			assertEquals(sliceDto.flows.get(0).name, "");
			assertEquals(sliceDto.flows.get(0).srcCENodeName, "tokyo");
			assertEquals(sliceDto.flows.get(0).srcCEPortNo, "00000001");
			assertEquals(sliceDto.flows.get(0).dstCENodeName, "osaka");
			assertEquals(sliceDto.flows.get(0).dstCEPortNo, "00000004");
			assertTrue(sliceDto.flows.get(0).reqBandWidth ==  10000);
			assertTrue(sliceDto.flows.get(0).reqDelay == 9999);
			assertEquals(sliceDto.flows.get(0).protectionLevel, "0");
			
		} catch (MloInputDataException e) {
			fail();
		}
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloCreateViewController#handleButtonAction()}.
	 */
	@Test
	public void testHandleButtonAction() {
		obj.setUpView();
		Button bt = new Button();
		bt.setId("addFlowBt");
		ActionEvent event = new ActionEvent(bt, null);
		obj.handleButtonAction(event);
			
		Parent pne = obj.getRoot();
		ScrollPane sPne = (ScrollPane)pne.lookup("#scrollPane");
		VBox vb = (VBox)sPne.getContent();
		int cnt = vb.getChildren().size();
		assertTrue(cnt == 2);
	}

}
