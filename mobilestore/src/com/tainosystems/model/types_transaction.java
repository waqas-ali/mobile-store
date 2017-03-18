package com.tainosystems.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class types_transaction {
	
	@XmlElement
	public String code_type_transaction;
	@XmlElement
	public String type_transaction;
	@XmlElement
	public String signe_transaction;
	@XmlElement
	public String monnaie;

	
	

}

