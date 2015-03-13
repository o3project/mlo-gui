/**
 * MloClientException.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * This exception class designates failure occurred in mlo-client.
 */
public abstract class MloClientException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5874369740311499855L;

	/**
	 * Constructs an instance with the specified detail message.
	 * @param msg the detail message.
	 */
	public MloClientException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an instance with the specified cause and a detail message.
	 * @param msg the detail message. 
	 * @param cause the cause.
	 */
	public MloClientException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
