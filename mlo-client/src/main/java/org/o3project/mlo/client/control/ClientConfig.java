/**
 * ClientConfig.java
 * (C) 2013,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * This interface designates configurations of mlo-client.
 */
public interface ClientConfig {

	/**
	 * Obtains the base URI of mlo-srv.
	 * @return the base URI. 
	 */
	String getServerBaseUri();

	/**
	 * Obtains the connection timeout in connecting from mlo-client to mlo-srv.
	 * The unit is seconds.
	 * If zero is obtained, no timeout is set.
	 * @return the timeout.
	 */
	Integer getConnectionTimeoutSec();
	
	/**
	 * Obtains the read timeout in connecting from mlo-client to mlo-srv.
	 * The unit is seconds.
	 * If zero is obtained, no timeout is set.
	 * @return the timeout.
	 */
	Integer getReadTimeoutSec();
	
	/**
	 * Obtains source component name.
	 * @return the source component name.
	 */
	String getSrcComponentName();

	/**
	 * Designates whether the application behaves in dummy mode.
	 * In dummy mode, the application uses dummy web api invoker, 
	 * so it does not connect to MLO.
	 * @return  Returns true in dummy mode.
	 */
	boolean getDummyInvokerSetFlag();
	
	/**
	 * Obtains the topology view URI.
	 * @return the URI.
	 */
	String getTopologyViewUri();
}
