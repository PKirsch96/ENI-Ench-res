package fr.eni.eniD2WM147.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleVendu {
	private int noArticle;
	private String nomArticle;
	private String description;
	private LocalDateTime dateDebutEncheres;
	private LocalDateTime dateFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private String etatVente;
	private Enchere encheres;
	private Utilisateur utilisateur;
	private Retrait lieuxDeRetrait;
	private Categories categorie;

	public ArticleVendu(int noArticle, String nomArticle, String description,LocalDateTime dateDebutEncheres,
			LocalDateTime dateFinEncheres,int miseAPrix, int prixVente,String etatVente) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
	}

	public ArticleVendu(String nomArticle, String description, Categories categorie, LocalDateTime dateDebutEncheres,
			LocalDateTime dateFinEncheres, int miseAPrix) {
		super();
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
	}

	@Override
	public String toString() {
		return "ArticleVendu [nomArticle=" + nomArticle + ", description=" + description + ", dateDebutEncheres="
				+ dateDebutEncheres + ", dateFinEncheres=" + dateFinEncheres + ", miseAPrix=" + miseAPrix
				+ ", prixVente=" + prixVente + ", etatVente=" + etatVente + ", encheres=" + encheres + ", utlilisateur="
				+ utilisateur + ", lieuxDeRetrait=" + lieuxDeRetrait + ", categorie=" + categorie + "]";
	}

	public int getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(LocalDateTime dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public LocalDateTime getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(LocalDateTime dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getMiseAPrix() {
		return miseAPrix;
	}

	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public String getEtatVente() {
		return etatVente;
	}

	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Enchere getEncheres() {
		return encheres;
	}

	public void setEncheres(Enchere encheres) {
		this.encheres = encheres;
	}

	public Retrait getLieuxDeRetrait() {
		return lieuxDeRetrait;
	}

	public void setLieuxDeRetrait(Retrait lieuxDeRetrait) {
		this.lieuxDeRetrait = lieuxDeRetrait;
	}

	public Categories getCategorie() {
		return categorie;
	}

	public void setCategorie(Categories categorie) {
		this.categorie = categorie;
	}

	public String getPrixInitial() {
		return null;
	}
}