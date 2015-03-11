/**
 * SliceDataManagerImplTest.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXB;

import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.SliceDto;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.o3project.mlo.client.control.MloClientException;
import org.o3project.mlo.client.impl.control.SliceDataManagerImpl;

public class SliceDataManagerImplTest {

    private SliceDataManagerImpl sliceDataManagerImpl;
    private static final String DATA_PATH = "src/test/resources/org/o3project/mlo/client/control/data/base";
    private MloInvokerImplDummy dummyInvoker;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        sliceDataManagerImpl = new SliceDataManagerImpl();
        dummyInvoker = new MloInvokerImplDummy();
    }

    @After
    public void tearDown() throws Exception {
        sliceDataManagerImpl = null;
        dummyInvoker = null;
    }
    
    @Test
    public void testGetSliceList() {
        try {
            dummyInvoker.setXmlName("list.res.xml");// 受信レスポンスファイル指定
            sliceDataManagerImpl.setMloInvoker(dummyInvoker);// ダミークラス設定
            List<SliceDto> sliceDto = sliceDataManagerImpl.getSliceList(); // 実行
            assertEquals(sliceDto.size(), 3);
            assertEquals(sliceDto.get(0).id, new Integer(1));
            assertEquals(sliceDto.get(0).name, "sliceA");
            assertEquals(sliceDto.get(1).id, new Integer(2));
            assertEquals(sliceDto.get(1).name, "sliceB");
            assertEquals(sliceDto.get(2).id, new Integer(3));
            assertEquals(sliceDto.get(2).name, "sliceC");
        } catch (MloClientException e) {
            fail();
        } catch (Throwable e) {
            fail();
        }
        assertEquals("", "");
    }

    @Test
    public void testGetSliceInfo() {
        try {
            dummyInvoker.setXmlName("read.res.xml");// 受信レスポンスファイル指定
            sliceDataManagerImpl.setMloInvoker(dummyInvoker);// ダミークラス設定
            RestifRequestDto requestDto = createTempDto("read.req.xml", RestifRequestDto.class);//　リクエストXML DTO変換
            SliceDto responseDto = sliceDataManagerImpl.getSliceInfo(requestDto.slice);// 実行

            assertEquals(responseDto.name, "sliceA");
            assertEquals(responseDto.id, new Integer(1));
            assertEquals(responseDto.flows.size(), 2);

            assertEquals(responseDto.flows.get(0).id, new Integer(1));
            assertEquals(responseDto.flows.get(0).srcPTNodeName, "AMN64001");
            assertEquals(responseDto.flows.get(0).srcPTNodeId, new Integer(1));
            assertEquals(responseDto.flows.get(0).dstPTNodeName, "AMN64003");
            assertEquals(responseDto.flows.get(0).dstPTNodeId, new Integer(3));
            assertEquals(responseDto.flows.get(0).usedBandWidth, new Integer(10000));
            assertEquals(responseDto.flows.get(0).delayTime, new Integer(8));
            assertEquals(responseDto.flows.get(0).underlayLogicalList, "00000001_00000003");
            assertEquals(responseDto.flows.get(0).overlayLogicalList, "00000001_00000003");
            
            assertEquals(responseDto.flows.get(0).linkInfoList.size(), 1);
            assertEquals(responseDto.flows.get(0).linkInfoList.get(0).id, new Integer(50000013));
            assertEquals(responseDto.flows.get(0).linkInfoList.get(0).srcPTNodeName, "AMN64001");
            assertEquals(responseDto.flows.get(0).linkInfoList.get(0).srcPTNodeId, new Integer(1));
            assertEquals(responseDto.flows.get(0).linkInfoList.get(0).dstPTNodeName, "AMN64003");
            assertEquals(responseDto.flows.get(0).linkInfoList.get(0).dstPTNodeId, new Integer(3));

            assertEquals(responseDto.flows.get(1).id, new Integer(2));
            assertEquals(responseDto.flows.get(1).srcPTNodeName, "AMN64001");
            assertEquals(responseDto.flows.get(1).srcPTNodeId, new Integer(1));
            assertEquals(responseDto.flows.get(1).dstPTNodeName, "AMN64003");
            assertEquals(responseDto.flows.get(1).dstPTNodeId, new Integer(3));
            assertEquals(responseDto.flows.get(1).usedBandWidth, new Integer(10000));
            assertEquals(responseDto.flows.get(1).delayTime, new Integer(9999));
            assertEquals(responseDto.flows.get(1).underlayLogicalList, "00000001_00000002_00000003");
            assertEquals(responseDto.flows.get(1).overlayLogicalList, "00000001_00000003");
            
            assertEquals(responseDto.flows.get(1).linkInfoList.size(), 2);
            assertEquals(responseDto.flows.get(1).linkInfoList.get(0).id, new Integer(50000012));
            assertEquals(responseDto.flows.get(1).linkInfoList.get(0).srcPTNodeName, "AMN64001");
            assertEquals(responseDto.flows.get(1).linkInfoList.get(0).srcPTNodeId, new Integer(1));
            assertEquals(responseDto.flows.get(1).linkInfoList.get(0).dstPTNodeName, "AMN64002");
            assertEquals(responseDto.flows.get(1).linkInfoList.get(0).dstPTNodeId, new Integer(2));
            assertEquals(responseDto.flows.get(1).linkInfoList.get(1).id, new Integer(50000023));
            assertEquals(responseDto.flows.get(1).linkInfoList.get(1).srcPTNodeName, "AMN64002");
            assertEquals(responseDto.flows.get(1).linkInfoList.get(1).srcPTNodeId, new Integer(2));
            assertEquals(responseDto.flows.get(1).linkInfoList.get(1).dstPTNodeName, "AMN64003");
            assertEquals(responseDto.flows.get(1).linkInfoList.get(1).dstPTNodeId, new Integer(3));
            
        } catch (MloClientException e) {
            fail();
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testCreateSliceInfo() {
        try {
            dummyInvoker.setXmlName("create.res.xml");// 受信レスポンスファイル指定
            sliceDataManagerImpl.setMloInvoker(dummyInvoker);// ダミークラス設定
            RestifRequestDto requestDto = createTempDto("create.req.xml", RestifRequestDto.class);//　リクエストXML DTO変換
            SliceDto responseDto = sliceDataManagerImpl.createSliceInfo(requestDto.slice);// 実行
            assertEquals(responseDto.name, "slice1");
            assertEquals(responseDto.id, new Integer(1));
            assertEquals(responseDto.flows.size(), 1);
            assertEquals(responseDto.flows.get(0).name, "fa101");
            assertEquals(responseDto.flows.get(0).id, new Integer(1));
        } catch (MloClientException e) {
            fail();
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testUpdateSliceInfo() {
        try {
            dummyInvoker.setXmlName("update.res.xml");// 受信レスポンスファイル指定
            sliceDataManagerImpl.setMloInvoker(dummyInvoker);// ダミークラス設定
            RestifRequestDto requestDto = createTempDto("update.req.xml", RestifRequestDto.class);//　リクエストXML DTO変換
            SliceDto responseDto = sliceDataManagerImpl.updateSliceInfo(requestDto.slice);// 実行
            assertEquals(responseDto.id, new Integer(1));
            assertEquals(responseDto.flows.size(), 2);
            assertEquals(responseDto.flows.get(0).name, "fa101");
            assertEquals(responseDto.flows.get(0).id, new Integer(1));
            assertEquals(responseDto.flows.get(1).name, "fa102");
            assertEquals(responseDto.flows.get(1).id, new Integer(2));
        } catch (MloClientException e) {
            fail();
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testDeleteSliceInfo() {
        try {
            dummyInvoker.setXmlName("delete.res.xml");// 受信レスポンスファイル指定
            sliceDataManagerImpl.setMloInvoker(dummyInvoker);// ダミークラス設定
            RestifRequestDto requestDto = createTempDto("delete.req.xml", RestifRequestDto.class);//　リクエストXML DTO変換
            SliceDto responseDto = sliceDataManagerImpl.deleteSliceInfo(requestDto.slice);// 実行
            assertEquals(responseDto.id, new Integer(1));
        } catch (MloClientException e) {
            fail();
        } catch (Throwable e) {
            fail();
        }
    }

    /**
	 * テンプレートファイル(xml）を読み込み、リクエストDtoを作成する
	 * @param xmlFile
	 * @return リクエストDto
	 * @throws MloException 
	 */
	private static <T> T createTempDto(String xmlFileName, Class<T> type) {
		T reqDto = null;
		InputStream istream = null;
		File xmlFile = new File(DATA_PATH, xmlFileName);
		try {
			istream = new FileInputStream(xmlFile);
			reqDto = JAXB.unmarshal(istream, type);
		} catch (FileNotFoundException e) {
			// 基本、このルートには入らない
			fail();
		} finally {
			if (istream != null) {
				try {
					istream.close();
				} catch (IOException e) {
					reqDto = null;
					e.printStackTrace();
				}
			}
		}
		
		return reqDto;
	}
}
