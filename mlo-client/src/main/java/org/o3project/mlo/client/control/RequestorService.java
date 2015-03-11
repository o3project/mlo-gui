/**
 * RequestorService.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import org.o3project.mlo.server.dto.RestifRequestDto;

/**
 * MLO サーバへの並列実行や連続運転を行うためのインタフェースです。<br>
 * このインタフェースは、MLO サーバの負荷実行を検証するために使用します。
 */
public interface RequestorService {

	/**
	 * スライス要求を並列で実行します。
	 * @param requestType CREATE/UPDATE/DELETE/READ
	 * @param requestTemplate スライス情報
	 * @param threadNum 同時実行数
	 */
	void request(String requestType, RestifRequestDto requestTemplate, int threadNum);

	/**
	 * 日立単独デモ環境を想定した、連続運転を行います。
	 * 以下の操作を無限に繰り返します。
	 * <ul>
	 *  <li>スライス作成
	 *  <li>スライス取得
	 *  <li>スライス変更（フロー追加）
	 *  <li>スライス取得
	 *  <li>スライス変更（フロー変更）
	 *  <li>スライス取得
	 *  <li>スライス変更（フロー削除）
	 *  <li>スライス取得
	 *  <li>スライス削除
	 * </ul>
	 */
	void requestMloClient();

	/**
	 * 他社連携デモ環境を想定した、連続運転を行います。
	 * 以下の操作を無限に繰り返します。
	 * <ul>
	 *  <li>スライス作成
	 *  <li>スライス取得
	 *  <li>スライス変更（フロー追加）
	 *  <li>スライス取得
	 *  <li>スライス変更（フロー変更）
	 *  <li>スライス取得
	 *  <li>スライス変更（フロー削除）
	 *  <li>スライス取得
	 *  <li>スライス削除
	 * </ul>
	 */
	void requestDemoApl();

}
