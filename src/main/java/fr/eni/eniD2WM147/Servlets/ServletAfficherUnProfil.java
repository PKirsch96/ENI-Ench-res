package fr.eni.eniD2WM147.Servlets;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.eniD2WM147.bo.Utilisateur;



@WebServlet("/ServletAfficherUnProfil")

public class ServletAfficherUnProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	
		Utilisateur utilisateur = (Utilisateur)request.getSession().getAttribute("UtilisateurCo");

		String pseudo = utilisateur.getPseudo();
		String nom = utilisateur.getNom();
		String prenom = utilisateur.getPrenom();
		String email = utilisateur.getEmail();
		String telephone = utilisateur.getTelephone();
		String rue = utilisateur.getRue();
		String codePostal = utilisateur.getCodePostal() ;
		String ville = utilisateur.getVille();
		int credit = utilisateur.getCredit();
		boolean admin = utilisateur.getAdministrateur();
		
		Utilisateur client = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, credit, admin);
		
		request.setAttribute("client",client);
		
    	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/AfficherUnProfil.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("do post afficher profil");
		doGet(request, response);
		
	}

}
