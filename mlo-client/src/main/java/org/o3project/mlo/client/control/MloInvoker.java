/**
 * MloInvoker.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import java.util.Map;

import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.RestifResponseDto;

/**
 * MLO にアクセスするためのインタフェースです。
 */
public interface MloInvoker {

	/**
	 * MLO にアクセスし、レスポンスを受け取ります。
	 * @param method HTTP メソッドインスタンス
	 * @param reqDto MLO にリクエストする {@link RestifRequestDto} インスタンス
	 * @param path URL パス
	 * @param params クエリパラメータ
	 * @return MLO からのレスポンス
	 * @throws MloAccessException アクセス異常例外
	 */
	RestifResponseDto invoke(MloMethod method, 
			RestifRequestDto reqDto, String path, Map<String, String> params) 
					throws MloAccessException;

}
