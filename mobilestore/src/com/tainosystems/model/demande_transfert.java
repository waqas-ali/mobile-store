package com.tainosystems.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class demande_transfert {

	@XmlElement
	public String no_demande;
	@XmlElement
	public String user_creation;
	@XmlElement
	public String code_magasin;
	@XmlElement
	public Date date_demande;
	@XmlElement
	public String magasin_destination;
	@XmlElement
	public String statut_demande;
	
}
