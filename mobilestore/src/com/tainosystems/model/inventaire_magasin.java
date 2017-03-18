package com.tainosystems.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class inventaire_magasin {

	@XmlElement
	public String code_magasin;
	@XmlElement
	public String code_article;
	@XmlElement
	public String code_lieu_entreposage;
	@XmlElement
	public Date control_date;
	@XmlElement
	public double quantite_stock;
	@XmlElement
	public double qte_non_livree;
	@XmlElement
	public double qte_dechiree;
	
	
}
