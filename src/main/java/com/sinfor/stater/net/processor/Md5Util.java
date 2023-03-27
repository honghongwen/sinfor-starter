package com.sinfor.stater.net.processor;

import com.sinfor.stater.net.util.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * md5加密单元
 * @author 李新周
 */
public class Md5Util {

	public static byte[] encodeMd5(final byte[] byteContent) {

		return Base64.encodeBase64(DigestUtils.md5(byteContent));


	}

	/**
	 * md5加密并且Base64
	 * 
	 * @param byteContent
	 * @return
	 * @throws Exception
	 */
	public static byte[] encodeMd5(final String content) throws Exception {
		byte[] bytesMd5 = DigestUtils.md5(StringUtil.getBytesUtf8(content));
		return Base64.encodeBase64(bytesMd5);
		


	}
}
