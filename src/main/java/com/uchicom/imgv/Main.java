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
			BufferedImage image = ImageIO.read(new File("sample/logo.png"));
			File f = new File("test-jpeg2000-lossless.jp2");
			Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jp2");
			ImageWriter writer = writers.next();
			J2KImageWriteParam writeParams = (J2KImageWriteParam) writer.getDefaultWriteParam();
			writeParams.setLossless(true);
			writeParams.setNumDecompositionLevels(3);
			// writeParams.setFilter(J2KImageWriteParam.FILTER_53);
			//			 writeParams.setEncodingRate(64.0f);
			writeParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			writeParams.setCompressionType("JPEG2000");
//			writeParams.setCompressionQuality(0.02f);
//			writeParams.setTiling(1024, 1024, 0, 0);
			writeParams.setProgressionType("res");

//			IIOMetadata imageMeta = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), writeParams);
//			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder docbuilder = dbfactory.newDocumentBuilder(); // DocumentBuilderインスタンス
//			Document document = docbuilder.newDocument(); // Documentの生成
//			for (String name : imageMeta.getMetadataFormatNames()) {
//				System.out.println(name);
//			}
//			Element root = document.createElement("com_sun_media_imageio_plugins_jpeg2000_image_1.0");
//			Element tree = (Element) imageMeta.getAsTree("com_sun_media_imageio_plugins_jpeg2000_image_1.0");
//			NodeList nodeList =  tree.getChildNodes();
//			for (int i = 0;i < nodeList.getLength(); i++) {
//				Node child  = nodeList.item(i);
//				System.out.println(child.getNodeName());
//				NodeList childList =  child.getChildNodes();
//				for (int j = 0;j < childList.getLength(); j++) {
//					Node cc  = childList.item(j);
//					System.out.println(" " + cc.getNodeName());
//				}
//			}
//			tree = (Element) imageMeta.getAsTree("javax_imageio_1.0");
//			System.out.println(tree.getOwnerDocument());
//			nodeList =  tree.getChildNodes();
//			for (int i = 0;i < nodeList.getLength(); i++) {
//				Node child  = nodeList.item(i);
//				System.out.println(child.getNodeName());
//				NodeList childList =  child.getChildNodes();
//				for (int j = 0;j < childList.getLength(); j++) {
//					Node cc  = childList.item(j);
//					System.out.println(" " + cc.getNodeName());
//				}
//			}
//			tree = document.createElement("javax_imageio_jpeg_image_1.0");
//			Element elem = document.createElement("markerSequence");
//			tree.appendChild(elem);
//			Element elem2 = document.createElement("com");
//			elem.appendChild(elem2);
//			Element comment = document.createElement("Comment");
//			comment.appendChild(document.createTextNode("Copyright: National Diet Library, Japan"));
//			elem2.appendChild(comment);
//			//			Element com = (Element) tree.getElementsByTagName("Comment").item(0);
//			//			com.setAttribute("com", "Copyright: National Diet Library, Japan"); // 解像度の単位をDPIにする。
//			// メタデータを設定
//			imageMeta.setFromTree("com_sun_media_imageio_plugins_jpeg2000_image_1.0", tree);
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
