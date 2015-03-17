/**
 * MloUpdateViewControllerTest
 */
package org.o3project.mlo.client.impl.control;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXB;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.o3project.mlo.server.dto.FlowDto;
import org.o3project.mlo.server.dto.SliceDto;
import org.o3project.mlo.server.logic.InternalException;
import org.o3project.mlo.server.logic.MloException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.control.FXMLUtil;
import org.o3project.mlo.client.control.MloInputDataException;
import org.o3project.mlo.client.impl.control.MloUpdateViewController;

/**
 *
 */
public class MloUpdateViewControllerTest {

	private MloUpdateViewController obj;
	private SliceDto sliceDto;
	private FlowDto defaultFlowDto;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		obj = FXMLUtil.createController(MloUpdateViewController.class, "update.fxml");
		File xmlFile = new File("src/test/resources/org/o3project/mlo/client/control/data/base", "updateViewTargetSlice.xml");
		sliceDto = readDto(xmlFile);
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
		sliceDto = null;
		defaultFlowDto = null;
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloUpdateViewController#setUpView()}.
	 */
	@Test
	public void testSetUpView() {
		obj.setUpView();
		obj.setData(sliceDto);
		assertTrue(obj != null);
		assertTrue(obj.slicePropsDispTable != null);
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloUpdateViewController#getRoot()}.
	 */
	@Test
	public void testGetRoot() {
		obj.setUpView();
		obj.setData(sliceDto);
		Parent pne = obj.getRoot();
		
		ScrollPane sPne = (ScrollPane)pne.lookup("#scrollPane");
		assertTrue(sPne != null);
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloUpdateViewController#getData()}.
	 */
	@Test
	public void testGetData() {
		obj.setUpView();
		obj.setData(sliceDto);
		Button bt = new Button();
		bt.setId("addFlowBt");
		ActionEvent event = new ActionEvent(bt, null);
		obj.handleButtonAction(event);
		
		try {
			SliceDto sliceDto = obj.getData();
			
			// Checks whether default values can be retrieved or not.
			assertEquals(sliceDto.name, null);
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
	 * Test method for {@link org.o3project.mlo.client.impl.control.MloUpdateViewController#handleButtonAction()}.
	 */
	@Test
	public void testHandleButtonAction() {
		obj.setUpView();
		obj.setData(sliceDto);
		Button bt = new Button();
		bt.setId("addFlowBt");
		ActionEvent event = new ActionEvent(bt, null);
		obj.handleButtonAction(event);
			
		Parent pne = obj.getRoot();
		ScrollPane sPne = (ScrollPane)pne.lookup("#scrollPane");
		VBox vb = (VBox)sPne.getContent();
		int cnt = vb.getChildren().size();
		assertTrue(cnt == 3);
	}
	
	private SliceDto readDto(File xmlFile) throws MloException{
		SliceDto resDto = null;
		InputStream istream = null;
		try {
			istream = new FileInputStream(xmlFile);
			resDto = JAXB.unmarshal(istream, SliceDto.class);
		} catch (FileNotFoundException e) {
			throw new InternalException("templete file is not found [file = " + xmlFile.getName() +"]");
		} finally {
			if (istream != null) {
				try {
					istream.close();
				} catch (IOException e) {
					resDto = null;
					e.printStackTrace();
				}
			}
		}
		
		return resDto;
	}
	
}
