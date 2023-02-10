package fr.eni.eniD2WM147.dal;

import fr.eni.eniD2WM147.BusinessException.BusinessException;
import fr.eni.eniD2WM147.bo.ArticleVendu;
import fr.eni.eniD2WM147.bo.Utilisateur;

public interface UtilisateurDAO {
	
	public Utilisateur connection(String identifiant, String motDePasse) throws BusinessException;
	
	public void newUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
	public void modifUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
	public void deleteUtilisateur( Utilisateur utilisateur) throws BusinessException; 
	
}
