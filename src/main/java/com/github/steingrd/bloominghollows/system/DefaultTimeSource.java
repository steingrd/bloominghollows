package com.github.steingrd.bloominghollows.system;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class DefaultTimeSource implements TimeSource {

	@Override
	public DateTime now() {
		return DateTime.now();
	}

}
