/**
 * SliceTaskFactory.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * This interface designates slice operation task factory features.
 */
public interface SliceTaskFactory {
	
	/**
	 * Confirmation message: Creates a slice.
	 */
    String CREATE_TASK_LB = "Will you continue the create process ?";
    
	/**
	 * Confirmation message: Deletes a slice.
	 */
    String DELETE_TASK_LB = "Will you continue the delete process ?";
    
	/**
	 * Confirmation message: Updates a slice.
	 */
    String UPDATE_TASK_LB = "Will you continue the update process ?";
    
	/**
	 * Confirmation message: Cancels the operation.
	 */
    String CANCEL_TASK_LB = "Will you continue the cancel process ?";
    
	/**
	 * Confirmation message: Gets slice list.
	 */
    String LIST_TASK_LB = "Is get slice list ?";
    
	/**
	 * Confirmation message: Gets a slice.
	 */
    String READ_TASK_LB = "Is get slice info ?";

	/**
	 * Creates a slice operation task.
	 * @param taskKey the key of the task.
	 * @return the task instance.
	 */
	SliceTask createTask(String taskKey);
}
