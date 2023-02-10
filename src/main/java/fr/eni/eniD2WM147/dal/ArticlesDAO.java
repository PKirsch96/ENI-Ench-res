package fr.eni.eniD2WM147.dal;

import java.sql.SQLException;
import java.util.List;

import fr.eni.eniD2WM147.BusinessException.BusinessException;
import fr.eni.eniD2WM147.bo.ArticleVendu;
import fr.eni.eniD2WM147.bo.Categories;
import fr.eni.eniD2WM147.bo.Retrait;
import fr.eni.eniD2WM147.bo.Utilisateur;

public interface ArticlesDAO {

	public List<ArticleVendu> selectAllArticle() throws BusinessException;

	public List<Categories> libelleCategories() throws SQLException;

	public List<ArticleVendu> selectArticlesAvecFiltres(String recherche, int libelle) throws SQLException;
	
	public List<ArticleVendu> selectAchats(Utilisateur utilisateur, String recherche, int libelle, boolean encheresEc, boolean mesEncheresEc,
			boolean mesEncheresVd) throws SQLException;
	
	public List<ArticleVendu> selectVentes(Utilisateur utilisateur,String recherche, int libelle, boolean mesVentesEc, boolean mesVentesCr,
			boolean mesVentesVd) throws SQLException;
	
	public void newArticle(ArticleVendu art,Retrait retrait) throws BusinessException;
	
	public ArticleVendu selectById(int id)throws SQLException;
}
