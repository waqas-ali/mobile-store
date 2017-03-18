package com.tainosystems.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class lignes_transferts {
	@XmlElement
	public String no_ligne_transfert;
	@XmlElement
	public String no_transfert;
	@XmlElement
	public String code_article;
	@XmlElement
	public double quantite_transferee;
	
}
