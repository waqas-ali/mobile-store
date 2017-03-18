package com.tainosystems.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class transferts {
	
	@XmlElement
	public String no_transfert;
	@XmlElement
	public String code_magasin_origine;
	@XmlElement
	public String code_magasin_destination;
	@XmlElement
	public Date date_transfert;
	@XmlElement
	public String livre_par;
	@XmlElement
	public String recu_par;
	@XmlElement
	public String statut;
	
}
