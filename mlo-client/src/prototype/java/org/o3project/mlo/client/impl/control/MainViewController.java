/**
 * MloMainViewController.java
 * (C) 2015, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.net.URL;
import java.util.ResourceBundle;

import org.o3project.mlo.client.control.CredentialListener;
import org.o3project.mlo.client.control.MloViewController;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.AnchorPane;

/**
 * MloMainViewController
 *
 */
public class MainViewController implements MloViewController, Initializable {
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
	private Label titleLabel;
	
	@FXML
	private ChoiceBox<String> menuChoiceBox;
	
	@FXML
	private Label userLabel;
	
	@FXML
	private Button logoutButton;
	
	@FXML
	private AnchorPane bodyPane;
	
	private CredentialListener credentialListener;
	
	private void checkFields() {
		assert(rootPane != null);
		assert(titleLabel != null);
		assert(menuChoiceBox != null);
		assert(userLabel != null);
		assert(logoutButton != null);
		assert(bodyPane != null);
	}

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		checkFields();
		menuChoiceBox.getItems().clear();
		//menuChoiceBox.
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.MloViewController#getRoot()
	 */
	@Override
	public Parent getRoot() {
		return rootPane;
	}
	
	/**
	 * @param credentialListener the credentialListener to set
	 */
	public void setCredentialListener(CredentialListener credentialListener) {
		this.credentialListener = credentialListener;
	}

	public void setBodyContent(Parent contentParent) {
		AnchorPane.setTopAnchor(contentParent, 0.0d);
		AnchorPane.setBottomAnchor(contentParent, 0.0d);
		AnchorPane.setLeftAnchor(contentParent, 0.0d);
		AnchorPane.setRightAnchor(contentParent, 0.0d);
		bodyPane.getChildren().clear();
		bodyPane.getChildren().add(contentParent);
	}
	
	public void setUserid(String userid) {
		userLabel.setText(userid + ",");
	}
	
	@FXML
	void handleLogoutButtonAction(ActionEvent evt) {
		if (logoutButton == evt.getSource()) {
			if (credentialListener != null) {
				credentialListener.doLogout();
			}
		}
	}
	
	void setMainMenus(String... menuNames) {
		menuChoiceBox.getItems().clear();
		for (String menuName: menuNames) {
			menuChoiceBox.getItems().add(menuName);
		}
	}
	
	void setCurrentMainMenu(String menuName) {
		menuChoiceBox.setValue(menuName);
	}
	
	void setMainMenuChangeListener(ChangeListener<String> listener) {
		SingleSelectionModel<String> selectionModel = menuChoiceBox.getSelectionModel();
		selectionModel.selectedItemProperty().addListener(listener);
		int selectedIndex = selectionModel.getSelectedIndex();
		selectionModel.clearAndSelect(selectedIndex);
	}
}
