/**
 * SliceTaskTest.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.control;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.o3project.mlo.server.dto.SliceDto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.control.MloClientException;
import org.o3project.mlo.client.control.MloInputDataException;
import org.o3project.mlo.client.control.SliceTask;
import org.o3project.mlo.client.impl.control.MloClient;

/**
 * MloAccessExceptionTest
 *
 */
public class SliceTaskTest {

	
	private SliceTaskStub obj;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		obj = new SliceTaskStub(null);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		obj = null;
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.control.SliceTask#getMloClient}.
	 */
	@Test
	public void testGetMloClient() {
		MloClient mloClient = obj.getMloClient();
		assertEquals(mloClient, null);
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.control.SliceTask#setSliceDtoList}.
	 */
	@Test
	public void testSetSliceDtoList() {
		
		List<SliceDto> sliceDtoList = new ArrayList<SliceDto>();
		SliceDto dto = new SliceDto();
		dto.name = "sliceName";
		sliceDtoList.add(dto);
		obj.setSliceDtoList(sliceDtoList);
		
		List<SliceDto> getList = obj.getSliceDtoList();
		
		for(int i=0; i< getList.size(); i++){
			assertEquals(getList.get(i).name, sliceDtoList.get(i).name);
		}
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.control.SliceTask#getError}.
	 */
	@Test
	public void testGetError() {
		String errorMsg = "testErrorMsg";
		MloClientException error = new MloInputDataException(errorMsg);
		obj.setErrorCode(error);
		
		MloClientException error2 = obj.getError();
		assertTrue(error2 instanceof MloInputDataException);
		assertEquals(error2.getMessage(), errorMsg);
	}
}

class SliceTaskStub extends SliceTask{

	protected SliceTaskStub(MloClient mloClient) {
		super(mloClient);
	}

	@Override
	protected Void call() throws Exception {
		return null;
	}
	
	public void setErrorCode(MloClientException error){
		super.setError(error);
	}
	
}
