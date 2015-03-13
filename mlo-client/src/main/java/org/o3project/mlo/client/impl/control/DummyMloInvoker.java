/**
 * DummyMloInvoker.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import javax.xml.bind.JAXB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.o3project.mlo.client.control.MloAccessException;
import org.o3project.mlo.client.control.MloInvoker;
import org.o3project.mlo.client.control.MloMethod;
import org.seasar.framework.util.ResourceUtil;

import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.RestifResponseDto;

/**
 * This class is the dummy class of mlo-srv connection.
 * This class is used only in debugging.
 * Even when mlo-srv is not launched, this class returns fake responses like that mlo-srv is launched.
 */
public class DummyMloInvoker implements MloInvoker {
	private static final Log LOG = LogFactory.getLog(DummyMloInvoker.class);

    private static final String PATH_LIST_SLICE = "slices";
    private static final String PATH_READ_SLICE = "READ";
    private static final String PATH_CREATE_SLICE = "CREATE";
    private static final String PATH_UPDATE_SLICE = "UPDATE";
    private static final String PATH_DELETE_SLICE = "DELETE";
	private static final String DATA_BASE_PATH = "org/o3project/mlo/client/control";
	
	private static final String LIST_XML = "dummyInvoker.list.res.xml";
	private static final String READ_XML = "dummyInvoker.read.res.xml";
	private static final String CREATE_XML = "dummyInvoker.create.res.xml";
	private static final String UPDATE_XML = "dummyInvoker.update.res.xml";
	private static final String DELETE_XML = "dummyInvoker.delete.res.xml";

	/*
	 * (non-Javadoc)
	 * @see org.o3project.mlo.client.control.MloInvoker#invoke(org.o3project.mlo.client.control.MloMethod, org.o3project.mlo.server.dto.RestifRequestDto, java.lang.String, java.util.Map)
	 */
	@Override
	public RestifResponseDto invoke(MloMethod method,
			RestifRequestDto reqDto, String path, Map<String, String> params)
			throws MloAccessException {
		
		RestifResponseDto resDto = null;
		
		if("POST".equals(method.getName())){
			if(PATH_READ_SLICE.equals(path)){
				// getSliceInfo response
				resDto = createDto(READ_XML);
			}else if(PATH_CREATE_SLICE.equals(path)){
				// createSliceInfo response.
				resDto = createDto(CREATE_XML);
			}else if(PATH_UPDATE_SLICE.equals(path)){
				// updateSliceInfo response.
				resDto = createDto(UPDATE_XML);
			}else if(PATH_DELETE_SLICE.equals(path)){
				// deleteSliceInfo response.
				resDto = createDto(DELETE_XML);
			}
		}else if ("GET".equals(method.getName())){
			if(PATH_LIST_SLICE.equals(path)){
				// getSliceList response.
				resDto = createDto(LIST_XML);
			}
		}
		
		return resDto;
	}
	
	/**
	 * Creates response DTO.
	 * @param xmlFileName the fake response xml file name.
	 * @return the response DTO. 
	 */
	private RestifResponseDto createDto(String xmlFileName) throws MloAccessException{
		RestifResponseDto resDto = null;
		InputStream istream = null;
		URL xmlFileUrl = null;
		try {
			xmlFileUrl = ResourceUtil.getResource(DATA_BASE_PATH + "/" + xmlFileName);
			istream = xmlFileUrl.openStream();
			resDto = JAXB.unmarshal(istream, RestifResponseDto.class);
		} catch (IOException e) {
			// Never pass here.
			LOG.error(e);
			throw new MloAccessException("templete response file is not found [file = " + xmlFileName +"]");
		} finally {
			if (istream != null) {
				try {
					istream.close();
				} catch (IOException e) {
					LOG.error(e);
					resDto = null;
				}
			}
		}
		
		return resDto;
	}
}
