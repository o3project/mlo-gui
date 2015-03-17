package org.o3project.mlo.client.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.o3project.mlo.server.dto.FlowDto;
import org.o3project.mlo.server.dto.LinkInfoDto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.control.MloInputDataException;
import org.o3project.mlo.client.view.FlowPanel;
import org.o3project.mlo.client.view.MloCommonTable;

public class FlowPanelTest {

    private FlowPanel obj;
    private FlowDto flowDto;
    
    private FlowDto defaultFlowDto;

    private static final String EDIT_PARAM_KEY_FLOW_NAME = "FlowName";
    private static final String EDIT_PARAM_KEY_SRC_CE_NODE_NAME = "SourceCENodeName";
    private static final String EDIT_PARAM_KEY_SRC_CE_PORT_NO = "SourceCEPortNo";
    private static final String EDIT_PARAM_KEY_DST_CE_NODE_NAME = "DestCENodeName";
    private static final String EDIT_PARAM_KEY_DST_CE_PORT_NO = "DestCEPortNo";
    private static final String EDIT_PARAM_KEY_REQUEST_BANDWIDTH = "RequestBandWidth";
    private static final String EDIT_PARAM_KEY_REQUEST_DELAY = "RequestDelay";
    private static final String EDIT_PARAM_KEY_PROTECTION_LEVEL = "ProtectionLevel";
    private static final String ERROR_DETAIL_PREFIX = "BadRequest/InvalidData/";
    
    private static final int BANDWIDTH = 10;
    private static final int DELAY = 9999;

    /*
     * Sets up
     */
    @Before
    public void setUp() throws Exception {
    	
        flowDto = new FlowDto();
        flowDto.type = "RequestType";
        flowDto.name = "FlowName";
        flowDto.id = 12345678;
        flowDto.srcCENodeName = "SourceCENodeName";
        flowDto.srcCEPortNo = "SourceCEPortNo";
        flowDto.dstCENodeName = "DestCENodeName";
        flowDto.dstCEPortNo = "DestCEPortNo";
        flowDto.reqBandWidth = 500;
        flowDto.reqDelay = 400;
        flowDto.protectionLevel = "ProtectionLevel";
        flowDto.srcPTNodeName = "SourcePTNodeName";
        flowDto.srcPTNodeId = 300;
        flowDto.dstPTNodeName = "DestPTNodeName";
        flowDto.dstPTNodeId = 200;
        flowDto.usedBandWidth = 100;
        flowDto.delayTime = 150;
        flowDto.underlayLogicalList = "UnderlayLogicalList";
        flowDto.overlayLogicalList = "OverlayLogicalList";
        flowDto.linkInfoList = null;

        LinkInfoDto link = new LinkInfoDto();
        link.id = 123;
        link.srcPTNodeName = "SourcePTNodeName";
        link.srcPTNodeId = 234;
        link.dstPTNodeName = "DestPTNodeName";
        link.dstPTNodeId = 345;

        List<LinkInfoDto> list = new ArrayList<LinkInfoDto>();

        flowDto.linkInfoList = list;
        flowDto.linkInfoList.add(link);
        
        defaultFlowDto = new FlowDto();
        defaultFlowDto.name = "";
        defaultFlowDto.protectionLevel = "0";
        defaultFlowDto.srcCENodeName = "tokyo";
        defaultFlowDto.srcCEPortNo = "00000001";
        defaultFlowDto.dstCENodeName = "osaka";
        defaultFlowDto.dstCEPortNo = "00000004";
        defaultFlowDto.reqBandWidth = BANDWIDTH;
        defaultFlowDto.reqDelay = DELAY;
    }

    /*
     * Cleans up
     */
    @After
    public void tearDown() throws Exception {
    	flowDto = null;
    	defaultFlowDto = null;
    }

    /*
     * A constructor 1
     */
    @Test
    public void constructorTest1() {
        try {
            obj = new FlowPanel("test", defaultFlowDto);
            assertTrue(true);
        } catch (Exception e) {
        	fail();
        }
    }

    /*
     * A constructor 2
     */
    @Test
    public void constructorTest2() {
        try {
            obj = new FlowPanel("test", flowDto, true, defaultFlowDto);
            assertTrue(true);
        } catch (Exception e) {
        	fail();
        }
    }

