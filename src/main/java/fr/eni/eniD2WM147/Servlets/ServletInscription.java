package fr.eni.eniD2WM147.Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.eniD2WM147.BusinessException.BusinessException;
import fr.eni.eniD2WM147.bll.UtilisateurManager;
import fr.eni.eniD2WM147.bo.Utilisateur;

/**
 * Servlet implementation class ServletMonProfil
 */
@WebServlet("/ServletInscription")
public class ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/inscription.jsp");
		rd.forward(request, response);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Utilisateur newUtilisateur = null;
		BusinessException be = new BusinessException();
	
		try {
			
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
				
			}else {
				
				be.addMessage("Pseudo - non renseigné");
				
			}
			
			if(!request.getParameter("nom").isBlank()) {
				 
				nom = request.getParameter("nom");
				request.setAttribute("nom", nom);
				
			}else {
				
				be.addMessage("Nom - non renseigné");
				
			}
			
			if(!request.getParameter("prenom").isBlank()) {
				
				prenom = request.getParameter("prenom");
				request.setAttribute("prenom", prenom);
				
			}else {
				
				be.addMessage("prenom - non renseigné");
			}
			
			if(!request.getParameter("email").isBlank()) {
				
				email = request.getParameter("email");
				request.setAttribute("email", email);
				
			}else {
				be.addMessage("Email - non renseigné");
			}
			
			if(!request.getParameter("telephone").isBlank()) {
				
				telephone = request.getParameter("telephone");
				request.setAttribute("telephone", telephone);
				
			}else {
				be.addMessage("Telephone - non renseigné");
			}
			
			if(!request.getParameter("rue").isBlank()) {
				
				rue = request.getParameter("rue");
				request.setAttribute("rue", rue);
				
			}else {
				be.addMessage("Rue - non renseigné");
			}
			
			if(!request.getParameter("code_postal").isBlank()) {
				
				codePostal = request.getParameter("code_postal");
				request.setAttribute("codePostal", codePostal);
				
			}else {
				be.addMessage("Code postal - non renseigné");
			}
			
			if(!request.getParameter("ville").isBlank()) {
				
				ville = request.getParameter("ville");
				request.setAttribute("ville", ville);
				
			}else {
				be.addMessage("Ville - non renseigné");
			}
			
			if(!request.getParameter("mot_de_passe").isBlank()) {
				
				motDePasse = request.getParameter("mot_de_passe");
				
			}else {
				be.addMessage("Mot de Passe - non renseigné");
			}
			
			if(!be.getListeMessage().isEmpty()) {
				
				throw be;
				
			} 
			Integer credit = 100;
			boolean administrateur = false;
			
			newUtilisateur = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, credit, administrateur);
			newUtilisateur.setMotDePasse(motDePasse);

			HttpSession session = request.getSession(false);
		    
			if (session != null) {
		        session.invalidate();
		    }
		    
		    if(request.getParameter("mot_de_passe").trim().equals(request.getParameter("mdp").trim())) {
		        UtilisateurManager em = UtilisateurManager.getInstance();
				em.newUtilisateur(newUtilisateur);
		        session = request.getSession(true);
		        session.setAttribute("UtilisateurCo", newUtilisateur);
		        response.sendRedirect(request.getContextPath()+"/ServletAccueil");
		    }else {
		    	be.addMessage("Les mot de passe sont différents");
		        throw be;
		    }
		
			
			
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listerreur", e.getListeMessage());
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/inscription.jsp");
			rd.forward(request, response);
		}
	}

}
