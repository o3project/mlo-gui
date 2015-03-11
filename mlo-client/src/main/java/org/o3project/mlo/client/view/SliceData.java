/**
 * SliceData.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * データ管理用クラスです。
 * 
 */
public class SliceData {

	private StringProperty key;
	private StringProperty value;
	
	/**
	 * コンストラクタ
	 * @param key データキー
	 * @param value データ値
	 */
	SliceData(String key, String value) {
		this.key = new SimpleStringProperty(key);
		this.value = new SimpleStringProperty(value);
	}
	
	/**
	 * キープロパティを取得します。
	 * @return キープロパティ
	 */
	public StringProperty keyProperty() { return key; }
	
	/**
	 * データ値プロパティを取得します。
	 * @return データ値プロパティ
	 */
	public StringProperty valueProperty() { return value; }

	/**
	 * データキーを設定します。
	 * @param key キー文字列
	 */
	public void setKey(String key) { this.key.set(key); }
	
	/**
	 * データ値を設定します。
	 * @param value 値文字列
	 */
	public void setValue(String value) { this.value.set(value); }

	/**
	 * データキー文字列を取得します。
	 * @return キー文字列
	 */
	public String getKey() { return this.key.get(); }
	
	/**
	 * データ値文字列を取得します。
	 * @return 値文字列
	 */
	public String getValue() { return this.value.get(); }

}
