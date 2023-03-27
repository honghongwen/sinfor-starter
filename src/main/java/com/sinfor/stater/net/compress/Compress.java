package com.sinfor.stater.net.compress;
/**
 * 压缩处理
 * @author 李新周
 */
public interface Compress {
	  /**
	   * 压缩
	   * @param data
	   * @return
	   * @throws Exception
	   */
	  byte[] compress(byte[] data) throws Exception;
	  /**
	   * 解压缩
	   * @param data
	   * @return
	   * @throws Exception
	   */
	  byte[] uncompress(byte[] data) throws Exception;
}
