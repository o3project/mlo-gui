/**
 * SliceMultiRequestImplTest.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXB;

import org.o3project.mlo.server.dto.FlowDto;
import org.o3project.mlo.server.dto.RestifErrorDto;
import org.o3project.mlo.server.dto.RestifRequestDto;
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
import org.o3project.mlo.client.impl.control.RequestorServiceImpl;

/**
 * RequestServiceImplTest
 *
 */
public class RequestServiceImplTest {

	private static final String CONFIG_DATA_PATH = "src/test/resources/org/o3project/mlo/client/control/data";
	
	private static final String REQUEST_DATA_PATH = "src/test/resources/org/o3project/mlo/client/control/data/base/";
	
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

	private ClientConfigImpl initConfig(String fileName) {
		File propFile = new File(CONFIG_DATA_PATH, fileName);
		ConfigProvider configProvider = new ClientConfigProviderImpl(propFile.getAbsolutePath());
		ClientConfigImpl clientConfig = new ClientConfigImpl();
		clientConfig.setConfigProvider(configProvider);
		return clientConfig;
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#request(java.lang.String, org.o3project.mlo.server.dto.RestifRequestDto, int)}.
	 */
	@Test
	public void testRequest_create_normal() throws Exception {
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		
		InputStream is = null;
		try {
			try {
				File file = new File(REQUEST_DATA_PATH + "create.req.xml");
				is = new FileInputStream(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			} 
			RestifRequestDto requestTemplate = JAXB.unmarshal(is, RestifRequestDto.class);
			obj.request("create", requestTemplate, 10);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		synchronized (sdm.createdSliceDtos) {
			assertEquals(10, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#request(java.lang.String, org.o3project.mlo.server.dto.RestifRequestDto, int)}.
	 */
	@Test
	public void testRequest_create_error() throws Exception {
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		sdm.isFailedToCreateSlice = true;
		
		InputStream is = null;
		try {
			try {
				File file = new File(REQUEST_DATA_PATH + "create.req.xml");
				is = new FileInputStream(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			} 
			RestifRequestDto requestTemplate = JAXB.unmarshal(is, RestifRequestDto.class);
			obj.request("create", requestTemplate, 10);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		synchronized (sdm.createdSliceDtos) {
			assertEquals(10, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#request(java.lang.String, org.o3project.mlo.server.dto.RestifRequestDto, int)}.
	 */
	@Test
	public void testRequest_update_normal() throws Exception {
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		
		InputStream is = null;
		try {
			try {
				File file = new File(REQUEST_DATA_PATH + "update.req.xml");
				is = new FileInputStream(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			} 
			RestifRequestDto requestTemplate = JAXB.unmarshal(is, RestifRequestDto.class);
			obj.request("update", requestTemplate, 10);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(10, sdm.updatedSliceDtos.size());
		}
		
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#request(java.lang.String, org.o3project.mlo.server.dto.RestifRequestDto, int)}.
	 */
	@Test
	public void testRequest_update_error() throws Exception {
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		sdm.isFailedToUpdateSlice = true;
		
		InputStream is = null;
		try {
			try {
				File file = new File(REQUEST_DATA_PATH + "update.req.xml");
				is = new FileInputStream(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			} 
			RestifRequestDto requestTemplate = JAXB.unmarshal(is, RestifRequestDto.class);
			obj.request("update", requestTemplate, 10);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(10, sdm.updatedSliceDtos.size());
		}
		
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#request(java.lang.String, org.o3project.mlo.server.dto.RestifRequestDto, int)}.
	 */
	@Test
	public void testRequest_delete_normal() throws Exception {
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		
		InputStream is = null;
		try {
			try {
				File file = new File(REQUEST_DATA_PATH + "delete.req.xml");
				is = new FileInputStream(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			} 
			RestifRequestDto requestTemplate = JAXB.unmarshal(is, RestifRequestDto.class);
			obj.request("delete", requestTemplate, 10);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(10, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#request(java.lang.String, org.o3project.mlo.server.dto.RestifRequestDto, int)}.
	 */
	@Test
	public void testRequest_delete_error() throws Exception {
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		sdm.isFailedToDeleteSlice = true;
		
		InputStream is = null;
		try {
			try {
				File file = new File(REQUEST_DATA_PATH + "delete.req.xml");
				is = new FileInputStream(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			} 
			RestifRequestDto requestTemplate = JAXB.unmarshal(is, RestifRequestDto.class);
			obj.request("delete", requestTemplate, 10);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(10, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#request(java.lang.String, org.o3project.mlo.server.dto.RestifRequestDto, int)}.
	 */
	@Test
	public void testRequest_read_normal() throws Exception {
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		
		InputStream is = null;
		try {
			try {
				File file = new File(REQUEST_DATA_PATH + "create.req.xml");
				is = new FileInputStream(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			} 
			RestifRequestDto requestTemplate = JAXB.unmarshal(is, RestifRequestDto.class);
			obj.request("create", requestTemplate, 10);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		try {
			try {
				File file = new File(REQUEST_DATA_PATH + "read.ct.req.xml");
				is = new FileInputStream(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			} 
			RestifRequestDto requestTemplate = JAXB.unmarshal(is, RestifRequestDto.class);
			obj.request("read", requestTemplate, 10);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		synchronized (sdm.createdSliceDtos) {
			assertEquals(10, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		assertEquals(10, sdm.foundCount);
		
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#request(java.lang.String, org.o3project.mlo.server.dto.RestifRequestDto, int)}.
	 */
	@Test
	public void testRequest_read_error() throws Exception {
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		sdm.isFailedToGetInfo = true;
		
		InputStream is = null;
		try {
			try {
				File file = new File(REQUEST_DATA_PATH + "create.req.xml");
				is = new FileInputStream(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			} 
			RestifRequestDto requestTemplate = JAXB.unmarshal(is, RestifRequestDto.class);
			obj.request("create", requestTemplate, 10);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		try {
			try {
				File file = new File(REQUEST_DATA_PATH + "read.ct.req.xml");
				is = new FileInputStream(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			} 
			RestifRequestDto requestTemplate = JAXB.unmarshal(is, RestifRequestDto.class);
			obj.request("read", requestTemplate, 10);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		synchronized (sdm.createdSliceDtos) {
			assertEquals(10, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		assertEquals(0, sdm.foundCount);
		
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceMultiRequestImpl#request(java.lang.String, org.o3project.mlo.server.dto.RestifRequestDto, int)}.
	 */
	@Test
	public void testRequest_invalid_type() throws Exception {
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		
		InputStream is = null;
		try {
			try {
				File file = new File(REQUEST_DATA_PATH + "create.req.xml");
				is = new FileInputStream(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			} 
			RestifRequestDto requestTemplate = JAXB.unmarshal(is, RestifRequestDto.class);
			obj.request("test", requestTemplate, 10);
		} finally {
			if (is != null) {
				is.close();
			}
		}
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
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.RequestServiceImpl#doProcess(org.o3project.mlo.client.control.RequestorService, org.o3project.mlo.client.control.ClientConfig, java.lang.String)}.
	 */
	@Test
	public void testDoProcess_create() {
		ClientConfigImpl config = initConfig("client.config.demoApl.properties");
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		String[] args = null;
		
		args = new String[]{"-t", "create", REQUEST_DATA_PATH + "create.req.xml", "10"};
		RequestorServiceImpl.doProcess(obj, config, args);
		synchronized (sdm.createdSliceDtos) {
			assertEquals(10, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.RequestServiceImpl#doProcess(org.o3project.mlo.client.control.RequestorService, org.o3project.mlo.client.control.ClientConfig, java.lang.String)}.
	 */
	@Test
	public void testDoProcess_update() {
		ClientConfigImpl config = initConfig("client.config.demoApl.properties");
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		String[] args = null;
		
		args = new String[]{"-t", "update", REQUEST_DATA_PATH + "update.req.xml", "10"};
		RequestorServiceImpl.doProcess(obj, config, args);
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(10, sdm.updatedSliceDtos.size());
		}
		
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.RequestServiceImpl#doProcess(org.o3project.mlo.client.control.RequestorService, org.o3project.mlo.client.control.ClientConfig, java.lang.String)}.
	 */
	@Test
	public void testDoProcess_delete() {
		ClientConfigImpl config = initConfig("client.config.demoApl.properties");
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		String[] args = null;
		
		args = new String[]{"-t", "delete", REQUEST_DATA_PATH + "delete.req.xml", "10"};
		RequestorServiceImpl.doProcess(obj, config, args);
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(10, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.RequestServiceImpl#doProcess(org.o3project.mlo.client.control.RequestorService, org.o3project.mlo.client.control.ClientConfig, java.lang.String)}.
	 */
	@Test
	public void testDoProcess_read() {
		ClientConfigImpl config = initConfig("client.config.demoApl.properties");
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		String[] args = null;
		
		args = new String[]{"-t", "create", REQUEST_DATA_PATH + "create.req.xml", "10"};
		RequestorServiceImpl.doProcess(obj, config, args);
		args = new String[]{"-t", "read", REQUEST_DATA_PATH + "read.ct.req.xml", "10"};
		RequestorServiceImpl.doProcess(obj, config, args);
		synchronized (sdm.createdSliceDtos) {
			assertEquals(10, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		assertEquals(10, sdm.foundCount);
		
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.RequestServiceImpl#doProcess(org.o3project.mlo.client.control.RequestorService, org.o3project.mlo.client.control.ClientConfig, java.lang.String)}.
	 */
	@Test
	public void testDoProcess_demoApl_normal() throws Exception {
		final ClientConfigImpl config = initConfig("client.config.demoApl.properties");
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		final RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		final String[] args = new String[]{"-t", "serial"};
		
		Runnable task = new Runnable() {
			@Override
			public void run() {
				RequestorServiceImpl.doProcess(obj, config, args);
			}
		};
		
		Thread thread = new Thread(task, "doProcess-thread");
		thread.start();
		Thread.sleep(3000);
		thread.interrupt();

		synchronized (sdm.createdSliceDtos) {
			assertTrue(sdm.createdSliceDtos.size() > 0);
		}
		synchronized (sdm.deletedSliceDtos) {
			assertTrue(sdm.deletedSliceDtos.size() > 0);
		}
		synchronized (sdm.updatedSliceDtos) {
			assertTrue(sdm.updatedSliceDtos.size() > 0);
		}
		assertTrue(sdm.foundCount > 0);
		
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.RequestServiceImpl#doProcess(org.o3project.mlo.client.control.RequestorService, org.o3project.mlo.client.control.ClientConfig, java.lang.String)}.
	 */
	@Test
	public void testDoProcess_demoApl_error() throws Exception {
		final ClientConfigImpl config = initConfig("client.config.demoApl.properties");
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		final RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		sdm.isFailedToCreateSlice = true;
		
		final String[] args = new String[]{"-t", "serial"};
		
		Runnable task = new Runnable() {
			@Override
			public void run() {
				RequestorServiceImpl.doProcess(obj, config, args);
			}
		};
		
		Thread thread = new Thread(task, "doProcess-thread");
		thread.start();
		Thread.sleep(3000);
		thread.interrupt();

		synchronized (sdm.createdSliceDtos) {
			assertTrue(sdm.createdSliceDtos.size() > 0);
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		assertEquals(0, sdm.foundCount);
		
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.RequestServiceImpl#doProcess(org.o3project.mlo.client.control.RequestorService, org.o3project.mlo.client.control.ClientConfig, java.lang.String)}.
	 */
	@Test
	public void testDoProcess_mloClient_normal() throws Exception {
		final ClientConfigImpl config = initConfig("client.config.mloClient.properties");
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		final RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		final String[] args = new String[]{"-t", "serial"};
		
		Runnable task = new Runnable() {
			@Override
			public void run() {
				RequestorServiceImpl.doProcess(obj, config, args);
			}
		};
		
		Thread thread = new Thread(task, "doProcess-thread");
		thread.start();
		Thread.sleep(3000);
		thread.interrupt();

		synchronized (sdm.createdSliceDtos) {
			assertTrue(sdm.createdSliceDtos.size() > 0);
		}
		synchronized (sdm.deletedSliceDtos) {
			assertTrue(sdm.deletedSliceDtos.size() > 0);
		}
		synchronized (sdm.updatedSliceDtos) {
			assertTrue(sdm.updatedSliceDtos.size() > 0);
		}
		assertTrue(sdm.foundCount > 0);
		
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.RequestServiceImpl#doProcess(org.o3project.mlo.client.control.RequestorService, org.o3project.mlo.client.control.ClientConfig, java.lang.String)}.
	 */
	@Test
	public void testDoProcess_mloClient_error() throws Exception {
		final ClientConfigImpl config = initConfig("client.config.mloClient.properties");
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		final RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		sdm.isFailedToCreateSlice = true;
		
		final String[] args = new String[]{"-t", "serial"};
		
		Runnable task = new Runnable() {
			@Override
			public void run() {
				RequestorServiceImpl.doProcess(obj, config, args);
			}
		};
		
		Thread thread = new Thread(task, "doProcess-thread");
		thread.start();
		Thread.sleep(3000);
		thread.interrupt();

		synchronized (sdm.createdSliceDtos) {
			assertTrue(sdm.createdSliceDtos.size() > 0);
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		assertEquals(0, sdm.foundCount);
		
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.RequestServiceImpl#doProcess(org.o3project.mlo.client.control.RequestorService, org.o3project.mlo.client.control.ClientConfig, java.lang.String)}.
	 */
	@Test
	public void testDoProcess_fileNotFound() {
		ClientConfigImpl config = initConfig("client.config.demoApl.properties");
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		String[] args = null;
		
		args = new String[]{"-t", "create", REQUEST_DATA_PATH + "test.xml", "10"};
		RequestorServiceImpl.doProcess(obj, config, args);
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		assertEquals(0, sdm.foundCount);
		
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.RequestServiceImpl#doProcess(org.o3project.mlo.client.control.RequestorService, org.o3project.mlo.client.control.ClientConfig, java.lang.String)}.
	 */
	@Test
	public void testDoProcess_NoProcess() {
		ClientConfigImpl config = initConfig("client.config.other.properties");
		SliceDataManagerSerialStub sdm = new SliceDataManagerSerialStub();
		RequestorServiceImpl obj = new RequestorServiceImpl(sdm);
		String[] args = null;
		
		args = new String[]{};
		RequestorServiceImpl.doProcess(obj, config, args);
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		
		args = new String[]{"-T"};
		RequestorServiceImpl.doProcess(obj, config, args);
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		
		args = new String[]{"-t", "test"};
		RequestorServiceImpl.doProcess(obj, config, args);
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		
		args = new String[]{"-t", "create", "filepath"};
		RequestorServiceImpl.doProcess(obj, config, args);
		synchronized (sdm.createdSliceDtos) {
			assertEquals(0, sdm.createdSliceDtos.size());
		}
		synchronized (sdm.deletedSliceDtos) {
			assertEquals(0, sdm.deletedSliceDtos.size());
		}
		synchronized (sdm.updatedSliceDtos) {
			assertEquals(0, sdm.updatedSliceDtos.size());
		}
		
		args = new String[]{"-t", "serial"};
		RequestorServiceImpl.doProcess(obj, config, args);
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
	
	public class SliceDataManagerSerialStub implements SliceDataManager {
		Boolean isNullList = false;
		Boolean isFailedToGetList = false;
		Boolean isFailedToGetInfo = false;
		Boolean isFailedToCreateSlice = false;
		Boolean isFailedToUpdateSlice = false;
		Boolean isFailedToDeleteSlice = false;
		int foundCount = 0;
		
		Map<Integer, SliceDto> createdSliceDtos = new HashMap<Integer, SliceDto>();
		
		List<SliceDto> deletedSliceDtos = new ArrayList<SliceDto>();
		
		List<SliceDto> updatedSliceDtos = new ArrayList<SliceDto>();

		/* (non-Javadoc)
		 * @see org.o3project.mlo.client.control.SliceDataManager#getSliceList()
		 */
		@Override
		public List<SliceDto> getSliceList() throws MloClientException {
			if (isFailedToGetList) {
				throw new MloNbiException("aMessageInGettingSlices", createErrorDto("InGettingSlices"));
			}
			if (isNullList) {
				return null;
			}
			List<SliceDto> list = new ArrayList<SliceDto>();
			synchronized (createdSliceDtos) {
				for(Map.Entry<Integer, SliceDto> e : createdSliceDtos.entrySet()) {
					SliceDto slice = new SliceDto();
					slice.id = e.getValue().id;
					slice.name = e.getValue().name;
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
					throw new MloNbiException("aMessageInGettingInfo", createErrorDto("InGettingInfo"));
				}
				SliceDto ret = createdSliceDtos.get(slice.id);
				if (ret != null) {
					foundCount++;
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
				createdSliceDtos.put(slice.id, slice);
				if (isFailedToCreateSlice) {
					throw new MloNbiException("aMessageInCreatingSlice", createErrorDto("InCreatingSlice"));
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
					throw new MloNbiException("aMessageInUpdatingSlice", createErrorDto("InUpdatingSlice"));
				}
			}
			SliceDto createdSlice = getSliceInfo(slice);
			if (createdSlice != null) {
				synchronized (createdSliceDtos) {
					for (FlowDto flow : slice.flows) {
						switch (flow.type) {
						case "add":
							flow.id = Integer.valueOf(flow.name.replaceAll("flow", ""));
							createdSlice.flows.add(flow);
							break;
						case "mod":
							for (FlowDto createdFlow : createdSlice.flows) {
								if (createdFlow.id.equals(flow.id)) {
									createdSlice.flows.remove(createdFlow);
									createdSlice.flows.add(flow);
									break;
								}
							}
							break;
						case "del":
							for (FlowDto createdFlow : createdSlice.flows) {
								if (createdFlow.id.equals(flow.id)) {
									createdSlice.flows.remove(createdFlow);
									break;
								}
							}
							break;
						}
					}
				}
			}
			return slice;
		}

		/* (non-Javadoc)
		 * @see org.o3project.mlo.client.control.SliceDataManager#deleteSliceInfo(org.o3project.mlo.server.dto.SliceDto)
		 */
		@Override
		public SliceDto deleteSliceInfo(SliceDto slice) throws MloClientException {
			synchronized (deletedSliceDtos) {
				deletedSliceDtos.add(slice);
				if (isFailedToDeleteSlice) {
					throw new MloNbiException("aMessageInDeletingSlice", createErrorDto("InDeletingSlice"));
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
}
