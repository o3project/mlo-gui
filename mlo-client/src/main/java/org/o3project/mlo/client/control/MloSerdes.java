/**
 * MloSerdes.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import java.io.InputStream;
import java.io.OutputStream;

import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.RestifResponseDto;

/**
 * MLO NBIで使用されるオブジェクト のデシリアライザおよびシリアライザインタフェースです。
 * <ul>
 * <li>内部で処理したオブジェクトを外部へリクエストとして返すために書き出しを行う</li>
 * <li>外部からのレスポンスを、内部で処理するためにオブジェクト化する</li>
 * </ul>
 */
public interface MloSerdes {

	/**
	 * MLO NBI のレスポンス XML を含んだストリームを読み取り、レスポンスオブジェクトを生成します。
	 * 
	 * @param istream XML を含む入力ストリーム
	 * @return MLO NBI レスポンスオブジェクト
	 */
	RestifResponseDto deserializeFromXml(InputStream istream);

	/**
	 * MLO NBI のリクエストオブジェクトを指定された出力ストリームに書き出します。
	 * このメソッドは RuntimeException をスローする可能性があります。
	 * 
	 * @param reqDto MLO NBI のリクエストオブジェクト
	 * @param ostream XML 出力先ストリーム
	 */
	void serializeToXml(RestifRequestDto reqDto, OutputStream ostream);

}
