/**
 * MloClient.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.o3project.mlo.client.control.CredentialListener;
import org.o3project.mlo.client.control.ClientConfig;
import org.o3project.mlo.client.control.ClientConfigConstants;
import org.o3project.mlo.client.control.FXMLUtil;
import org.o3project.mlo.client.control.Credential;
import org.o3project.mlo.client.control.MloAccessException;
import org.o3project.mlo.client.control.MloClientException;
import org.o3project.mlo.client.control.MloInputDataException;
import org.o3project.mlo.client.control.MloNbiException;
import org.o3project.mlo.client.control.ResultDialogboxController;
import org.o3project.mlo.client.control.SliceDataManager;
import org.o3project.mlo.client.control.SliceTaskFactory;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.o3project.mlo.server.dto.FlowDto;
import org.o3project.mlo.server.dto.SliceDto;


/**
 * This class is the main class of mlo-client.
 */
public class MloClient extends Application implements CredentialListener, ChangeListener<String> {
	private static final Log LOG = LogFactory.getLog(MloClient.class);

	private static MloClient instance;
	
	private Stage stage;

	private Stage indicatorStage;
		
	/**
	 * Slice information view controller.
	 */
	public MloInfoViewController infoViewCtrl;
	
	/**
	 * Slice updating view controller.
	 */
	public MloUpdateViewController updateViewCtrl;
	
	/**
	 * Slice creation view controller.
	 */
	public MloCreateViewController createViewCtrl;
	
	private ResultDialogboxController resultDlgCtrl;
	
	private SliceDto selectSliceDto;
	
	private SliceDataManager sliceDataManager;
	private ClientConfig clientConfig;
	
	private static final double INDICATOR_WIDTH = 150d;
	private static final double INDICATOR_HEIGHT = 150d;
	private static final double INDICATOR_MARGINE_X = 50d;
	private static final double INDICATOR_MARGINE_Y = 50d;
	
	private ExecutorService executorService;
	
	private SliceTaskFactory sliceTaskFactory;
	
	/*
	 * (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		LOG.info("MloClient#start() starts.");
		instance = this;
		
		// Launches Seasar2 
		try {
			SingletonS2ContainerFactory.setConfigPath("app.dicon");
			SingletonS2ContainerFactory.init();
			LOG.info("S2Container has been initialized.");
			
			sliceDataManager = SingletonS2Container.getComponent("sliceDataManager");
			clientConfig = SingletonS2Container.getComponent("clientConfig");
			LOG.info("S2 task has been started.");
		} catch (RuntimeException e) {
			LOG.fatal("Failed to initialize S2.", e);
			throw e;
		}
		
		executorService = Executors.newSingleThreadExecutor();
		
		sliceTaskFactory = new SliceTaskFactoryImpl(this);
		
		// Sets window title.
		stage = primaryStage; 
		stage.setTitle(ClientConfigConstants.APP_NAME);
		//stage.setResizable(false);

		infoViewCtrl = FXMLUtil.createController(MloInfoViewController.class, "info.fxml");
		updateViewCtrl = FXMLUtil.createController(MloUpdateViewController.class, "update.fxml");
		createViewCtrl = FXMLUtil.createController(MloCreateViewController.class, "create.fxml");
		
		indicatorStage = createIndicatorStage();
		
		resultDlgCtrl = FXMLUtil.createController(ResultDialogboxControllerImpl.class, "resultDialogbox.fxml");
	
		infoViewCtrl.setTopologyViewUrl(clientConfig.getTopologyViewUri());
		
		if(ClientConfigConstants.CLIENT_TYPE_OTHER.equals(clientConfig.getSrcComponentName())){
		}
		
		// Sets default flow settings.
		FlowDto defaultFlowDto = createDefaultFlowDto();
		updateViewCtrl.setDefaultFlowDto(defaultFlowDto);
		createViewCtrl.setDefaultFlowDto(defaultFlowDto);
		
		// Creates and setups main stage.
		//Parent root = mainViewCtrl.getRoot();
		Parent root = infoViewCtrl.getRoot();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		// Show stage
		//loginStage.show();
		stage.show();
		
		LOG.info("MloClient#start() successfully ends.");
	}

	 /*
	  * (non-Javadoc)
	  * @see javafx.application.Application#stop()
	  */
	@Override
	public void stop() throws Exception {
		LOG.info("MloClient#stop() starts.");
		try {
			final int waitSec = 2;
			executorService.shutdownNow();
			executorService.awaitTermination(waitSec, TimeUnit.SECONDS);
			
			SingletonS2ContainerFactory.destroy();
		} finally {
			super.stop();
		}
		LOG.info("MloClient#stop() successfully ends.");
	}
	
