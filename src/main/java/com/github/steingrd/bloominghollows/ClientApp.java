package com.github.steingrd.bloominghollows;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.steingrd.bloominghollows.client.FileTempDevice;
import com.github.steingrd.bloominghollows.client.TempDevice;
import com.github.steingrd.bloominghollows.client.TemperatureClient;
import com.github.steingrd.bloominghollows.client.UsbHidTempDevice;

import static java.lang.Integer.parseInt;

import static com.github.steingrd.bloominghollows.App.propertyOrEnvVariable;
import static com.github.steingrd.bloominghollows.App.propertyOrEnvVariableWithDefault;

public class ClientApp {

	final static Logger log = LoggerFactory.getLogger(ClientApp.class);

	public static void start() throws Exception {
		log.info("Starting client app...");

		final String url = propertyOrEnvVariable("BLOOMING_HOLLOWS_TEMPERATURE_URL");
		final String authToken = propertyOrEnvVariable("BLOOMING_HOLLOWS_AUTH_TOKEN");
		final String deviceType = propertyOrEnvVariable("BLOOMING_HOLLOWS_DEVICE");
		final int seconds = parseInt(propertyOrEnvVariableWithDefault("BLOOMING_HOLLOWS_INTERVAL", "5"));
		final TempDevice device = createDevice(deviceType);
		final TemperatureClient client = new TemperatureClient(url, authToken);
		
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
		executorService.scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				try {
					float t = device.getTemperature();
					LoggerFactory.getLogger(ClientApp.class).info("Got temperature [{}].", t);
					client.post(t);
				} catch (Exception e) {
					log.error("Ooops", e);
					throw new RuntimeException(e);
				}
			}
			
		}, 0, seconds, TimeUnit.SECONDS);

	}
	
	public static void testDevice() {
		final String deviceType = propertyOrEnvVariable("BLOOMING_HOLLOWS_DEVICE");
		final TempDevice device = createDevice(deviceType);
		
		log.info("Testing device...");
		float temperature = device.getTemperature();
		log.info("Got temperature [{}].", temperature);
	}

	private static TempDevice createDevice(final String deviceType) {
		if (deviceType.equals("file")) {
			return new FileTempDevice();
		} else if (deviceType.equals("usbhid")) {
			return new UsbHidTempDevice();
		} else {
			throw new RuntimeException("Unknown device type: " + deviceType);
		}
	}

}
