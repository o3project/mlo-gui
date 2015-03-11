/**
 * MloLoginViewController.java
 * (C) 2015, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.o3project.mlo.client.control.CredentialListener;
import org.o3project.mlo.client.control.Credential;
import org.o3project.mlo.client.control.MloViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * MloLoginViewController
 *
 */
public class LoginViewController implements MloViewController, Initializable {
	private static final Log LOG = LogFactory.getLog(LoginViewController.class);
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
	private Label titleLabel;
	
	@FXML
	private TextField userIdTextField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private Label notificationLabel;
	
	@FXML
	private Button loginButton;
	
	@FXML
	private Button exitButton;
	
	private CredentialListener credentialListener;

	/**
	 * 
	 */
	private void checkFields() {
		assert(rootPane != null);
		assert(titleLabel != null);
		assert(userIdTextField != null);
		assert(passwordField != null);
		assert(notificationLabel != null);
		assert(loginButton != null);
		assert(exitButton != null);
	}
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		checkFields();

		reset();
	}
	
	public void reset() {
		userIdTextField.setText("");
		passwordField.setText("");
		
		// Clears user notification message.
		notificationLabel.setText("");
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.MloViewController#getRoot()
	 */
	@Override
	public Parent getRoot() {
		return rootPane;
	}
	
	/**
	 * @param accountListener the accountListener to set
	 */
	public void setCredentialListener(CredentialListener accountListener) {
		this.credentialListener = accountListener;
	}
	
	@FXML
	void handleLoginButtonAction(ActionEvent event) {
		LOG.debug("BEGIN handleLoginButtonEvent()");
		if (loginButton == event.getSource()) {
			if (credentialListener != null) {
				String userid = userIdTextField.getText();
				String passwd = passwordField.getText();
				Credential credential = new Credential(userid, passwd);
				credentialListener.loginTrialCalled(credential);
				if (!credential.isTried()) {
					String errMsg = "Unexpected situation. The credential must be tried.";
					LOG.error(errMsg);
					throw new IllegalStateException(errMsg);
				} else if (credential.isOk()) {
					notificationLabel.setText(credential.getMessage());
					credentialListener.doLogin(credential);
					LOG.info("Logined : " + userid);
				} else {
					LOG.warn("Login failed : " + userid);
					notificationLabel.setText(credential.getMessage());
				}
			}
		}
		LOG.debug("END handleLoginButtonEvent()");
	}
	
	@FXML
	void handleExitButtonAction(ActionEvent event) {
		LOG.debug("BEGIN handleExitButtonEvent()");
		if (exitButton == event.getSource()) {
			if (credentialListener != null) {
				credentialListener.doExit();
				LOG.info("Exit this application.");
			}
		}
		LOG.debug("END handleExitButtonEvent()");
	}
}
