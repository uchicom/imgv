// (c) 2017 uchicom
package com.uchicom.imgv;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import com.github.jaiimageio.jpeg2000.J2KImageWriteParam;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] ReadFormats = ImageIO.getReaderFormatNames();
		System.out.println("読み込めるファイル形式:");
		for (int i = 0; i < ReadFormats.length; i++) {
			System.out.println(ReadFormats[i]);
		}
		// ロスレス
		lossless();
		// ロス
		lossyWrite();
	}

	public static void lossless() {
		try {
			BufferedImage image = ImageIO.read(new File("sample/jio.png"));
			File f = new File("test-jpeg2000-lossless.jp2");
			Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jp2");
			ImageWriter writer = writers.next();
			J2KImageWriteParam writeParams = (J2KImageWriteParam) writer.getDefaultWriteParam();
			writeParams.setLossless(true);
			// writeParams.setFilter(J2KImageWriteParam.FILTER_53);
			// writeParams.setEncodingRate(64.0f);
			 writeParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			 writeParams.setCompressionType("JPEG2000");
			 writeParams.setCompressionQuality(0.48f);
			 writeParams.setTiling(1024, 1024, 0, 0);

			ImageOutputStream ios = ImageIO.createImageOutputStream(f);
			writer.setOutput(ios);
			writer.write(null, new IIOImage(image, null, null), writeParams);
			writer.dispose();
			ios.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void lossyWrite() {
		try {

			BufferedImage image = ImageIO.read(new File("sample/jio.png"));
			File f = new File("test-jpeg2000-lossy.jp2");
			Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jp2");
			ImageWriter writer = writers.next();
			J2KImageWriteParam writeParams = (J2KImageWriteParam) writer.getDefaultWriteParam();
			writeParams.setLossless(false);
			writeParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			writeParams.setCompressionType("JPEG2000");
			// writeParams.setFilter(J2KImageWriteParam.FILTER_97);

			writeParams.setCompressionQuality(0.0f);
			writeParams.setEncodingRate(0.5f);
			ImageOutputStream ios = ImageIO.createImageOutputStream(f);
			writer.setOutput(ios);
			writer.write(null, new IIOImage(image, null, null), writeParams);
			writer.dispose();
			ios.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
