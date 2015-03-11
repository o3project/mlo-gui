/**
 * LoginListener.java
 * (C) 2015, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * LoginListener
 *
 */
public interface CredentialListener {
	
	public void loginTrialCalled(Credential credential);
	
	public void doLogin(Credential credential);
	
	public void doLogout();

	public void doExit();
}
