package fr.eni.eniD2WM147.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.eniD2WM147.BusinessException.BusinessException;
import fr.eni.eniD2WM147.bll.ArticlesManager;
import fr.eni.eniD2WM147.bll.UtilisateurManager;
import fr.eni.eniD2WM147.bo.ArticleVendu;
import fr.eni.eniD2WM147.bo.Categories;
import fr.eni.eniD2WM147.bo.Retrait;
import fr.eni.eniD2WM147.bo.Utilisateur;

/**
 * Servlet implementation class ServletNouvelleVente
 */
@WebServlet("/ServletNouvelleVente")
public class ServletNouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	List<Categories> libelles;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArticlesManager am = ArticlesManager.getInstance();
		
		try {
			
			libelles = am.libelleCategories();
			request.setAttribute("libelles", libelles);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/NouvelleVente.jsp");
		rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BusinessException be = new BusinessException();
		request.setCharacterEncoding("UTF-8");
		
		try {
	
		String nomArticle = null /*request.getParameter("nomArticle")*/;
		String description = null;
		Categories categorie = null;
		LocalDateTime dateDebutEncheres = null;
		LocalDateTime dateFinEncheres = null;
		int miseAPrix = 0; 
		String rue = null;
		String codePostal = null;
		String ville = null;
		
		if(!request.getParameter("nomArticle").isBlank()){
			nomArticle = request.getParameter("nomArticle");
		} else {
			be.addMessage("renseigner nom article");
		}
		
		if(!request.getParameter("description").isBlank()){
			description = request.getParameter("description");
		} else {
			be.addMessage("Veillez renseigner une description");
		}
		
		if(!request.getParameter("cate").isBlank()){
			categorie =  libelles.get(1);
		} else {
			be.addMessage("Veillez renseigner une description");
		}
		
		if(!request.getParameter("dateDebutEncheres").isBlank()){
			dateDebutEncheres = LocalDateTime.parse(request.getParameter("dateDebutEncheres"));
		} else {
			be.addMessage("Veillez renseigner la date de debut de l'enchere");
		}
		
		if(!request.getParameter("dateFinEncheres").isBlank()){
			dateFinEncheres = LocalDateTime.parse(request.getParameter("dateFinEncheres"));
		} else {
			be.addMessage("Veillez renseigner la date de fin de l'enchere");
		}
		
		if(!request.getParameter("miseAPrix").isBlank()){
			miseAPrix = Integer.parseInt(request.getParameter("miseAPrix"));
		} else {
			be.addMessage("Veillez renseigner le prix initial");
		}
		
		if(!request.getParameter("rue").isBlank()){
			rue = request.getParameter("rue");
		}else {
			be.addMessage("Veillez renseigner le nom de la rue");
		}
		
		if(!request.getParameter("codePostal").isBlank()){
			codePostal = request.getParameter("codePostal");
		} else {
			be.addMessage("Veillez renseigner le code postal");
		}
		
		if(!request.getParameter("ville").isBlank()){
			ville = request.getParameter("ville");
		} else {
			be.addMessage("Veillez renseigner le nom de la ville");
		}
		
		int noCate = Integer.parseInt(request.getParameter("cate"));
	
		if (be.getListeMessage().isEmpty()) {
			
			ArticleVendu article = new ArticleVendu(nomArticle,description,categorie,dateDebutEncheres,dateFinEncheres,miseAPrix);
			
			Utilisateur user = (Utilisateur) request.getSession().getAttribute("UtilisateurCo");
			article.setUtilisateur(user);
			
			Categories cat = new Categories(noCate, null);
			article.setCategorie(cat);
			
			Retrait retrait = new Retrait(rue,codePostal,ville,article);
		
			ArticlesManager am = ArticlesManager.getInstance();
			am.newArticle(article, retrait);
			
			response.sendRedirect(request.getContextPath()+"/ServletAccueil");
		
		}else {
			throw be;
		}

		}
		catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listerreur", e.getListeMessage());
			doGet(request, response);
			
		}
	}
	
}
