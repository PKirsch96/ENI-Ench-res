package fr.eni.eniD2WM147.dal;

public  class DAOFactory {
	
	public static UtilisateurDAO getutilisateurDAO (){
		return new UtilisateurDAOJdbclmpl();
	}
	
	public static ArticlesDAO getarticlesDAO (){
		return new ArticlesJdbcImpl();
	}

}