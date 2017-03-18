package com.tainosystems.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class utilisateurs {
	
	@XmlElement
	public String code_utilisateur;
	@XmlElement
	public String motpass;
	@XmlElement
	public String nom;
	@XmlElement
	public String prenom;
	@XmlElement
	public String adresse;
	@XmlElement
	public String poste;
	@XmlElement
	public Date date_mod_pass;
	@XmlElement
	public String code_type_utilisateur;
	@XmlElement
	public String user_creation;
	@XmlElement
	public Date date_creation;
	@XmlElement
	public String user_modification;
	@XmlElement
	public Date date_modification;
	@XmlElement
	public String phone;
	@XmlElement
	public String actif;


}
