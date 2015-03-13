/**
 * MloCreateViewController.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.o3project.mlo.client.control.ClientConfigConstants;
import org.o3project.mlo.client.control.MloDialogController;
import org.o3project.mlo.client.control.MloInputDataException;
import org.o3project.mlo.client.control.MloViewController;
import org.o3project.mlo.client.control.SliceTaskFactory;
import org.o3project.mlo.client.view.FlowPanel;
import org.o3project.mlo.client.view.MloCommonTable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.o3project.mlo.server.dto.FlowDto;
import org.o3project.mlo.server.dto.SliceDto;

/**
 * スライス作成画面のコントローラクラスです。
 */
public class MloCreateViewController implements MloViewController, Initializable {
	private static final Log LOG = LogFactory.getLog(MloCreateViewController.class);
	
	private static final String SLICE_PARAM_KEY_NAME = "Name";
	
	private static final double PROP_TABLE_WIDTH = 740d;
	private static final double PROP_TABLE_HEIGHT = 60d;
	
	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private Label headerLabel;
	
	@FXML
	private ScrollPane scrollPane;
	
	@FXML
	private Pane sliceInfoPanel;
	
	@FXML
	private Label copyrightLabel;
	
	/**
	 * スライス情報テーブル
	 */
	public MloCommonTable slicePropsDispTable;
	
	private final VBox sliceFlowsDispVBox = new VBox();
	
	private FlowDto defaultFlowDto;

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		headerLabel.setText(ClientConfigConstants.APP_NAME);
		copyrightLabel.setText(ClientConfigConstants.APP_COPYRIGHT);
		scrollPane.setContent(sliceFlowsDispVBox);

		setUpView();
	}

	/*
	 * (non-Javadoc)
	 * @see org.o3project.mlo.client.control.MloViewController#getRoot()
	 */
	@Override
	public Parent getRoot() {
		return anchorPane;
	}

	/**
	 * ビュー要素のセットアップを行います。
	 */
	public void setUpView() {
		setUpSlicePropsView();
		setUpSliceFlowsView();
	}
	
	/**
	 * スライスプロパティ表示部のセットアップを行う。
	 */
	private void setUpSlicePropsView() {
		Pane basePane = sliceInfoPanel;
		
		if (slicePropsDispTable != null) {
			basePane.getChildren().removeAll(slicePropsDispTable);
			slicePropsDispTable.dispose();
			slicePropsDispTable = null;
		}
		
		LinkedHashMap<String, String> sliceInfoMap = new LinkedHashMap<String, String>();
		sliceInfoMap.put(SLICE_PARAM_KEY_NAME, "");
		slicePropsDispTable = new MloCommonTable(sliceInfoMap, PROP_TABLE_WIDTH, true);
		slicePropsDispTable.setPrefSize(PROP_TABLE_WIDTH, PROP_TABLE_HEIGHT);
		basePane.getChildren().add(slicePropsDispTable);
	}

	/**
	 * スライスフロー表示部のセットアップを行う。
	 */
	private void setUpSliceFlowsView() {
		Pane basePane = sliceFlowsDispVBox;
		
		FlowPanel[] fps = basePane.getChildren().toArray(new FlowPanel[0]);
		basePane.getChildren().removeAll(fps);
		for (FlowPanel fp : fps) {
			fp.dispose();
		}
		LOG.debug("size: " + sliceFlowsDispVBox.getChildren());
		
		FlowPanel flowPanel = new FlowPanel("new Flow", defaultFlowDto);
		basePane.getChildren().add(flowPanel);
	}
	
	/**
	 * 画面の入力内容から {@link SliceDto} インスタンスを取得します。
	 * @return インスタンス
	 * @throws MloInputDataException 入力異常
	 */
	public SliceDto getData() throws MloInputDataException{
		SliceDto sliceDto= new SliceDto();
		sliceDto.name = slicePropsDispTable.getData().get(SLICE_PARAM_KEY_NAME);
		List<FlowDto> flowList = new ArrayList<FlowDto>();
		
		// フローごとに判定処理
		for (Node node : sliceFlowsDispVBox.getChildren()) {
			flowList.add(((FlowPanel) node).getCreateFlowDto());
		}
		sliceDto.flows = flowList;
		
		return sliceDto;
	}
	
	/**
	 * デフォルトフロー情報を設定します。
	 * @param flowDto デフォルトフロー情報
	 */
	public void setDefaultFlowDto(FlowDto flowDto){
		defaultFlowDto = flowDto;
	}

	/**
	 * スライス作成画面のボタンのアクションハンドラです。
	 * FXML からのハンドラ指定用。
	 * @param event アクションイベント
	 */
	@FXML
	void handleButtonAction(ActionEvent event) {
		
		Button bt = (Button) event.getSource();
		if("addFlowBt".equals(bt.getId())){
			FlowPanel flowPanel = new FlowPanel("new Flow", defaultFlowDto);
			sliceFlowsDispVBox.getChildren().add(flowPanel);
		}
		
		if("createSliceBt".equals(bt.getId())){
			MloDialogController mloDialog = new MloDialogController();
			mloDialog.setMsgLabl(SliceTaskFactory.CREATE_TASK_LB);
			mloDialog.showDaialog();
		}
		
		if("createCancelBt".equals(bt.getId())){
			MloDialogController mloDialog = new MloDialogController();
			mloDialog.setMsgLabl(SliceTaskFactory.CANCEL_TASK_LB);
			mloDialog.showDaialog();
		}
	}
}
