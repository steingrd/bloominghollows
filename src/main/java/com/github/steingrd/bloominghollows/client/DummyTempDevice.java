package com.github.steingrd.bloominghollows.client;

import java.io.File;
import java.io.IOException;

import com.github.steingrd.bloominghollows.App;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class DummyTempDevice implements TempDevice {

	private final String path;

	public DummyTempDevice() {
		this.path = App.propertyOrEnvVariable("BLOOMING_HOLLOWS_DUMMY_PATH");
	}
	
	@Override
	public float getTemperature() {
		String line;
		try {
			line = Files.readFirstLine(new File(path), Charsets.UTF_8);
			return Float.valueOf(line);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
