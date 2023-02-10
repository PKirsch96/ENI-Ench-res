package fr.eni.eniD2WM147.bll;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.eni.eniD2WM147.BusinessException.BusinessException;
import fr.eni.eniD2WM147.bo.ArticleVendu;
import fr.eni.eniD2WM147.bo.Categories;
import fr.eni.eniD2WM147.bo.Retrait;
import fr.eni.eniD2WM147.bo.Utilisateur;
import fr.eni.eniD2WM147.dal.ArticlesDAO;
import fr.eni.eniD2WM147.dal.DAOFactory;

public class ArticlesManager {
	
	private static ArticlesManager amngr;

	ArticlesDAO articlesDAO;
	
	
	//Sinngleton
	public static ArticlesManager getInstance() {
		if(amngr == null) {
			amngr = new ArticlesManager();
		}
		return amngr;
	}

	private ArticlesManager() {
		this.articlesDAO = DAOFactory.getarticlesDAO();
	}
	//
	

	
	//Retourne tous les article dont l'état est "En Cours" en mode déconnecter
	public List<ArticleVendu> afficherArticle() throws BusinessException {

		List<ArticleVendu> selectArticle = articlesDAO.selectAllArticle();

		return selectArticle;
	}
	
	//Recupère le libelle et le numéro de catégorie pour les afficher et permettre de filtrer les recherches
	public List<Categories> libelleCategories() throws SQLException {
		List<Categories> libelles = articlesDAO.libelleCategories();
		return libelles;
	}

	
	//filtrer les articles en mode déconnecté
	public List<ArticleVendu> selectArticlesAvecFiltres(String recherche, int libelle) throws SQLException {
		List<ArticleVendu> listefiltree = articlesDAO.selectArticlesAvecFiltres(recherche, libelle);
		return listefiltree;
	}
	
	
	//Création d'article
	public void newArticle(ArticleVendu art,Retrait retrait)throws BusinessException {
		
		BusinessException be = new BusinessException();
		
		validerInfos(art,retrait, be);
		
		if (be.getListeMessage().isEmpty()) {
			articlesDAO.newArticle(art,retrait);
		} else {
			
			throw be;
			
		}
	}
	
	
	//filtre en mode conecté pour les enchères
	public List<ArticleVendu> selectAchats(Utilisateur utilisateur, String recherche, int libelle, boolean encheresEc, boolean mesEncheresEc,
			boolean mesEncheresVd) throws SQLException{
		
		List<ArticleVendu> listefiltree = articlesDAO.selectAchats(utilisateur, recherche, libelle, encheresEc, mesEncheresEc,mesEncheresVd);
		
		return listefiltree;
	}
	
	
	//filtre en mode conecté pour les articles créé par le client
	public List<ArticleVendu> selectVentes(Utilisateur utilisateur,String recherche, int libelle, boolean mesVentesEc, boolean mesVentesCr,
			boolean meVentesVd) throws SQLException{
		
		List<ArticleVendu> listefiltree = articlesDAO.selectVentes(utilisateur, recherche, libelle, mesVentesEc, mesVentesCr,meVentesVd);
		
		return listefiltree;
	}
	
	//affiche les détails de l'articles sélectionné en mode connecté
	public ArticleVendu selectById(int id)throws SQLException{
		
		ArticleVendu artV = articlesDAO.selectById(id);
		
		return artV;
	}
	
	//valide la taille des paramétres du nouvel article pour la BDD et appel une autre methode en fonction de critères
	private void validerInfos(ArticleVendu art,Retrait retrait,BusinessException businessException){

		if (!queDesChiffresOuDesLettres(art.getNomArticle())) {
			businessException.addMessage("nom invalide");
		} else if (art.getNomArticle().length() > 30) {
			businessException.addMessage("Le nom de l'article ne doit pas depasser 30 caractères");
		}
		

		if (!queDesChiffresOuDesLettres(art.getDescription())) {
			businessException.addMessage("description non valide");
		} else if (art.getDescription().length() > 300) {
			businessException.addMessage("La description ne doit pas depasser 30 caractères");
		}

		if (!dateDebut(art.getDateDebutEncheres())) {
			businessException.addMessage("date invalide");
		} 

		if (!dateFin(art.getDateDebutEncheres(), art.getDateFinEncheres(), art)) {
			businessException.addMessage("date invalide");
		} 

		if (!queDesChiffres(String.valueOf(art.getMiseAPrix()))) {
			businessException.addMessage("Le prix ne doit contenir que des Chiffres");
		} 
		
		if (!queDesChiffresOuDesLettres(retrait.getRue())) {
			businessException.addMessage("le nom de rue ne correspond pas aux critères");
		} else if (retrait.getRue().length() > 30) {
			businessException.addMessage("Le nom de rue ne doit pas depasser 30 caractères");
		}
		if (!queDesChiffres(retrait.getCode_postal())) {
			businessException.addMessage("le code postal ne correspond pas aux critères");
		} else if (retrait.getCode_postal().length() > 15) {
			businessException.addMessage("Le code postal ne doit pas depasser 5 caractères");
		}
		if (!queDesLettres(retrait.getVille())) {
			businessException.addMessage("le nom de la ville ne correspond pas aux critères");
		} else if (retrait.getVille().length() > 30) {
			businessException.addMessage("Le nom de la ville ne doit pas depasser 30 caractères");
		}
	}
	
	//méthode qui vérifie les critères en fonction de pattern ou date
	private static boolean queDesLettres(String str) {
		Pattern pattern = Pattern.compile("[a-zA-Z'\\s]+");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	private static boolean queDesChiffres(String str) {
		Pattern pattern = Pattern.compile("[0-9\\s]+");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	private static boolean dateDebut(LocalDateTime debut) {
		LocalDateTime now = LocalDateTime.now();
		
		if(debut.isBefore(now)) {
			return false;
		}
		return true;
	}
	
	private static boolean dateFin(LocalDateTime debut, LocalDateTime fin, ArticleVendu art) {
		
		if(fin.isBefore(debut)) {
			return false;
		}
		return true;
	}
	
	private static boolean queDesChiffresOuDesLettres(String str) {
		Pattern pattern = Pattern.compile("^[\\w\\d\\u0080-\\uffff\\'\\s]+$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
}
