package com.tainosystems.model;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class lignes_demande_transfert {
	@XmlElement
	public String no_demande;
	@XmlElement
	public int no_ligne_demande;
	@XmlElement
	public String code_article;
	@XmlElement
	public double quantite_transferee;
	
}
