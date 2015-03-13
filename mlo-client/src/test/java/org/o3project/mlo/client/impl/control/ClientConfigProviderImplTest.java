/**
 * ClientConfigProviderImplTest.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import static org.junit.Assert.*;

import java.util.Map;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.control.ClientConfigConstants;
import org.o3project.mlo.client.impl.control.ClientConfigProviderImpl;

/**
 * ClientConfigProviderImplTest
 *
 */
public class ClientConfigProviderImplTest implements ClientConfigConstants {
	
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

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ClientConfigProviderImpl#ConfigProviderImpl(java.lang.String)}.
	 */
	@Test
	public void testConfigProviderImpl() {
		String propertiesFilePath = null;
		ClientConfigProviderImpl obj = new ClientConfigProviderImpl(propertiesFilePath);
		assertNotNull(obj);
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ClientConfigProviderImpl#getProperties()}.
	 */
	@Test
	public void testGetProperties_normal_path_null() {
		String propertiesFilePath = null;
		ClientConfigProviderImpl obj = new ClientConfigProviderImpl(propertiesFilePath);
		Map<String, String> props = obj.getProperties();
		assertNotNull(props);
		assertNotNull(props.get(PROP_KEY_DEBUG_CLIENTS));
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ClientConfigProviderImpl#getProperties()}.
	 */
	@Test
	public void testGetProperties_normal() {
		String propertiesFilePath = DATA_PATH + "/mlo-client.001.properties";
		ClientConfigProviderImpl obj = new ClientConfigProviderImpl(propertiesFilePath);
		Map<String, String> props = obj.getProperties();
		assertNotNull(props);
		assertEquals("test001", props.get(PROP_KEY_DEBUG_CLIENTS));
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ClientConfigProviderImpl#getProperties()}.
	 */
	@Test
	public void testGetProperties_anomaly_no_file() {
		String propertiesFilePath = DATA_PATH + "/no_file";
		ClientConfigProviderImpl obj = new ClientConfigProviderImpl(propertiesFilePath);
		Map<String, String> props = obj.getProperties();
		assertNotNull(props);
		assertNotNull(props.get(PROP_KEY_DEBUG_CLIENTS));
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ClientConfigProviderImpl#getProperties()}.
	 */
	@Test
	public void testGetProperties_anomaly_directory() {
		String propertiesFilePath = DATA_PATH;
		ClientConfigProviderImpl obj = new ClientConfigProviderImpl(propertiesFilePath);
		Map<String, String> props = obj.getProperties();
		assertNotNull(props);
		assertNotNull(props.get(PROP_KEY_DEBUG_CLIENTS));
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ClientConfigProviderImpl#getProperty(java.lang.String)}.
	 */
	@Test
	public void testGetProperty_normal() {
		String propertiesFilePath = DATA_PATH + "/mlo-client.001.properties";
		ClientConfigProviderImpl obj = new ClientConfigProviderImpl(propertiesFilePath);
		String prop = obj.getProperty(PROP_KEY_DEBUG_CLIENTS);
		assertEquals("test001", prop);
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.ClientConfigProviderImpl#getCommaSplitedProperty(java.lang.String)}.
	 */
	@Test
	public void testGetCommaSplitedProperty_normal() {
		String propertiesFilePath = DATA_PATH + "/mlo-client.002.properties";
		ClientConfigProviderImpl obj = new ClientConfigProviderImpl(propertiesFilePath);
		String[] actuals = obj.getCommaSplitedProperty(PROP_KEY_DEBUG_CLIENTS);
		String[] expecteds = new String[]{"client001", "client002", "client003"};
		assertEquals(expecteds.length, actuals.length);
		assertArrayEquals(expecteds, actuals);
	}
	
	/**
	 * Test method for {@link ClientConfigProviderImpl#getBooleanProperty(String)}.
	 */
	@Test
	public void testBooleanProperty_normal_true() {
		String propertiesFilePath = DATA_PATH + "/mlo-client.001.properties";
		String propKey = "mlo.client.config.test.booleanTrueProp";
		ClientConfigProviderImpl obj = new ClientConfigProviderImpl(propertiesFilePath);
		Boolean prop = obj.getBooleanProperty(propKey);
		assertEquals(Boolean.TRUE, prop);
	}
	
	/**
	 * Test method for {@link ClientConfigProviderImpl#getBooleanProperty(String)}.
	 */
	@Test
	public void testBooleanProperty_normal_false() {
		String propertiesFilePath = DATA_PATH + "/mlo-client.001.properties";
		String propKey = "mlo.client.config.test.booleanFalseProp";
		ClientConfigProviderImpl obj = new ClientConfigProviderImpl(propertiesFilePath);
		Boolean prop = obj.getBooleanProperty(propKey);
		assertEquals(Boolean.FALSE, prop);
	}
	
	/**
	 * Test method for {@link ClientConfigProviderImpl#getBooleanProperty(String)}.
	 */
	@Test
	public void testBooleanProperty_anomaly_notTrueFalse() {
		String propertiesFilePath = DATA_PATH + "/mlo-client.001.properties";
		String propKey = "mlo.client.config.test.booleanNotTrueFalseProp";
		ClientConfigProviderImpl obj = new ClientConfigProviderImpl(propertiesFilePath);
		Boolean prop = obj.getBooleanProperty(propKey);
		assertEquals(Boolean.FALSE, prop);
	}
	
	/**
	 * Test method for {@link ClientConfigProviderImpl#getIntegerProperty(String)}.
	 */
	@Test
	public void testIntegerProperty_normal_integer100() {
		String propertiesFilePath = DATA_PATH + "/mlo-client.001.properties";
		String propKey = "mlo.client.config.test.integer100Prop";
		ClientConfigProviderImpl obj = new ClientConfigProviderImpl(propertiesFilePath);
		Integer prop = obj.getIntegerProperty(propKey);
		assertEquals(Integer.valueOf(100), prop);
	}
	
	/**
	 * Test method for {@link ClientConfigProviderImpl#getIntegerProperty(String)}.
	 */
	@Test
	public void testIntegerProperty_anomaly_notInteger() {
		String propertiesFilePath = DATA_PATH + "/mlo-client.001.properties";
		String propKey = "mlo.client.config.test.integerNotIntegerProp";
		ClientConfigProviderImpl obj = new ClientConfigProviderImpl(propertiesFilePath);
		Integer prop = obj.getIntegerProperty(propKey);
		assertEquals(null, prop);
	}

	/**
	 * Test method for {@link ClientConfigProviderImpl#getSubProperties(String)}.
	 */
	@Test
	public void testGetSubProperties() {
		String propertiesFilePath = DATA_PATH + "/mlo-client.001.properties";
		ClientConfigProviderImpl obj = new ClientConfigProviderImpl(propertiesFilePath);
		
		Map<String, String> map = obj.getSubProperties("test");
		assertTrue(map.size() == 0);
	
	}
}
