package com.github.steingrd.bloominghollows;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.steingrd.bloominghollows.client.TemperatureClient;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ClientApp {
	
	private final static Logger log = LoggerFactory.getLogger(ClientApp.class);
	
	public static void start() throws Exception {
		log.info("Starting client app...");
		
		final String url = url();
		final String device = deviceName();
		final String authToken = authToken();
		
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
		executorService.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					TemperatureClient client = new TemperatureClient(url, authToken);
					client.post(readTemperature(device));
				} catch (Exception e) {
					log.error("Ooops", e);
					throw new RuntimeException(e);
				}
			}
		}, 0, 5, TimeUnit.SECONDS);
		
	}

	private static int readTemperature(String device) throws IOException {
		String line = Files.readFirstLine(new File(device), Charsets.UTF_8);
		return Integer.valueOf(line);
	}
	
	private static String authToken() {
		return propertyOrEnvVariable("BLOOMING_HOLLOWS_AUTH_TOKEN");
	}

	private static String url() {
		return propertyOrEnvVariable("BLOOMING_HOLLOWS_TEMPERATURE_URL");
	}
	
	private static String deviceName() {
		return propertyOrEnvVariable("BLOOMING_HOLLOWS_DEVICE");
	}
	
	private static String propertyOrEnvVariable(String name) {
		String value = System.getProperty(name);
		if (value != null) {
			log.info("System property BLOOMING_HOLLOWS_TEMPERATURE_URL is [{}].", value);
			return value;
		}
		
		value = System.getenv(name);
		if (value != null) {
			log.info("Environment variable BLOOMING_HOLLOWS_TEMPERATURE_URL is [{}].", value);
			return value;
		}
		
		throw new RuntimeException("No environment variable or system property named " + name);
	}
}
