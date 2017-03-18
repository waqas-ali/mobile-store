package com.tainosystems.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class type_article {
	
	@XmlElement
	public String code_type_article;
	@XmlElement
	public String type_article;
	
}
