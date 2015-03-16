/**
 * EditingCell.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * This class is the editing table cell view class.
 */
public class EditingCell extends TableCell<SliceData, String> {

	private TextField textField = new TextField();
	private boolean inputFlag = false;
	
	/**
	 * A constructor.
	 */
	public EditingCell() {
		textField.setText(getString());
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
				if (t.getCode() == KeyCode.ENTER) {
					if(inputFlag){
						commitEdit(textField.getText());
						inputFlag = false;
					}else{
						inputFlag = true;
					}
				} else if (t.getCode() == KeyCode.ESCAPE) {
					cancelEdit();
				}else{
					inputFlag = true;
				}
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see javafx.scene.control.TableCell#startEdit()
	 */
	@Override
	public void startEdit() {
		super.startEdit();
		inputFlag = false;
		
		textField.setText(getString());
		setText(null);
		setGraphic(textField);
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				textField.selectAll();
				textField.requestFocus();
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see javafx.scene.control.TableCell#cancelEdit()
	 */
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText((String) getItem());
		setGraphic(null);
		inputFlag = false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javafx.scene.control.Cell#updateItem(java.lang.Object, boolean)
	 */
	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				setText(null);
				setGraphic(textField);
			} else {
				setText(getString());
				setGraphic(null);
			}
		}
	}
	
	/**
	 * 
	 * @return cellData
	 */
	private String getString() {
		return getItem() == null ? "" : getItem().toString();
	}
}
