/**
 * LoginResultListener.java
 * (C) 2015, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * LoginResultListener
 *
 */
public class Credential {
	
	public final String userid;
	
	public final String passwd;
	
	private boolean isTried = false;
	
	private Boolean isOk = null;
	
	private String message = null;
	
	public Credential(String userid, String passwd) {
		this.userid = userid;
		this.passwd = passwd;
	}

	public void setResult(boolean isOk, String message) {
		if (!isTried) {
			this.isOk = isOk;
			this.message = message;
			this.isTried = true;
		}
	}
	
	public boolean isTried() {
		return isTried;
	}
	
	public Boolean isOk() {
		return isOk;
	}
	
	public String getMessage() {
		return message;
	}
}
