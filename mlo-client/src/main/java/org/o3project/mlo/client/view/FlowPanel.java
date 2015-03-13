/**
 * FlowPanel.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.view;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.o3project.mlo.client.control.MloInputDataException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.o3project.mlo.server.dto.FlowDto;
import org.o3project.mlo.server.dto.LinkInfoDto;

/**
 * フロー情報表示用のパネルクラスです。
 */
public class FlowPanel extends TitledPane {

    // テーブル作成用パラメータ
    // edit用
    /** 編集パラメータキー : フロー名 */
    private static final String EDIT_PARAM_KEY_FLOW_NAME = "FlowName";
    
    /** 編集パラメータキー : フローの始点となるCE名 */
    private static final String EDIT_PARAM_KEY_SRC_CE_NODE_NAME = "SourceCENodeName";
    
    /** 編集パラメータキー : フローの始点となるCEのポート番号 */
    private static final String EDIT_PARAM_KEY_SRC_CE_PORT_NO = "SourceCEPortNo";
    
    /** 編集パラメータキー : フローの終点となるCE名 */
    private static final String EDIT_PARAM_KEY_DST_CE_NODE_NAME = "DestCENodeName";
    
    /** 編集パラメータキー : フローの終点となるCEのポート番号 */
    private static final String EDIT_PARAM_KEY_DST_CE_PORT_NO = "DestCEPortNo";
    
    /** 編集パラメータキー : フローの使用帯域幅 */
    private static final String EDIT_PARAM_KEY_REQUEST_BANDWIDTH = "RequestBandWidth [Mbps]";
    
    /** 編集パラメータキー : フローへの要求遅延値 */
    private static final String EDIT_PARAM_KEY_REQUEST_DELAY = "RequestDelay [msec]";
    
    // read用
    /** 閲覧パラメータキー : フロー名に紐付くID */
    private static final String READ_PARAM_KEY_FLOW_ID = "FlowId";
    
    /** 閲覧パラメータキー : フローの始点となるパケトラノード名 */
    private static final String READ_PARAM_KEY_SRC_PT_NODE_NAME = "SourcePTNodeName";
    
    /** 閲覧パラメータキー : フローの始点となるパケトラノードID */
    private static final String READ_PARAM_KEY_SRC_PT_PORT_ID = "SourcePTNodeId";
    
    /** 閲覧パラメータキー : フローの終点となるパケトラノード名 */
    private static final String READ_PARAM_KEY_DST_PT_NODE_NAME = "DestPTNodeName";
    
    /** 閲覧パラメータキー : フローの終点となるパケトラノードID */
    private static final String READ_PARAM_KEY_DST_PT_NODE_ID = "DestPTNodeId";
    
    /** 閲覧パラメータキー : フローの使用帯域幅 */
    private static final String READ_PARAM_KEY_USED_BANDWITDH = "UsedBandWidth [Mbps]";
    
    /** 閲覧パラメータキー : フローで計測された遅延値 */
    private static final String READ_PARAM_KEY_DELAY_TIME = "DelayTime [msec]";
    
    /** 閲覧パラメータキー : リンクが経由するノードIDのリスト */
    private static final String READ_PARAM_KEY_UNDERLAY_LOGICAL_LIST = "UnderlayLogicalList";
    
    /** 閲覧パラメータキー : フローが経由するノードIDのリスト */
    private static final String READ_PARAM_KEY_OVERLAY_LOGICAL_LIST = "OverlayLogicalList";

    // LinkInfo用
    /** リンクパラメータキー : リンクに対して一意に割り当てられるID */
    private static final String LINK_PARAM_KEY_LINK_ID = "LinkId";
    
    /** リンクパラメータキー : リンクの始点となるノードのノードID */
    private static final String LINK_PARAM_KEY_SRC_PT_NODE_NAME = "SourcePTNodeName";
    
    /** リンクパラメータキー : リンクの始点となるパケトラノード名 */
    private static final String LINK_PARAM_KEY_SRC_PT_NODE_ID = "SourcePTNodeId";
    
    /** リンクパラメータキー : リンクの終点となるパケトラノード名 */
    private static final String LINK_PARAM_KEY_DST_PT_NODE_NAME = "DestPTNodeName";
    