	/**
	 * Launches this application.
	 * @param args parameters.
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			// Launches JavaFX application.
			launch(args);
		} else if (args.length > 0 && "-m".equals(args[0])) {
			SliceMultiRequestImpl.main(args);
		} else if (args.length > 0 && "-t".equals(args[0])) {
			RequestorServiceImpl.main(args);
		}
	}
	
	/**
	 * Obtains the instance.
	 * @return the instance.
	 */
	public static MloClient getInstance() { 
		return instance; 
	}
	
	/**
	 * Transit to Info view.
	 */
	public void changeInfoView(){
		infoViewCtrl.setUpView();
		Parent root = infoViewCtrl.getRoot();
		changeContent(root);
	}
	
	/**
	 * Transit to Update view.
	 */
	public void changeUpdateView() {
		updateViewCtrl.setUpView();
		Parent root = updateViewCtrl.getRoot();
		updateViewCtrl.setData(selectSliceDto);
		changeContent(root);
	}
	
	/**
	 * Transit to Create view.
	 */
	public void changeCreateView() {
		createViewCtrl.setUpView();
		Parent root = createViewCtrl.getRoot();
		changeContent(root);
	}

	/**
	 * Changes content.
	 * @param root the root changed to.
	 */
	private void changeContent(Parent root) {
		//mainViewCtrl.setBodyContent(root);
		
		Scene scene = stage.getScene();
		if(scene == null){
			scene = new Scene(root);
			stage.setScene(scene);
		}else{
			stage.getScene().setRoot(root); 
		}
	}
	
	/**
	 * Shows main window.
	 */
	public void showStage(){
		changeInfoView();
		stage.show();
	}
	
	/**
	 * 
	 * Creates indicator stage instance.
	 * @return the instance.
	 */
	public static Stage createIndicatorStage() {
		ProgressIndicator pi = new ProgressIndicator();
		pi.setPrefSize(INDICATOR_MARGINE_X, INDICATOR_MARGINE_Y);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setStyle("-fx-background-radius: 10;-fx-background-color: rgba(0,0,0,0.3);");
		borderPane.setPrefSize(INDICATOR_WIDTH, INDICATOR_HEIGHT);
		borderPane.setCenter(pi);

		StackPane root = new StackPane();

		Scene scene = new Scene(root, INDICATOR_WIDTH, INDICATOR_HEIGHT);
		scene.setFill(null);
		
		root.getChildren().add(borderPane);
		
		Stage stage = new Stage(StageStyle.TRANSPARENT);
		stage.setResizable(false);
		stage.setScene(scene);
		
		stage.initModality(Modality.APPLICATION_MODAL);
		
		return stage;
	}

