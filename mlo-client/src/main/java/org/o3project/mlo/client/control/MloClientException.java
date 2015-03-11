/**
 * MloClientException.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * MLOClientで発生する異常を表現する例外クラスの基底クラスです。
 */
public abstract class MloClientException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5874369740311499855L;

	/**
	 * 指定されたエラーメッセージで例外を作成します。
	 * @param msg エラーメッセージ
	 */
	public MloClientException(String msg) {
		super(msg);
	}

	/**
	 * 指定されたエラーメッセージと原因で例外を作成します。
	 * @param msg エラーメッセージ
	 * @param cause 原因
	 */
	public MloClientException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
