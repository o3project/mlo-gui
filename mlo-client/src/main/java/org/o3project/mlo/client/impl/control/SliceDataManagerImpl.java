/**
 * SliceDataManagerImpl.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.o3project.mlo.client.control.ClientConfig;
import org.o3project.mlo.client.control.MloClientException;
import org.o3project.mlo.client.control.MloInvoker;
import org.o3project.mlo.client.control.MloMethod;
import org.o3project.mlo.client.control.MloNbiException;
import org.o3project.mlo.client.control.SliceDataManager;
import org.seasar.framework.container.annotation.tiger.Aspect;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.annotation.tiger.InitMethod;

import org.o3project.mlo.server.dto.RestifCommonDto;
import org.o3project.mlo.server.dto.RestifComponentDto;
import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.RestifResponseDto;
import org.o3project.mlo.server.dto.SliceDto;

/**
 * This class is the implementation class of {@link SliceDataManager} interface.
 */
@Aspect("traceInterceptor")
public class SliceDataManagerImpl implements SliceDataManager {

    private static final Log LOG = LogFactory.getLog(SliceDataManagerImpl.class);

    private static final String PATH_LIST_SLICE = "slices";
    private static final String PATH_READ_SLICE = "READ";
    private static final String PATH_CREATE_SLICE = "CREATE";
    private static final String PATH_UPDATE_SLICE = "UPDATE";
    private static final String PATH_DELETE_SLICE = "DELETE";

    private static final Integer INTERFACE_VERSION = 1;
    private static final String DEST_COMPONENT_NAME = "mlo";
    private static final String REQUEST_OPERATION = "Request";

    private String srcComponentName;
    
    @Binding
    private MloInvoker actualInvoker;
    
    @Binding
    private MloInvoker dummyInvoker;
    
    @Binding(bindingType=BindingType.NONE)
    private MloInvoker mloInvoker;
    
    @Binding
    private MloMethod mloGetMethod;
    
    @Binding
    private MloMethod mloPostMethod;
    
    @Binding
    private ClientConfig clientConfig;

    /**
     * Setter method (for DI setter injection).
     * @param mloInvoker the instance.
     */
	public void setMloInvoker(MloInvoker mloInvoker) {
		this.mloInvoker = mloInvoker;
	}

    /**
     * Setter method (for DI setter injection).
     * @param mloGetMethod the instance.
     */
	public void setMloGetMethod(MloMethod mloGetMethod) {
		this.mloGetMethod = mloGetMethod;
	}

    /**
     * Setter method (for DI setter injection).
     * @param mloPostMethod the instance.
     */
	public void setMloPostMethod(MloMethod mloPostMethod) {
		this.mloPostMethod = mloPostMethod;
	}