    /*
     * A constructor 3
     */
    @Test
    public void constructorTest3() {
        try {
            obj = new FlowPanel("test", flowDto, false, null);
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }

    /*
     * getCreateFlowDto test (NORMAL)
     */
    @Test
    public void getCreateFlowDtoTest() {
        try {
            obj = new FlowPanel("test", defaultFlowDto);
            flowDto = obj.getCreateFlowDto();
            assertEquals(flowDto.name, defaultFlowDto.name);
            assertEquals(flowDto.srcCENodeName, defaultFlowDto.srcCENodeName);
            assertEquals(flowDto.srcCEPortNo, defaultFlowDto.srcCEPortNo);
            assertEquals(flowDto.dstCENodeName, defaultFlowDto.dstCENodeName);
            assertEquals(flowDto.dstCEPortNo, defaultFlowDto.dstCEPortNo);
            assertEquals(flowDto.reqBandWidth, defaultFlowDto.reqBandWidth);
            assertEquals(flowDto.reqDelay, defaultFlowDto.reqDelay);
            assertEquals(flowDto.protectionLevel, defaultFlowDto.protectionLevel);
        } catch (Exception e) {
        	fail();
        }

    }

    /*
     * getCreateFlowDto test (ANOMALY: Invalid band width)
     */
    @Test
    public void getCreateFlowDtoTest2() {
        try {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<String, String>();
            paramMap.put(EDIT_PARAM_KEY_FLOW_NAME, "flowname");
            paramMap.put(EDIT_PARAM_KEY_SRC_CE_NODE_NAME, "cenodename");
            paramMap.put(EDIT_PARAM_KEY_SRC_CE_PORT_NO, "ceportno");
            paramMap.put(EDIT_PARAM_KEY_DST_CE_NODE_NAME, "cenodename");
            paramMap.put(EDIT_PARAM_KEY_DST_CE_PORT_NO, "portno");
            paramMap.put(EDIT_PARAM_KEY_REQUEST_BANDWIDTH, "abc");
            paramMap.put(EDIT_PARAM_KEY_REQUEST_DELAY, "123");
            paramMap.put(EDIT_PARAM_KEY_PROTECTION_LEVEL, "2");
            MloCommonTable tbView = new MloCommonTable(paramMap, 690d, true);
            obj = new FlowPanel("test", null);
            obj.setTbView(tbView);
            flowDto = obj.getCreateFlowDto();
            fail();
        } catch (MloInputDataException e) {
            assertEquals(ERROR_DETAIL_PREFIX + EDIT_PARAM_KEY_REQUEST_BANDWIDTH, e.getMessage());
        }
    }

    /*
     * getCreateFlowDto test (ANOMALY: Invalid delay time)
     */
    @Test
    public void getCreateFlowDtoTest3() {
        try {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<String, String>();
            paramMap.put(EDIT_PARAM_KEY_FLOW_NAME, "flowname");
            paramMap.put(EDIT_PARAM_KEY_SRC_CE_NODE_NAME, "cenodename");
            paramMap.put(EDIT_PARAM_KEY_SRC_CE_PORT_NO, "ceportno");
            paramMap.put(EDIT_PARAM_KEY_DST_CE_NODE_NAME, "cenodename");
            paramMap.put(EDIT_PARAM_KEY_DST_CE_PORT_NO, "portno");
            paramMap.put(EDIT_PARAM_KEY_REQUEST_BANDWIDTH, "234");
            paramMap.put(EDIT_PARAM_KEY_REQUEST_DELAY, "a");
            paramMap.put(EDIT_PARAM_KEY_PROTECTION_LEVEL, "2");
            MloCommonTable tbView = new MloCommonTable(paramMap, 690d, true);
            obj = new FlowPanel("test", null);
            obj.setTbView(tbView);
            flowDto = obj.getCreateFlowDto();
            fail();
        } catch (MloInputDataException e) {
            assertEquals(ERROR_DETAIL_PREFIX + EDIT_PARAM_KEY_REQUEST_DELAY, e.getMessage());
        }
    }

    /*
     * getUpdateFlowDto test (NORMAL)
     */
    @Test
    public void getUpdateFlowDtoTest() {
        try {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<String, String>();
            paramMap.put(EDIT_PARAM_KEY_FLOW_NAME, defaultFlowDto.name);
            paramMap.put(EDIT_PARAM_KEY_SRC_CE_NODE_NAME, defaultFlowDto.srcCENodeName);
            paramMap.put(EDIT_PARAM_KEY_SRC_CE_PORT_NO, defaultFlowDto.srcCEPortNo);
            paramMap.put(EDIT_PARAM_KEY_DST_CE_NODE_NAME, defaultFlowDto.dstCENodeName);
            paramMap.put(EDIT_PARAM_KEY_DST_CE_PORT_NO, defaultFlowDto.dstCEPortNo);
            paramMap.put(EDIT_PARAM_KEY_REQUEST_BANDWIDTH, String.valueOf(defaultFlowDto.reqBandWidth));
            paramMap.put(EDIT_PARAM_KEY_REQUEST_DELAY, String.valueOf(defaultFlowDto.reqDelay));
            paramMap.put(EDIT_PARAM_KEY_PROTECTION_LEVEL, defaultFlowDto.protectionLevel);
            
            obj = new FlowPanel("test", flowDto, true, defaultFlowDto);
            MloCommonTable tbView = new MloCommonTable(paramMap, 690d, true);
            obj.setTbView(tbView);
            FlowDto resFlowDto = obj.getUpdateFlowDto();
            assertEquals(resFlowDto.id, flowDto.id);
            assertEquals(resFlowDto.type, "mod");
            assertEquals(resFlowDto.name, defaultFlowDto.name);
            assertEquals(resFlowDto.srcCENodeName, defaultFlowDto.srcCENodeName);
            assertEquals(resFlowDto.srcCEPortNo, defaultFlowDto.srcCEPortNo);
            assertEquals(resFlowDto.dstCENodeName, defaultFlowDto.dstCENodeName);
            assertEquals(resFlowDto.dstCEPortNo, defaultFlowDto.dstCEPortNo);
            assertEquals(resFlowDto.reqBandWidth, defaultFlowDto.reqBandWidth);
            assertEquals(resFlowDto.reqDelay, defaultFlowDto.reqDelay);
            assertEquals(resFlowDto.protectionLevel, defaultFlowDto.protectionLevel);
        } catch (MloInputDataException e) {
        	fail();
        }
    }

    /*
     * getUpdateFlowDto test (ANOMALY: Invalid band width)s
     */
    @Test
    public void getUpdateFlowDtoTest2() {
        try {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<String, String>();
            paramMap.put(EDIT_PARAM_KEY_FLOW_NAME, "flowname");
            paramMap.put(EDIT_PARAM_KEY_SRC_CE_NODE_NAME, "cenodename");
            paramMap.put(EDIT_PARAM_KEY_SRC_CE_PORT_NO, "ceportno");
            paramMap.put(EDIT_PARAM_KEY_DST_CE_NODE_NAME, "cenodename");
            paramMap.put(EDIT_PARAM_KEY_DST_CE_PORT_NO, "portno");
            paramMap.put(EDIT_PARAM_KEY_REQUEST_BANDWIDTH, "a");
            paramMap.put(EDIT_PARAM_KEY_REQUEST_DELAY, "234");
            paramMap.put(EDIT_PARAM_KEY_PROTECTION_LEVEL, "2");

            MloCommonTable tbView = new MloCommonTable(paramMap, 690d, true);
            obj = new FlowPanel("test", flowDto, true, defaultFlowDto);
            obj.setTbView(tbView);
            flowDto = obj.getUpdateFlowDto();
            fail();
        } catch (MloInputDataException e) {
            assertEquals(ERROR_DETAIL_PREFIX + EDIT_PARAM_KEY_REQUEST_BANDWIDTH, e.getMessage());
        }
    }

    /*
     * getUpdateFlowDto test (ANOMALY: Invalid delay time)
     */
    @Test
    public void getUpdateFlowDtoTest3() {
        try {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<String, String>();
            paramMap.put(EDIT_PARAM_KEY_FLOW_NAME, "flowname");
            paramMap.put(EDIT_PARAM_KEY_SRC_CE_NODE_NAME, "cenodename");
            paramMap.put(EDIT_PARAM_KEY_SRC_CE_PORT_NO, "ceportno");
            paramMap.put(EDIT_PARAM_KEY_DST_CE_NODE_NAME, "cenodename");
            paramMap.put(EDIT_PARAM_KEY_DST_CE_PORT_NO, "portno");
            paramMap.put(EDIT_PARAM_KEY_REQUEST_BANDWIDTH, "123");
            paramMap.put(EDIT_PARAM_KEY_REQUEST_DELAY, "a");
            paramMap.put(EDIT_PARAM_KEY_PROTECTION_LEVEL, "2");

            MloCommonTable tbView = new MloCommonTable(paramMap, 690d, true);
            obj = new FlowPanel("test", flowDto, true, null);
            obj.setTbView(tbView);
            flowDto = obj.getUpdateFlowDto();
            fail();
        } catch (MloInputDataException e) {
            assertEquals(ERROR_DETAIL_PREFIX + EDIT_PARAM_KEY_REQUEST_DELAY, e.getMessage());
        }
    }

    /*
     * getDeleteFlowDto test (NORMAL)
     */
    @Test
    public void getDeleteFlowDtoTest() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<String, String>();
        paramMap.put(EDIT_PARAM_KEY_FLOW_NAME, "flowname");
        paramMap.put(EDIT_PARAM_KEY_SRC_CE_NODE_NAME, "cenodename");
        paramMap.put(EDIT_PARAM_KEY_SRC_CE_PORT_NO, "ceportno");
        paramMap.put(EDIT_PARAM_KEY_DST_CE_NODE_NAME, "cenodename");
        paramMap.put(EDIT_PARAM_KEY_DST_CE_PORT_NO, "portno");
        paramMap.put(EDIT_PARAM_KEY_REQUEST_BANDWIDTH, "123");
        paramMap.put(EDIT_PARAM_KEY_REQUEST_DELAY, "234");
        paramMap.put(EDIT_PARAM_KEY_PROTECTION_LEVEL, "2");

        obj = new FlowPanel("test", flowDto, true, defaultFlowDto);
        FlowDto delflow = obj.getDeleteFlowDto();
        assertEquals(delflow.id, flowDto.id);
        assertEquals(delflow.type, "del");
    }

    /*
     * getFlowType test
     */
    @Test
    public void getFlowTypeTest() {
        obj = new FlowPanel("test", defaultFlowDto);
        assertEquals(obj.getFlowType(), "add");
    }

    /*
     * disposeTest test
     */
    @Test
    public void disposeTest() {
        try{
            obj = new FlowPanel("test", flowDto, true, null);
            obj.dispose();
            assertTrue(true);
        }catch (Exception e){
            fail();
        }
    }

}
