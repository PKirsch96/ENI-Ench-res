package fr.eni.eniD2WM147.Servlets;

import java.io.IOException;

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


@WebServlet("/ServletDeleteUtilisateur")
public class ServletDeleteUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/ModifUtilisateur.jsp");
		rd.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		Utilisateur test = (Utilisateur) request.getSession().getAttribute("UtilisateurCo");
		
		try {
		
			UtilisateurManager um = UtilisateurManager.getInstance();
			um.deleteUtilisateur(test);
			HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		
		response.sendRedirect(request.getContextPath()+"/ServletAccueil");		
	
		} catch (BusinessException e) {
			doGet(request, response);
			e.printStackTrace();
		}
	}

}
