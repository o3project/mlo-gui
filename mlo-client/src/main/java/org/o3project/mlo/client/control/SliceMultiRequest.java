/**
 * SliceMultiRequest.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * This interface designates features to request multiple slice operations from CLI.
 */
public interface SliceMultiRequest {
	/**
	 * Creates multiple slices.
	 * @param sliceDataManager the slice data manager.
	 * @param nSlice the number of slices.
	 * @param nFlowForSlice the number of flows in a slice.
	 * @param bandWidth the band width.
	 * @param latency the delay time.
	 * @throws InterruptedException Interrupted.
	 */
	void doCreateSlices(SliceDataManager sliceDataManager, Integer nSlice, Integer nFlowForSlice, Integer bandWidth, Integer latency) throws InterruptedException;
	
	/**
	 * Updates all slices.
	 * @param sliceDataManager the slice data manager.
	 * @param bandWidth the band width.
	 * @param latency the delay time.
	 * @throws InterruptedException Interrupted.
	 */
	void doUpdateAllSlices(SliceDataManager sliceDataManager, Integer bandWidth, Integer latency) throws InterruptedException;
	
	/**
	 * Deletes all slices.
	 * @param sliceDataManager the slice data manager.
	 * @throws InterruptedException Interrupted.
	 */
	void doDeleteAllSlices(SliceDataManager sliceDataManager) throws InterruptedException;
}
