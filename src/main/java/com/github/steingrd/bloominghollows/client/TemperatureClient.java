package com.github.steingrd.bloominghollows.client;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.google.common.base.Charsets;


public class TemperatureClient {
	
	private final Logger log = LoggerFactory.getLogger(TemperatureClient.class);
	
	private final String url;
	private final String authToken;

	public TemperatureClient(String url, String authToken) {
		this.url = url;
		this.authToken = authToken;
	}

	public void post(float temperature) throws Exception {
		String json = json(temperature);
		log.info("REQUEST => [{}]", json);
		
		HttpPost request = new HttpPost(url);
		request.addHeader("X-Auth-Token", authToken);
		request.setEntity(new StringEntity(json, Charsets.UTF_8));
		request.setHeader("Content-type", MediaType.APPLICATION_JSON_VALUE);
		
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			try (CloseableHttpResponse response = client.execute(request)) {
				log.info("RESPONSE => [{}].", response.getStatusLine());
			} catch (Exception e) {
				throw new RuntimeException("Could not post temperature!", e);
			}
		}
	}

	private String json(float temperature) {
		String timestamp = DateTime.now().toString();
		return String.format("{\"timestamp\": \"%s\", \"temperature\": %f}", timestamp, temperature);
	}

}
