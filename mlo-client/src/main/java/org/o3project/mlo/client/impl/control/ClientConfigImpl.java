/**
 * ClientConfigImpl.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import org.o3project.mlo.client.control.ClientConfig;
import org.o3project.mlo.client.control.ClientConfigConstants;
import org.seasar.framework.container.annotation.tiger.Binding;

import org.o3project.mlo.server.logic.ConfigProvider;

/**
 * This class is the implementation class of {@link ClientConfig} interface.
 */
public class ClientConfigImpl implements ClientConfig, ClientConfigConstants {
	
	@Binding
	private ConfigProvider configProvider;
	
	/**
	 * Setter method (for DI setter injection).
	 * @param configProvider the instance.
	 */
	public void setConfigProvider(ConfigProvider configProvider) {
		this.configProvider = configProvider;
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.ClientConfig#getServerBaseUri()
	 */
	@Override
	public String getServerBaseUri() {
		return configProvider.getProperty(PROP_KEY_SERVER_BASE_URI);
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.ClientConfig#getConnectionTimeoutSec()
	 */
	@Override
	public Integer getConnectionTimeoutSec() {
		return configProvider.getIntegerProperty(PROP_KEY_SERVER_CONNECTION_TIMEOUT_SEC);
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.ClientConfig#getReadTimeoutSec()
	 */
	@Override
	public Integer getReadTimeoutSec() {
		return configProvider.getIntegerProperty(PROP_KEY_SERVER_READ_TIMEOUT_SEC);
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.ClientConfig#getSrcComponentName()
	 */
	@Override
	public String getSrcComponentName() {
		return configProvider.getProperty(PROP_KEY_SERVER_SRC_COMPONENT_NAME);
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.ClientConfig#getDummyInvokerSetFlag()
	 */
	@Override
	public boolean getDummyInvokerSetFlag() {
		return configProvider.getBooleanProperty(PROP_KEY_SERVER_DUMMY_INVOKER_SET_FLAG);
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.ClientConfig#getTopologyViewUri()
	 */
	@Override
	public String getTopologyViewUri() {
		String topologyViewUri = null;
		String configUri = configProvider.getProperty(PROP_KEY_SERVER_TOPOLOGY_VIEW_URI);
		if (configUri == null || configUri.isEmpty()) {
			String srvUri = this.getServerBaseUri();
			topologyViewUri = srvUri + "/client/index.html";
		} else {
			topologyViewUri = configUri;
		}
		return topologyViewUri;
	}
}
