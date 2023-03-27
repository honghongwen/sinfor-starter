package com.sinfor.stater.net.encrypt;


import com.sinfor.stater.net.util.StringUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * ECB模式，PKCS5Padding填充，密码必须是16位 base64编码结果
 * @author 李新周
 */
public class AesEncrypt implements Encrypt {
	/**
	 * 加密String
	 * 
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@Override
	public byte[] encrypt(final String content,final String key) throws Exception {
		return encrypt(StringUtil.getBytesUtf8(content), key);
	}

	/**
	 * 加密Bytes
	 * 
	 * @param byteData
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@Override
	public byte[] encrypt(final byte[] byteData,final String key) throws Exception {
		if (byteData == null || key == null) {
			return null;
		}
		final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(StringUtil.getBytesUtf8(key), "AES"));
		return cipher.doFinal(byteData);
	}

	/**
	 * 解密String
	 * 
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@Override
	public byte[] decrypt(final String content, final String key) throws Exception {

		return decrypt(StringUtil.getBytesUtf8(content), key);
	}

	/**
	 * 解密Bytes
	 * 
	 * @param byteData
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@Override
	public byte[] decrypt(final byte[] byteData,final String key) throws Exception {
		if (byteData == null || key == null) {
			return null;
		}
		final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(StringUtil.getBytesUtf8(key), "AES"));
		return  cipher.doFinal(byteData);
	}


}
