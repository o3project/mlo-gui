/**
 * MloDialogController.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.o3project.mlo.client.impl.control.MloClient;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * This class is UI controller class for confirmation dialog box.
 */
public class MloDialogController implements Initializable {
	private static final Log LOG = LogFactory.getLog(MloDialogController.class);

	@FXML
	private Pane dialogPanel;
	
	@FXML
	private Label textLb;
	
	static MloDialogController instance;
	
	private Stage indicatorStage;
	
	private String msgLb;
	
	/**
	 * Shows dialog box.
	 */
	public void showDaialog(){
		
		try {
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.setTitle(ClientConfigConstants.APP_NAME);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog.fxml"));
			loader.load();
			Parent root = loader.getRoot();
			
			textLb = (Label) root.lookup("#textLb");
			textLb.setText(msgLb);

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			
		} catch (IOException e) {
			LOG.error("Exception occurs.", e);
		}
		
	}
	
	/**
	 * Handles OK button pressed event.
	 * @param event the event.
	 */
	@FXML
	void handleBtnYesAction(ActionEvent event) {
		getWindow().hide();
		
		final MloClient mloClient = MloClient.getInstance();
		
		String lbMsg = MloDialogController.getInstance().getMsg().getText();
		
		final SliceTask task = mloClient.getSliceTaskFactory().createTask(lbMsg);
		
		task.setOnScheduled(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent evt) {
				LOG.debug("SliceTask scheduled.");
				mloClient.getIndicatorStage().show();
			}
		});
		
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent evt) {
				LOG.debug("SliceTask succeeded.");
				mloClient.getIndicatorStage().hide();
				mloClient.showResultAndGoBackToInfoView(task.getError());
			}
		});
		
		task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent evt) {
				LOG.debug("SliceTask canceled.");
				mloClient.getIndicatorStage().hide();
				mloClient.changeInfoView();
			}
		});

		mloClient.getExecutorService().submit(task);
	}
	
	/**
	 * Handles Cancel button pressed event.
	 * @param event the event.
	 */
	@FXML
	void handleBtnNoAction(ActionEvent event) {
		getWindow().hide();
	}
	
	/**
	 * Obtains confirmation dialog box.
	 * @return the dialog box.
	 */
	private Window getWindow() {
        return dialogPanel.getScene().getWindow();
    }
	
	/**
	 * Closes dialog box.
	 */
	public void closeDialog(){
		indicatorStage.hide();
	}

	/**
	 * Obtains instance.
	 * @return the instance.
	 */
	static MloDialogController getInstance(){
		return instance;
	}
	
	/**
	 * Sets body message.
	 * @param msgLb the message.
	 */
	public void setMsgLabl(String msgLb){
		this.msgLb = msgLb;
	}
	
	/**
	 * Obtains label instance.
	 * @return the label.
	 */
	public Label getMsg(){
		return this.textLb;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle rbu) {
		instance = this;
		
		indicatorStage = MloClient.getInstance().getIndicatorStage();
	}
}
