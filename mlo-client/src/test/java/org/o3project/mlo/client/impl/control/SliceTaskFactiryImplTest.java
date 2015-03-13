/**
 * SliceTaskFactiryImplTest
 */
package org.o3project.mlo.client.impl.control;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.control.SliceTask;
import org.o3project.mlo.client.impl.control.CancelTask;
import org.o3project.mlo.client.impl.control.CreateSliceTask;
import org.o3project.mlo.client.impl.control.DeleteSliceTask;
import org.o3project.mlo.client.impl.control.GetSlicesTask;
import org.o3project.mlo.client.impl.control.ReadSliceTask;
import org.o3project.mlo.client.impl.control.SliceTaskFactoryImpl;
import org.o3project.mlo.client.impl.control.UpdateSliceTask;

/**
 *
 */
public class SliceTaskFactiryImplTest {

	private SliceTaskFactoryImpl obj;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		obj = new SliceTaskFactoryImpl(null);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		obj = null;
	}

	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceTaskFactoryImpl#createTask()}.
	 */
	@Test
	public void testCreateTask() {
		SliceTask tsk = obj.createTask(SliceTaskFactoryImpl.CREATE_TASK_LB);
		assertTrue(tsk instanceof CreateSliceTask);
		
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceTaskFactoryImpl#createTask()}.
	 */
	@Test
	public void testCreateTask2() {
		SliceTask tsk = obj.createTask(SliceTaskFactoryImpl.DELETE_TASK_LB);
		assertTrue(tsk instanceof DeleteSliceTask);
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceTaskFactoryImpl#createTask()}.
	 */
	@Test
	public void testCreateTask3() {
		SliceTask tsk = obj.createTask(SliceTaskFactoryImpl.UPDATE_TASK_LB);
		assertTrue(tsk instanceof UpdateSliceTask);
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceTaskFactoryImpl#createTask()}.
	 */
	@Test
	public void testCreateTask4() {
		SliceTask tsk = obj.createTask(SliceTaskFactoryImpl.CANCEL_TASK_LB);
		assertTrue(tsk instanceof CancelTask);
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceTaskFactoryImpl#createTask()}.
	 */
	@Test
	public void testCreateTask5() {
		SliceTask tsk = obj.createTask(SliceTaskFactoryImpl.LIST_TASK_LB);
		assertTrue(tsk instanceof GetSlicesTask);
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceTaskFactoryImpl#createTask()}.
	 */
	@Test
	public void testCreateTask6() {
		SliceTask tsk = obj.createTask(SliceTaskFactoryImpl.READ_TASK_LB);
		assertTrue(tsk instanceof ReadSliceTask);
	}
	
	/**
	 * Test method for {@link org.o3project.mlo.client.impl.control.SliceTaskFactoryImpl#createTask()}.
	 */
	@Test
	public void testCreateTask7() {
		try{
			obj.createTask("test");
			fail();
		}catch(Exception e){
			assertTrue(e instanceof UnsupportedOperationException);
		}
	}
}
