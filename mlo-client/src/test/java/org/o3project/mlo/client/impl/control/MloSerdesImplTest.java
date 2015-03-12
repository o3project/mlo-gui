/**
 * MloSerdesImplTest.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.o3project.mlo.server.dto.FlowDto;
import org.o3project.mlo.server.dto.RestifCommonDto;
import org.o3project.mlo.server.dto.RestifComponentDto;
import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.RestifResponseDto;
import org.o3project.mlo.server.dto.SliceDto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.impl.control.MloSerdesImpl;

/**
 * SdtncSerdesImplTest
 *
 */
public class MloSerdesImplTest {
    private static final String DATA_PATH = "src/test/resources/org/o3project/mlo/client/control/data";

    private MloSerdesImpl serdes;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        serdes = new MloSerdesImpl();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        serdes = null;
    }

    /**
     * Test method for {@link org.o3project.mlo.client.impl.control.MloSerdesImpl#deserializeFromXml(java.io.InputStream)}.
     * @throws Throwable
     */
    @Test
    public void testDeserializeFromXml() throws Throwable {
        RestifResponseDto resDto = ClientTestUtil.readResFromXml(serdes, DATA_PATH, "mlo.create.res.xml");
        assertNotNull(resDto.common);
        assertNull(resDto.error);
        assertNotNull(resDto.slices);
        
        assertEquals(new Integer(1), resDto.common.version);
        assertEquals("mlo", resDto.common.srcComponent.name);
        assertEquals("clientMlo", resDto.common.dstComponent.name);
        assertEquals("Response", resDto.common.operation);
        
        assertEquals(1, resDto.slices.size());
        assertEquals(new Integer(1), resDto.slices.get(0).id);
        assertEquals("sliceA", resDto.slices.get(0).name);
        assertEquals(1, resDto.slices.get(0).flows.size());
        assertEquals(new Integer(1), resDto.slices.get(0).flows.get(0).id);
        assertEquals("fa101", resDto.slices.get(0).flows.get(0).name);
    }

    /**
     * Test method for {@link org.o3project.mlo.client.impl.control.MloSerdesImpl#serializeToXml(RestifRequestDto, java.io.OutputStream)}.
     * @throws Throwable
     */
    @Test
    public void testSerializeToXml_login_post_req() throws Throwable {

		RestifComponentDto srcComponent = new RestifComponentDto();
		srcComponent.name = "clientMlo";
		RestifComponentDto dstComponent = new RestifComponentDto();
		dstComponent.name = "mlo";

		RestifCommonDto common = new RestifCommonDto();
		common.version = 1;
		common.srcComponent = srcComponent;
		common.dstComponent = dstComponent;
		common.operation = "Request";

		FlowDto flow = new FlowDto();
        flow.name = "fa101";
        flow.srcCENodeName = "tokyo";
        flow.srcCEPortNo = "00000001";
        flow.dstCENodeName = "osaka";
        flow.dstCEPortNo = "00000004";
        flow.reqBandWidth = 5000;
        flow.reqDelay = 10;
        flow.protectionLevel = "0";
        List<FlowDto> flows = new ArrayList<FlowDto>();
        flows.add(flow);
        
		SliceDto slice = new SliceDto();
		slice.name = "sliceA";
		slice.flows = flows;
		
    	RestifRequestDto reqDto = new RestifRequestDto();
    	reqDto.common = common;
    	reqDto.slice = slice;

        assertTrue(ClientTestUtil.isSameXmlAs(serdes, reqDto, DATA_PATH, "mlo.create.req.xml"));
    }
}
