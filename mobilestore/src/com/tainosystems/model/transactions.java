package com.tainosystems.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class transactions {
	@XmlElement
	public String code_client;
	@XmlElement
	public String code_fournisseur;
	@XmlElement
	public String code_type_transaction;	
	@XmlElement
	public Date date_transaction;
	@XmlElement
	public String code_caissier;
	@XmlElement
	public double montant_transaction;
	@XmlElement
	public String code_magasin;
	@XmlElement
	public String type_paiement;
	@XmlElement
	public String code_lieu_entreposage;
	@XmlElement
	public String no_cheque;
	@XmlElement
	public String banque;
	@XmlElement
	public String monnaie;
	@XmlElement
	public String no_vente;
	@XmlElement
	public String no_livraison_client;
	@XmlElement
	public Date date_creation;
	@XmlElement
	public String remarque;

}
