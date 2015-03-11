/**
 * 
 */
package org.o3project.mlo.client.impl.control;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.impl.control.MloPostMethodImpl;

/**
 *
 */
public class MloPostMethodImplTest {

	private MloPostMethodImpl obj;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		obj = new MloPostMethodImpl();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		obj = null;
	}

	/**
	 * Test method for {@link org.o3project.mlo.server.impl.rpc.service.SdtncPostMethodImpl#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("POST", obj.getName());
	}

	/**
	 * Test method for {@link org.o3project.mlo.server.impl.rpc.service.SdtncPostMethodImpl#isSetDoOutput()}.
	 */
	@Test
	public void testIsSetDoOutput() {
		assertEquals(Boolean.TRUE, obj.isSetDoOutput());
	}

}