    /** リンクパラメータキー : リンクの終点となるノードのノードID */
    private static final String LINK_PARAM_KEY_DST_PT_NODE_ID = "DestPTNodeId";

    // マージン
    /** レイアウトマージン */
    private static final double LAYOUT_MARGINE = 10d;
    
    /** チョイスボックスY座標マージン  */
    private static final double CHOICE_BOX_MARGINE_Y = 30d;
    
    /** スクロールバー幅 */
    private static final double SCROLL_BAR_WIDTH = 10d;
    
    // テーブル高さ
    /** リンク情報テーブル高さ */
    private static final double LINK_TABLE_HEIGHT = 160d;
    
    /** 閲覧用フローテーブル高さ */
    private static final double READ_FLOW_TABLE_HEIGHT = 250d;
    
    /** 変種用フローテーブル高さ */
    private static final double EDIT_FLOW_TABLE_HEIGHT = 210d;
    
    // 閲覧用パネル幅
    /** 閲覧用フローパネル幅 */
    private static final double READ_FLOW_PANEL_WIDTH = 500d;
    
    /** 編集用フローパネル幅 */
    private static final double EDIT_FLOW_PANEL_WIDTH = 720d;
    
    // 編集用パネル高さ (フローテーブル高さ＋マージン）
    /** 編集用パネル高さ */
    private static final double EDIT_PANEL_HEIGHT = EDIT_FLOW_TABLE_HEIGHT + LAYOUT_MARGINE;
    
    // ラベル
    /** ラベル幅 */
    private static final double LABEL_HEIGHT = 20d;
    
    /** 削除用ラベルX座標マージン */
    private static final double DISPLAY_DELETE_LABEL_MARGINE_X = 150d;
    
    /** 削除用ラベルY座標マージン */
    private static final double DISPLAY_DELETE_LABEL_MARGINE_Y = LAYOUT_MARGINE;
    
    // テーブル幅(エリア幅 - 左右のマージン - スクロールバー幅) 
    /** 閲覧用フローテーブル幅 */
    private static final double READ_FLOW_TABLE_WIDTH = READ_FLOW_PANEL_WIDTH - (LAYOUT_MARGINE * 2) - SCROLL_BAR_WIDTH;
    
    /** 編集用フローテーブル幅 */
    private static final double EDIT_FLOW_TABLE_WIDTH = EDIT_FLOW_PANEL_WIDTH - (LAYOUT_MARGINE * 2) - SCROLL_BAR_WIDTH;
    
    /** ラベル */
    private Label lb;
    
    /** 閲覧用パネル */
    private AnchorPane readPanel;
    
    /** 編集用パネル */
    private AnchorPane editPanel;

    /** フローテーブル幅 */
    private double flowTableWidth;
    
    /** 閲覧用パネル高さ  */
    private double readPanelHeight;

    // フロー状態
    /** フロー状態 : 変更なし */
    public static final String FLOW_TYPE_NO_CHANGE = "no change";
    
    /** フロー状態 : 追加 */
    public static final String FLOW_TYPE_ADD = "add";
    
    /** フロー状態 : 削除 */
    public static final String FLOW_TYPE_DELETE = "delete";
    
    /** フロー状態 : 更新 */
    public static final String FLOW_TYPE_UPDATE = "update";
    
    /** 選択中のフロー状態 */
    private String selectType;

    /** エラー文言の頭文字 */
    private static final String ERROR_DETAIL_PREFIX = "BadRequest/InvalidData/";

    // 追加用フロー保持 編集用フロー保持
    /** 編集用フローテーブル  */
    private MloCommonTable editFlowTable = null;
    
    /** フローパネルレイアウト */
    private final VBox flowDispVBox = new VBox();

    /** 選択中のフロー情報 */
    private FlowDto targetFlowDto;
    
