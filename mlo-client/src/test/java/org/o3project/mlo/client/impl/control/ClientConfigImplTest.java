/**
 * ClientConfigImplTest.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import static org.junit.Assert.*;

import java.io.File;

import org.o3project.mlo.server.logic.ConfigProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.impl.control.ClientConfigImpl;
import org.o3project.mlo.client.impl.control.ClientConfigProviderImpl;

/**
 * ClientConfigImplTest
 *
 */
public class ClientConfigImplTest {

	private static final String DATA_PATH = "src/test/resources/org/o3project/mlo/client/control/data";
	private static final String DATA_FILE_001 = "client.config.001.properties";
	
	private ConfigProvider configProvider;
	private ClientConfigImpl obj;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		File propFile = new File(DATA_PATH, DATA_FILE_001);
		configProvider = new ClientConfigProviderImpl(propFile.getAbsolutePath());
		obj = new ClientConfigImpl();
		obj.setConfigProvider(configProvider);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		obj = null;
		configProvider = null;
	}

	/**
	 * Test method for {@link ClientConfigImpl#setConfigProvider(ConfigProvider)}.
	 */
	@Test
	public void testSetConfigProvider() {
		try {
			obj.setConfigProvider(configProvider);
			assertTrue(true);
		} catch (Throwable th) {
			fail();
		}
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ClinetConfigImpl#getServerBaseUri()}.
	 */
	@Test
	public void testGetNbiBaseUri() {
		String actual = obj.getServerBaseUri();
		String expected = "http://myHost/myPath";
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ClinetConfigImpl#getConnectionTimeoutSec()}.
	 */
	@Test
	public void testGetConnectionTimeoutSec() {
		Integer actual = obj.getConnectionTimeoutSec();
		Integer expected = Integer.valueOf(123);
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ClinetConfigImpl#getReadTimeoutSec()}.
	 */
	@Test
	public void testGetReadTimeoutSec() {
		Integer actual = obj.getReadTimeoutSec();
		Integer expected = Integer.valueOf(456);
		assertEquals(expected, actual);
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ClinetConfigImpl#getDummyInvokerSetFlag()}.
	 */
	@Test
	public void testGetDummyInvokerSetFlag() {
		boolean actual = obj.getDummyInvokerSetFlag();
		boolean expected = false;
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ClinetConfigImpl#getSrcComponentName()}.
	 */
	@Test
	public void testGetSrcComponentName() {
		String actual = obj.getSrcComponentName();
		String expected = "clientMlo";
		assertEquals(expected, actual);
	}
}
