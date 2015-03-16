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
 * This class is to display flow information.
 */
public class FlowPanel extends TitledPane {

    // Parameters to create table.
    // For editing
    /** Editing parameter key : flow name. */
    private static final String EDIT_PARAM_KEY_FLOW_NAME = "FlowName";
    
    /** Editing parameter key : Source CE node name. */
    private static final String EDIT_PARAM_KEY_SRC_CE_NODE_NAME = "SourceCENodeName";
    
    /** Editing parameter key : Source CE node port. */
    private static final String EDIT_PARAM_KEY_SRC_CE_PORT_NO = "SourceCEPortNo";
    
    /** Editing parameter key : Destination CE node name. */
    private static final String EDIT_PARAM_KEY_DST_CE_NODE_NAME = "DestCENodeName";
    
    /** Editing parameter key : Destination CE port. */
    private static final String EDIT_PARAM_KEY_DST_CE_PORT_NO = "DestCEPortNo";
    
    /** Editing parameter key : Band width. */
    private static final String EDIT_PARAM_KEY_REQUEST_BANDWIDTH = "RequestBandWidth [Mbps]";
    
    /** Editing parameter key : Delay time. */
    private static final String EDIT_PARAM_KEY_REQUEST_DELAY = "RequestDelay [msec]";
    
    // For displaying
    /** Displaying parameter key : Flow ID */
    private static final String READ_PARAM_KEY_FLOW_ID = "FlowId";
    
    /** Displaying parameter key : Source PT node name. */
    private static final String READ_PARAM_KEY_SRC_PT_NODE_NAME = "SourcePTNodeName";
    
    /** Displaying parameter key : Source PT node ID. */
    private static final String READ_PARAM_KEY_SRC_PT_PORT_ID = "SourcePTNodeId";
    
    /** Displaying parameter key : Destination PT node name. */
    private static final String READ_PARAM_KEY_DST_PT_NODE_NAME = "DestPTNodeName";
    
    /** Displaying parameter key : Destination PT node ID. */
    private static final String READ_PARAM_KEY_DST_PT_NODE_ID = "DestPTNodeId";
    
    /** Displaying parameter key : Band width. */
    private static final String READ_PARAM_KEY_USED_BANDWITDH = "UsedBandWidth [Mbps]";
    
    /** Displaying parameter key : Delay time. */
    private static final String READ_PARAM_KEY_DELAY_TIME = "DelayTime [msec]";
    
    /** Displaying parameter key : Underlay logical list. */
    private static final String READ_PARAM_KEY_UNDERLAY_LOGICAL_LIST = "UnderlayLogicalList";
    
    /** Displaying parameter key : Overlay logical list. */
    private static final String READ_PARAM_KEY_OVERLAY_LOGICAL_LIST = "OverlayLogicalList";

    // For LinkInfo
    /** Link info parameter key : Link ID */
    private static final String LINK_PARAM_KEY_LINK_ID = "LinkId";
    
    /** Link info parameter key : Source PT node name. */
    private static final String LINK_PARAM_KEY_SRC_PT_NODE_NAME = "SourcePTNodeName";
    
    /** Link info parameter key : Source PT node ID. */
    private static final String LINK_PARAM_KEY_SRC_PT_NODE_ID = "SourcePTNodeId";
    
    /** Link info parameter key : Destination PT node name. */
    private static final String LINK_PARAM_KEY_DST_PT_NODE_NAME = "DestPTNodeName";
    
    /** Link info parameter key : Destination PT node ID. */
    private static final String LINK_PARAM_KEY_DST_PT_NODE_ID = "DestPTNodeId";

    // margins
    /** layout margin */
    private static final double LAYOUT_MARGINE = 10d;
    
    /** Choice box vertical margin.  */
    private static final double CHOICE_BOX_MARGINE_Y = 30d;
    
    /** Scroll bar width. */
    private static final double SCROLL_BAR_WIDTH = 10d;
    
    // Height of table.
    /** Height of link info table. */
    private static final double LINK_TABLE_HEIGHT = 160d;
    
