package fr.eni.eniD2WM147.Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.eniD2WM147.BusinessException.BusinessException;
import fr.eni.eniD2WM147.bll.UtilisateurManager;
import fr.eni.eniD2WM147.bo.Utilisateur;

@WebServlet("/ServletModifUtilisateur")
public class ServletModifUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Utilisateur utilisateurModif = (Utilisateur) request.getSession().getAttribute("UtilisateurCo");
			
			request.setAttribute("pseudo", utilisateurModif.getPseudo());
			request.setAttribute("nom", utilisateurModif.getNom());
			request.setAttribute("prenom", utilisateurModif.getPrenom());
			request.setAttribute("email", utilisateurModif.getEmail());
			request.setAttribute("telephone", utilisateurModif.getTelephone());
			request.setAttribute("rue", utilisateurModif.getRue());
			request.setAttribute("code_postal", utilisateurModif.getCodePostal());
			request.setAttribute("ville", utilisateurModif.getVille());
		
		
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/ModifUtilisateur.jsp");
			rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			request.setCharacterEncoding("UTF-8");
			Utilisateur utilisateurModif = (Utilisateur) request.getSession().getAttribute("UtilisateurCo");
			BusinessException be = new BusinessException();
			
		try {
				
			int noUtilisateur = (!(utilisateurModif.getNoUtilisateur()== null))? utilisateurModif.getNoUtilisateur():0; 
			String pseudo = null;
			String nom = null;
			String prenom = null;
			String email = null;
			String telephone = null;
			String rue = null;
			String codePostal = null;
			String ville = null;
			String motDePasse = null;
	
			if(!request.getParameter("pseudo").isBlank()) {
				pseudo = request.getParameter("pseudo");
				request.setAttribute("pseudo", utilisateurModif.getPseudo());
			}else {
				be.addMessage("Pseudo - non renseigné");
			}
			
			if(!request.getParameter("nom").isBlank()) {
				nom = request.getParameter("nom");
				request.setAttribute("nom", utilisateurModif.getNom());
			}else {
				be.addMessage("Nom - non renseigné");
			}
			
			if(!request.getParameter("prenom").isBlank()) {
				prenom = request.getParameter("prenom");
				request.setAttribute("prenom", utilisateurModif.getPrenom());
			}else {
				be.addMessage("prenom - non renseigné");
			}
			
			if(!request.getParameter("email").isBlank()) {
				email = request.getParameter("email");
				request.setAttribute("email", utilisateurModif.getEmail());
			}else {
				be.addMessage("Email - non renseigné");
			}
			
			if(!request.getParameter("telephone").isBlank()) {
				telephone = request.getParameter("telephone");
				request.setAttribute("telephone", utilisateurModif.getTelephone());
			}else {
				be.addMessage("Telephone - non renseigné");
			}
			
			if(!request.getParameter("rue").isBlank()) {
				rue = request.getParameter("rue");
				request.setAttribute("rue", utilisateurModif.getRue());
			}else {
				be.addMessage("Rue - non renseigné");
			}
			
			if(!request.getParameter("code_postal").isBlank()) {
				codePostal = request.getParameter("code_postal");
				request.setAttribute("code_postal", utilisateurModif.getCodePostal());
			}else {
				be.addMessage("Code postal - non renseigné");
			}
			
			if(!request.getParameter("ville").isBlank()) {
				ville = request.getParameter("ville");
				request.setAttribute("ville", utilisateurModif.getVille());
			}else {
				be.addMessage("Ville - non renseigné");
			}
			
			if(!request.getParameter("new_mdp").isBlank()) {
				motDePasse = request.getParameter("new_mdp");
				
			}else {
				be.addMessage("Mot de Passe - non renseigné");
			}
			
			if(!be.getListeMessage().isEmpty()) {
				throw be;
			}     
			
			Utilisateur utilisateur = new Utilisateur(noUtilisateur,pseudo, nom, prenom, email, telephone, rue, codePostal, ville, utilisateurModif.getCredit(), utilisateurModif.getAdministrateur());
			utilisateur.setMotDePasse(motDePasse);
			
			if(request.getParameter("mdp").trim().equals(String.valueOf(((Utilisateur) request.getSession().getAttribute("UtilisateurCo")).getMotDePasse().trim())) && request.getParameter("conf_mdp").trim().equals(request.getParameter("new_mdp").trim())) { 
				
				UtilisateurManager um = UtilisateurManager.getInstance();
				um.modifUtilisateur(utilisateur);
				
				HttpSession session = request.getSession();
				session.setAttribute("UtilisateurCo", utilisateur);
				
				RequestDispatcher rd = request.getRequestDispatcher("/ServletAfficherUnProfil");
			    rd.forward(request, response);
			}
	   
		    RequestDispatcher rd = request.getRequestDispatcher("/ServletAfficherUnProfil");
		    rd.forward(request, response);
			
		} catch (BusinessException e) {
			
			e.printStackTrace();
			request.setAttribute("list erreur", e.getListeMessage());
			doGet(request, response);
			
		}
		
	}
	
	
}

	
	

