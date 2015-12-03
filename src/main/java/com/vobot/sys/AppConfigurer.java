package com.vobot.sys;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class AppConfigurer extends PropertyPlaceholderConfigurer {

	public AppConfigurer(String configKey) {
		Resource configResource = null;
		String configPath = System.getProperty(configKey);
		if (configPath != null) {
			configResource = new FileSystemResource(configPath);
		} else {
			configResource = new ClassPathResource("applicationContext.properties");
		}
		try {
			super.setLocation(configResource);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
