/**
 * MloInputDataExceptionTest.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.control.MloInputDataException;

/**
 * MloAccessExceptionTest
 *
 */
public class MloInputDataExceptionTest {

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
	 * Test method for {@link org.o3project.mlo.client.control.MloInputDataException#MloAccessException(java.lang.String)}.
	 */
	@Test
	public void testMloInputDataExceptionString() {
		String msg = "myMsg1";
		MloInputDataException obj = new MloInputDataException(msg);
		
		assertEquals("myMsg1", obj.getMessage());
		assertEquals(null, obj.getCause());
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.control.MloInputDataException#MloAccessException(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testMloInputDataExceptionStringThrowable() {
		String msg = "myMsg2";
		IOException ioe = new IOException("myIOException");
		MloInputDataException obj = new MloInputDataException(msg, ioe);
		
		assertEquals("myMsg2", obj.getMessage());
		assertEquals(ioe, obj.getCause());
		assertEquals("myIOException", obj.getCause().getMessage());
	}

}
