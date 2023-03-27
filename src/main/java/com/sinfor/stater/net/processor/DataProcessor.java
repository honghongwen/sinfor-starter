package com.sinfor.stater.net.processor;

import com.sinfor.stater.net.encrypt.AesEncrypt;
import com.sinfor.stater.net.encrypt.Encrypt;
import com.sinfor.stater.net.compress.Compress;
import com.sinfor.stater.net.compress.GzipCompress;
import com.sinfor.stater.net.domain.Response;
import com.sinfor.stater.net.util.*;
import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * 数据处理
 *
 * <pre>
 * 发送：压缩->Aes加密->Base64
 * 接收：Base64->Aes解密->解压
 * </pre>
 *
 * @author 李新周
 */
@Data
public class DataProcessor {

    private static Compress compress = new GzipCompress();
    private static Encrypt encrypt = new AesEncrypt();
    private static String encryptKey = "ABCD12344321DBCA";
    private static long enabledPackageSize = 3;
    private static boolean useEncryptResponse = false;

    /**
     * 信丰数据加密传输
     *
     * @param text
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String text, boolean useEncrypt, boolean useCompress, boolean useBase64) throws Exception {
        if (text == null) {
            return null;
        }
        return encrypt(StringUtil.getBytesUtf8(text), useEncrypt, useCompress, useBase64);
    }

    public static byte[] encrypt(byte[] bytesData, boolean useEncrypt, boolean useCompress, boolean useBase64) throws Exception {
        if (bytesData == null) {
            return null;
        }
        if (useCompress) {
            bytesData = compress.compress(bytesData);
        }
        if (useEncrypt) {
            bytesData = encrypt.encrypt(bytesData, encryptKey);
        }
		if (useBase64){
			return null;
		}
        else {
            return bytesData;
        }
    }

    /**
     * 信丰数据解密传输
     *
     * @param bytesData
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] bytesData, boolean useDecrypt, boolean useCompress, boolean useBase64) throws Exception {
		if (useBase64){
			bytesData = null;
		}
        if (useDecrypt) {
            bytesData = encrypt.decrypt(bytesData, encryptKey);
        }
        if (useCompress) {
            return compress.uncompress(bytesData);
        } else {
            return bytesData;
        }

    }

    /**
     * 编码响应
     *
     * @param response
     * @throws Exception
     */
    public static void encodeResponse(Response response, boolean useBase64) throws Exception {
        byte[] bodyBytes;
        /**
         * 如果isCompress为true表示数据已压缩过了
         */
        if (response.isCompress()) {
            bodyBytes = objectToBytes(response.getData());
            response.setCompress(true);
            /**
             * 已压缩过;不需再压缩
             */
            bodyBytes = encrypt(bodyBytes, useEncryptResponse, false, useBase64);
        } else {
            bodyBytes = objectToBytes(response.getData());
            boolean compressFlag = (bodyBytes != null) && (bodyBytes.length > 1024 * enabledPackageSize);
            response.setCompress(compressFlag);
            bodyBytes = encrypt(bodyBytes, useEncryptResponse, response.isCompress(), useBase64);
        }

        response.setData(bodyBytes);
        response.setSign(getResponseSign(response));

    }

    /**
     * 生成Response的签名
     *
     * @param response
     * @return
     * @throws Exception
     */
    private static byte[] getResponseSign(Response response) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write(ByteUtil.shortToByteArray(response.getSequence()));

            baos.write(ByteUtil.intToByteArray(response.getModule()));

            baos.write(ByteUtil.shortToByteArray(response.getCmd()));

            baos.write(ByteUtil.intToByteArray(response.getStateCode()));
            byte[] bodyBytes = (byte[]) response.getData();
            if (bodyBytes != null) {
                baos.write(bodyBytes);
            }
            byte[] bytesSign = Md5Util.encodeMd5(baos.toByteArray());
            return bytesSign;
        } finally {
            baos.close();
        }

    }


    /**
     * Object转bytes
     *
     * @param source
     * @return
     * @throws Exception
     */
    public static byte[] objectToBytes(Object source) {
        if (source == null) {
            return null;
        }
        String bodyText;
        byte[] bodyBytes;
        if (source instanceof String) {
            bodyText = (String) source;
            bodyBytes = StringUtil.getBytesUtf8(bodyText);
        } else if (source instanceof List) {
            bodyBytes = JsonUtil.objectToBytes(source);
        } else if (source instanceof byte[]) {
            bodyBytes = (byte[]) source;
        } else {
            bodyBytes = JsonUtil.objectToBytes(source);
        }
        return bodyBytes;
    }


}
