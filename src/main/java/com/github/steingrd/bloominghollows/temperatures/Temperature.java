package com.github.steingrd.bloominghollows.temperatures;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.github.steingrd.bloominghollows.brews.Brew;

@Entity
@Table(name = "temperatures")
@XmlRootElement
public class Temperature {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public Long id;

	@ManyToOne
	private Brew brew;
	
	@XmlElement
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime timestamp;
	
	@Column
	@XmlElement
	private Integer temperature;

	private Temperature() {
	}
	
	public Temperature(Brew brew, DateTime timestamp, Integer temperature) {
		this();
		this.brew = brew;
		this.timestamp = timestamp;
		this.temperature = temperature;
	}
	
	public void setBrew(Brew brew) {
		this.brew = brew;
	}
	
	public Brew getBrew() {
		return brew;
	}

	public Integer getTemperature() {
		return temperature;
	}
	
	public DateTime getTimestamp() {
		return timestamp;
	}
	
}
