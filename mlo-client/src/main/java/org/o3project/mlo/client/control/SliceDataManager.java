/**
 * SliceDataManager.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import java.util.List;

import org.o3project.mlo.server.dto.SliceDto;

/**
 * 検証アプリとMLO間でのデータI/Oを制御するクラスのインタフェースです。
 */
public interface SliceDataManager {

	/**
	 * スライス一覧を取得します。
	 * @return スライス一覧
	 * @throws MloClientException 処理中に発生した異常
	 */
	List<SliceDto> getSliceList() throws MloClientException;
	
	/**
	 * スライス情報を取得します。
	 * @param slice リクエスト用のスライス情報
	 * @return スライス情報
	 * @throws MloClientException 処理中に発生した異常
	 */
	SliceDto getSliceInfo(SliceDto slice) throws MloClientException;
	
	/**
	 * スライス情報を登録します。
	 * @param slice スライス情報
	 * @return リクエスト結果
	 * @throws MloClientException 処理中に発生した異常
	 */
	SliceDto createSliceInfo(SliceDto slice) throws MloClientException;
	
	/**
	 * スライス情報を更新します。
	 * @param slice スライス情報
	 * @return リクエスト結果
	 * @throws MloClientException 処理中に発生した異常
	 */
	SliceDto updateSliceInfo(SliceDto slice) throws MloClientException;
	
	/**
	 * スライス情報を削除します。
	 * @param slice スライス情報
	 * @return リクエスト結果
	 * @throws MloClientException 処理中に発生した異常
	 */
	SliceDto deleteSliceInfo(SliceDto slice) throws MloClientException;
	
}