    /**
     * 追加用フローパネル生成用コンストラクタです。
     * 
     * @param title パネルタイトル
     * @param defaultFlowDto デフォルトフロー情報
     */
    public FlowPanel(String title, FlowDto defaultFlowDto) {

        // スーパークラス呼び出し
        super(title, null);

        // フローパネルの幅を設定
        double flowAreaWidth = EDIT_FLOW_PANEL_WIDTH;
        flowTableWidth = EDIT_FLOW_TABLE_WIDTH;

        // ベースパネルの作成
        AnchorPane panel = new AnchorPane();

        // 編集用パネル作成
        editPanel = new AnchorPane();

        // 追加用テーブル作成
        LinkedHashMap<String, String> paramMap = getEditFlowData(defaultFlowDto);
        editFlowTable = new MloCommonTable(paramMap, EDIT_FLOW_TABLE_WIDTH, true);
        editFlowTable.setLayoutX(0);
        editFlowTable.setLayoutY(0);
        editFlowTable.setPrefSize(flowTableWidth, EDIT_FLOW_TABLE_HEIGHT);
        editPanel.getChildren().add(editFlowTable);
        editPanel.setLayoutX(LAYOUT_MARGINE);
        editPanel.setLayoutY(LAYOUT_MARGINE);
        editPanel.setPrefSize(flowTableWidth, EDIT_PANEL_HEIGHT);
        editPanel.setVisible(true);

        // ベースパネルに編集用パネルを追加
        panel.getChildren().add(editPanel);

        this.setContent(panel);
        this.setPrefWidth(flowAreaWidth);
        selectType = FLOW_TYPE_ADD;
    }

    /**
     * 閲覧・編集用フローパネル生成用コンストラクタです。
     * @param title パネルタイトル
     * @param flowDto 対象となるフロー情報
     * @param editFlag 編集か否か。編集ならば true、閲覧なら false を設定。
     * @param defaultFlowDto デフォルトフロー情報。editFlag が false の場合は null を設定。
     */
    public FlowPanel(String title, FlowDto flowDto, boolean editFlag, FlowDto defaultFlowDto) {

        // スーパークラス呼び出し
        super(title, null);
        targetFlowDto = flowDto;
        double flowAreaWidth;
        double choiceMargin;
        // フローパネルの幅を設定
        if (editFlag) {
            flowAreaWidth = EDIT_FLOW_PANEL_WIDTH;
            flowTableWidth = EDIT_FLOW_TABLE_WIDTH;
            choiceMargin = CHOICE_BOX_MARGINE_Y;
        } else {
            flowAreaWidth = READ_FLOW_PANEL_WIDTH;
            flowTableWidth = READ_FLOW_TABLE_WIDTH;
            choiceMargin = 0d;
        }
        double linkTableWidth = flowTableWidth - LAYOUT_MARGINE;

        // ベースパネル作成
        AnchorPane panel = new AnchorPane();

        // 閲覧用パネル作成
        readPanel = new AnchorPane();
        readPanel.setLayoutX(LAYOUT_MARGINE);
        readPanel.setLayoutY(LAYOUT_MARGINE + choiceMargin);
        readPanel.setVisible(true);

        // 閲覧用フローテーブル作成
        LinkedHashMap<String, String> readFlowMap = getReadFlowData(flowDto);
        MloCommonTable readFlowTable = new MloCommonTable(readFlowMap, flowTableWidth, false);
        readFlowTable.setPrefSize(flowTableWidth, READ_FLOW_TABLE_HEIGHT);
        flowDispVBox.getChildren().add(readFlowTable);

        // 閲覧用リンクテーブル作成
        Label lb1 = null;
        int i = 1;
        List<LinkInfoDto> linkInfoList = flowDto.linkInfoList;
        for (LinkInfoDto linkInfo : linkInfoList) {
            // 閲覧用リンクテーブル作成
            lb1 = new Label("<LinkInfo " + i + ">");
            lb1.setPrefHeight(LABEL_HEIGHT);
            flowDispVBox.getChildren().add(lb1);
            LinkedHashMap<String, String> linkInfoMap = getReadLinkInfoData(linkInfo);
            TableView<?> tbViewlinkow = new MloCommonTable(linkInfoMap, flowTableWidth, false);
            tbViewlinkow.setPrefSize(linkTableWidth, LINK_TABLE_HEIGHT);
            flowDispVBox.getChildren().add(tbViewlinkow);
            i++;
        }

        // 配置
        readPanel.getChildren().add(flowDispVBox);
        
        // 高さを計算 ( フローテーブル高さ + (リンクテーブル高さ＋ラベル高さ) * リンクテーブル数 + マージン)
        readPanelHeight = READ_FLOW_TABLE_HEIGHT + (LINK_TABLE_HEIGHT + LABEL_HEIGHT) * (i - 1) + LAYOUT_MARGINE;
        readPanel.setPrefSize(flowTableWidth, readPanelHeight);

        // ベースパネルに閲覧用パネルを配置
        panel.getChildren().add(readPanel);

        // 編集画面の場合
        if (editFlag) {

            // 編集用
            editPanel = new AnchorPane();

            ChoiceBox<String> cb = new ChoiceBox<String>();
            cb.getItems().addAll(FLOW_TYPE_NO_CHANGE, FLOW_TYPE_UPDATE, FLOW_TYPE_DELETE);
            cb.getSelectionModel().selectFirst();
            cb.setLayoutX(LAYOUT_MARGINE);
            cb.setLayoutY(LAYOUT_MARGINE);
            cb.setPrefHeight(LABEL_HEIGHT);
            setChoiceAction(cb);

            panel.getChildren().add(cb);

            lb = new Label();
            lb.setLayoutX(DISPLAY_DELETE_LABEL_MARGINE_X);
            lb.setLayoutY(DISPLAY_DELETE_LABEL_MARGINE_Y);
            lb.setPrefHeight(LABEL_HEIGHT);
            lb.setStyle("-fx-text-fill: red");
            panel.getChildren().add(lb);

            // 編集用フローテーブル作成
            LinkedHashMap<String, String> paramMap = getEditFlowData(null);
            editFlowTable = new MloCommonTable(paramMap, flowTableWidth, true);
            editFlowTable.setLayoutX(0);
            editFlowTable.setLayoutY(0);
            editFlowTable.setPrefSize(flowTableWidth, EDIT_FLOW_TABLE_HEIGHT);
            editPanel.getChildren().add(editFlowTable);
            editPanel.setLayoutX(LAYOUT_MARGINE);
            editPanel.setLayoutY(LAYOUT_MARGINE + CHOICE_BOX_MARGINE_Y);
            editPanel.setPrefSize(flowTableWidth, EDIT_PANEL_HEIGHT);
            editPanel.setVisible(true);
            panel.getChildren().add(editPanel);

            editPanel.setPrefSize(flowTableWidth, 0);
            editPanel.setVisible(false);

        }

        // ベースパネルの配置
        this.setContent(panel);
        this.setPrefWidth(flowAreaWidth);

    }
    
