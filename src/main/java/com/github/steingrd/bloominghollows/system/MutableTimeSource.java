package com.github.steingrd.bloominghollows.system;

import org.joda.time.DateTime;

public class MutableTimeSource implements TimeSource {

	private DateTime dateTime;

	public MutableTimeSource(DateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	@Override
	public DateTime now() {
		return dateTime;
	}
	
	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

}
