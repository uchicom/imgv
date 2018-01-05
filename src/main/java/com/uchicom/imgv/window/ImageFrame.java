// (c) 2017 uchicom
package com.uchicom.imgv.window;

import com.uchicom.imgv.Constants;
import com.uchicom.ui.ResumeFrame;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class ImageFrame extends ResumeFrame {

	/**
	 * @param configFile
	 * @param windowKey
	 */
	public ImageFrame() {
		super(Constants.CONF_FILE, Constants.PROP_KEY_WJM);
		initComponents();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

}
