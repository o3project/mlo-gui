/**
 * MloNbiException.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import org.o3project.mlo.server.dto.RestifErrorDto;

/**
 * MLO の NBI で返却されるエラーを表現する例外クラスです。
 */
public class MloNbiException extends MloClientException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7947452279550763904L;

	private final RestifErrorDto errorDto;

	/**
	 * 指定されたエラーメッセージで例外を作成します。
	 * @param msg エラーメッセージ
	 * @param errorDto MLO NBI で返却されたエラー DTO
	 */
	public MloNbiException(String msg, RestifErrorDto errorDto) {
		super(msg);
		this.errorDto = errorDto;
	}

	/**
	 * MLO NBI で返却されたエラー DTO を取得します。
	 * @return MLO NBI で返却されたエラー DTO
	 */
	public RestifErrorDto getErrorDto() {
		return errorDto;
	}
}
