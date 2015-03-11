/**
 * MloViewController.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import javafx.scene.Parent;

/**
 * ビューコントローラの共通インタフェースです。
 */
public interface MloViewController {

	/**
	 * ビューペアレントを取得します。
	 * @return ビューペアレント
	 */
	Parent getRoot();
	
}
