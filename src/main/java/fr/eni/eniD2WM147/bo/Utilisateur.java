package fr.eni.eniD2WM147.bo;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {

	private Integer noUtilisateur;
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String rue;
	private String codePostal;
	private String ville;
	private String motDePasse;
	private Integer credit;
	private boolean administrateur;
	private List<ArticleVendu> ArtVendu;
	private List<Enchere> ListEncheres;

	
	public Utilisateur(Integer noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, int credit, boolean administrateur) {
		super();
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.credit = credit;
		this.administrateur = administrateur;
		ArtVendu = new ArrayList<>();
		ListEncheres = new ArrayList<>();
	}

	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue,
			String codePostal, String ville, int credit, boolean administrateur) {
		super();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.credit = credit;
		this.administrateur = administrateur;
		ArtVendu = new ArrayList<>();
		ListEncheres = new ArrayList<>();
	}

	

	// ajouter un article
	public void AjoutArtVendu(ArticleVendu Art) {
		this.ArtVendu.add(Art);
	}

	// ajouter une enchères
	public void AjoutEnchere(Enchere enchere) {
		this.ListEncheres.add(enchere);
	}

	public Integer getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(Integer noUtilisateur) {
		this.noUtilisateur = noUtilisateur;

	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;

	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public boolean getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}

	public List<ArticleVendu> getArtVendu() {
		return ArtVendu;
	}

	public void setArtVendu(List<ArticleVendu> artVendu) {
		ArtVendu = artVendu;
	}

	public List<Enchere> getListEncheres() {
		return ListEncheres;
	}

	public void setListEncheres(List<Enchere> listEncheres) {
		ListEncheres = listEncheres;
	}

	@Override
	public String toString() {
		return "Utilisateur [id = "+ noUtilisateur + "pseudo=" + pseudo + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email
				+ ", telephone=" + telephone + ", rue=" + rue + ", codePostal=" + codePostal + ", ville=" + ville
				+ ", motDePasse=" + motDePasse + ", credit=" + credit + ", administrateur=" + administrateur
				+ ", ArtVendu=" + ArtVendu + ", ListEncheres=" + ListEncheres + "]";
	}

}
