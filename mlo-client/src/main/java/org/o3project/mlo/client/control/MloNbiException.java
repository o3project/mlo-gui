/**
 * MloNbiException.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import org.o3project.mlo.server.dto.RestifErrorDto;

/**
 * This exception class designates failure occurred in accessing mlo-srv NBI.
 */
public class MloNbiException extends MloClientException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7947452279550763904L;

	private final RestifErrorDto errorDto;

	/**
	 * Constructs an instance with the specified detail message.
	 * @param msg the message
	 * @param errorDto Error DTO received from mlo-srv.
	 */
	public MloNbiException(String msg, RestifErrorDto errorDto) {
		super(msg);
		this.errorDto = errorDto;
	}

	/**
	 * Obtains error DTO.
	 * @return the error DTO.
	 */
	public RestifErrorDto getErrorDto() {
		return errorDto;
	}
}
