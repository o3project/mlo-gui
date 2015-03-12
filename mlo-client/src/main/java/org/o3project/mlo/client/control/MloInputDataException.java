/**
 * MloInputDataException.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * This exception class designates anomaly of input data in mlo-client.
 */
public class MloInputDataException extends MloClientException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4060991710948010187L;

	/**
	 * Constructs an instance with the specified detail message.
	 * @param msg the detail message.
	 */
	public MloInputDataException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an instance with the specified cause and a detail message.
	 * @param msg the detail message. 
	 * @param cause the cause.
	 */
	public MloInputDataException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
