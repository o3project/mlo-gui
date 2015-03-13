/**
 * MloBaseMethod.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.RestifResponseDto;

import org.o3project.mlo.client.control.ClientConfig;
import org.o3project.mlo.client.control.MloMethod;
import org.o3project.mlo.client.control.MloSerdes;
import org.seasar.framework.container.annotation.tiger.Binding;

/**
 * This class is the abstract base class of HTTP method in connecting to mlo-srv.
 */
public abstract class MloBaseMethod implements MloMethod {

    @Binding
    private MloSerdes mloSerdes;

    @Binding
    private ClientConfig clientConfig;

    /**
     * Setter method (for DI setter injection).
     * @param mloSerdes the instance. 
     */
    public void setMloSerdes(MloSerdes mloSerdes) {
        this.mloSerdes = mloSerdes;
    }
    
    /**
     * Setter method (for DI setter injection).
     * @param clientConfig the instance.
     */
    public void setClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    /* (non-Javadoc)
     * @see org.o3project.mlo.client.control.MloMethod#handleReqOutput(org.o3project.mlo.server.dto.RestifRequestDto, java.io.OutputStream)
     */
    @Override
    public void handleReqOutput(RestifRequestDto reqDto, OutputStream ostream) {
        mloSerdes.serializeToXml(reqDto, ostream);
    }

    /* (non-Javadoc)
     * @see org.o3project.mlo.client.control.MloMethod#handleResInput(java.io.InputStream)
     */
    @Override
    public RestifResponseDto handleResInput(InputStream istream) {
        return mloSerdes.deserializeFromXml(istream);
    }

    /* (non-Javadoc)
     * @see org.o3project.mlo.client.control.MloMethod#getConnectionTimeoutSec()
     */
    @Override
    public Integer getConnectionTimeoutSec() {
        return clientConfig.getConnectionTimeoutSec();
    }

    /* (non-Javadoc)
     * @see org.o3project.mlo.client.control.MloMethod#getReadTimeoutSec()
     */
    @Override
    public Integer getReadTimeoutSec() {
        return clientConfig.getReadTimeoutSec();
    }

    /* (non-Javadoc)
     * @see org.o3project.mlo.client.control.MloMethod#constructUrl(java.lang.String, java.util.Map)
     */
    @Override
    public String constructUrl(String path, Map<String, String> paramMap) {
        String sUrl = String.format("%s/%s", clientConfig.getServerBaseUri(), path);
        if (paramMap != null && paramMap.size() > 0) {
            List<String> paramList = new ArrayList<String>();
            for (Entry<String, String> entry : paramMap.entrySet()) {
                paramList.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
            }
            sUrl = sUrl + "?" + join(paramList);
        }
        return sUrl;
    }

    private static String join(List<String> paramList) {
        StringBuffer sb = new StringBuffer();
        for (int idx = 0; idx < paramList.size(); idx += 1) {
            if (idx != 0) {
                sb.append("&");
            }
            sb.append(paramList.get(idx));
        }
        return sb.toString();
    }
}

