package com.tainosystems.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class vente {
	@XmlElement
	public int no_vente;
	@XmlElement
	public String code_client;
	@XmlElement
	public String code_magasin;
	@XmlElement
	public String depot;
	@XmlElement
	public String monnaie;
	@XmlElement
	public String statut_vente;
	@XmlElement
	public String statut_livraison;
	@XmlElement
	public String statut_paiement;
	@XmlElement
	public String type_vente;
	@XmlElement
	public double montant_sous_total;
	@XmlElement
	public double montant_taxe;
	@XmlElement
	public double montant_taxe_1;
	@XmlElement
	public double montant_taxe_2;
	@XmlElement
	public double montant_rabais;
	@XmlElement
	public double montant_transport;
	@XmlElement
	public double montant_manutention;
	@XmlElement
	public double montant_total;
	@XmlElement
	public Date date_vente;
	@XmlElement
	public String user_creation;
	@XmlElement
	public String user_modification;
	@XmlElement
	public Date date_creation;
	@XmlElement
	public Date date_modification;

	
}
