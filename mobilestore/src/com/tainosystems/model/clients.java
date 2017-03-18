package com.tainosystems.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class clients {
	@XmlElement
	public String code_client;
	@XmlElement
	public String denomination_client;
	@XmlElement
	public String sigle_client;
	@XmlElement
	public String nomcontact;
	@XmlElement
	public String prenomcontact;
	@XmlElement
	public String adresse;
	@XmlElement
	public String ville;
	@XmlElement
	public String province_etat;
	@XmlElement
	public String code_postale;
	@XmlElement
	public String pays;
	@XmlElement
	public String boite_postale;
	@XmlElement
	public String tel;
	@XmlElement
	public String cell;
	@XmlElement
	public String fax;
	@XmlElement
	public String email;
	@XmlElement
	public String site_web;
	@XmlElement
	public String limite_credit;
	@XmlElement
	public double solde;
	@XmlElement
	public String date_dernier_paiement;
	@XmlElement
	public String statut;
	@XmlElement
	public String qualite;
	@XmlElement
	public String note;
	@XmlElement
	public String marche;
	@XmlElement
	public String code_magasin;
	
}

