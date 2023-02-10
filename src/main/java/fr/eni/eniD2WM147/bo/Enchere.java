package fr.eni.eniD2WM147.bo;

import java.time.LocalDateTime;

public class Enchere {
	private LocalDateTime dateEnchere;
	private Integer montant_enchere;
	private Utilisateur utilisateur;
	private ArticleVendu artVendu;

	public Enchere(LocalDateTime dateEnchere, Integer montant_enchere) {
		super();
		this.dateEnchere = dateEnchere;
		this.montant_enchere = montant_enchere;
	}

	public Enchere(LocalDateTime dateEnchere, Integer montant_enchere, Utilisateur utilisateur, ArticleVendu artVendu) {
		super();
		this.dateEnchere = dateEnchere;
		this.montant_enchere = montant_enchere;
		this.utilisateur = utilisateur;
		this.artVendu = artVendu;
	}

	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(LocalDateTime dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public Integer getMontant_enchere() {
		return montant_enchere;
	}

	public void setMontant_enchere(Integer montant_enchere) {
		this.montant_enchere = montant_enchere;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public ArticleVendu getArtVendu() {
		return artVendu;
	}

	public void setArtVendu(ArticleVendu artVendu) {
		this.artVendu = artVendu;
	}

}
