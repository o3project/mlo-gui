/**
 * MloAccessExceptionTest.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.control;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.control.MloAccessException;

/**
 * MloAccessExceptionTest
 *
 */
public class MloAccessExceptionTest {

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
	 * Test method for {@link org.o3project.mlo.client.control.MloAccessException#MloAccessException(java.lang.String)}.
	 */
	@Test
	public void testMloAccessExceptionString() {
		String msg = "myMsg1";
		MloAccessException obj = new MloAccessException(msg);
		
		assertEquals("myMsg1", obj.getMessage());
		assertEquals(null, obj.getCause());
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.control.MloAccessException#MloAccessException(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testMloAccessExceptionStringThrowable() {
		String msg = "myMsg2";
		IOException ioe = new IOException("myIOException");
		MloAccessException obj = new MloAccessException(msg, ioe);
		
		assertEquals("myMsg2", obj.getMessage());
		assertEquals(ioe, obj.getCause());
		assertEquals("myIOException", obj.getCause().getMessage());
	}

}
