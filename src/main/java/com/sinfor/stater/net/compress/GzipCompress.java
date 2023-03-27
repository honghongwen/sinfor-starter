package com.sinfor.stater.net.compress;


import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * apache commo Gzip压缩
 * @author 李新周
 */
public class GzipCompress implements Compress{
	@Override
	public byte[] compress(byte[] data) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		GzipCompressorOutputStream gcos = new GzipCompressorOutputStream(baos);
		try {
			gcos.write(data);
		} finally {
			gcos.close();
			baos.close();
		}
		return baos.toByteArray();
	}
	@Override
	public byte[] uncompress(byte[] data) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		GzipCompressorInputStream gcis = new GzipCompressorInputStream(new ByteArrayInputStream(data));
		try {
			int count;
			byte[] buffer = new byte[1024*3];
			while ((count = gcis.read(buffer)) != -1) {
				baos.write(buffer, 0, count);
			}
		} finally {
			gcis.close();
			baos.close();
		}

		return baos.toByteArray();
	}
}