    /**
     * 編集用フローテーブル情報マップを作成します。
     * 
     * @param defaultFlowDto デフォルトフロー情報
     * @return 編集用フローテーブル情報
     */
    private LinkedHashMap<String, String> getEditFlowData(FlowDto defaultFlowDto){
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<String, String>();
        if(defaultFlowDto != null){
            paramMap.put(EDIT_PARAM_KEY_FLOW_NAME, defaultFlowDto.name);
            paramMap.put(EDIT_PARAM_KEY_SRC_CE_NODE_NAME, defaultFlowDto.srcCENodeName);
            paramMap.put(EDIT_PARAM_KEY_SRC_CE_PORT_NO, defaultFlowDto.srcCEPortNo);
            paramMap.put(EDIT_PARAM_KEY_DST_CE_NODE_NAME, defaultFlowDto.dstCENodeName);
            paramMap.put(EDIT_PARAM_KEY_DST_CE_PORT_NO, defaultFlowDto.dstCEPortNo);
            paramMap.put(EDIT_PARAM_KEY_REQUEST_BANDWIDTH, String.valueOf(defaultFlowDto.reqBandWidth));
            paramMap.put(EDIT_PARAM_KEY_REQUEST_DELAY, String.valueOf(defaultFlowDto.reqDelay));
        }else{
            paramMap.put(EDIT_PARAM_KEY_FLOW_NAME, "");
            paramMap.put(EDIT_PARAM_KEY_SRC_CE_NODE_NAME, "");
            paramMap.put(EDIT_PARAM_KEY_SRC_CE_PORT_NO, "");
            paramMap.put(EDIT_PARAM_KEY_DST_CE_NODE_NAME, "");
            paramMap.put(EDIT_PARAM_KEY_DST_CE_PORT_NO, "");
            paramMap.put(EDIT_PARAM_KEY_REQUEST_BANDWIDTH, "");
            paramMap.put(EDIT_PARAM_KEY_REQUEST_DELAY, "");
        }
        return paramMap;
    }
    