    /** Height of displaying flow table. */
    private static final double READ_FLOW_TABLE_HEIGHT = 250d;
    
    /** Height of editing flow table. */
    private static final double EDIT_FLOW_TABLE_HEIGHT = 210d;
    
    // Displaying panel width.
    /** Width of displaying flow panel. */
    private static final double READ_FLOW_PANEL_WIDTH = 500d;
    
    /** Width of editing flow panel. */
    private static final double EDIT_FLOW_PANEL_WIDTH = 720d;
    
    // Height of editing panel (height of flow table plus the margin).
    /** Height of editing panel.  */
    private static final double EDIT_PANEL_HEIGHT = EDIT_FLOW_TABLE_HEIGHT + LAYOUT_MARGINE;
    
    // Labels
    /** Width of label. */
    private static final double LABEL_HEIGHT = 20d;
    
    /** Horizontal margin of deletion label. */
    private static final double DISPLAY_DELETE_LABEL_MARGINE_X = 150d;
    
    /** Vertical margin of deletion label. */
    private static final double DISPLAY_DELETE_LABEL_MARGINE_Y = LAYOUT_MARGINE;
    
    // Width of table (Width of area - margins of right and left - width of scroll bar)
    /** Width of editing flow table. */
    private static final double READ_FLOW_TABLE_WIDTH = READ_FLOW_PANEL_WIDTH - (LAYOUT_MARGINE * 2) - SCROLL_BAR_WIDTH;
    
    /** Width of editing flow table. */
    private static final double EDIT_FLOW_TABLE_WIDTH = EDIT_FLOW_PANEL_WIDTH - (LAYOUT_MARGINE * 2) - SCROLL_BAR_WIDTH;
    
    /** Label. */
    private Label lb;
    
    /** Displaying panel. */
    private AnchorPane readPanel;
    
    /** Editing panel. */
    private AnchorPane editPanel;

    /** Width of flow table. */
    private double flowTableWidth;
    
    /** Height of flow table. */
    private double readPanelHeight;

    // Flow status.
    /** Flow status : no change. */
    public static final String FLOW_TYPE_NO_CHANGE = "no change";
    
    /** Flow status : add */
    public static final String FLOW_TYPE_ADD = "add";
    
    /** Flow status : delete */
    public static final String FLOW_TYPE_DELETE = "delete";
    
    /** Flow status : update */
    public static final String FLOW_TYPE_UPDATE = "update";
    
    /** Selected flow status. */
    private String selectType;

    /** Error detail prefix. */
    private static final String ERROR_DETAIL_PREFIX = "BadRequest/InvalidData/";

    /** Editing flow table. */
    private MloCommonTable editFlowTable = null;
    
    /** Flow panel layout. */
    private final VBox flowDispVBox = new VBox();

    /** Selected flow DTO. */
    private FlowDto targetFlowDto;
    
    /**
     * A constructor.
     * @param title the title of panel.
     * @param defaultFlowDto the default flow DTO.
     */
    public FlowPanel(String title, FlowDto defaultFlowDto) {
        super(title, null);

        // Sets width of flow panel.
        double flowAreaWidth = EDIT_FLOW_PANEL_WIDTH;
        flowTableWidth = EDIT_FLOW_TABLE_WIDTH;

        // Creates base panel.
        AnchorPane panel = new AnchorPane();

        // Creates editing panel.
        editPanel = new AnchorPane();

        // Create table for adding.
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

        // Adds editing panel to base panel.
        panel.getChildren().add(editPanel);

        this.setContent(panel);
        this.setPrefWidth(flowAreaWidth);
        selectType = FLOW_TYPE_ADD;
    }

