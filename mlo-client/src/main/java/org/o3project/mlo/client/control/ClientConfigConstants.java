/**
 * ClientConfigConstants.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * 検証アプリで使用されるコンフィグレーション定数の定義インタフェースです。
 *
 */
public interface ClientConfigConstants {
	
	/**
	 * プロパティのプレフィックスです。
	 */
	String PROP_KEY_PREFIX_ = "mlo.client.config.";
	
	/*
	 * 全般
	 */
	/**
	 * デバック用動作を行う場合に指定するコンポーネント名を表すプロパティのキー。
	 * プロパティの値は文字列です。
	 */
	String PROP_KEY_DEBUG_CLIENTS = PROP_KEY_PREFIX_ + "debug.clients";
	
	/*
	 * MLO-SRV関連
	 */
	/**
	 * MLO サーバのベース URI を表すプロパティのキー。
	 * プロパティの値は文字列です。
	 */
	String PROP_KEY_SERVER_BASE_URI = PROP_KEY_PREFIX_ + "server.baseUri";
	
	/**
	 * MLO サーバのコネクションタイムアウト時間を表すプロパティのキー。
	 * プロパティの値は0または正の整数です。
	 * 単位は秒です。
	 */
	String PROP_KEY_SERVER_CONNECTION_TIMEOUT_SEC = PROP_KEY_PREFIX_ + "server.connectionTimeoutSec";
	
	/**
	 * MLO サーバの読み込みタイムアウト時間を表すプロパティのキー。
	 * プロパティの値は0または正の整数です。
	 * 単位は秒です。
	 */
	String PROP_KEY_SERVER_READ_TIMEOUT_SEC       = PROP_KEY_PREFIX_ + "server.readTimeoutSec";
	
	/**
	 * MLO クライアントの動作環境を表すプロパティのキー。
	 * プロパティの値は文字列です。
	 */
	String PROP_KEY_SERVER_SRC_COMPONENT_NAME       = PROP_KEY_PREFIX_ + "server.src.componentName";
	
	/*
	 * MLO-SRV関連(連携用フラグ)
	 */
	/**
	 * MLO サーバを未接続で動作させるかを表すプロパティのキー。
	 * プロパティの値はブールです。
	 * true の場合、MLO サーバ未接続で動作します。
	 */
	String PROP_KEY_SERVER_DUMMY_INVOKER_SET_FLAG       = PROP_KEY_PREFIX_ + "server.dummy.invoker.flag";
	
	/*
	 * for topology view
	 */

	String PROP_KEY_SERVER_TOPOLOGY_VIEW_URI = PROP_KEY_PREFIX_ + "server.topologyViewUri";
	
	/**
	 * MLO クライアントを他社連携デモとして動作させる場合の設定値です。
	 */
	String CLIENT_TYPE_OTHER = "demoApl";
	
	/**
	 * MLO クライアントを日立単独デモとして動作させる場合の設定値です。
	 */
	String CLIENT_TYPE_HITACHI = "mloClient";
	
	/**
	 * MLO クライアントの表示用アプリケーション名文字列です。
	 */
	String APP_NAME = "Multi Layer Orchestrator";
	
	/**
	 * 
	 */
	String APP_COPYRIGHT = "Copyright (C) 2015, Hitachi, Ltd. All rights reserved.";
}
