/**
 * MloSerdesImpl.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXB;

import org.o3project.mlo.client.control.MloSerdes;

import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.RestifResponseDto;

/**
 * {@link MloSerdes} インタフェースの実装クラスです。
 */
public class MloSerdesImpl implements MloSerdes {
	
	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control#deserializeFromXml(java.io.InputStream)
	 */
	@Override
	public RestifResponseDto deserializeFromXml(InputStream istream) {
		RestifResponseDto response = JAXB.unmarshal(istream , RestifResponseDto.class);
		return response;
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control#serializeToXml(org.o3project.mlo.server.dto.RestifRequestDto, java.io.OutputStream)
	 */
	@Override
	public void serializeToXml(RestifRequestDto reqDto, OutputStream ostream) {
		JAXB.marshal(reqDto, ostream);
	}
}
