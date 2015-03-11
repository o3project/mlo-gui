/**
 * MloCommonTable.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.view;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sun.javafx.scene.control.behavior.TableCellBehavior;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * クライアントに表示されるテーブルクラスです。
 *
 */
public class MloCommonTable extends TableView<SliceData> {
	
	private static final String COLUMN_KEY = "Key";
	private static final String COLUMN_VALUE = "Value";
	
	private int dataCount;
	private static final double LAYOUT_MARGINE = 20d;
	
	/**
	 * テーブルを作成します。
	 * @param dataMap 表示するデータマップ
	 * @param tableWidth テーブル幅
	 * @param editFlag 編集中状態か否かを示すフラグ。true の場合編集中状態。
	 */
	public MloCommonTable(LinkedHashMap<String, String> dataMap, double tableWidth, boolean editFlag){
		super();
		double columnWidth = (tableWidth - LAYOUT_MARGINE) / 2;
		
		// テーブル用データの作成
		ObservableList<SliceData> data = FXCollections.observableArrayList();
		
		for(Map.Entry<String, String> entry : dataMap.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			SliceData obj = new SliceData(key, value);
			data.add(obj);
		}
		dataCount = data.size();
		TableColumn<SliceData, String> paramKeyCol = new TableColumn<SliceData, String>();
		paramKeyCol.setText(COLUMN_KEY);
		paramKeyCol.setCellValueFactory(new PropertyValueFactory<SliceData, String>(COLUMN_KEY));
		paramKeyCol.setPrefWidth(columnWidth);
		paramKeyCol.setEditable(false);
		paramKeyCol.setSortable(false);
		
		TableColumn<SliceData, String> paramValueCol = new TableColumn<SliceData, String>();
		paramValueCol.setText(COLUMN_VALUE);
		paramValueCol.setCellValueFactory(new PropertyValueFactory<SliceData, String>(COLUMN_VALUE));
		paramValueCol.setPrefWidth(columnWidth);

		Callback<TableColumn<SliceData, String>, TableCell<SliceData, String>> cellFactory;

		cellFactory=
				new Callback<TableColumn<SliceData, String>, TableCell<SliceData, String>>() {
					public TableCell<SliceData, String> call(TableColumn<SliceData, String> p) {
						return new EditingCell();
					}
				};
		
		paramValueCol.setCellFactory(cellFactory);
		paramValueCol.setEditable(editFlag);
		paramValueCol.setSortable(false);
		
		updateObservableListProperties(paramValueCol);
		
		this.setEditable(true);
		this.setItems(data);
		this.getColumns().addAll(paramKeyCol, paramValueCol);
		
	}

	/**
	 * インスタンスに割り当てられていたメモリ領域を解放します。
	 * JavaFX 2.2 TableView のメモリリークバグ対策。
	 * JavaFX 2.2 TableView には、テーブル要素(行など)にフォーカスだけでメモリがリークするバグがある。
	 * メモリリークの原因となる参照をこのメソッドで解除する。
	 */
	public void dispose() {
		
		getFocusModel().focus(null);
		
		Class<TableCellBehavior> tcbClass = TableCellBehavior.class;
		
		try {
			Method anchorMethod = null;
			anchorMethod = tcbClass.getDeclaredMethod("setAnchor", TableView.class, TablePosition.class);
			anchorMethod.setAccessible(true);
			anchorMethod.invoke(null, this, null);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		
		setOnMouseClicked(null);
		setSelectionModel(null);
		getColumns().clear();
		
		this.setItems(FXCollections.observableArrayList(new ArrayList<SliceData>()));
	}
	
	/**
	 * 入力したデータをテーブルに反映する。
	 * @param nameCol 名前列
	 */
	private void updateObservableListProperties(TableColumn<SliceData, String> nameCol) {
		nameCol.setOnEditCommit(new EventHandler<CellEditEvent<SliceData, String>>() {
			@Override public void handle(CellEditEvent<SliceData, String> t) {
				((SliceData) t.getTableView().getItems().get(t.getTablePosition().getRow())).setValue(t.getNewValue());
			}
		});
	} 
	
	/**
	 * テーブルからデータを取得します。
	 * @return データマップ
	 */
	public LinkedHashMap<String, String> getData(){
		ObservableList<SliceData> oblist = this.getItems();
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		for(int i = 0; i < dataCount; i++){
			map.put(oblist.get(i).getKey(), oblist.get(i).getValue());
		}
		
		return map;
	}
}