    /**
     * 閲覧用フローテーブル情報を作成します。
     * 
     * @param flowDto 閲覧対象のフローDTO
     * @return 閲覧用フローテーブル情報
     */
    private LinkedHashMap<String, String> getReadFlowData(FlowDto flowDto){
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<String, String>();
        paramMap.put(READ_PARAM_KEY_FLOW_ID, flowDto.id.toString());
        paramMap.put(READ_PARAM_KEY_SRC_PT_NODE_NAME, flowDto.srcPTNodeName);
        paramMap.put(READ_PARAM_KEY_SRC_PT_PORT_ID, String.valueOf(flowDto.srcPTNodeId));
        paramMap.put(READ_PARAM_KEY_DST_PT_NODE_NAME, flowDto.dstPTNodeName);
        paramMap.put(READ_PARAM_KEY_DST_PT_NODE_ID, String.valueOf(flowDto.dstPTNodeId));
        paramMap.put(READ_PARAM_KEY_USED_BANDWITDH, flowDto.usedBandWidth.toString());
        paramMap.put(READ_PARAM_KEY_DELAY_TIME, flowDto.delayTime.toString());
        paramMap.put(READ_PARAM_KEY_UNDERLAY_LOGICAL_LIST, flowDto.underlayLogicalList);
        paramMap.put(READ_PARAM_KEY_OVERLAY_LOGICAL_LIST, flowDto.overlayLogicalList);
    	return paramMap;
    }
    
    /**
     * リンクテーブル情報を作成します。
     * 
     * @param linkInfo 対象のリンクDTO
     * @return リンクテーブル情報
     */
    private LinkedHashMap<String, String> getReadLinkInfoData(LinkInfoDto linkInfo){
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<String, String>();
        paramMap.put(LINK_PARAM_KEY_LINK_ID, linkInfo.id.toString());
        paramMap.put(LINK_PARAM_KEY_SRC_PT_NODE_NAME, linkInfo.srcPTNodeName);
        paramMap.put(LINK_PARAM_KEY_SRC_PT_NODE_ID, linkInfo.srcPTNodeId.toString());
        paramMap.put(LINK_PARAM_KEY_DST_PT_NODE_NAME, linkInfo.dstPTNodeName);
        paramMap.put(LINK_PARAM_KEY_DST_PT_NODE_ID, linkInfo.dstPTNodeId.toString());
        return paramMap;
    }
    
