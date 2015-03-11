/**
 * SliceMultiRequest.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * 複数のスライス操作を CLI でリクエストするためのインタフェースです。
 */
public interface SliceMultiRequest {
	/**
	 * 引数で指定された数だけスライスを作成します。
	 * @param sliceDataManager スライスデータマネージャ
	 * @param nSlice スライス数
	 * @param nFlowForSlice 1 スライス内のフロー数
	 * @param bandWidth 使用帯域幅
	 * @param latency 遅延時間
	 * @throws InterruptedException 割り込み例外
	 */
	void doCreateSlices(SliceDataManager sliceDataManager, Integer nSlice, Integer nFlowForSlice, Integer bandWidth, Integer latency) throws InterruptedException;
	
	/**
	 * すべてのスライスを更新します。
	 * @param sliceDataManager スライスデータマネージャ
	 * @param bandWidth 使用帯域幅
	 * @param latency 遅延時間
	 * @throws InterruptedException 割り込み例外
	 */
	void doUpdateAllSlices(SliceDataManager sliceDataManager, Integer bandWidth, Integer latency) throws InterruptedException;
	
	/**
	 * すべてのスライスを削除します。
	 * @param sliceDataManager スライスデータマネージャ
	 * @throws InterruptedException 割り込み例外
	 */
	void doDeleteAllSlices(SliceDataManager sliceDataManager) throws InterruptedException;
}