    /**
     * A constructor for displaying and editing flow panel.
     * @param title the title of the panel.
     * @param flowDto the flow DTO.
     * @param editFlag the flag whether editing layout or not. true if editing. false if displaying.
     * @param defaultFlowDto the default flow DTO. If editing, this value should be null.
     */
    public FlowPanel(String title, FlowDto flowDto, boolean editFlag, FlowDto defaultFlowDto) {
        super(title, null);
        
        targetFlowDto = flowDto;
        double flowAreaWidth;
        double choiceMargin;
        // Sets width of flow panel.
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

        // Creates base panel.
        AnchorPane panel = new AnchorPane();

        // Creates displaying panel.
        readPanel = new AnchorPane();
        readPanel.setLayoutX(LAYOUT_MARGINE);
        readPanel.setLayoutY(LAYOUT_MARGINE + choiceMargin);
        readPanel.setVisible(true);

        // Creates displaying flow table.
        LinkedHashMap<String, String> readFlowMap = getReadFlowData(flowDto);
        MloCommonTable readFlowTable = new MloCommonTable(readFlowMap, flowTableWidth, false);
        readFlowTable.setPrefSize(flowTableWidth, READ_FLOW_TABLE_HEIGHT);
        flowDispVBox.getChildren().add(readFlowTable);

        // Creates displaying link tables.
        Label lb1 = null;
        int i = 1;
        List<LinkInfoDto> linkInfoList = flowDto.linkInfoList;
        for (LinkInfoDto linkInfo : linkInfoList) {
            // Creates each displaying link table.
            lb1 = new Label("<LinkInfo " + i + ">");
            lb1.setPrefHeight(LABEL_HEIGHT);
            flowDispVBox.getChildren().add(lb1);
            LinkedHashMap<String, String> linkInfoMap = getReadLinkInfoData(linkInfo);
            TableView<?> tbViewlinkow = new MloCommonTable(linkInfoMap, flowTableWidth, false);
            tbViewlinkow.setPrefSize(linkTableWidth, LINK_TABLE_HEIGHT);
            flowDispVBox.getChildren().add(tbViewlinkow);
            i++;
        }

        // Layout.
        readPanel.getChildren().add(flowDispVBox);
        
        // Calculates height (height of flow table + (height of link table + height of label) * the number of link table + margin).
        readPanelHeight = READ_FLOW_TABLE_HEIGHT + (LINK_TABLE_HEIGHT + LABEL_HEIGHT) * (i - 1) + LAYOUT_MARGINE;
        readPanel.setPrefSize(flowTableWidth, readPanelHeight);

        // Layouts displaying panel to base panel.
        panel.getChildren().add(readPanel);

        // In the case of editing.
        if (editFlag) {

            // For editing.
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

            // Creates editing flow table.
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

        // Layouts base panel.
        this.setContent(panel);
        this.setPrefWidth(flowAreaWidth);

    }
    
    /**
     * Creates editing flow table map.
     * @param defaultFlowDto the default flow DTO.
     * @return editing flow information map.
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
     * Creates displaying flow table.
     * @param flowDto the flow DTO.
     * @return the displaying flow table information.
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
     * Creates link table information.
     * @param linkInfo the link info DTO.
     * @return the link table.
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
     * Sets choice box action.
     * @param cb the choice box.
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
     * Creates the adding flow DTO.
     * @return the flow DTO.
     * @throws MloInputDataException Error occurs in input data.
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
     * Creates flow DTO for deletion.
     * @return the flow DTO.
     */
    public FlowDto getDeleteFlowDto() {
        FlowDto flowdto = new FlowDto();
        flowdto.type = "del";
        flowdto.id = targetFlowDto.id;
        return flowdto;
    }

    /**
     * Creates flow DTO for updating.
     * @return the flow DTO.
     * @throws MloInputDataException Error occurs in input data.
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
     * Obtains flow type.
     * @return the flow type.
     */
    public String getFlowType() {
        return selectType;
    }

    /**
     * Sets common table (only for debugging).
     * @param table the common table.
     */
    public void setTbView(MloCommonTable table) {
        this.editFlowTable = table;
    }

	/**
	 * Disposes this instance.
	 * This is for workaround of memory leak of JavaFX 2.2 Table view.
	 * JavaFX 2.2 TableView includes a bug of memory leak in focusing a table row.
	 * This class releases references of {@link MloCommonTable} which causes a memory leak.
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
