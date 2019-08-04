package com.vencuts.boot.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Maharajan Rajagopal
 *
 */
@XmlRootElement(name = "records")
@XmlAccessorType(XmlAccessType.FIELD)
public class Records{
	
	
	public Records() {
		super();
	}
	public Records(List<Record> record) {
		super();
		this.record = record;
	}

	@XmlElement
	private List<Record> record;

	public List<Record> getRecord() {
		return record;
	}
	public void setRecord(List<Record> record) {
		this.record = record;
	}

	
	
	
}