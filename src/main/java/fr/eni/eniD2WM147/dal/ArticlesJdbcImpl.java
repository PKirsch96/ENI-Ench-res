package fr.eni.eniD2WM147.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import fr.eni.eniD2WM147.BusinessException.BusinessException;
import fr.eni.eniD2WM147.bo.ArticleVendu;
import fr.eni.eniD2WM147.bo.Categories;
import fr.eni.eniD2WM147.bo.Retrait;
import fr.eni.eniD2WM147.bo.Utilisateur;

public class ArticlesJdbcImpl implements ArticlesDAO {
	
	//Requètes SQL coupé pour faciliter la concaténation en fonction des besoins
	//Les Tables 
	private static final String LIBELLES = "SELECT * FROM CATEGORIES";
	private static final String FROM_ART = "FROM ARTICLES_VENDUS a";
	//Les Jointures en fonction des besoins
	private static final String JOIN_E = "LEFT JOIN ENCHERES e ON a.no_article = e.no_article ";
	private static final String JOIN_U = "JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur ";
	private static final String JOIN_C = "JOIN CATEGORIES c ON a.no_categorie = c.no_categorie ";
	private static final String JOIN_R = "LEFT JOIN RETRAITS r ON a.no_article = r.no_article";
	//Les Conditions de filtre à remplir 
	private static final String LIKE = "a.nom_article LIKE ?";
	private static final String CATEGORIE = "a.no_categorie =?";
	private static final String ENCHERE_UTILISATEUR = "e.no_utilisateur =?";
	private static final String ARTICLE_UTILISATEUR = "a.no_utilisateur = ?";
	private static final String ARTICLE = "a.no_article = ?";
	//Les Conditions de filtre sur l'état
	private static final String EC = "a.etat_vente = 'EC'";
	private static final String CR = "a.etat_vente = 'CR'";
	private static final String VD = "a.etat_vente = 'VD'";
	//Données en fonction des besoins
	private static final String DONNEES_ARTICLE = "SELECT a.no_article,a.nom_article, a.description, a.date_debut_enchere,a.date_fin_enchere,a.prix_initial,"
			+ "a.prix_vente, a.etat_vente, a.no_utilisateur aNoUtilisateur";
	private static final String DONNEES_ENCHERE = ",e.montant_enchere,e.no_utilisateur,e.no_article,e.date_enchere";
	private static final String DONNEES_UTILISATEUR = ",u.no_utilisateur noUtilisateur,u.pseudo, u.nom,u.prenom,u.email,u.telephone,u.rue,u.code_postal"
			+ ",u.ville,u.credit,u.administrateur";
	private static final String DONNEES_CATEGORIE = ",c.no_categorie noCategorie,c.libelle";
	private static final String DONNEES_RETRAIT = ",r.code_postal rCP, r.no_article rNoArt, r.rue rRue, r.ville rVille";
	//Insertion d'articles et de Retrait
	private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_enchere,date_fin_enchere,"
			+ "prix_initial,prix_vente,no_utilisateur,no_categorie,etat_vente) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS (no_article ,rue ,code_postal ,ville) VALUES (?, ?, ?, ?)";

