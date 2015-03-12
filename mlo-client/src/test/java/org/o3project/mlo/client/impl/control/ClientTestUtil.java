/**
 * ClientTestUtil.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.RestifResponseDto;

import org.o3project.mlo.client.control.MloSerdes;
import org.w3c.dom.Document;

/**
 * ClientTestUtil
 *
 */
public class ClientTestUtil {

    /**
    * XML ファイルからリクエスト DTO を読み込む。
    * @param serdes {@link MloSerdes} インスタンス
    * @param dirName xml ファイルのディレクトリ
    * @param xmlFileName xml ファイルのファイル名
    * @return リクエスト DTO
    * @throws Throwable 読み込み中の異常例外
    */
    public static RestifResponseDto readResFromXml(MloSerdes serdes, String dirName, String xmlFileName) throws Throwable {
        File xmlfile = new File(dirName, xmlFileName);
        InputStream istream = null;
        RestifResponseDto resDto = null;
        try {
            istream = new FileInputStream(xmlfile);
            resDto = serdes.deserializeFromXml(istream);
        } finally {
            if (istream != null) {
                istream.close();
            }
        }
        return resDto;
    }

    /**
     * XML ファイルからリクエスト DTO を読み込む。
     * @param serdes {@link MloSerdes} インスタンス
     * @param dirName xml ファイルのディレクトリ
     * @param xmlFileName xml ファイルのファイル名
     * @return リクエスト DTO
     * @throws Throwable 読み込み中の異常例外
     */
    public static RestifRequestDto readReqFromXml(MloSerdes serdes, String dirName, String xmlFileName) throws Throwable {
        File xmlfile = new File(dirName, xmlFileName);
        InputStream istream = null;
        RestifRequestDto reqDto = null;
        try {
            istream = new FileInputStream(xmlfile);
            reqDto = JAXB.unmarshal(istream, RestifRequestDto.class);
        } finally {
            if (istream != null) {
                istream.close();
            }
        }
        return reqDto;
    }

    /**
     * リクエスト DTO の内容が XML ファイルと同じかどうか。
     * @param serdes {@link MloSerdes} インスタンス
     * @param reqDto {@link RestifRequestDto} インスタンス
     * @param dirName xml ファイルのディレクトリ
     * @param xmlFileName xml ファイルのファイル名
     * @return 同じか否か。同じならば true を返す。
     * @throws Throwable 処理中の異常例外
     */
    public static boolean isSameXmlAs(MloSerdes serdes, RestifRequestDto reqDto, String dirName, String xmlFileName) throws Throwable {
        String resXml = null;
        ByteArrayOutputStream ostream = null;
        try {
            ostream = new ByteArrayOutputStream();
            serdes.serializeToXml(reqDto, ostream);
            resXml = new String(ostream.toByteArray(), "UTF-8");
        } finally {
            if (ostream != null) {
                ostream.close();
            }
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setCoalescing(true);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setIgnoringComments(true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream istream1 = null;
        InputStream istream2 = null;
        Document doc1 = null;
        Document doc2 = null;

        try {
            istream1 = new ByteArrayInputStream(resXml.getBytes("UTF-8"));
            doc1 = db.parse(istream1);
            doc1.normalizeDocument();

            istream2 = new FileInputStream(new File(dirName, xmlFileName));
            doc2 = db.parse(istream2);
            doc2.normalizeDocument();
        } finally {
            if (istream1 != null) {
                istream1.close();
            }
            if (istream2 != null) {
                istream2.close();
            }
        }

        return doc1.isEqualNode(doc2);
    }
    

    /**
     * リクエスト DTO の内容が XML ファイルと同じかどうか。
     * @param reqDto {@link RestifResponseDto} インスタンス
     * @param dirName xml ファイルのディレクトリ
     * @param xmlFileName xml ファイルのファイル名
     * @return 同じか否か。同じならば true を返す。
     * @throws Throwable 処理中の異常例外
     */
    public static boolean isSameXmlAs(RestifResponseDto resDto, String dirName, String xmlFileName) throws Throwable {
        String resXml = null;
        ByteArrayOutputStream ostream = null;
        try {
            ostream = new ByteArrayOutputStream();
            JAXB.marshal(resDto, ostream);
            resXml = new String(ostream.toByteArray(), "UTF-8");
        } finally {
            if (ostream != null) {
                ostream.close();
            }
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setCoalescing(true);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setIgnoringComments(true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream istream1 = null;
        InputStream istream2 = null;
        Document doc1 = null;
        Document doc2 = null;

        try {
            istream1 = new ByteArrayInputStream(resXml.getBytes("UTF-8"));
            doc1 = db.parse(istream1);
            doc1.normalizeDocument();

            istream2 = new FileInputStream(new File(dirName, xmlFileName));
            doc2 = db.parse(istream2);
            doc2.normalizeDocument();
        } finally {
            if (istream1 != null) {
                istream1.close();
            }
            if (istream2 != null) {
                istream2.close();
            }
        }

        return doc1.isEqualNode(doc2);
    }
}

