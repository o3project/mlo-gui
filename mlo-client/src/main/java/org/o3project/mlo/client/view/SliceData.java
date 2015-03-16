/**
 * SliceData.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class is to manage data.
 */
public class SliceData {

	private StringProperty key;
	private StringProperty value;
	
	/**
	 * A constructor.
	 * @param key the data key.
	 * @param value the data value.
	 */
	SliceData(String key, String value) {
		this.key = new SimpleStringProperty(key);
		this.value = new SimpleStringProperty(value);
	}
	
	/**
	 * Obtains key property.
	 * @return the key property.
	 */
	public StringProperty keyProperty() { return key; }
	
	/**
	 * Obtains value property.
	 * @return the value property.
	 */
	public StringProperty valueProperty() { return value; }

	/**
	 * Sets data key.
	 * @param key the data key.
	 */
	public void setKey(String key) { this.key.set(key); }
	
	/**
	 * Sets data value.
	 * @param value the data value.
	 */
	public void setValue(String value) { this.value.set(value); }

	/**
	 * Obtains data key string.
	 * @return the string.
	 */
	public String getKey() { return this.key.get(); }
	
	/**
	 * Obtains data value string.
	 * @return the string.
	 */
	public String getValue() { return this.value.get(); }

}