	//Retourne tous les article dont l'état est "En Cours" en mode déconnecter
	@Override
	public List<ArticleVendu> selectAllArticle() throws BusinessException {
		
		List<ArticleVendu> listeart = new ArrayList<>();
		BusinessException be = new BusinessException();
		
		try (Connection cnx = ConnectionProvider.getConnection()) {
			
			Statement stmt = cnx.createStatement();
			
			ResultSet rs = stmt.executeQuery(DONNEES_ARTICLE + DONNEES_UTILISATEUR + DONNEES_CATEGORIE + " " + FROM_ART
					+ " " + JOIN_U + " " + JOIN_C + " WHERE " + EC);
			
			while (rs.next()) {

				ArticleVendu art = new ArticleVendu(
						rs.getInt("no_article"), 
						rs.getString("nom_article"),
						rs.getString("description"),
						LocalDateTime.of((
								rs.getDate("date_debut_enchere").toLocalDate()),
								rs.getTime("date_debut_enchere").toLocalTime()),
						LocalDateTime.of((
								rs.getDate("date_fin_enchere").toLocalDate()),
								rs.getTime("date_fin_enchere").toLocalTime()),
						rs.getInt("prix_initial"),
						(!Objects.isNull((Integer) rs.getInt("prix_vente"))) ? rs.getInt("prix_vente") : 0,
						rs.getString("etat_vente"));
				art.setUtilisateur(new Utilisateur(
						rs.getInt("noUtilisateur"), 
						rs.getString("pseudo"),
						rs.getString("nom"), 
						rs.getString("prenom"), 
						rs.getString("email"), 
						rs.getString("telephone"),
						rs.getString("rue"), 
						rs.getString("code_postal"), 
						rs.getString("ville"), 
						rs.getInt("credit"),
						rs.getBoolean("administrateur")));
				art.setCategorie(new Categories(
						rs.getInt("noCategorie"), 
						rs.getString("libelle")));
				
				listeart.add(art);

		}
		
		} catch (SQLException e) {
			e.printStackTrace();
			be.addMessage("Creation impossible");
		}
		
		return listeart;
	
	}

