/**
 * SimpleViewController.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import org.o3project.mlo.server.dto.SliceDto;

/**
 * 簡易表示ビューの制御インタフェースです。
 */
public interface SimpleViewController{

	/**
	 * 簡易表示ビューを表示します。
	 * @param sliceDto スライス情報
	 */
	void showDialog(SliceDto sliceDto);
	
	/**
	 * 簡易表示更新用のスライス情報を取得します。
	 * @return 更新用スライス情報
	 */
	SliceDto getData();
}
