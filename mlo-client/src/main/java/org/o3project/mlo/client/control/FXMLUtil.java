/**
 * FXMLUtil.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javafx.fxml.FXMLLoader;

/**
 * FXML 用ユーティリティクラスです。
 *
 */
public final class FXMLUtil {
	private static final Log LOG = LogFactory.getLog(FXMLUtil.class);

	/**
	 * コンストラクタ 
	 */
	private FXMLUtil() {
		super();
	}
	
	/**
	 * FXML ファイルをロードしてコントローラインスタンスを取得します。
	 * 指定されたファイル名の FXML ファイルをロードし、そのコントローラインスタンスを返します。
	 * FXML ファイルは、コントローラクラスのクラスファイルと同一パスに存在する必要があります。
	 * @param controllerType 取得するコントローラの型。FXML で指定された型と同一である必要があります。
	 * @param fxmlFileName FXML ファイル名
	 * @return コントローラインスタンス
	 */
	public static <T> T createController(Class<T> controllerType, String fxmlFileName) {
		T obj = null;
		try {
			FXMLLoader loader = new FXMLLoader(controllerType.getResource(fxmlFileName));
			loader.load();
			obj = loader.getController();
		} catch (IOException e) {
			LOG.debug(e);
			throw new RuntimeException(e);
		}
		return obj;
	}

}
