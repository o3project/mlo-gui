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
 * This class is utility class for FXML.
 */
public final class FXMLUtil {
	private static final Log LOG = LogFactory.getLog(FXMLUtil.class);

	/**
	 * A constructor.
	 */
	private FXMLUtil() {
		super();
	}
	
	/**
	 * Loads specified FXML file, and then creates UI controller instance.
	 * FXML file must located at the same directory as UI controller class file.
	 * @param controllerType the class of UI controller. This class must be specified in FXML file.
	 * @param fxmlFileName the FXML file name.
	 * @return the UI controller instance.
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
