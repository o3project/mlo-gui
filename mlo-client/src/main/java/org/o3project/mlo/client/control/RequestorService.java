/**
 * RequestorService.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.control;

import org.o3project.mlo.server.dto.RestifRequestDto;

/**
 * This interface designates features to access to mlo-srv.
 * This is used only for debug.
 */
public interface RequestorService {

	/**
	 * Requests to create multiple slices concurrently to mlo-srv.
	 * @param requestType CREATE/UPDATE/DELETE/READ
	 * @param requestTemplate the template of request DTO.
	 * @param threadNum the number of slices.
	 */
	void request(String requestType, RestifRequestDto requestTemplate, int threadNum);

	/**
	 * Requests continuously to mlo-srv with mloClient configuration.
	 * The following operation set is repeatedly requested until stopped.
	 * <ul>
	 *  <li>Creates a slice.
	 *  <li>Gets the slice.
	 *  <li>Update the slice (Adds a flow).
	 *  <li>Gets the slice.
	 *  <li>Update the slice (Modifies a flow).
	 *  <li>Gets the slice.
	 *  <li>Update the slice (Deletes a flow).
	 *  <li>Gets the slice.
	 *  <li>Deletes the slice.
	 * </ul>
	 */
	void requestMloClient();

	/**
	 * Requests continuously to mlo-srv with demoApl configuration.
	 * The following operation set is repeatedly requested until stopped.
	 * <ul>
	 *  <li>Creates a slice.
	 *  <li>Gets the slice.
	 *  <li>Update the slice (Adds a flow).
	 *  <li>Gets the slice.
	 *  <li>Update the slice (Modifies a flow).
	 *  <li>Gets the slice.
	 *  <li>Update the slice (Deletes a flow).
	 *  <li>Gets the slice.
	 *  <li>Deletes the slice.
	 * </ul>
	 */
	void requestDemoApl();

}
