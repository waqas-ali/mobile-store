package com.tainosystems.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class livraisons_clients {
	
	@XmlElement
	public String no_livraison_client;
	@XmlElement
	public String code_client;
	@XmlElement
	public String code_magasin;
	@XmlElement
	public Date date_livraison_client;
	@XmlElement
	public String livre_par;
	@XmlElement
	public String recu_par;
	@XmlElement
	public double montant_fact_client;
	@XmlElement
	public double rabais_client;
	@XmlElement
	public double taxe_fact_client;
	@XmlElement
	public double net_fact_client;
	@XmlElement
	public String type_vente;
	@XmlElement
	public String no_immatriculation;
	@XmlElement
	public String nom_prenom_chauf;
	@XmlElement
	public String no_license_chauf;
	@XmlElement
	public double montant_avance;
	@XmlElement
	public String code_adresse_client;
	@XmlElement
	public String to_lieu_id;
	@XmlElement
	public String from_lieu_id;
	@XmlElement
	public int no_vente;
	@XmlElement
	public String statut;
	@XmlElement
	public Date date_reception;
	@XmlElement
	public String signature;

	
}
