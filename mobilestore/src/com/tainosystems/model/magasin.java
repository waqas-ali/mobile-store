package com.tainosystems.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class magasin {
	
	@XmlElement
	public String code_magasin;
	@XmlElement
	public String nom_magasin;
	@XmlElement
	public String sigle_magasin;
	@XmlElement
	public String adresse_magasin;
	@XmlElement
	public String phone;
	@XmlElement
	public String email;
	@XmlElement
	public String responsable_magasin;
	@XmlElement
	public String statut;
	@XmlElement
	public Date date_saisie;
	@XmlElement
	public String user_saisie;
}
