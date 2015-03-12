/**
 * MloClientBuilder.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.lang.reflect.Field;
import java.util.concurrent.Executors;

import org.o3project.mlo.client.control.FXMLUtil;
import org.o3project.mlo.client.impl.control.MloClient;
import org.o3project.mlo.client.impl.control.MloCreateViewController;
import org.o3project.mlo.client.impl.control.MloInfoViewController;
import org.o3project.mlo.client.impl.control.MloUpdateViewController;
import org.o3project.mlo.client.impl.control.ResultDialogboxControllerImpl;
import org.o3project.mlo.client.impl.control.SliceDataManagerImpl;
import org.o3project.mlo.client.impl.control.SliceTaskFactoryImpl;

import javafx.stage.Stage;

/**
 * MloClientBuilder
 *
 */
public class MloClientBuilder {
	
	/**
	 * 
	 */
	private MloClientBuilder() {
		super();
	}
	
	public static MloClient builSimpleInstance(Stage primaryStage) throws Exception {
		MloClient obj = new MloClient();
		obj.start(primaryStage);
		return obj;
	}
	
	public static MloClient buildInstance() throws Exception {
		MloClient obj = new MloClient();
		
		setPrivateStaticField(MloClient.class, "instance", obj);
		
		setPrivateField(obj, "sliceDataManager", new SliceDataManagerImpl());
		
		obj.setExecutorService(Executors.newSingleThreadExecutor());
		
		obj.setSliceTaskFactory(new SliceTaskFactoryImpl(obj));
		
		Stage stage = new Stage();
		stage.setTitle("MLO Client");
		stage.setResizable(false);
		setPrivateField(obj, "stage", stage);
		
		obj.infoViewCtrl = new MloInfoViewController();
		obj.updateViewCtrl = new MloUpdateViewController();
		obj.createViewCtrl = new MloCreateViewController();
		
		setPrivateField(obj, "indicatorStage", MloClient.createIndicatorStage());
		
		setPrivateField(obj, "resultDlgCtrl", FXMLUtil.createController(ResultDialogboxControllerImpl.class, "resultDialogbox.fxml"));
		
		return obj;
	}
	
    public static void setPrivateField(Object obj, String fieldName, Object fieldValue) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, fieldValue);
    }
	
    public static void setPrivateStaticField(Class<?> clazz, String fieldName, Object fieldValue) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(clazz, fieldValue);
    }
}
