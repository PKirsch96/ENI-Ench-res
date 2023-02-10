package fr.eni.eniD2WM147.bo;

public class Retrait {
	private String rue;
	private String code_postal;
	private String ville;
	private ArticleVendu articleVendue;

	public Retrait(String rue, String code_postal, String ville) {
		super();
		this.rue = rue;
		this.code_postal = code_postal;
		this.ville = ville;
	}

	public Retrait(String rue, String code_postal, String ville, ArticleVendu articleVendue) {
		super();
		this.rue = rue;
		this.code_postal = code_postal;
		this.ville = ville;
		this.setArticleVendue(articleVendue);
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public ArticleVendu getArticleVendue() {
		return articleVendue;
	}

	public void setArticleVendue(ArticleVendu articleVendue) {
		this.articleVendue = articleVendue;
	}

	@Override
	public String toString() {
		return "Retrait [rue=" + rue + ", code_postal=" + code_postal + ", ville=" + ville + "]";
	}
	
}
