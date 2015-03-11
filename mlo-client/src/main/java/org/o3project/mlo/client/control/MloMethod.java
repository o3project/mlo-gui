/**
 * MloMethod.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.RestifResponseDto;

/**
 * MLO へのアクセスメソッドを表すインタフェースです。
 */
public interface MloMethod {
	
	/**
	 * メソッド名を取得します。
	 * GET/POST のいずれかである必要があります。
	 * @return メソッド名
	 */
	String getName();
	
	/**
	 * リクエスト時にボディ部にデータを書き込むか否かを表します。
	 * @return true の場合、ボディ部にデータを書き込みます。
	 */
	boolean isSetDoOutput();
	
	/**
	 * HTTP リクエスト送信時に出力ストリームにリクエスト情報を書き込みます。
	 * @param reqDto リクエスト情報
	 * @param ostream リクエストのボディ部を表す出力ストリーム
	 */
	void handleReqOutput(RestifRequestDto reqDto, OutputStream ostream);
	
	/**
	 * HTTP レスポンス受信時に入力ストリームからレスポンス情報を取得します。
	 * @param istream レスポンスのボディ部を表す入力ストリーム
	 * @return レスポンス情報
	 */
	RestifResponseDto handleResInput(InputStream istream);
	
	/**
	 * 接続タイムアウト時間を取得します。
	 * ここで取得される値が HTTP 接続時に設定されます。
	 * 0が取得される場合、タイムアウト時間は設定されません。
	 * @return タイムアウト時間[秒]
	 */
	Integer getConnectionTimeoutSec();

	/**
	 * 読み込みタイムアウト時間を取得します。
	 * ここで取得される値が HTTP 接続時に設定されます。
	 * 0が取得される場合、タイムアウト時間は設定されません。
	 * @return タイムアウト時間[秒]
	 */
	Integer getReadTimeoutSec();
	
	
	/**
	 * パスとクエリパラメータから URL を作成します。
	 * ベース URL はコンフィグから取得されます。
	 * ベース URL が http://myHost/myBasePath、
	 * パスが myPath、
	 * クエリパラメータが {"param1":"value1", "param2":"value2"} である場合、
	 * 以下の文字列を返します。
	 * http://myHost/myBasePath/myPath?param1=value1&amp;param2=value2
	 * @param path パス
	 * @param params クエリパラメータ
	 * @return URL 文字列
	 */
	String constructUrl(String path, Map<String, String> params);
}
