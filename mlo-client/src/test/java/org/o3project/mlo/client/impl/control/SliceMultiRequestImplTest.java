/**
 * SliceMultiRequestImplTest.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.o3project.mlo.server.dto.FlowDto;
import org.o3project.mlo.server.dto.RestifErrorDto;
import org.o3project.mlo.server.dto.SliceDto;
import org.o3project.mlo.server.logic.ConfigProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.control.MloClientException;
import org.o3project.mlo.client.control.MloNbiException;
import org.o3project.mlo.client.control.SliceDataManager;
import org.o3project.mlo.client.impl.control.ClientConfigImpl;
import org.o3project.mlo.client.impl.control.ClientConfigProviderImpl;
import org.o3project.mlo.client.impl.control.SliceMultiRequestImpl;

/**
 * SliceMultiRequestImplTest
 *
 */
public class SliceMultiRequestImplTest {

	private static final String DATA_PATH = "src/test/resources/org/o3project/mlo/client/control/data";
	
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

	private SliceMultiRequestImpl initObject(String fileName) {
		File propFile = new File(DATA_PATH, fileName);
		ConfigProvider configProvider = new ClientConfigProviderImpl(propFile.getAbsolutePath());
		ClientConfigImpl clientConfig = new ClientConfigImpl();
		clientConfig.setConfigProvider(configProvider);
		SliceMultiRequestImpl obj = new SliceMultiRequestImpl(clientConfig);
		return obj;
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#doCreateSlices(org.o3project.mlo.client.control.SliceDataManager, java.lang.Integer, java.lang.Integer)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testDoCreateSlices() throws InterruptedException {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");
		
		SliceDataManagerStubImpl sdm = new SliceDataManagerStubImpl();
		
		obj.doCreateSlices(sdm, 5, 10, 1, 2);

		synchronized (sdm.createdSliceDtos) {
			assertEquals(5, sdm.createdSliceDtos.size());
			for (int idx = 0; idx < 5; idx += 1) {
				assertEquals(10, sdm.createdSliceDtos.get(idx).flows.size());
				for (FlowDto flow : sdm.createdSliceDtos.get(idx).flows) {
					assertEquals(Integer.valueOf(1), flow.reqBandWidth);
					assertEquals(Integer.valueOf(2), flow.reqDelay);
				}
			}
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#doCreateSlices(org.o3project.mlo.client.control.SliceDataManager, java.lang.Integer, java.lang.Integer)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testDoCreateSlices_FailedToCreate() throws InterruptedException {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");
		
		SliceDataManagerStubImpl sdm = new SliceDataManagerStubImpl();
		sdm.isFailedToCreateSlice = true;
		
		obj.doCreateSlices(sdm, 5, 10, 1, 2);

		synchronized (sdm.createdSliceDtos) {
			assertEquals(5, sdm.createdSliceDtos.size());
			for (int idx = 0; idx < 5; idx += 1) {
				assertEquals(10, sdm.createdSliceDtos.get(idx).flows.size());
				for (FlowDto flow : sdm.createdSliceDtos.get(idx).flows) {
					assertEquals(Integer.valueOf(1), flow.reqBandWidth);
					assertEquals(Integer.valueOf(2), flow.reqDelay);
				}
			}
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#doUpdateAllSlices(org.o3project.mlo.client.control.SliceDataManager)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testDoUpdateAllSlices() throws InterruptedException {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");
		
		SliceDataManagerStubImpl sdm = new SliceDataManagerStubImpl();
		
		obj.doCreateSlices(sdm, 3, 10, 0, 0);
		obj.doUpdateAllSlices(sdm, 1, 2);

		synchronized (sdm.updatedSliceDtos) {
			List<Integer> ids = Arrays.asList(
					0,
					1,
					2);
			assertEquals(3, sdm.updatedSliceDtos.size());
			for (int idx = 0; idx < 3; idx += 1) {
				assertEquals(true, ids.contains(sdm.updatedSliceDtos.get(idx).id));
				for (FlowDto flow : sdm.updatedSliceDtos.get(idx).flows) {
					assertEquals(Integer.valueOf(1), flow.reqBandWidth);
					assertEquals(Integer.valueOf(2), flow.reqDelay);
				}
			}
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#doUpdateAllSlices(org.o3project.mlo.client.control.SliceDataManager)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testDoUpdateAllSlices_FailedToUpdateSlice() throws InterruptedException {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");
		
		SliceDataManagerStubImpl sdm = new SliceDataManagerStubImpl();
		sdm.isFailedToUpdateSlice = true;
		
		obj.doCreateSlices(sdm, 3, 10, 0, 0);
		obj.doUpdateAllSlices(sdm, 1, 2);

		synchronized (sdm.updatedSliceDtos) {
			List<Integer> ids = Arrays.asList(
					0,
					1,
					2);
			assertEquals(3, sdm.updatedSliceDtos.size());
			for (int idx = 0; idx < 3; idx += 1) {
				assertEquals(true, ids.contains(sdm.updatedSliceDtos.get(idx).id));
				for (FlowDto flow : sdm.updatedSliceDtos.get(idx).flows) {
					assertEquals(Integer.valueOf(1), flow.reqBandWidth);
					assertEquals(Integer.valueOf(2), flow.reqDelay);
				}
			}
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#doUpdateAllSlices(org.o3project.mlo.client.control.SliceDataManager)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testDoUpdateAllSlices_FailedToGetSlices() throws InterruptedException {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");
		
		SliceDataManagerStubImpl sdm = new SliceDataManagerStubImpl();
		sdm.isFailedToGetList = true;
		
		obj.doCreateSlices(sdm, 3, 10, 0, 0);
		try {
			obj.doUpdateAllSlices(sdm, 1, 2);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(e.getMessage(), "getSliceList is failed");
		}

		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#doUpdateAllSlices(org.o3project.mlo.client.control.SliceDataManager)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testDoUpdateAllSlices_NullListToGetSlices() throws InterruptedException {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");
		
		SliceDataManagerStubImpl sdm = new SliceDataManagerStubImpl();
		sdm.isNullList = true;
		
		obj.doCreateSlices(sdm, 3, 10, 0, 0);
		try {
			obj.doUpdateAllSlices(sdm, 1, 2);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(e.getMessage(), "getSliceList is null");
		}

		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#doUpdateAllSlices(org.o3project.mlo.client.control.SliceDataManager)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testDoUpdateAllSlices_FailedToGetSliceInfo() throws InterruptedException {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");
		
		SliceDataManagerStubImpl sdm = new SliceDataManagerStubImpl();
		sdm.isFailedToGetInfo = true;
		
		obj.doCreateSlices(sdm, 3, 10, 0, 0);
		try {
			obj.doUpdateAllSlices(sdm, 1, 2);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(e.getMessage(), "getSliceInfo is failed");
		}

		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#doDeleteAllSlices(org.o3project.mlo.client.control.SliceDataManager)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testDoDeleteAllSlices() throws InterruptedException {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");
		
		SliceDataManagerStubImpl sdm = new SliceDataManagerStubImpl();
		
		obj.doCreateSlices(sdm, 3, 10, 0, 0);
		obj.doDeleteAllSlices(sdm);

		synchronized (sdm.deletedSliceDtos) {
			List<String> names = Arrays.asList(
					"slice00000000",
					"slice00000001",
					"slice00000002");
			assertEquals(3, sdm.deletedSliceDtos.size());
			for (int idx = 0; idx < 3; idx += 1) {
				assertEquals(true, names.contains(sdm.deletedSliceDtos.get(idx).name));
			}
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#doDeleteAllSlices(org.o3project.mlo.client.control.SliceDataManager)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testDoDeleteAllSlices_FailedToDeleteSlice() throws InterruptedException {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");
		
		SliceDataManagerStubImpl sdm = new SliceDataManagerStubImpl();
		sdm.isFailedToDeleteSlice = true;
		
		obj.doCreateSlices(sdm, 3, 10, 0, 0);
		obj.doDeleteAllSlices(sdm);

		synchronized (sdm.deletedSliceDtos) {
			List<String> names = Arrays.asList(
					"slice00000000",
					"slice00000001",
					"slice00000002");
			assertEquals(3, sdm.deletedSliceDtos.size());
			for (int idx = 0; idx < 3; idx += 1) {
				assertEquals(true, names.contains(sdm.deletedSliceDtos.get(idx).name));
			}
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#doDeleteAllSlices(org.o3project.mlo.client.control.SliceDataManager)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testDoDeleteAllSlices_FailedToGetSlices() throws InterruptedException {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");
		
		SliceDataManagerStubImpl sdm = new SliceDataManagerStubImpl();
		sdm.isFailedToGetList = true;
		
		try {
			obj.doDeleteAllSlices(sdm);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(e.getMessage(), "getSliceList is failed");
		}

		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#doDeleteAllSlices(org.o3project.mlo.client.control.SliceDataManager)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testDoDeleteAllSlices_NullListToGetSlices() throws InterruptedException {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");
		
		SliceDataManagerStubImpl sdm = new SliceDataManagerStubImpl();
		sdm.isNullList = true;
		
		try {
			obj.doDeleteAllSlices(sdm);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(e.getMessage(), "getSliceList is null");
		}

		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#createSliceDtos(java.lang.Integer, java.lang.Integer)}.
	 */
	@Test
	public void testCreateSliceDtos() {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");

		List<SliceDto> sliceDtos = null;
		int nSlice = 4;
		int nFlowForSlice = 4;
		sliceDtos = obj.createSliceDtos(nSlice, nFlowForSlice, null, null);

		assertEquals(4, sliceDtos.size());
		assertEquals("slice00000000", sliceDtos.get(0).name);
		assertEquals("slice00000001", sliceDtos.get(1).name);
		assertEquals("slice00000002", sliceDtos.get(2).name);
		assertEquals("slice00000003", sliceDtos.get(3).name);
		
		List<FlowDto> flows = null;
		
		flows = sliceDtos.get(0).flows;
		assertEquals(4, flows.size());
		_assertRequestFlowDto(flows.get(0), "flow00000000", "00000100");
		_assertRequestFlowDto(flows.get(1), "flow00000001", "00000101");
		_assertRequestFlowDto(flows.get(2), "flow00000002", "00000102");
		_assertRequestFlowDto(flows.get(3), "flow00000003", "00000103");
		
		flows = sliceDtos.get(1).flows;
		assertEquals(4, flows.size());
		_assertRequestFlowDto(flows.get(0), "flow00000004", "00000104");
		_assertRequestFlowDto(flows.get(1), "flow00000005", "00000105");
		_assertRequestFlowDto(flows.get(2), "flow00000006", "00000106");
		_assertRequestFlowDto(flows.get(3), "flow00000007", "00000107");
		
		flows = sliceDtos.get(2).flows;
		assertEquals(4, flows.size());
		_assertRequestFlowDto(flows.get(0), "flow00000008", "00000108");
		_assertRequestFlowDto(flows.get(1), "flow00000009", "00000109");
		_assertRequestFlowDto(flows.get(2), "flow00000010", "00000110");
		_assertRequestFlowDto(flows.get(3), "flow00000011", "00000111");
		
		flows = sliceDtos.get(3).flows;
		assertEquals(4, flows.size());
		_assertRequestFlowDto(flows.get(0), "flow00000012", "00000112");
		_assertRequestFlowDto(flows.get(1), "flow00000013", "00000113");
		_assertRequestFlowDto(flows.get(2), "flow00000014", "00000114");
		_assertRequestFlowDto(flows.get(3), "flow00000015", "00000115");
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#createRequestFlowDto(java.lang.String, java.lang.Integer)}.
	 */
	@Test
	public void testCreateRequestFlowDto() {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");

		FlowDto flowDto = null;
		
		flowDto = obj.createRequestFlowDto("myFlow1", 135, null, null);
		final String flowName = "myFlow1";
		final String cePortNo = "00000135";
		_assertRequestFlowDto(flowDto, flowName, cePortNo);
	}

	/**
	 * @param flowDto
	 * @param flowName
	 * @param cePortNo
	 */
	private void _assertRequestFlowDto(FlowDto flowDto, final String flowName,
			final String cePortNo) {
		assertEquals(flowName, flowDto.name);
		assertEquals("tokyo", flowDto.srcCENodeName);
		assertEquals(cePortNo, flowDto.srcCEPortNo);
		assertEquals("osaka", flowDto.dstCENodeName);
		assertEquals(cePortNo, flowDto.dstCEPortNo);
		assertEquals(Integer.valueOf(1), flowDto.reqBandWidth);
		assertEquals(true, flowDto.reqDelay.equals(9999) || flowDto.reqDelay.equals(10));
		assertEquals("0", flowDto.protectionLevel);
		
		assertEquals(null, flowDto.id);
		assertEquals(null, flowDto.srcPTNodeId);
		assertEquals(null, flowDto.srcPTNodeName);
		assertEquals(null, flowDto.dstPTNodeId);
		assertEquals(null, flowDto.dstPTNodeName);
		assertEquals(null, flowDto.usedBandWidth);
		assertEquals(null, flowDto.delayTime);
		assertEquals(null, flowDto.linkInfoList);
		assertEquals(null, flowDto.overlayLogicalList);
		assertEquals(null, flowDto.underlayLogicalList);
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#createRequestFlowDto(java.lang.String, java.lang.Integer)}.
	 */
	@Test
	public void testCreateRequestFlowDto_checkRandomReqDelay() {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");

		Set<Integer> delaySet = new HashSet<>(); 
		FlowDto flowDto = null;
		final int offset = 123;
		final int len = 10000;
		String name = null;
		Integer portNo = null;
		for (int idx = 0; idx < len; idx += 1) {
			name = String.format("flow%08d", idx);
			portNo = offset + idx;
			flowDto = obj.createRequestFlowDto(name, portNo, null, null);
			delaySet.add(flowDto.reqDelay);
		}
		
		assertEquals(true, delaySet.contains(10));
		assertEquals(true, delaySet.contains(9999));
		assertEquals(2, delaySet.size());
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#createRequestFlowDto(java.lang.String, java.lang.Integer)}.
	 */
	@Test
	public void testCreateRequestFlowDto_checkHitachiSpecification() {
		SliceMultiRequestImpl obj = initObject("client.config.mloClient.properties");

		FlowDto flowDto = obj.createRequestFlowDto("flow00000001", 100, 2, 3);
		
		assertEquals(flowDto.name, "flow00000001");
		assertEquals(flowDto.srcCENodeName, "tokyo123");
		assertEquals(flowDto.srcCEPortNo, "00000100");
		assertEquals(flowDto.dstCENodeName, "osaka123");
		assertEquals(flowDto.dstCEPortNo, "00000100");
		assertEquals(flowDto.reqBandWidth, new Integer(2));
		assertEquals(flowDto.reqDelay, new Integer(3));
		assertEquals(flowDto.protectionLevel, "0");
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#createRequestFlowDto(java.lang.String, java.lang.Integer)}.
	 */
	@Test
	public void testCreateRequestFlowDto_checkThirdPartySpecification() {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");

		FlowDto flowDto = obj.createRequestFlowDto("flow00000001", 100, 2, 3);
		
		assertEquals(flowDto.name, "flow00000001");
		assertEquals(flowDto.srcCENodeName, "tokyo");
		assertEquals(flowDto.srcCEPortNo, "00000100");
		assertEquals(flowDto.dstCENodeName, "osaka");
		assertEquals(flowDto.dstCEPortNo, "00000100");
		assertEquals(flowDto.reqBandWidth, new Integer(2));
		assertEquals(flowDto.reqDelay, new Integer(3));
		assertEquals(flowDto.protectionLevel, "0");
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#createRequestFlowDto(java.lang.String, java.lang.Integer)}.
	 */
	@Test
	public void testDoProcess_CreateMxN_bandWidth_latency() {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");
		SliceDataManagerStubImpl sdm = new SliceDataManagerStubImpl();
		String[] args = new String[]{"-m", "create", "2", "3", "5", "6"};
		
		SliceMultiRequestImpl.doProcess(obj, sdm, args);

		synchronized (sdm.createdSliceDtos) {
			assertEquals(2, sdm.createdSliceDtos.size());
			for (int idx = 0; idx < 2; idx += 1) {
				SliceDto sliceDto = sdm.createdSliceDtos.get(idx);
				assertEquals(3, sliceDto.flows.size());
			}
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#createRequestFlowDto(java.lang.String, java.lang.Integer)}.
	 */
	@Test
	public void testDoProcess_DeleteAll() {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");
		SliceDataManagerStubImpl sdm = new SliceDataManagerStubImpl();
		String[] createArgs = new String[]{"-m", "create", "3", "3"};
		String[] deleteArgs = new String[]{"-m", "delete"};
		
		SliceMultiRequestImpl.doProcess(obj, sdm, createArgs);
		SliceMultiRequestImpl.doProcess(obj, sdm, deleteArgs);

		synchronized (sdm.deletedSliceDtos) {
			assertEquals(3, sdm.deletedSliceDtos.size());
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#createRequestFlowDto(java.lang.String, java.lang.Integer)}.
	 */
	@Test
	public void testDoProcess_UpdateAll() {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");
		SliceDataManagerStubImpl sdm = new SliceDataManagerStubImpl();
		String[] createArgs = new String[]{"-m", "create", "3", "3"};
		String[] UpdateArgs = new String[]{"-m", "update"};
		
		SliceMultiRequestImpl.doProcess(obj, sdm, createArgs);
		SliceMultiRequestImpl.doProcess(obj, sdm, UpdateArgs);

		synchronized (sdm.updatedSliceDtos) {
			assertEquals(3, sdm.updatedSliceDtos.size());
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#createRequestFlowDto(java.lang.String, java.lang.Integer)}.
	 */
	@Test
	public void testDoProcess_UpdateAll_BandWidth_Latency() {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");
		SliceDataManagerStubImpl sdm = new SliceDataManagerStubImpl();
		String[] createArgs = new String[]{"-m", "create", "3", "3"};
		String[] UpdateArgs = new String[]{"-m", "update", "2", "4"};
		
		SliceMultiRequestImpl.doProcess(obj, sdm, createArgs);
		SliceMultiRequestImpl.doProcess(obj, sdm, UpdateArgs);

		synchronized (sdm.updatedSliceDtos) {
			assertEquals(3, sdm.updatedSliceDtos.size());
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#createRequestFlowDto(java.lang.String, java.lang.Integer)}.
	 */
	@Test
	public void testDoProcess_NoProcess() {
		SliceMultiRequestImpl obj = initObject("client.config.demoApl.properties");
		SliceDataManagerStubImpl sdm = new SliceDataManagerStubImpl();
		String[] args = null;
		
		args = new String[]{};
		SliceMultiRequestImpl.doProcess(obj, sdm, args);
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		
		args = new String[]{"-n"};
		SliceMultiRequestImpl.doProcess(obj, sdm, args);
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		
		args = new String[]{"-m", "Create"};
		SliceMultiRequestImpl.doProcess(obj, sdm, args);
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		
		args = new String[]{"-m", "create", "100"};
		SliceMultiRequestImpl.doProcess(obj, sdm, args);
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		
		args = new String[]{"-m", "create", "100", "10", "5000"};
		SliceMultiRequestImpl.doProcess(obj, sdm, args);
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		
		args = new String[]{"-m", "update", "5000"};
		SliceMultiRequestImpl.doProcess(obj, sdm, args);
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
	}
}

class SliceDataManagerStubImpl implements SliceDataManager {
	
	Boolean isNullList = false;
	Boolean isFailedToGetList = false;
	Boolean isFailedToGetInfo = false;
	Boolean isFailedToCreateSlice = false;
	Boolean isFailedToUpdateSlice = false;
	Boolean isFailedToDeleteSlice = false;
	
	List<SliceDto> createdSliceDtos = new ArrayList<>();
	
	List<SliceDto> deletedSliceDtos = new ArrayList<>();
	
	List<SliceDto> updatedSliceDtos = new ArrayList<>();

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.SliceDataManager#getSliceList()
	 */
	@Override
	public List<SliceDto> getSliceList() throws MloClientException {
		if (isFailedToGetList) {
			throw new MloNbiException("aMessageInGettingSlices", 
					createErrorDto("InGettingSlices"));
		}
		if (isNullList) {
			return null;
		}
		List<SliceDto> list = new ArrayList<SliceDto>();
		synchronized (createdSliceDtos) {
			for (SliceDto sliceDto : createdSliceDtos) {
				SliceDto slice = new SliceDto();
				slice.id = sliceDto.id;
				slice.name = sliceDto.name;
				list.add(slice);
			}
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.SliceDataManager#getSliceInfo(org.o3project.mlo.server.dto.SliceDto)
	 */
	@Override
	public SliceDto getSliceInfo(SliceDto slice) throws MloClientException {
		synchronized (createdSliceDtos) {
			if (isFailedToGetInfo) {
				throw new MloNbiException("aMessageInGettingInfo", 
						createErrorDto("InGettingInfo"));
			}
			SliceDto ret = null;
			for (SliceDto sliceDto : createdSliceDtos) {
				if (sliceDto.id.equals(slice.id)) {
					ret = sliceDto;
					break;
				}
			}
			return ret;
		}
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.SliceDataManager#createSliceInfo(org.o3project.mlo.server.dto.SliceDto)
	 */
	@Override
	public SliceDto createSliceInfo(SliceDto slice) throws MloClientException {
		synchronized (createdSliceDtos) {
			slice.id = Integer.valueOf(slice.name.replaceAll("slice", ""));
			for (FlowDto flow : slice.flows) {
				flow.id = Integer.valueOf(flow.name.replaceAll("flow", ""));
			}
			createdSliceDtos.add(slice);
			if (isFailedToCreateSlice) {
				throw new MloNbiException("aMessageInCreatingSlice", 
						createErrorDto("InCreatingSlice"));
			}
			return slice;
		}
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.SliceDataManager#updateSliceInfo(org.o3project.mlo.server.dto.SliceDto)
	 */
	@Override
	public SliceDto updateSliceInfo(SliceDto slice) throws MloClientException {
		synchronized (updatedSliceDtos) {
			updatedSliceDtos.add(slice);
			if (isFailedToUpdateSlice) {
				throw new MloNbiException("aMessageInUpdatingSlice", 
						createErrorDto("InUpdatingSlice"));
			}
			return slice;
		}
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.SliceDataManager#deleteSliceInfo(org.o3project.mlo.server.dto.SliceDto)
	 */
	@Override
	public SliceDto deleteSliceInfo(SliceDto slice) throws MloClientException {
		synchronized (deletedSliceDtos) {
			deletedSliceDtos.add(slice);
			if (isFailedToDeleteSlice) {
				throw new MloNbiException("aMessageInDeletingSlice", 
						createErrorDto("InDeletingSlice"));
			}
			return slice;
		}
	}	

	/**
	 * @return
	 */
	RestifErrorDto createErrorDto(String suffix) {
		RestifErrorDto errorDto = new RestifErrorDto();
		errorDto.cause = "aCause" + suffix;
		errorDto.detail = "aDetail" + suffix;
		return errorDto;
	}
}
