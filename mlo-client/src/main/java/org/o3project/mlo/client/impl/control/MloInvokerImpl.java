/**
 * MloInvokerImpl.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.o3project.mlo.client.control.MloAccessException;
import org.o3project.mlo.client.control.MloInvoker;
import org.o3project.mlo.client.control.MloMethod;

import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.RestifResponseDto;

/**
 * {@link MloInvoker} インタフェースの実装クラスです。
 *
 */
public class MloInvokerImpl implements MloInvoker {
	private static final Log LOG = LogFactory.getLog(MloInvokerImpl.class);
	
	private static final int ONE_SECOND = 1000;
	
	private static final int SC_400_BAD_REQUEST = 400;

	/*
	 * (non-Javadoc)
	 * @see org.o3project.mlo.client.control.MloInvoker#invoke(org.o3project.mlo.client.control.MloMethod, org.o3project.mlo.server.dto.RestifRequestDto, java.lang.String, java.util.Map)
	 */
	@Override
	public RestifResponseDto invoke(MloMethod method, 
			RestifRequestDto reqDto, String path, Map<String, String> params) 
					throws MloAccessException {
		RestifResponseDto resDto = null;
		try {
			LOG.info("invoke() starts");
			resDto = invokeInternal(method, reqDto, path, params);
		} catch (IOException e) {
			LOG.debug("Exception occurs.", e);
			throw new MloAccessException("MLO-SERVER cannot be connected:" + e.getMessage(), e);
		} finally {
			LOG.info("invoke() ends.");
		}
		return resDto;
	}
	
	/**
	 * 実行処理の内部実装です。
	 * @param method メソッドインスタンス
	 * @param reqDto リクエストインスタンス
	 * @param path URL パス
	 * @param paramMap クエリパラメータ
	 * @return レスポンスインスタンス
	 * @throws IOException IO 例外
	 * @throws MloException API 異常例外
	 */
	private RestifResponseDto invokeInternal(MloMethod method, 
			RestifRequestDto reqDto, String path, Map<String, String> paramMap) 
					throws IOException, MloAccessException {
		RestifResponseDto resDto = null;
		
		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(method.constructUrl(path, paramMap));
			LOG.info("Invoking to ... " + url);
			conn = (HttpURLConnection) url.openConnection();
			if (method.getConnectionTimeoutSec() != null) {
				conn.setConnectTimeout(method.getConnectionTimeoutSec() * ONE_SECOND);				
			}
			if (method.getReadTimeoutSec() != null) {
				conn.setReadTimeout(method.getReadTimeoutSec() * ONE_SECOND);				
			}
			conn.setRequestMethod(method.getName());
			
			boolean isHandleOut = method.isSetDoOutput();
			conn.setDoOutput(isHandleOut);
			if (isHandleOut) {
				conn.setRequestProperty("Content-Type", "application/xml; charset=UTF-8");
				try(OutputStream ostream = conn.getOutputStream()) {
					method.handleReqOutput(reqDto, ostream);
				}
			}
			
			int statusCode = conn.getResponseCode();
			
			try (InputStream istream = conn.getInputStream()) {
				resDto = method.handleResInput(istream);
			}
			
			LOG.info("statusCode = " + statusCode);
			if (statusCode >= SC_400_BAD_REQUEST) {
				throw new MloAccessException("MloServerAccessError/" + statusCode);
			}
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		
		return resDto;
	}
}
