/**
 * MloInputDataException.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * MLO Clientで入力値エラーが発生する異常を表現する例外クラスです。
 * 入力したフロー情報が不正な値の場合に、この例外がスローされます。
 */
public class MloInputDataException extends MloClientException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4060991710948010187L;

	/**
	 * 指定されたエラーメッセージで例外を作成します。
	 * @param msg エラーメッセージ
	 */
	public MloInputDataException(String msg) {
		super(msg);
	}

	/**
	 * 指定されたエラーメッセージと原因で例外を作成します。
	 * @param msg エラーメッセージ
	 * @param cause 原因
	 */
	public MloInputDataException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
