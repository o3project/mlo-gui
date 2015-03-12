/**
 * LoginResultListener.java
 * (C) 2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * This class designates credentials.
 */
public class Credential {

	/**
	 * User ID.
	 */
	public final String userid;

	/**
	 * Password.
	 */
	public final String passwd;
	
	private boolean isTried = false;
	
	private Boolean isOk = null;
	
	private String message = null;
	
	/**
	 * A constructor.
	 * @param userid the user ID.
	 * @param passwd the password.
	 */
	public Credential(String userid, String passwd) {
		this.userid = userid;
		this.passwd = passwd;
	}

	/**
	 * Sets authentication result.
	 * @param isOk Authentication result.
	 * @param message Message. Especially, if failed, this message includes the cause.
	 */
	public void setResult(boolean isOk, String message) {
		if (!isTried) {
			this.isOk = isOk;
			this.message = message;
			this.isTried = true;
		}
	}

	/**
	 * Designates whether this credential is already tried or not.
	 * @return true if already tried.
	 */
	public boolean isTried() {
		return isTried;
	}
	
	/**
	 * Designates whether authentication is OK or not.
	 * @return true if authentication is OK.
	 */
	public Boolean isOk() {
		return isOk;
	}
	
	/**
	 * Obtains message.
	 * @return the message.
	 */
	public String getMessage() {
		return message;
	}
}
