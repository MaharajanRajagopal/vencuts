package com.vencuts.boot.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


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