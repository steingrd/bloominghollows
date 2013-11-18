package com.github.steingrd.bloominghollows.brews;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import com.google.common.base.Objects;

@Entity
@Table(name = "brews")
@XmlRootElement
public class Brew {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@XmlElement
	public Long id;

	@Column
	@XmlElement
	private String name;
	
	private Brew() {
	}
	
	public Brew(String name) {
		this();
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id).add("name", name).toString();
	}
}
