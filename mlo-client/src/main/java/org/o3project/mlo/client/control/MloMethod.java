/**
 * MloMethod.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.RestifResponseDto;

/**
 * This interface designates HTTP method in accessing to mlo-srv.
 */
public interface MloMethod {
	
	/**
	 * Obtains method name, GET or POS.
	 * @return the method name. 
	 */
	String getName();
	
	/**
	 * Specifies whether this request writes HTTP request body or not.
	 * For GET or DELETE method, request body will not be needed.
	 * @return If true, the request body is written.
	 */
	boolean isSetDoOutput();
	
	/**
	 * Writes the request body to output stream in invoking the HTTP request.
	 * @param reqDto The request DTO.
	 * @param ostream The output stream for the request body.
	 */
	void handleReqOutput(RestifRequestDto reqDto, OutputStream ostream);
	
	/**
	 * Handles the HTTP response as a response DTO from input stream.
	 * @param istream The input stream for the response body.
	 * @return The response DTO.
	 */
	RestifResponseDto handleResInput(InputStream istream);
	
	/**
	 * Obtains the connection timeout.
	 * The HTTP connection uses the value obtained by this method.
	 * If this method returns zero, no timeout is set.
	 * @return timeout [seconds].
	 */
	Integer getConnectionTimeoutSec();

	/**
	 * Obtains the read timeout.
	 * The HTTP connection uses the value obtained by this method.
	 * If this method returns zero, no timeout is set.
	 * @return timeout [seconds].
	 */
	Integer getReadTimeoutSec();
	
	
	/**
	 * Constructs URL with path and query parameters.
	 * The base URL is retrieved from configuration.
	 * In the case that, for example, base URL is http://myHost/myBasePath,
	 * path is myPath, 
	 * and query parameters are {"param1":"value1", "param2":"value2"},
	 * this method returns the following string.
	 * http://myHost/myBasePath/myPath?param1=value1&amp;param2=value2
	 * @param path the path
	 * @param params the query paraemters.
	 * @return URL string.
	 */
	String constructUrl(String path, Map<String, String> params);
}
