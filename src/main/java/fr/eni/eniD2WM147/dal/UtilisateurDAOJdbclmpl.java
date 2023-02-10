package fr.eni.eniD2WM147.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.eni.eniD2WM147.BusinessException.BusinessException;
import fr.eni.eniD2WM147.bo.ArticleVendu;
import fr.eni.eniD2WM147.bo.Enchere;

import fr.eni.eniD2WM147.bo.Utilisateur;


public class UtilisateurDAOJdbclmpl implements UtilisateurDAO {

	private static final String SELECT_UTILISATEURS_PAR_MDP = "SELECT * FROM UTILISATEURS WHERE (email = ? AND mot_de_passe = ?) OR (pseudo = ?  AND mot_de_passe = ?)";
	private static final String INSERT_NEW_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_UTILISATEUR = "UPDATE UTILISATEURS set pseudo = ?, nom = ?, prenom = ?, email= ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? WHERE no_utilisateur = ?";
	private static final String DELETE_RETRAITS = "DELETE r FROM RETRAITS r JOIN ARTICLES_VENDUS av on r.no_article = av.no_article WHERE r.no_article = ?";
	private static final String DELETE_ENCHERES = "DELETE e FROM ENCHERES e JOIN ARTICLES_VENDUS av on e.no_article = av.no_article WHERE av.no_utilisateur = ?";
	private static final String DELETE_ARTICLES_VENDUS = "DELETE av FROM ARTICLES_VENDUS av join UTILISATEURS u on av.no_utilisateur = u.no_utilisateur WHERE av.no_utilisateur = ?";
	private static final String DELETE_UTILISATEURS = "DELETE FROM UTILISATEURS WHERE no_utilisateur = ?";
	
	public Utilisateur connection(String identifiant, String mdp) throws BusinessException {

		Utilisateur utilisateur = null;
		BusinessException be = new BusinessException();

		try (Connection cnx = ConnectionProvider.getConnection()) {

			PreparedStatement pstmt = cnx.prepareStatement(SELECT_UTILISATEURS_PAR_MDP);
			pstmt.setString(1, identifiant);
			pstmt.setString(2, mdp);
			pstmt.setString(3, identifiant);
			pstmt.setString(4, mdp);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				System.out.println("dal next co");
				int idUtilisateur = rs.getInt("no_utilisateur");
				String pseudo = rs.getString("pseudo");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				String telephone = rs.getString("telephone");
				String rue = rs.getString("rue");
				String codePostal = rs.getString("code_postal");
				String ville = rs.getString("ville");
				String motDePasse = rs.getString("mot_de_passe");
				
				Integer credit = rs.getInt("credit");
				boolean administrateur = rs.getBoolean("administrateur");

				utilisateur = new Utilisateur(idUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, ville,credit, administrateur);
				utilisateur.setMotDePasse(motDePasse);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			be.addMessage("connection impossible");
		}
		return utilisateur;
	}

	@Override
	public void newUtilisateur(Utilisateur utilisateur) throws BusinessException {
		System.out.println("impl" + utilisateur);

		BusinessException be = new BusinessException();

		try (Connection cnx = ConnectionProvider.getConnection()) {

			PreparedStatement pstmt = cnx.prepareStatement(INSERT_NEW_UTILISATEUR,
					PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, utilisateur.getPseudo());
			pstmt.setString(2, utilisateur.getNom());
			pstmt.setString(3, utilisateur.getPrenom());
			pstmt.setString(4, utilisateur.getEmail());
			pstmt.setString(5, utilisateur.getTelephone());
			pstmt.setString(6, utilisateur.getRue());
			pstmt.setString(7, utilisateur.getCodePostal());
			pstmt.setString(8, utilisateur.getVille());
			pstmt.setString(9, utilisateur.getMotDePasse());
			pstmt.setInt(10, utilisateur.getCredit());
			pstmt.setBoolean(11, utilisateur.getAdministrateur());
			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				utilisateur.setNoUtilisateur(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			be.addMessage("Creation impossible");
		}
	}

	@Override
	public void modifUtilisateur(Utilisateur utilisateur) throws BusinessException {
		
		BusinessException be = new BusinessException();
		
		try (Connection cnx = ConnectionProvider.getConnection()) {
			
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_UTILISATEUR);
			
			pstmt.setInt(10, utilisateur.getNoUtilisateur());

			pstmt.setString(1, utilisateur.getPseudo());
			pstmt.setString(2, utilisateur.getNom());
			pstmt.setString(3, utilisateur.getPrenom());
			pstmt.setString(4, utilisateur.getEmail());
			pstmt.setString(5, utilisateur.getTelephone());
			pstmt.setString(6, utilisateur.getRue());
			pstmt.setString(7, utilisateur.getCodePostal());
			pstmt.setString(8, utilisateur.getVille());
			pstmt.setString(9, utilisateur.getMotDePasse());
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			be.addMessage("Modification impossible");
		}

	}
	
	
	public void deleteUtilisateur( Utilisateur utilisateur) throws BusinessException {
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			List<ArticleVendu> listArticle = utilisateur.getArtVendu();
			List<Enchere> listEnchere = utilisateur.getListEncheres();
		
			for(ArticleVendu art: listArticle) {
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_RETRAITS);
			pstmt.setInt(1, art.getLieuxDeRetrait().getArticleVendue().getNoArticle());
			pstmt.executeUpdate();
			}
			
			for(Enchere enchere: listEnchere) {
			PreparedStatement pstmt1 = cnx.prepareStatement(DELETE_ENCHERES);
			pstmt1.setInt(1, enchere.getArtVendu().getNoArticle());
			pstmt1.executeUpdate();
			}
			
			for(ArticleVendu art: listArticle) {
			PreparedStatement pstmt2 = cnx.prepareStatement(DELETE_ARTICLES_VENDUS);
			pstmt2.setInt(1, art.getNoArticle());
			pstmt2.executeUpdate();
			}
			
			PreparedStatement pstmt3 = cnx.prepareStatement(DELETE_UTILISATEURS);
			pstmt3.setInt(1, utilisateur.getNoUtilisateur());
			pstmt3.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			
		}
		
	}
	
	
	

}