    /**
     * チョイスボックスにアクションを登録します。
     * 
     * @param cb アクションを設定するチョイスボックス
     */
    private void setChoiceAction(ChoiceBox<String> cb){
        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {

            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                if (FLOW_TYPE_UPDATE.equals(newValue)) {
                    readPanel.setPrefSize(flowTableWidth, 0);
                    readPanel.setVisible(false);

                    editPanel.setPrefSize(flowTableWidth, EDIT_PANEL_HEIGHT);
                    editPanel.setVisible(true);
                    lb.setText("");
                    selectType = FLOW_TYPE_UPDATE;

                } else if (FLOW_TYPE_DELETE.equals(newValue)) {
                    readPanel.setPrefSize(flowTableWidth, readPanelHeight);
                    readPanel.setVisible(true);

                    editPanel.setPrefSize(flowTableWidth, 0);
                    editPanel.setVisible(false);
                    lb.setText("This flow was selected as delete target !");
                    selectType = FLOW_TYPE_DELETE;
                } else {
                    readPanel.setPrefSize(flowTableWidth, readPanelHeight);
                    readPanel.setVisible(true);

                    editPanel.setPrefSize(flowTableWidth, 0);
                    editPanel.setVisible(false);
                    lb.setText("");
                    selectType = FLOW_TYPE_NO_CHANGE;
                }
            }
        });
    }

    /**
     * 追加用フローDTOを作成します。
     * 
     * @return フロー DTO
     * @throws MloInputDataException 入力値異常 
     */
    public FlowDto getCreateFlowDto() throws MloInputDataException {
        Map<String, String> dataMap = editFlowTable.getData();
        FlowDto flowdto = new FlowDto();
        flowdto.type = "add";
        flowdto.name = dataMap.get(EDIT_PARAM_KEY_FLOW_NAME);
        flowdto.srcCENodeName = dataMap.get(EDIT_PARAM_KEY_SRC_CE_NODE_NAME);
        flowdto.srcCEPortNo = dataMap.get(EDIT_PARAM_KEY_SRC_CE_PORT_NO);
        flowdto.dstCENodeName = dataMap.get(EDIT_PARAM_KEY_DST_CE_NODE_NAME);
        flowdto.dstCEPortNo = dataMap.get(EDIT_PARAM_KEY_DST_CE_PORT_NO);
        try {
            flowdto.reqBandWidth = Integer.valueOf(dataMap.get(EDIT_PARAM_KEY_REQUEST_BANDWIDTH));
        } catch (NumberFormatException e) {
            throw new MloInputDataException(ERROR_DETAIL_PREFIX + EDIT_PARAM_KEY_REQUEST_BANDWIDTH, e);
        }
        try {
            flowdto.reqDelay = Integer.valueOf(dataMap.get(EDIT_PARAM_KEY_REQUEST_DELAY));
        } catch (NumberFormatException e) {
            throw new MloInputDataException(ERROR_DETAIL_PREFIX + EDIT_PARAM_KEY_REQUEST_DELAY, e);
        }
        flowdto.protectionLevel = "0";
        return flowdto;

    }

    /**
     * 削除用フローDTOを作成します。
     * 
     * @return フロー DTO
     */
    public FlowDto getDeleteFlowDto() {
        FlowDto flowdto = new FlowDto();
        flowdto.type = "del";
        flowdto.id = targetFlowDto.id;
        return flowdto;
    }

    /**
     * 更新用フローDTOを作成します。
     * 
     * @return フロー DTO
     * @throws MloInputDataException 入力値異常 
     */
    public FlowDto getUpdateFlowDto() throws MloInputDataException {
        Map<String, String> dataMap = editFlowTable.getData();
        FlowDto flowdto = new FlowDto();
        flowdto.type = "mod";
        flowdto.name = dataMap.get(EDIT_PARAM_KEY_FLOW_NAME);
        flowdto.id = targetFlowDto.id;
        flowdto.srcCENodeName = dataMap.get(EDIT_PARAM_KEY_SRC_CE_NODE_NAME);
        flowdto.srcCEPortNo = dataMap.get(EDIT_PARAM_KEY_SRC_CE_PORT_NO);
        flowdto.dstCENodeName = dataMap.get(EDIT_PARAM_KEY_DST_CE_NODE_NAME);
        flowdto.dstCEPortNo = dataMap.get(EDIT_PARAM_KEY_DST_CE_PORT_NO);

        try {
            flowdto.reqBandWidth = Integer.valueOf(dataMap.get(EDIT_PARAM_KEY_REQUEST_BANDWIDTH));
        } catch (NumberFormatException e) {
            throw new MloInputDataException(ERROR_DETAIL_PREFIX + EDIT_PARAM_KEY_REQUEST_BANDWIDTH, e);
        }
        try {
            flowdto.reqDelay = Integer.valueOf(dataMap.get(EDIT_PARAM_KEY_REQUEST_DELAY));
        } catch (NumberFormatException e) {
            throw new MloInputDataException(ERROR_DETAIL_PREFIX + EDIT_PARAM_KEY_REQUEST_DELAY, e);
        }

        flowdto.protectionLevel = "0";
        return flowdto;
    }

    /**
     * フロー種別を返します。
     * 
     * @return フロー種別
     */
    public String getFlowType() {
        return selectType;
    }

    /**
     * 共通テーブルを設定します。テスト用。
     * @param table 共通テーブル
     */
    public void setTbView(MloCommonTable table) {
        this.editFlowTable = table;
    }

	/**
	 * インスタンスを片付けます。
	 * JavaFX 2.2 TableView のメモリリークバグ対策。
	 * JavaFX 2.2 TableView には、テーブル要素(行など)にフォーカスだけでメモリがリークするバグがある。
	 * このクラス内の {@link MloCommonTable} のメモリリークの原因となる参照をこのメソッドで解除する。
	 */
    public void dispose() {
		if (editFlowTable != null) {
			editPanel.getChildren().removeAll(editFlowTable);
			editFlowTable.dispose();
		}
		
		for (Node child : flowDispVBox.getChildren()) {
			if (child instanceof MloCommonTable) {
				((MloCommonTable) child).dispose();
			}
		}
    }
}
