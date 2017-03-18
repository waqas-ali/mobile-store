package com.tainosystems.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class monnaie {
	@XmlElement
	public String code_monnaie;
	@XmlElement
	public String monnaie;

}
