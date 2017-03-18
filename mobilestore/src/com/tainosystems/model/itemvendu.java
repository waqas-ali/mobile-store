package com.tainosystems.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class itemvendu {
	@XmlElement
	public int no_itemvendu;
	@XmlElement
	public int no_vente;
	@XmlElement
	public String code_article;
	@XmlElement
	public String monnaie;
	@XmlElement
	public String user_creation;
	@XmlElement
	public String user_modifiction;
	@XmlElement
	public double prix_unit;
	@XmlElement
	public double quantite_vendue;
	@XmlElement
	public Date date_creation;
	@XmlElement
	public Date date_modification;
}

