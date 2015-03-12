/**
 * SliceTask.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.control;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.o3project.mlo.client.impl.control.MloClient;

import javafx.concurrent.Task;
import org.o3project.mlo.server.dto.SliceDto;

/**
 * This class is abstract base class of slice operation task.
 * Subclasses of this class will call methods of {@link SliceDataManager} to operate slices.
 * In subclasses, view-control code must not be written, that is, JavaFX classes must not be used in subclasses.
 * If view-control code is written, task thread badly conflict with JavaFX thread. 
 * View-control code should be written in setOnSucceeded() method and so on.
 */
public abstract class SliceTask extends Task<Void> {
	
	protected static final Log LOG = LogFactory.getLog(SliceTask.class);
	
	private final MloClient mloClient;
	
	private List<SliceDto> sliceDtoList;
	
	private MloClientException error;

	/**
	 * A constructor. 
	 * @param mloClient the mlo client instance.
	 */
	protected SliceTask(MloClient mloClient) {
		this.mloClient = mloClient;
	}
	
	/**
	 * Obtains the {@link MloClient} instace.
	 * @return the instance.
	 */
	public final MloClient getMloClient() {
		return mloClient;
	}
	
	/**
	 * Obtains slice DTOs which are results of this task.
	 * @return the slice DTO list.
	 */
	public final List<SliceDto> getSliceDtoList() {
		return sliceDtoList;
	}
	
	/**
	 * Sets slice DTOs which are results of this task.
	 * @param sliceDtoList the slice DTO list.
	 */
	protected final void setSliceDtoList(List<SliceDto> sliceDtoList) {
		this.sliceDtoList = sliceDtoList;
	}
	
	/**
	 * Obtains an error in retrieving or operating slices.
	 * @return the error instance.
	 */
	public final MloClientException getError() {
		return error;
	}
	
	/**
	 * Obtains an error in retrieving or operating slices.
	 * @param error the error instance.
	 */
	protected final void setError(MloClientException error) {
		this.error = error;
	}
}
