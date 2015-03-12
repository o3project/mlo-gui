/**
 * MloViewController.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

import javafx.scene.Parent;

/**
 * This interface is the common interface for UI view controller.
 */
public interface MloViewController {

	/**
	 * Obtains parent instance which is root of view.
	 * @return the instance. 
	 */
	Parent getRoot();
	
}