	/**
	 * Shows result dialog box.
	 * If the argument is null, closes the dialog box, and then transit to Info view.
	 * If the argument is not null, only closes the dialog box. 
	 * @param e Error information.
	 */
	public void showResultAndGoBackToInfoView(final MloClientException e) {
        final ResultDialogboxController dlg = getResultDialogboxController();
        Button btn = dlg.getOkButton();
        btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent evt) {
                if (null == e) {
                    changeInfoView();
                }
                dlg.closeDialog();
			}
		});
        String message;
        String detail;
        if (e instanceof MloNbiException) {
            message = ((MloNbiException) e).getErrorDto().cause;
            detail = ((MloNbiException) e).getErrorDto().detail;
        } else if (e instanceof MloAccessException) {
            message = e.getMessage();
            detail = e.getCause().getMessage();
        } else if (e instanceof MloInputDataException) {
            message = e.getMessage();
            detail = e.getCause().getMessage();
        } else {
            message = "The operation has been completed.";
            detail = "";
        }
        dlg.showDialog(ClientConfigConstants.APP_NAME, message, detail);
	}

	
	/**
	 * Obtains the indicator stage instance.
	 * @return the instance.
	 */
	public Stage getIndicatorStage() {
		return indicatorStage;
	}
	
	/**
	 * Invalidates indicator stage.
	 */
	public void closeDialog(){
		indicatorStage.hide();
	}

	/**
	 * Sets selected slice.
	 * @param sliceDto the slice DTO.
	 */
	public void setSelectSliceDto(SliceDto sliceDto){
		this.selectSliceDto = sliceDto;
	}
	
	/**
	 * Obtains {@link SliceDataManager} instance.
	 * @return SliceDataManager the instance.
	 */
	public SliceDataManager getSliceDataManager() {
		return sliceDataManager;
	}
	
	/**
	 * Obtains {@link ResultDialogboxController} instance.
	 * @return ResultDialogboxController the instance.
	 */
	public ResultDialogboxController getResultDialogboxController() {
		return resultDlgCtrl;
	}
	
	/**
	 * Obtains {@link SliceTaskFactory} instance.
	 * @return the instance.
	 */
	public SliceTaskFactory getSliceTaskFactory() {
		return sliceTaskFactory;
	}
	
	/**
	 * Sets {@link SliceTaskFactory} instance.
	 * @param sliceTaskFactory the instance.
	 */
	void setSliceTaskFactory(SliceTaskFactory sliceTaskFactory) {
		this.sliceTaskFactory = sliceTaskFactory;
	}
	
	/**
	 * Obtains {@link ExecutorService} instance.
	 * @return the instance.
	 */
	public ExecutorService getExecutorService() {
		return executorService;
	}
	
	/**
	 * Sets {@link ExecutorService} instance.
	 * @param executorService the instance.
	 */
	void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
	
	/**
	 * Creates default flow DTO.
	 * @return the flow DTO.
	 */
	private FlowDto createDefaultFlowDto(){
		
		FlowDto flowDto = new FlowDto();
		flowDto.name = "";
		flowDto.protectionLevel = "0";
		
		String srcComponentName = clientConfig.getSrcComponentName();
		if(ClientConfigConstants.CLIENT_TYPE_DEFAULT.equals(srcComponentName)){
			final int bandWidth = 10;
			final int delay = 25;
			flowDto.srcCENodeName = "h1";
			flowDto.srcCEPortNo = "eth1";
			flowDto.dstCENodeName = "h2";
			flowDto.dstCEPortNo = "eth1";
			flowDto.reqBandWidth = bandWidth;
			flowDto.reqDelay = delay;
		} else if(ClientConfigConstants.CLIENT_TYPE_OTHER.equals(srcComponentName)){
			final int otherBandWidth = 10;
			final int otherDelay = 9999;
			flowDto.srcCENodeName = "tokyo";
			flowDto.srcCEPortNo = "00000001";
			flowDto.dstCENodeName = "osaka";
			flowDto.dstCEPortNo = "00000004";
			flowDto.reqBandWidth = otherBandWidth;
			flowDto.reqDelay = otherDelay;
		}else{
			final int hitachiBandWidth = 5;
			final int hitachiDelay = 10;
			flowDto.srcCENodeName = "tokyo123";
			flowDto.srcCEPortNo = "00000001";
			flowDto.dstCENodeName = "osaka123";
			flowDto.dstCEPortNo = "00000001";
			flowDto.reqBandWidth = hitachiBandWidth;
			flowDto.reqDelay = hitachiDelay;
		}
		
		return flowDto;
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.AccountListener#loginTrialCalled(org.o3project.mlo.client.control.LoginTrial)
	 */
	@Override
	public void loginTrialCalled(Credential credential) {
		if ("developer".equals(credential.userid) && "developer".equals(credential.passwd)) {
			credential.setResult(true, null);
		} else {
			credential.setResult(false, "Failed to login.");
		}
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.AccountListener#doLogin()
	 */
	@Override
	public void doLogin(Credential credential) {
		stage.show();
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.AccountListener#doLogout()
	 */
	@Override
	public void doLogout() {
		stage.hide();
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.AccountListener#doExit()
	 */
	@Override
	public void doExit() {
	}

	/* (non-Javadoc)
	 * @see javafx.beans.value.ChangeListener#changed(javafx.beans.value.ObservableValue, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void changed(ObservableValue<? extends String> obsValue, String value, String newValue) {
	}
}
