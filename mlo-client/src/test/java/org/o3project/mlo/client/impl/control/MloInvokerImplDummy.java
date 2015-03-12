/**
 * MloInvokerImplDummy.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.io.IOException;
import java.util.Map;

import org.o3project.mlo.client.control.MloAccessException;
import org.o3project.mlo.client.control.MloInvoker;
import org.o3project.mlo.client.control.MloMethod;
import org.o3project.mlo.client.impl.control.MloSerdesImpl;

import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.RestifResponseDto;
import org.o3project.mlo.server.logic.MloException;

/**
 * {@link MloInvoker} インタフェースの実装クラスです。
 *
 */
public class MloInvokerImplDummy implements MloInvoker {

    private static final String DATA_PATH = "src/test/resources/org/o3project/mlo/client/control/data/base";

    private final MloSerdesImpl serdes = new MloSerdesImpl();

    private String xmlName;

    /*
     * (non-Javadoc)
     * @see org.o3project.mlo.client.control.MloInvoker#invoke(org.o3project.mlo.client.control.MloMethod, org.o3project.mlo.client.control.RestifRequestDto, java.lang.String, java.util.Map)
     */
    @Override
    public RestifResponseDto invoke(MloMethod method,
    		RestifRequestDto reqDto, String path, Map<String, String> params)
                    throws MloAccessException {
        RestifResponseDto resDto = null;
        try {
            resDto = invokeInternal(method, reqDto, path, params);
        } catch (IOException e) {
            throw new MloAccessException("", e);
        } finally {
            ;
        }
        return resDto;
    }

    /**
     * 実行処理の内部実装です。
     * @param method メソッドインスタンス
     * @param reqDto リクエストインスタンス
     * @param path URL パス
     * @param paramMap クエリパラメタ
     * @return レスポンスインスタンス
     * @throws IOException IO 例外
     * @throws MloException API 異常例外
     */
    private RestifResponseDto invokeInternal(MloMethod method,
    		RestifRequestDto reqDto, String path, Map<String, String> paramMap)
                    throws IOException, MloAccessException {

        RestifResponseDto resDto = null;
        try {
            resDto = ClientTestUtil.readResFromXml(serdes, DATA_PATH, xmlName);
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resDto;
    }

    // 期待レスポンスXML指定
    public void setXmlName(String xmlName) {
        this.xmlName = xmlName;
    }

}
