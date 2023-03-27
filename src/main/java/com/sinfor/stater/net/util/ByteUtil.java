package com.sinfor.stater.net.util;

/**
 * Bytes转换单元
 * @author 李新周
 */
public class ByteUtil {
	/**
	 * boolean转byte
	 * @param boolFlag
	 * @return
	 */
	public static byte booleanToByte(final boolean boolFlag) {
		return (byte) (boolFlag ? 0x01 : 0x00);
	}
	/**
	 * byte转boolean
	 * @param byteContent
	 * @return
	 */
	public static boolean byteToBoolean(final byte byteContent) {

		return (byteContent == 0x00) ? false : true;

	}

	/**
	 * short转Bytes
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] shortToByteArray(final short s) {
		byte[] targets = new byte[2];
		final short length = 2;
		for (int i = 0; i < length; i++) {
			final int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >>> offset) & 0xff);
		}
		return targets;
	}

	/**
	 * Bytes转short
	 * 
	 * @param b
	 * @return
	 */
	public static short byteArrayToShort(final byte[] b) {
		return (short) (((b[0] < 0 ? b[0] + 256 : b[0]) << 8) + (b[1] < 0 ? b[1] + 256 : b[1]));
	}

	/**
	 * Bytes转int
	 * 
	 * @param b
	 * @return
	 */
	public static int byteArrayToInt(final byte[] b) {
		return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24;
	}

	/**
	 * int转Bytes
	 * 
	 * @param a
	 * @return
	 */
	public static byte[] intToByteArray(final int a) {
		return new byte[] { (byte) ((a >> 24) & 0xFF), (byte) ((a >> 16) & 0xFF), (byte) ((a >> 8) & 0xFF),
				(byte) (a & 0xFF) };
	}


	public static byte[] intToBytes( int value )
	{
		byte[] src = new byte[4];
		src[3] =  (byte) ((value>>24) & 0xFF);
		src[2] =  (byte) ((value>>16) & 0xFF);
		src[1] =  (byte) ((value>>8) & 0xFF);
		src[0] =  (byte) (value & 0xFF);
		return src;
	}
	/**
	 * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。  和bytesToInt2（）配套使用
	 */
	public static byte[] intToBytes2(int value)
	{
		byte[] src = new byte[4];
		src[0] = (byte) ((value>>24) & 0xFF);
		src[1] = (byte) ((value>>16)& 0xFF);
		src[2] = (byte) ((value>>8)&0xFF);
		src[3] = (byte) (value & 0xFF);
		return src;
	}

	/**
	 * long转bytes
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] longToByteArray(final long s) {
		byte[] targets = new byte[2];
		final short length = 8;
		for (int i = 0; i < length; i++) {
			final int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >>> offset) & 0xff);
		}
		return targets;
	}

}
