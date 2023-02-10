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

/**
 * Servlet implementation class ServletConnection
 */
@WebServlet("/ServletConnection")
public class ServletConnection extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/PageDeCo.jsp");
		rd.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		BusinessException be = new BusinessException();
		
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		String mdp = request.getParameter("mdp");

		try {
			UtilisateurManager em = UtilisateurManager.getInstance();
			Utilisateur utilisateur = em.seConnecter(id, mdp);
			
			if (utilisateur == null) {
				be.addMessage("connection impossible");
				request.setAttribute("listerreur", be.getListeMessage());
				doGet(request, response);
			} else {
				session.setAttribute("UtilisateurCo", utilisateur);
				response.sendRedirect(request.getContextPath()+"/ServletAccueil");
			}
			
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listerreur", be.getMessage());
			doGet(request, response);
		}
	}

}
