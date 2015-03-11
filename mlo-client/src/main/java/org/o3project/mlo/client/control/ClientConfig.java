/**
 * ClientConfig.java
 * (C) 2013, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * MLO 連携のコンフィグレーションを表現するインタフェースです。
 *
 */
public interface ClientConfig {

	/**
	 * MLO のベース URI を取得します。
	 * @return ベース URI 文字列
	 */
	String getServerBaseUri();

	/**
	 * 検証アプリから MLO へ接続する際の接続タイムアウト時間を取得します。
	 * 単位は秒です。
	 * 0 が取得される場合は、タイムアウト時間を設定しません。
	 * @return タイムアウト時間
	 */
	Integer getConnectionTimeoutSec();
	
	/**
	 * 検証アプリから MLO へ接続する際の読み込みタイムアウト時間を取得します。
	 * 単位は秒です。
	 * 0 が取得される場合は、タイムアウト時間を設定しません。
	 * @return タイムアウト時間
	 */
	Integer getReadTimeoutSec();
	
	/**
	 * 送信元機能コンポーネント名 を取得します。
	 * @return 送信元機能コンポーネント名
	 */
	String getSrcComponentName();

	/**
	 * 検証アプリを MLO 未接続で動作させるかを判定します。
	 * @return  MLO接続設定
	 */
	boolean getDummyInvokerSetFlag();
	
	String getTopologyViewUri();
}
