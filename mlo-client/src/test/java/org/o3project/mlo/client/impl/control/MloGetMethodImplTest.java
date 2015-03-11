/**
 * 
 */
package org.o3project.mlo.client.impl.control;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.impl.control.MloGetMethodImpl;

/**
 *
 */
public class MloGetMethodImplTest {
	
	private MloGetMethodImpl obj;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		obj = new MloGetMethodImpl();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		obj = null;
	}

	/**
	 * Test method for {@link org.o3project.mlo.server.impl.rpc.service.SdtncGetMethodImpl#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("GET", obj.getName());
	}

	/**
	 * Test method for {@link org.o3project.mlo.server.impl.rpc.service.SdtncGetMethodImpl#isSetDoOutput()}.
	 */
	@Test
	public void testIsSetDoOutput() {
		assertEquals(Boolean.FALSE, obj.isSetDoOutput());
	}

}
