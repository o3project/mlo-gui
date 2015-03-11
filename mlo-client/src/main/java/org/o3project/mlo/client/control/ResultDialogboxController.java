/**
 * ResultDialogboxController.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import javafx.scene.control.Button;

/**
 * 結果表示ダイアログボックスの制御インタフェースです。
 *
 */
public interface ResultDialogboxController {

	/**
	 * OK ボタンを取得します。
	 * OK ボタンを押下された際のハンドラをボタンに設定してください。
	 * @return OK ボタン
	 */
	Button getOkButton();
	
	/**
	 * ダイアログボックスを表示します。
	 * @param title ダイアログボックスのタイトル
	 * @param message ダイアログボックスのメッセージ
	 * @param detail ダイアログボックスのメッセージ詳細
	 */
	void showDialog(String title, String message, String detail);
	
	/**
	 * ダイアログボックスを閉じます。
	 */
	void closeDialog();
}
