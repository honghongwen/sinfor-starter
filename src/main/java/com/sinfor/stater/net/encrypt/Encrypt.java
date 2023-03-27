package com.sinfor.stater.net.encrypt;
/**
 * 加密处理
 * @author 李新周
 */
public interface Encrypt {
	/**
	 * 加密String
	 * 
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(String content, String key) throws Exception;
	/**
	 * 加密Bytes
	 * 
	 * @param byteData
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] byteData, String key) throws Exception;
	/**
	 * 解密String
	 * 
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public byte[] decrypt(String content, String key) throws Exception;

	/**
	 * 解密Bytes
	 * 
	 * @param byteData
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] byteData, String key) throws Exception;

}