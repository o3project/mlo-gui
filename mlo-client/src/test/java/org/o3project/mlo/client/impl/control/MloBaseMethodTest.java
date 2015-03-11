package org.o3project.mlo.client.impl.control;

import static org.junit.Assert.*;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.RestifResponseDto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.o3project.mlo.client.control.MloSerdes;
import org.o3project.mlo.client.impl.control.ClientConfigImpl;
import org.o3project.mlo.client.impl.control.ClientConfigProviderImpl;
import org.o3project.mlo.client.impl.control.MloPostMethodImpl;

public class MloBaseMethodTest {

	private static final String DATA_PATH = "src/test/resources/org/o3project/mlo/client/control/data";
	private static final String DATA_FILE_001 = "client.config.001.properties";

	private MloPostMethodImpl obj;
	private MloSerdesStub mloSerdes = null;

	@Before
	public void setUp() throws Exception {
		File propFile = new File(DATA_PATH, DATA_FILE_001);
		ClientConfigProviderImpl configProvider = new ClientConfigProviderImpl(propFile.getAbsolutePath());
		ClientConfigImpl clientConfig = new ClientConfigImpl();
		clientConfig.setConfigProvider(configProvider);
		
		mloSerdes = new MloSerdesStub();
		
		obj = new MloPostMethodImpl();
		obj.setClientConfig(clientConfig);
		obj.setMloSerdes(mloSerdes);
	}

	@After
	public void tearDown() throws Exception {
		obj = null;
		mloSerdes = null;
	}

	@Test
	public void testHandleReqOutput() {
		RestifRequestDto reqDto = new RestifRequestDto();
		OutputStream ostream = null;
		obj.handleReqOutput(reqDto, ostream);
		assertEquals(Boolean.TRUE, mloSerdes.isCalled_serializeToXml);
		assertEquals(reqDto, mloSerdes.reqDto);
	}

	@Test
	public void testHandleResInput() {
		RestifResponseDto resDto = new RestifResponseDto();
		mloSerdes.resDto = resDto;
		InputStream istream = null;
		
		RestifResponseDto actual = obj.handleResInput(istream);
		assertEquals(resDto, actual);
	}

	@Test
	public void testGetConnectionTimeoutSec() {
		Integer expected = 123;
		Integer actual = obj.getConnectionTimeoutSec();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetReadTimeoutSec() {
		Integer expected = 456;
		Integer actual = obj.getReadTimeoutSec();
		assertEquals(expected, actual);
	}

	@Test
	public void testConstructUrl() {
		String path = "mySubPath";
		Map<String, String> paramMap = new LinkedHashMap<String, String>();
		paramMap.put("abc", "123");
		paramMap.put("def", "456");
		
		String expected = "http://myHost/myPath/mySubPath?abc=123&def=456";
		String actual = obj.constructUrl(path, paramMap);
		assertEquals(expected, actual);
	}

	@Test
	public void testConstructUrl_nullParamMap() {
		String path = "mySubPath";
		Map<String, String> paramMap = null;
		
		String expected = "http://myHost/myPath/mySubPath";
		String actual = obj.constructUrl(path, paramMap);
		assertEquals(expected, actual);
	}

	@Test
	public void testConstructUrl_zeroSizeParamMap() {
		String path = "mySubPath";
		Map<String, String> paramMap = new LinkedHashMap<String, String>();
		
		String expected = "http://myHost/myPath/mySubPath";
		String actual = obj.constructUrl(path, paramMap);
		assertEquals(expected, actual);
	}

}


class MloSerdesStub implements MloSerdes {
	
	public boolean isCalled_serializeToXml = false;
	
	public RestifRequestDto reqDto = null;
	
	public RestifResponseDto resDto = null;

	@Override
	public RestifResponseDto deserializeFromXml(InputStream istream) {
		return resDto;
	}

	@Override
	public void serializeToXml(RestifRequestDto reqDto, OutputStream ostream) {
		isCalled_serializeToXml = true;
		this.reqDto = reqDto;
	}
	
}
