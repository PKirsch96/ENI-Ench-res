package fr.eni.eniD2WM147.bll;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.eni.eniD2WM147.BusinessException.BusinessException;
import fr.eni.eniD2WM147.bo.Utilisateur;
import fr.eni.eniD2WM147.dal.DAOFactory;
import fr.eni.eniD2WM147.dal.UtilisateurDAO;

public class UtilisateurManager {
	
	private static UtilisateurManager umngr;
	UtilisateurDAO utilisateurDAO;

	public static UtilisateurManager getInstance() {
		if (umngr == null) {
			umngr = new UtilisateurManager();
		}
		return umngr;
	}

	public UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getutilisateurDAO();
	}

	public Utilisateur seConnecter(String identifiant, String mdp) throws BusinessException {

		Utilisateur utilisateur = utilisateurDAO.connection(identifiant, mdp);

		return utilisateur;

	}

	public void newUtilisateur(Utilisateur utilisateur)
			throws BusinessException {
		
		BusinessException be = new BusinessException();
		validerInfos(utilisateur, be);
		
		if (be.getListeMessage().isEmpty()) {
			utilisateurDAO.newUtilisateur(utilisateur);
		} else {
			throw be;
		}

	}

	public void modifUtilisateur(Utilisateur utilisateur) throws BusinessException {
		
		BusinessException be = new BusinessException();
		
		validerInfos(utilisateur, be);
		
		if (be.getListeMessage().isEmpty()) {
			utilisateurDAO.modifUtilisateur(utilisateur);
		} else {
			throw be;
		}
	
	}
	public void deleteUtilisateur( Utilisateur utilisateur) throws BusinessException{
		
		utilisateurDAO.deleteUtilisateur(utilisateur);
		
	}
	
	private void validerInfos(Utilisateur utilisateur, BusinessException businessException){

		if (!queDesChiffresOuDesLettres(utilisateur.getPseudo())) {
			businessException.addMessage("pseudo invalide");
		} else if (utilisateur.getPseudo().length() > 30) {
			businessException.addMessage("Le pseudo ne doit pas depasser 30 caractères");
		}

		if (!queDesChiffresOuDesLettres(utilisateur.getRue())) {
			businessException.addMessage("nom de rue invalide");
		} else if (utilisateur.getRue().length() > 30) {
			businessException.addMessage("La rue ne doit pas depasser 30 caractères");
		}

		if (!verifEmail(utilisateur.getEmail())) {
			businessException.addMessage("email invalide");
		} else if (utilisateur.getEmail().length() > 100) {
			businessException.addMessage("L'email ne doit pas depasser 20 caractères");
		}

		if (!queDesLettres(utilisateur.getNom())) {
			businessException.addMessage("Le nom ne doit contenir que des lettres");
		} else if (utilisateur.getNom().length() > 30) {
			businessException.addMessage("Le nom ne doit pas depasser 30 lettres");
		}

		if (!queDesLettres(utilisateur.getPrenom())) {
			businessException.addMessage("Le prenom ne doit contenir que des lettres");
		} else if (utilisateur.getPrenom().length() > 30) {
			businessException.addMessage("Le prénom ne doit pas depasser 30 lettres");
		}

		if (!queDesLettres(utilisateur.getVille())) {
			businessException.addMessage("La ville ne doit contenir que des lettres");
		} else if (utilisateur.getVille().length() > 30) {
			businessException.addMessage("La ville ne doit pas depasser 30 lettres");
		}

		if (!queDesChiffres(utilisateur.getTelephone())) {
			businessException.addMessage("le numéro de téléphone ne dois contenir que des chiffres");
		} else if (utilisateur.getTelephone().length() > 10) {
			businessException.addMessage("Le numéro de téléphonne ne doit pas depasser 10 chiffres");
		}

		if (!queDesChiffres(utilisateur.getCodePostal())) {
			businessException.addMessage("le code postal ne dois contenir que des chiffres");
		} else if (utilisateur.getCodePostal().length() > 10) {
			businessException.addMessage("Le code postal  ne doit pas depasser 10 chiffres");
		}

		if (!verifMdp(utilisateur.getMotDePasse())) {
			businessException.addMessage("le mot de passe ne correspond pas aux critères");
		} else if (utilisateur.getMotDePasse().length() > 10) {
			businessException.addMessage("Le mot de passe ne doit pas depasser 30 caractères");
		}

	}
	
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

	private static boolean verifMdp(String str) {
		Pattern pattern = Pattern.compile("^\\S*(?=\\S{8,})(?=\\S*[a-z])(?=\\S*[A-Z])(?=\\S*[\\d])\\S*$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	private static boolean verifEmail(String str) {
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	private static boolean queDesChiffresOuDesLettres(String str) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9'\\s]+");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
}