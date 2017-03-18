package com.tainosystems.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class lieu_entreposage {
	@XmlElement
	public String code_lieu_entreposage;
	@XmlElement
	public String lieu_entreposage;
}
