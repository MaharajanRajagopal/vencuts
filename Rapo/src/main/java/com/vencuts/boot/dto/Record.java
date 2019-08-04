package com.vencuts.boot.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;



/**
 * @author Maharajan Rajagopal
 *
 */
@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
public class Record {
	
	public Record() {
		super();
	}
	public Record(String reference,String accountNumber, String description,String startBalance, String mutation, String endBalance) {
		this.reference = reference;
		this.accountNumber = accountNumber;
		this.mutation = mutation;
		this.endBalance = endBalance;
		this.description = description;
		
		this.startBalance = startBalance;
	}
	
	@XmlAttribute
	private String reference;
	@XmlElement
	private String accountNumber;
	@XmlElement
	private String mutation;
	@XmlElement
	private String endBalance;
	@XmlElement
	private String description;
	
	@XmlElement
	private String startBalance;
	
	
	

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getMutation() {
		return mutation;
	}

	public void setMutation(String mutation) {
		this.mutation = mutation;
	}

	public String getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(String endBalance) {
		this.endBalance = endBalance;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(String startBalance) {
		this.startBalance = startBalance;
	}

	@Override
	public String toString() {
		return "Record [reference = " + reference + ", mutation = " + mutation + ", endBalance = " + endBalance
				+ ", description = " + description + ", accountNumber = " + accountNumber + ", startBalance = "
				+ startBalance + "]";
	}
}
