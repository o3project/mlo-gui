/**
 * MloAccessException.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * MLO NBI アクセス時に発生する異常を表現する例外クラスです。
 * MLO から返却がないなど、MLO NBI から応答がない場合にこの例外がスローされます。
 * 例えば、MLO の URI が不正で MLO にアクセスができない場合に使用されます。
 * MLO NBI から正常に応答が返ってきたが、その応答がエラーを含む場合には {@link MloNbiException} がスローされるべきです。
 */
public class MloAccessException extends MloClientException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1595966171065226386L;

	/**
	 * 指定されたエラーメッセージで例外を作成します。
	 * @param msg エラーメッセージ
	 */
	public MloAccessException(String msg) {
		super(msg);
	}

	/**
	 * 指定されたエラーメッセージと原因で例外を作成します。
	 * @param msg エラーメッセージ
	 * @param cause 原因
	 */
	public MloAccessException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