    /**
     * Setter method (for DI setter injection).
     * @param clientConfig the ClientConfig to set
     */
	public void setClientConfig(ClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

    /**
     * Initializes this component.
     */
    @InitMethod
    public void init(){
        if(clientConfig.getDummyInvokerSetFlag()){
            this.mloInvoker = dummyInvoker;
            LOG.info("\n dummyInvoker set");
        }else{
            this.mloInvoker = actualInvoker;
            LOG.info("\n trueInvoker set");
        }
        srcComponentName = clientConfig.getSrcComponentName();
    }

	/* (non Javadoc)
	 * @see org.o3project.mlo.client.control.SliceDataManager#getSliceList()
	 */
	@Override
	public List<SliceDto> getSliceList() throws MloClientException {

        Map<String, String> params = new HashMap<String, String>();
        params.put("owner", srcComponentName);
        
        LOG.debug("########## : owner=" + params.get("owner"));
        RestifResponseDto resDto = mloInvoker.invoke(mloGetMethod, null, PATH_LIST_SLICE, params);
		if (null != resDto.error) {
			throw new MloNbiException("An error response from the MLO-SERVER : getSliceList", resDto.error);
		}
		return resDto.slices;
	}

	/* (non Javadoc)
	 * @see org.o3project.mlo.client.control.SliceDataManager#getSliceInfo(org.o3project.mlo.server.dto.SliceDto)
	 */
	@Override
	public SliceDto getSliceInfo(SliceDto slice) throws MloClientException {

		LOG.debug("##########  SliceId=" + slice.id + ", SliceName=" + slice.name);
        RestifRequestDto reqDto = createRequestDto(slice);
        RestifResponseDto resDto = mloInvoker.invoke(mloPostMethod, reqDto, PATH_READ_SLICE, null);
		if (null != resDto.error) {
			throw new MloNbiException("An error response from the MLO-SERVER : getSliceInfo", resDto.error);
		}
		return resDto.slices.get(0);
	}

	/* (non Javadoc)
	 * @see org.o3project.mlo.client.control.SliceDataManager#createSliceInfo(org.o3project.mlo.server.dto.SliceDto)
	 */
	@Override
	public SliceDto createSliceInfo(SliceDto slice) throws MloClientException {

		LOG.debug("##########  SliceId=" + slice.id + ", SliceName=" + slice.name);
        Map<String, String> params = null;
        RestifRequestDto reqDto = createRequestDto(slice);
        RestifResponseDto resDto = mloInvoker.invoke(mloPostMethod, reqDto, PATH_CREATE_SLICE, params);
		if (null != resDto.error) {
			throw new MloNbiException("An error response from the MLO-SERVER : createSliceInfo", resDto.error);
		}
		return resDto.slices.get(0);
	}

	/* (non Javadoc)
	 * @see org.o3project.mlo.client.control.SliceDataManager#updateSliceInfo(org.o3project.mlo.server.dto.SliceDto)
	 */
	@Override
	public SliceDto updateSliceInfo(SliceDto slice) throws MloClientException {

		LOG.debug("##########  SliceId=" + slice.id + ", SliceName=" + slice.name);
        RestifRequestDto reqDto = createRequestDto(slice);
        RestifResponseDto resDto = mloInvoker.invoke(mloPostMethod, reqDto, PATH_UPDATE_SLICE, null);
		if (null != resDto.error) {
			throw new MloNbiException("An error response from the MLO-SERVER : updateSliceInfo", resDto.error);
		}
		return resDto.slices.get(0);
	}

	/* (non Javadoc)
	 * @see org.o3project.mlo.client.control.SliceDataManager#deleteSliceInfo(org.o3project.mlo.server.dto.SliceDto)
	 */
	@Override
	public SliceDto deleteSliceInfo(SliceDto slice) throws MloClientException {

		LOG.debug("##########  SliceId=" + slice.id + ", SliceName=" + slice.name);
        RestifRequestDto reqDto = createRequestDto(slice);
        RestifResponseDto resDto = mloInvoker.invoke(mloPostMethod, reqDto, PATH_DELETE_SLICE, null);
		if (null != resDto.error) {
			throw new MloNbiException("An error response from the MLO-SERVER : deleteSliceInfo", resDto.error);
		}
		return resDto.slices.get(0);
	}

	/**
	 * Creates the request DTO.
	 * @param slice the slice DTO.
	 * @return the request DTO.
	 */
	private RestifRequestDto createRequestDto(SliceDto slice) {

		RestifComponentDto srcComponent = new RestifComponentDto();
		srcComponent.name = srcComponentName;
		RestifComponentDto dstComponent = new RestifComponentDto();
		dstComponent.name = DEST_COMPONENT_NAME;

		RestifCommonDto common = new RestifCommonDto();
		common.version = INTERFACE_VERSION;
		common.srcComponent = srcComponent;
		common.dstComponent = dstComponent;
		common.operation = REQUEST_OPERATION;

		RestifRequestDto request = new RestifRequestDto();
		request.common = common;
		request.slice = slice;

		return request;
	}
	
}
