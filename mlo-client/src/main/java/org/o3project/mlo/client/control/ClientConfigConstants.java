/**
 * ClientConfigConstants.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * This interface defines constants used in mlo-client.
 */
public interface ClientConfigConstants {
	
	/**
	 * Prefix of property.
	 */
	String PROP_KEY_PREFIX_ = "mlo.client.config.";
	
	/*
	 * common
	 */
	/**
	 * Property key of component names in debugging.
	 * The type of the property value is string.
	 */
	String PROP_KEY_DEBUG_CLIENTS = PROP_KEY_PREFIX_ + "debug.clients";
	
	/*
	 * For connection to mlo-srv.
	 */
	/**
	 * Property key of base URI of mlo-srv.
	 * The type of the property value is string.
	 */
	String PROP_KEY_SERVER_BASE_URI = PROP_KEY_PREFIX_ + "server.baseUri";
	
	/**
	 * Property key of connection timeout in connecting to mlo-srv.
	 * The type of the property value is zero or positive integer.
	 * The unit is seconds.
	 */
	String PROP_KEY_SERVER_CONNECTION_TIMEOUT_SEC = PROP_KEY_PREFIX_ + "server.connectionTimeoutSec";
	
	/**
	 * Property key of read timeout in connecting to mlo-srv.
	 * The type of the property value is zero or positive integer.
	 * The unit is seconds.
	 */
	String PROP_KEY_SERVER_READ_TIMEOUT_SEC       = PROP_KEY_PREFIX_ + "server.readTimeoutSec";
	
	/**
	 * Property key of component name of the mlo-client.
	 * The type of the property value is string.
	 */
	String PROP_KEY_SERVER_SRC_COMPONENT_NAME       = PROP_KEY_PREFIX_ + "server.src.componentName";
	
	/**
	 * Property key of flag which designates whether mlo-client behaves in dummy mode or not.
	 * In dummy mode, mlo-client does not connect to mlo-srv, but uses dummy data.
	 * The type of the property value is boolean.
	 */
	String PROP_KEY_SERVER_DUMMY_INVOKER_SET_FLAG       = PROP_KEY_PREFIX_ + "server.dummy.invoker.flag";
	
	/*
	 * for topology view
	 */

	/**
	 * Property key of topology view URI.
	 * The type of the property value is string.
	 */
	String PROP_KEY_SERVER_TOPOLOGY_VIEW_URI = PROP_KEY_PREFIX_ + "server.topologyViewUri";
	
	/**
	 * demoApl client component name. 
	 */
	String CLIENT_TYPE_OTHER = "demoApl";
	
	/**
	 * mloClient client component name.
	 */
	String CLIENT_TYPE_HITACHI = "mloClient";
	
	/**
	 * Application name, which is used as window title and so on.
	 */
	String APP_NAME = "Multi Layer Orchestrator";
	
	/**
	 * Copyright string.
	 */
	String APP_COPYRIGHT = "Copyright (C) 2015, Hitachi, Ltd. All rights reserved.";
}
