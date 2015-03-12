/**
 * MloAccessException.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * This exception class designates access failure to mlo-srv.
 * This class is thrown when mlo-client cannot receive response from mlo-srv.
 * For example, this class will be used when URI of mlo-srv is incorrect and so on.
 * In the case that mlo-client receives response but the response includes error,
 * {@link MloNbiExceptio} must be used.
 */
public class MloAccessException extends MloClientException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1595966171065226386L;

	/**
	 * Constructs an instance with the specified detail message.
	 * @param msg the detail message.
	 */
	public MloAccessException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an instance with the specified cause and a detail message.
	 * @param msg the detail message. 
	 * @param cause the cause.
	 */
	public MloAccessException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
