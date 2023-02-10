package fr.eni.eniD2WM147.bo;

import java.util.ArrayList;
import java.util.List;

public class Categories {
	Integer no_categorie;

	String libelle;

	List<ArticleVendu> artVendu;

	public Categories(Integer no_categorie, String libelle) {
		super();
		this.no_categorie = no_categorie;
		this.libelle = libelle;
		artVendu = new ArrayList<>();
	}

	public Categories(String libelle) {
		super();
		this.libelle = libelle;
		artVendu = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Categories [libelle=" + libelle + "]";
	}

	public void ADDArticles(ArticleVendu art) {
		this.artVendu.add(art);
	}

	public Integer getNo_categorie() {
		return no_categorie;
	}

	public void setNo_categorie(Integer no_categorie) {
		this.no_categorie = no_categorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

}
