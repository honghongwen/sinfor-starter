package com.sinfor.stater.net.util;


import java.nio.charset.Charset;

/**
 * 字符处理工具类 更多功能见:org.apache.commons.lang3.StringUtils
 * @author 李新周
 */
public class StringUtil {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private static byte[] getBytes(final String content, final Charset charset) {
		if (content == null) {
			return null;
		}
		return content.getBytes(charset);
	}

	public static byte[] getBytesUtf8(final String content) {
		return getBytes(content, DEFAULT_CHARSET);
	}

}
