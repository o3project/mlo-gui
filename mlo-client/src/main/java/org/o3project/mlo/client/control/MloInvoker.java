/**
 * MloInvoker.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import java.util.Map;

import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.RestifResponseDto;

/**
 * This interface designates feature to access to mlo-srv.
 */
public interface MloInvoker {

	/**
	 * Accesses to mlo-srv, and then receives the response.
	 * @param method the HTTP method instance.
	 * @param reqDto the request DTO.
	 * @param path the URL path.
	 * @param params the query parameters.
	 * @return the response DTO.
	 * @throws MloAccessException Failed to access to mlo-srv.
	 */
	RestifResponseDto invoke(MloMethod method, 
			RestifRequestDto reqDto, String path, Map<String, String> params) 
					throws MloAccessException;

}
