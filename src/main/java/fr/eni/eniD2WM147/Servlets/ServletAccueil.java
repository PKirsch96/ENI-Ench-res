package fr.eni.eniD2WM147.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.eniD2WM147.BusinessException.BusinessException;
import fr.eni.eniD2WM147.bll.ArticlesManager;
import fr.eni.eniD2WM147.bo.ArticleVendu;
import fr.eni.eniD2WM147.bo.Categories;
import fr.eni.eniD2WM147.bo.Utilisateur;

/**
 * Servlet implementation class ServletAccueil
 */
@WebServlet("/ServletAccueil")
public class ServletAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArticlesManager am = ArticlesManager.getInstance();

		try {
			
			List<ArticleVendu> artVendu = am.afficherArticle();
			List<Categories> libelles = am.libelleCategories();

			request.setAttribute("libelles", libelles);
			request.setAttribute("articlesAVendre", artVendu);
			
			HttpSession session = null;
			
			if ((request.getSession()) != null) {
				
				session = request.getSession();
	        }

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/Accueil.jsp");
			rd.forward(request, response);

		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String filtre = request.getParameter("filtres");
		int categorie = Integer.parseInt(request.getParameter("cate"));
		List<ArticleVendu> artVendu = null;
		ArticlesManager am = ArticlesManager.getInstance();
		
		if (request.getSession().getAttribute("UtilisateurCo") == null) {
			try {
				
				artVendu = am.selectArticlesAvecFiltres(filtre, categorie);
				List<Categories> libelles = am.libelleCategories();
				

				request.setAttribute("libelles", libelles);
				request.setAttribute("articlesAVendre", artVendu);

				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/Accueil.jsp");
				rd.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
		} else {
			try {

				if (Integer.parseInt(request.getParameter("choix")) == 0) {

					artVendu = am.selectAchats((Utilisateur) request.getSession().getAttribute("UtilisateurCo"), filtre,
							categorie, ((request.getParameter("EC") != null) ? true : false),
							((request.getParameter("mesEC") != null) ? true : false),
							((request.getParameter("mesEcVd") != null) ? true : false));
				} else if (Integer.parseInt(request.getParameter("choix")) == 1) {

					artVendu = am.selectVentes((Utilisateur) request.getSession().getAttribute("UtilisateurCo"), filtre,
							categorie, ((request.getParameter("mesVEc") != null) ? true : false),
							((request.getParameter("mesVCr") != null) ? true : false),
							((request.getParameter("mesVVd") != null) ? true : false));
					System.out.println(artVendu);
				}

				List<Categories> libelles = am.libelleCategories();
				

				request.setAttribute("libelles", libelles);
				request.setAttribute("articlesAVendre", artVendu);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/Accueil.jsp");
				rd.forward(request, response);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
