/**
 * LoginListener.java
 * (C) 2015, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * This interface designates authentication feature of credential.
 */
public interface CredentialListener {

	/**
	 * Is called as call back in authenticating credential.
	 * @param credential the credential.
	 */
	public void loginTrialCalled(Credential credential);

	/**
	 * Executes login.
	 * @param credential the credential.
	 */
	public void doLogin(Credential credential);
	
	/**
	 * Executes logout.
	 */
	public void doLogout();

	/**
	 * Exits.
	 */
	public void doExit();
}
