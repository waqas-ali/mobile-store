package com.tainosystems.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class lignes_livraison_client {
	
	@XmlElement
	public int no_ligne_livraison;
	@XmlElement
	public String no_livraison_client;
	@XmlElement
	public String code_article;
	@XmlElement
	public double quantite_livree;
	@XmlElement
	public double prix_unit;
	@XmlElement
	public double prix_unit_dollor;
	@XmlElement
	public double quantite_envoyee;
	@XmlElement
	public double quantite_abimee;
	@XmlElement
	public double quantite_manquee;
	@XmlElement
	public String to_lieu_id;
	@XmlElement
	public String from_lieu_id;
	@XmlElement
	public String statut;
	
}


