/**
 * SliceDataManager.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.control;

import java.util.List;

import org.o3project.mlo.server.dto.SliceDto;

/**
 * This interface designates boundary feature of mlo-srv.
 */
public interface SliceDataManager {

	/**
	 * Obtains slice list.
	 * @return the slices.
	 * @throws MloClientException Failed to process.
	 */
	List<SliceDto> getSliceList() throws MloClientException;
	
	/**
	 * Obtains a slice.
	 * @param the requested slice DTO.
	 * @return the slice DTO.
	 * @throws MloClientException Failed to process.
	 */
	SliceDto getSliceInfo(SliceDto slice) throws MloClientException;
	
	/**
	 * Creates a slice.
	 * @param the requested slice DTO.
	 * @return the slice DTO.
	 * @throws MloClientException Failed to process.
	 */
	SliceDto createSliceInfo(SliceDto slice) throws MloClientException;
	
	/**
	 * Updates a slice.
	 * @param the requested slice DTO.
	 * @return the slice DTO.
	 * @throws MloClientException Failed to process.
	 */
	SliceDto updateSliceInfo(SliceDto slice) throws MloClientException;
	
	/**
	 * Deletes a slice.
	 * @param the requested slice DTO.
	 * @return the slice DTO.
	 * @throws MloClientException Failed to process.
	 */
	SliceDto deleteSliceInfo(SliceDto slice) throws MloClientException;
	
}
