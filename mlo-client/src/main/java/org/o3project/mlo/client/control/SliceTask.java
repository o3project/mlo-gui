/**
 * SliceTask.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.o3project.mlo.client.impl.control.MloClient;

import javafx.concurrent.Task;
import org.o3project.mlo.server.dto.SliceDto;

/**
 * MLO へアクセスしスライスの取得や操作を行うタスクの基底クラスです。
 * このクラスのサブクラスでは {@link SliceDataManager} のメソッドを呼び出し、
 * MLO にリクエストを投げてレスポンスを取得する処理を記述します。</BR>
 * このクラスのサブクラスでは、ビューに関する処理を記述すべきではありません。
 * ビューに関する処理を記述しても、JavaFX スレッドとスレッド競合を起こします。
 * ビューに関する処理は、setOnSucceeded() メソッドなどに記述すべきです。
 */
public abstract class SliceTask extends Task<Void> {
	
	/**
	 * ログ
	 */
	protected static final Log LOG = LogFactory.getLog(SliceTask.class);
	
	private final MloClient mloClient;
	
	private List<SliceDto> sliceDtoList;
	
	private MloClientException error;

	/**
	 * コンストラクタ
	 * @param mloClient MLO クライアントインスタンス
	 */
	protected SliceTask(MloClient mloClient) {
		this.mloClient = mloClient;
	}
	
	/**
	 * {@link MloClient} インスタンスを取得します。
	 * @return インスタンス
	 */
	public final MloClient getMloClient() {
		return mloClient;
	}
	
	/**
	 * スライス取得または操作結果を取得します。
	 * @return 結果インスタンスリスト
	 */
	public final List<SliceDto> getSliceDtoList() {
		return sliceDtoList;
	}
	
	/**
	 * スライス取得または操作結果を設定します。
	 * @param sliceDtoList 結果インスタンスリスト
	 */
	protected final void setSliceDtoList(List<SliceDto> sliceDtoList) {
		this.sliceDtoList = sliceDtoList;
	}
	
	/**
	 * スライス取得または操作時のエラーを取得します。
	 * @return エラーインスタンス
	 */
	public final MloClientException getError() {
		return error;
	}
	
	/**
	 * スライス取得または操作時のエラーを設定します。
	 * @param error エラーインスタンス
	 */
	protected final void setError(MloClientException error) {
		this.error = error;
	}
}
