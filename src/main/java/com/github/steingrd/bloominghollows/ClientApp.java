package com.github.steingrd.bloominghollows;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.steingrd.bloominghollows.client.DummyTempDevice;
import com.github.steingrd.bloominghollows.client.TempDevice;
import com.github.steingrd.bloominghollows.client.TemperatureClient;
import com.github.steingrd.bloominghollows.client.UsbHidTempDevice;

import static com.github.steingrd.bloominghollows.App.propertyOrEnvVariable;

public class ClientApp {

	final static Logger log = LoggerFactory.getLogger(ClientApp.class);

	public static void start() throws Exception {
		log.info("Starting client app...");

		final String url = propertyOrEnvVariable("BLOOMING_HOLLOWS_TEMPERATURE_URL");
		final String authToken = propertyOrEnvVariable("BLOOMING_HOLLOWS_AUTH_TOKEN");
		final String deviceType = propertyOrEnvVariable("BLOOMING_HOLLOWS_DEVICE");
		
		final TempDevice device = createDevice(deviceType);
		
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
		executorService.scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				try {
					TemperatureClient client = new TemperatureClient(url, authToken);
					float t = device.getTemperature();
					LoggerFactory.getLogger(ClientApp.class).info("Got temperature [{}].", t);
					client.post(t);
				} catch (Exception e) {
					log.error("Ooops", e);
					throw new RuntimeException(e);
				}
			}
			
		}, 0, 5, TimeUnit.SECONDS);

	}

	private static TempDevice createDevice(final String deviceType) {
		if (deviceType.equals("dummy")) {
			return new DummyTempDevice();
		} else if (deviceType.equals("usbhid")) {
			return new UsbHidTempDevice();
		} else {
			throw new RuntimeException("Unknown device type: " + deviceType);
		}
	}

}
