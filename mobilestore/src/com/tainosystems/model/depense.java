package com.tainosystems.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class depense {
	@XmlElement
	public int no_depense;
	@XmlElement
	public String code_type_depense;
	@XmlElement
	public String code_magasin;
	@XmlElement
	public String raison_depense;
	@XmlElement
	public double montant;
	@XmlElement
	public String code_monnaie;
	@XmlElement
	public Date date_depense;
	@XmlElement
	public String user_creation;
	@XmlElement
	public Date user_modification;
	@XmlElement	
	public Date date_creation;
	@XmlElement
	public Date date_modification;
}
