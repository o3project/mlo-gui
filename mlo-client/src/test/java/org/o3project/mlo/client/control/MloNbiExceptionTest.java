/**
 * MloNbiExceptionTest.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.control;

import static org.junit.Assert.*;
import org.o3project.mlo.server.dto.RestifErrorDto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.control.MloNbiException;

/**
 * MloNbiExceptionTest
 *
 */
public class MloNbiExceptionTest {

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
	 * Test method for {@link org.o3project.mlo.client.control.MloNbiException#getErrorDto()}.
	 */
	@Test
	public void testGetErrorDto() {
		String msg = "myMsg";
		RestifErrorDto errorDto = new RestifErrorDto();
		MloNbiException obj = new MloNbiException(msg, errorDto);
		
		assertEquals("myMsg", obj.getMessage());
		assertEquals(errorDto, obj.getErrorDto());
	}

}