	//Recupère le libelle et le numéro de catégorie pour les afficher et permettre de filtrer les recherches
	@Override
	public List<Categories> libelleCategories() throws SQLException {

		List<Categories> libelles = new ArrayList<>();
		
		try (Connection cnx = ConnectionProvider.getConnection()) {
			Statement stmt = cnx.createStatement();
			
			ResultSet rs = stmt.executeQuery(LIBELLES);
			
			while (rs.next()) {
				
				Categories cat = new Categories(rs.getInt("no_categorie"), rs.getString("libelle"));
				libelles.add(cat);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return libelles;
	
	}
	
	//filtrer les articles en mode déconnecté
	@Override
	public List<ArticleVendu> selectArticlesAvecFiltres(String recherche, int libelle) throws SQLException {
		
		PreparedStatement pstmt = null;
		List<ArticleVendu> listefiltree = new ArrayList<>();
		List<String> listeParametre = new ArrayList<>();
		
		try (Connection cnx = ConnectionProvider.getConnection()) {
			//Requète à concaténer 
			String requete = DONNEES_ARTICLE + DONNEES_UTILISATEUR + DONNEES_CATEGORIE + " " + FROM_ART
					+ " " + JOIN_U + " " + JOIN_C + " WHERE " + EC;
			
			
			if(!recherche.trim().equals("")||libelle != 0) {
				
				if (!recherche.trim().equals("")) {
					
					requete += " AND " + LIKE;
			
					listeParametre.add(("%" + recherche + "%"));
				}
	
				if (libelle != 0) {
					
					requete += " AND "  + CATEGORIE;
					
					listeParametre.add(String.valueOf(libelle));
				}
				
				if ((requete.contains("("))) {
					requete += ")";
				}

			}
			//Je récupère la requète une fois concaténé
			pstmt = cnx.prepareStatement(requete);
			//Je récupère la liste de paramètre à insérer en String
			for (String str : listeParametre) {

				try {
					//J'essaye de convertir le paramètre en Integer si besoin est
					int param = Integer.parseInt(str);
					pstmt.setInt(listeParametre.indexOf(str) + 1, param);

				} catch (NumberFormatException e) {
					//sinon je l'ajoute en tant que String
					pstmt.setString(listeParametre.indexOf(str) + 1, str);
				}
			}
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ArticleVendu art = new ArticleVendu(
						rs.getInt("no_article"), 
						rs.getString("nom_article"),
						rs.getString("description"),
						LocalDateTime.of((
								rs.getDate("date_debut_enchere").toLocalDate()),
								rs.getTime("date_debut_enchere").toLocalTime()),
						LocalDateTime.of((
								rs.getDate("date_fin_enchere").toLocalDate()),
								rs.getTime("date_fin_enchere").toLocalTime()),
						rs.getInt("prix_initial"),
						(!Objects.isNull((Integer) rs.getInt("prix_vente"))) ? rs.getInt("prix_vente") : 0,
						rs.getString("etat_vente"));
				art.setUtilisateur(new Utilisateur(
						rs.getInt("noUtilisateur"), 
						rs.getString("pseudo"),
						rs.getString("nom"), 
						rs.getString("prenom"), 
						rs.getString("email"), 
						rs.getString("telephone"),
						rs.getString("rue"), 
						rs.getString("code_postal"), 
						rs.getString("ville"), 
						rs.getInt("credit"),
						rs.getBoolean("administrateur")));
				art.setCategorie(new Categories(
						rs.getInt("noCategorie"), 
						rs.getString("libelle")));
				
				listefiltree.add(art);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listefiltree;
	}
	
	//filtre en mode conecté pour les enchères
	@Override
	public List<ArticleVendu> selectAchats(Utilisateur utilisateur, String recherche, int libelle, boolean encheresEc,
			boolean mesEncheresEc, boolean mesEncheresVd) throws SQLException {
		
		PreparedStatement pstmt = null;
		List<ArticleVendu> listefiltree = new ArrayList<>();
		List<String> listeParametre = new ArrayList<>();
		
		try (Connection cnx = ConnectionProvider.getConnection()) {
			
			String requete = DONNEES_ARTICLE + DONNEES_UTILISATEUR + DONNEES_CATEGORIE + DONNEES_ENCHERE + " "
					+ FROM_ART + " " + JOIN_E + " " + JOIN_U + " " + JOIN_C;

			if (!recherche.trim().equals("")) {
				requete += ((!requete.contains("WHERE")) ? " WHERE " : " AND ") + LIKE;
				listeParametre.add(("%" + recherche + "%"));
			}

			if (libelle != 0) {
				requete += ((!requete.contains("WHERE")) ? " WHERE " : " AND ") + CATEGORIE;
				listeParametre.add(String.valueOf(libelle));
			}
			
			if (encheresEc && !mesEncheresVd || encheresEc && mesEncheresEc) {
				requete += ((!requete.contains("WHERE")) ? " WHERE " : " AND ") + EC;
			}

			if (mesEncheresEc && encheresEc && mesEncheresVd || encheresEc && mesEncheresVd) {
				requete += ((!requete.contains("WHERE")) ? " WHERE " : " AND ") + EC;
				requete += " OR (" + ENCHERE_UTILISATEUR;
				requete += " AND " + VD;
				listeParametre.add(String.valueOf(utilisateur.getNoUtilisateur()));
			}

			if (!encheresEc) {
				requete += ((!requete.contains("WHERE")) ? " WHERE " : " AND (") + ENCHERE_UTILISATEUR;
				listeParametre.add(String.valueOf(utilisateur.getNoUtilisateur()));

				if (mesEncheresEc) {
					if ((!requete.contains("("))) {
						requete += " AND (" + EC;
					} else {
						requete += ((!requete.contains("AND")) ? " AND " : " OR ") + EC;
					}
				}
				
				if (mesEncheresVd) {
					if ((!requete.contains("("))) {
						requete += " AND (" + VD;
					} else {
						requete += ((!requete.contains("AND")) ? " AND " : " OR ") + VD;
					}
				}
			}
			
			if ((requete.contains("("))) {
				requete += ")";
			}

			pstmt = cnx.prepareStatement(requete);

			for (String str : listeParametre) {

				try {
					int param = Integer.parseInt(str);
					pstmt.setInt(listeParametre.indexOf(str) + 1, param);
				} catch (NumberFormatException e) {

					pstmt.setString(listeParametre.indexOf(str) + 1, str);
				}
			}

			pstmt.executeQuery();
			ResultSet rs = pstmt.getResultSet();

			while (rs.next()) {

				ArticleVendu art = new ArticleVendu(
						rs.getInt("no_article"), 
						rs.getString("nom_article"),
						rs.getString("description"),
						LocalDateTime.of((rs.getDate("date_debut_enchere").toLocalDate()),
								rs.getTime("date_debut_enchere").toLocalTime()),
						LocalDateTime.of((rs.getDate("date_fin_enchere").toLocalDate()),
								rs.getTime("date_fin_enchere").toLocalTime()),
						rs.getInt("prix_initial"),
						(!Objects.isNull((Integer) rs.getInt("prix_vente"))) ? rs.getInt("prix_vente")
								: rs.getInt("montant_enchere"),
						rs.getString("etat_vente"));
				art.setUtilisateur(new Utilisateur(
						rs.getInt("noUtilisateur"), 
						rs.getString("pseudo"),
						rs.getString("nom"), 
						rs.getString("prenom"), 
						rs.getString("email"), 
						rs.getString("telephone"),
						rs.getString("rue"), 
						rs.getString("code_postal"), 
						rs.getString("ville"), 
						rs.getInt("credit"),
						rs.getBoolean("administrateur")));
				art.setCategorie(new Categories(
						rs.getInt("noCategorie"), 
						rs.getString("libelle")));
				listefiltree.add(art);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listefiltree;
	}

	//filtre en mode conecté pour les articles créé par le client
	@Override
	public List<ArticleVendu> selectVentes(Utilisateur utilisateur, String recherche, int libelle, boolean mesVentesEc,
			boolean mesVentesCr, boolean mesVentesVd) throws SQLException {

		PreparedStatement pstmt = null;
		List<ArticleVendu> listefiltree = new ArrayList<>();
		List<String> listeParametre = new ArrayList<>();
		
		try (Connection cnx = ConnectionProvider.getConnection()) {
			
			String requete = DONNEES_ARTICLE + DONNEES_UTILISATEUR + DONNEES_CATEGORIE + DONNEES_ENCHERE + " "
					+ FROM_ART + " " + JOIN_E + " " + JOIN_U + " " + JOIN_C;

			if (!recherche.trim().equals("")) {
				requete += ((!requete.contains("WHERE")) ? " WHERE " : " AND ") + LIKE;
				listeParametre.add(("%" + recherche + "%"));
			}

			if (libelle != 0) {
				requete += ((!requete.contains("WHERE")) ? " WHERE " : " AND ") + CATEGORIE;
				listeParametre.add(String.valueOf(libelle));
			}
			
			if (mesVentesEc || mesVentesCr || mesVentesVd) {
				requete += ((!requete.contains("WHERE")) ? " WHERE " : " AND (") + ARTICLE_UTILISATEUR;
				listeParametre.add(String.valueOf(utilisateur.getNoUtilisateur()));

				if (mesVentesEc) {
					if ((!requete.contains("("))) {
						requete += " AND (" + EC;
					} else {
						requete += ((!requete.contains("AND")) ? " AND " : " OR ") + EC;
					}
				}
				
				if (mesVentesCr) {
					if ((!requete.contains("("))) {
						requete += " AND (" + CR;
					} else {
						requete += ((!requete.contains("AND")) ? " AND " : " OR ") + CR;
					}
				}
				
				if (mesVentesVd) {
					if ((!requete.contains("("))) {
						requete += " AND (" + VD;
					} else {
						requete += ((!requete.contains("AND")) ? " AND " : " OR ") + VD;
					}

				}
				
				if ((requete.contains("("))) {
					requete += ")";
				}

			}

			ResultSet rs = null;
			
			if (listeParametre.isEmpty()) {
				Statement stmt = cnx.createStatement();
				rs = stmt.executeQuery(requete);
			} else {
				
				pstmt = cnx.prepareStatement(requete);
				
				for (String str : listeParametre) {
					try {
						int param = Integer.parseInt(str);
						pstmt.setInt(listeParametre.indexOf(str) + 1, param);
					} catch (NumberFormatException e) {
						pstmt.setString(listeParametre.indexOf(str) + 1, str);
					}
				}
				
				rs = pstmt.executeQuery();
				
			}
			

			while (rs.next()) {
				ArticleVendu art = new ArticleVendu(
						rs.getInt("no_article"), 
						rs.getString("nom_article"),
						rs.getString("description"),
						LocalDateTime.of((rs.getDate("date_debut_enchere").toLocalDate()),
								rs.getTime("date_debut_enchere").toLocalTime()),
						LocalDateTime.of((rs.getDate("date_fin_enchere").toLocalDate()),
								rs.getTime("date_fin_enchere").toLocalTime()),
						rs.getInt("prix_initial"),
						(!Objects.isNull((Integer) rs.getInt("prix_vente"))) ? rs.getInt("prix_vente")
								: rs.getInt("montant_enchere"),
						rs.getString("etat_vente"));
				art.setUtilisateur(new Utilisateur(
						rs.getInt("noUtilisateur"), 
						rs.getString("pseudo"),
						rs.getString("nom"), 
						rs.getString("prenom"), 
						rs.getString("email"), 
						rs.getString("telephone"),
						rs.getString("rue"), 
						rs.getString("code_postal"), 
						rs.getString("ville"), 
						rs.getInt("credit"),
						rs.getBoolean("administrateur")));
				art.setCategorie(new Categories(
						rs.getInt("noCategorie"), 
						rs.getString("libelle")));
				
				listefiltree.add(art);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listefiltree;
	}

	//Création d'article
	@Override
	public void newArticle(ArticleVendu art, Retrait retrait) throws BusinessException {

		BusinessException be = new BusinessException();

		try (Connection cnx = ConnectionProvider.getConnection()) {

			PreparedStatement pstmt = cnx.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, art.getNomArticle());
			pstmt.setString(2, art.getDescription());
			pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(art.getDateDebutEncheres()));
			pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(art.getDateFinEncheres()));
			pstmt.setInt(5, art.getMiseAPrix());
			pstmt.setInt(6, art.getPrixVente());
			pstmt.setInt(7, art.getUtilisateur().getNoUtilisateur());
			pstmt.setInt(8, art.getCategorie().getNo_categorie());
			pstmt.setString(9, "CR");
			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				art.setNoArticle(rs.getInt(1));
			}
			PreparedStatement pstmt1 = cnx.prepareStatement(INSERT_RETRAIT);
			pstmt1.setInt(1, retrait.getArticleVendue().getNoArticle());
			pstmt1.setString(2, retrait.getRue());
			pstmt1.setString(3, retrait.getCode_postal());
			pstmt1.setString(4, retrait.getVille());
			pstmt1.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			be.addMessage("Creation impossible");
		}
	}

	//affiche les détails de l'articles sélectionné en mode connecté
	@Override
	public ArticleVendu selectById(int id) throws SQLException {
		
		PreparedStatement pstmt = null;
		ArticleVendu art = null;
		
		try (Connection cnx = ConnectionProvider.getConnection()) {
			
			
			pstmt = cnx.prepareStatement(DONNEES_ARTICLE + DONNEES_UTILISATEUR + DONNEES_CATEGORIE + DONNEES_ENCHERE + DONNEES_RETRAIT + " "
					+ FROM_ART + " " + JOIN_E + " " + JOIN_U + " " + JOIN_C + " " + JOIN_R + " " + " WHERE " + ARTICLE);
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				art = new ArticleVendu(
						rs.getInt("no_article"),
						rs.getString("nom_article"),
						rs.getString("description"),
						LocalDateTime.of((rs.getDate("date_debut_enchere").toLocalDate()),
								rs.getTime("date_debut_enchere").toLocalTime()),
						LocalDateTime.of((rs.getDate("date_fin_enchere").toLocalDate()),
								rs.getTime("date_fin_enchere").toLocalTime()),
						rs.getInt("prix_initial"),
						(!Objects.isNull((Integer) rs.getInt("prix_vente"))) ? rs.getInt("prix_vente")
								: rs.getInt("montant_enchere"),
						rs.getString("etat_vente"));
				art.setUtilisateur(new Utilisateur(
						rs.getInt("noUtilisateur"), 
						rs.getString("pseudo"),
						rs.getString("nom"), 
						rs.getString("prenom"), 
						rs.getString("email"), 
						rs.getString("telephone"),
						rs.getString("rue"), 
						rs.getString("code_postal"), 
						rs.getString("ville"), 
						rs.getInt("credit"),
						rs.getBoolean("administrateur")));
				art.setCategorie(new Categories(
						rs.getInt("noCategorie"), 
						rs.getString("libelle")));
				art.setLieuxDeRetrait(new Retrait(
						rs.getString("rRue"), 
						rs.getString("rCP"), 
						rs.getString("rVille"), 
						art));
			}
			
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		return art;
		
	}
}