/**
 * MloSerdes.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.control;

import java.io.InputStream;
import java.io.OutputStream;

import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.RestifResponseDto;

/**
 * This interface designates serializer-deserializer feature in mlo-client.
 */
public interface MloSerdes {

	/**
	 * Desirializes response DTO from input stream including response XML data.
	 * @param istream the input stream.
	 * @return the response DTO from mlo-srv.
	 */
	RestifResponseDto deserializeFromXml(InputStream istream);

	/**
	 * Serializes request DTO into output stream.
	 * This method may throw RuntimeException.
	 * @param reqDto the request DTO.
	 * @param ostream the output stream.
	 */
	void serializeToXml(RestifRequestDto reqDto, OutputStream ostream);

}
