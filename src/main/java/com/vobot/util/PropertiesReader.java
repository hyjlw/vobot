/**
 * 
 */
package com.vobot.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author weilin
 *
 */
public class PropertiesReader {
	private static final String PROPERTIES_FILE_NAME = "applicationContext.properties";
	private static final String BASE_DIR_KEY = "vobot.base.dir";
	private static final String BAIDU_SPEEC_API_KEY = "baidu.speech.api.key";
	private static final String BAIDU_SPEECH_SECRET_KEY = "baidu.speech.secret.key";
	private static final String BAIDU_SPEECH_CUID = "baidu.speech.cuid";

	private static final PropertiesReader INSTANCE = new PropertiesReader();

	private PropertiesReader() {
	}

	public static PropertiesReader getInstnace() {
		return INSTANCE;
	}

	public String getBaseDir() {
		return getProperties().get(BASE_DIR_KEY).toString();
	}
	
	public String getApiKey() {
		return getProperties().get(BAIDU_SPEEC_API_KEY).toString();
	}
	
	public String getSecretKey() {
		return getProperties().get(BAIDU_SPEECH_SECRET_KEY).toString();
	}
	
	public String getCuid() {
		return getProperties().get(BAIDU_SPEECH_CUID).toString();
	}

	private Properties getProperties() {
		Properties props = new Properties();
		InputStream in = PropertiesReader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
}
