package com.github.steingrd.bloominghollows.temperatures;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "temperatures")
@XmlRootElement
public class Temperature {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public Long id;

	@XmlElement
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@XmlElement
	private Integer temperature;

	private Temperature() {
	}
	
	public Temperature(Date timestamp, Integer temperature) {
		this();
		this.timestamp = timestamp;
		this.temperature = temperature;
	}

	public Integer getTemperature() {
		return temperature;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
}
