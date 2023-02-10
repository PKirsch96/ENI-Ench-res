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

import fr.eni.eniD2WM147.BusinessException.BusinessException;
import fr.eni.eniD2WM147.bll.ArticlesManager;
import fr.eni.eniD2WM147.bo.ArticleVendu;
import fr.eni.eniD2WM147.bo.Categories;

/**
 * Servlet implementation class ServlteDeconnection
 */
@WebServlet(name = "ServletDeconnection", value = "/ServletDeconnection")
public class ServletDeconnection extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			ArticlesManager am = ArticlesManager.getInstance();
			List<Categories> libelles= am.libelleCategories();
			List<ArticleVendu> artVendu = am.afficherArticle();
			
			request.setAttribute("libelles", libelles);
			request.setAttribute("articlesAVendre", artVendu);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/Accueil.jsp");
			rd.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.getSession().invalidate();
			doGet(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
