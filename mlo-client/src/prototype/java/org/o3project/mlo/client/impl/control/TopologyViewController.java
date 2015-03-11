/**
 * TopologyViewController.java
 * (C) 2015, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import org.o3project.mlo.client.control.MloViewController;

/**
 * TopologyViewController
 *
 */
public class TopologyViewController implements MloViewController, Initializable {

	@FXML
	private AnchorPane rootPane;
	
	@FXML
	private WebView topologyWebView;
	
	private WebEngine topologyWebEngine;
	
	private String topologyViewUrl;
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		topologyWebEngine = topologyWebView.getEngine();
		
		reloadTopologyUrl();
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.MloViewController#getRoot()
	 */
	@Override
	public Parent getRoot() {
		return rootPane;
	}
	
	/**
	 * @param topologyViewUrl the topologyViewUrl to set
	 */
	public void setTopologyViewUrl(String topologyViewUrl) {
		this.topologyViewUrl = topologyViewUrl;
		reloadTopologyUrl();
	}
	
	public void reloadTopologyUrl() {
		if (this.topologyViewUrl != null) {
			topologyWebEngine.load(topologyViewUrl);
		}
	}
}
