package com.github.steingrd.bloominghollows.system;

import org.joda.time.DateTime;

public interface TimeSource {

	DateTime now();
	
}
