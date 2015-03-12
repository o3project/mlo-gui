/**
 * ResultDialogboxController.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.control;

import javafx.scene.control.Button;

/**
 * This interface designates UI controlling features of result dialog box.
 */
public interface ResultDialogboxController {

	/**
	 * Obtains OK button.
	 * For example, this method is called in setting UI handler to this button.
	 * @return the OK button.
	 */
	Button getOkButton();
	
	/**
	 * Shows this dialog box.
	 * @param title the title.
	 * @param message the message.
	 * @param detail the detail message.
	 */
	void showDialog(String title, String message, String detail);
	
	/**
	 * Close this dialog box.
	 */
	void closeDialog();
}
