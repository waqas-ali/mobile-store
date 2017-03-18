package com.tainosystems.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class articles {
	@XmlElement
	public String code_article;
	@XmlElement
	public String sku;
	@XmlElement
	public String code_type_article;
	@XmlElement
	public String nom_article;
	@XmlElement
	public String description_fiche_vente;
	@XmlElement
	public String unite;
	@XmlElement
	public String code_monnaie;
	@XmlElement
	public double prix_vente = 0.00;
	@XmlElement
	public double quantite_par_unite = 0.00;
	@XmlElement
	public int article_actif = 1;
	@XmlElement
	public String classe_taxes;
	@XmlElement
	public double quantite_en_inventaire;
	@XmlElement
	public double montant_paye = 0.00;
	@XmlElement
	public Date date_creation;
	@XmlElement
	public String user_creation;
	@XmlElement
	public String user_modification;
	@XmlElement
	public Date date_modification;
	@XmlElement
	public String statut_paiement;

}
