package com.github.steingrd.bloominghollows.client;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.steingrd.bloominghollows.App;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class FileTempDevice implements TempDevice {

	private final Logger log = LoggerFactory.getLogger(FileTempDevice.class);
	
	private final String path;
	private float temp;

	public FileTempDevice() {
		this.path = App.propertyOrEnvVariable("BLOOMING_HOLLOWS_TEMP_PATH");
	}
	
	@Override
	public float getTemperature() {
		String line;
		try {
			line = Files.readFirstLine(new File(path), Charsets.UTF_8);
			temp = Float.valueOf(line);
			return temp;
		} catch (IOException e) {
			log.error("Could not read temperature, returning cached value.");
			return temp;
		}
	}
	
}
