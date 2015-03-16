/**
 * ResultDialogboxControllerImpl.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.net.URL;
import java.util.ResourceBundle;

import org.o3project.mlo.client.control.ResultDialogboxController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class is the implementation class of {@link ResultDialogboxController} interface.
 */
public class ResultDialogboxControllerImpl implements Initializable, ResultDialogboxController {
	
	private Stage stage;
	
	@FXML
	private BorderPane resultDlg;
	
	@FXML
	private Button okButton;
	
	@FXML
	private Label msgLabel;


	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Scene scene = new Scene(resultDlg);
		
		stage = new Stage(StageStyle.UTILITY);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);

	}
	
	/**
	 * Obtains {@link Stage} instance.
	 * @return the instance.
	 */
	public Stage getStage() {
		return stage;
	}
	
	/**
	 * Obtains OK button.
	 * @return the okButton
	 */
	public Button getOkButton() {
		return okButton;
	}
	
	/**
	 * Obtains the message label.
	 * @return the msgLabel
	 */
	public Label getMsgLabel() {
		return msgLabel;
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.ResultDialogboxController#showDialog(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void showDialog(String title, String message, String detail) {
		msgLabel.setText(message + System.lineSeparator() + detail);
		stage.setTitle(title);
		
		stage.show();
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.ResultDialogboxController#closeDialog()
	 */
	@Override
	public void closeDialog() {
		stage.hide();
	}
}
