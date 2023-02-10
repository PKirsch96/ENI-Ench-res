<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="${pageContext.request.contextPath}/CSS/Accueil.css"
	rel="stylesheet">
<title>ENI-enchères</title>
</head>
<body>
	<div class="parent">

		<div class="div1">
			<div class="sdiv1">
				<c:if test="${!empty UtilisateurCo}">
					<h2>Bonjour ${UtilisateurCo.prenom}</h2>
				</c:if>
			</div>

			<div class="sdiv2">
				<h1><a href="${pageContext.request.contextPath}/ServletAccueil">ENI-Enchères</a></h1>
			</div>

			<div class="sdiv3">
				<form method="get"
					action="${pageContext.request.contextPath}/ServletAccueil">
					<input type="submit" value="s'inscrire">
				</form>
				<c:choose>
					<c:when test="${empty UtilisateurCo}">
						<form method="get"
							action="${pageContext.request.contextPath}/ServletConnection">
							<input type="submit" value="Connection">
						</form>
					</c:when>
					<c:otherwise>
						<form method="post"
							action="${pageContext.request.contextPath}/ServletDeconnection">
							<input type="submit" value="Deconnection">
						</form>
					</c:otherwise>
				</c:choose>
			</div>
		</div>



		<div class="div2">
			<h2>Liste des enchères</h2>
		</div>
		<div class="div3">
			<form action="" method="get" class="form-example">
				<label for="filtres">Filtres : </label> <input type="text"
					name="filtres" id="filtres" required> <br> <br> <label
					for="cate">Catégorie:</label> <select name="cate" id="cate">
					<option value="Informatique">Informatique</option>
					<option value="Ameublement">Ameublement</option>
					<option value="Vetement">Vêtement</option>
					<option value="SportLoisirs">Sport & Loisirs</option>
				</select> <br> <br> <input type="submit" value="RECHERCHER">
			</form>
		</div>

		<div class="div4">

			<c:forEach var="a" items="${articlesAVendre}">
				<div class="cards">
					<article class="card">
						<header>
							<h2>${a.getNomArticle()}</h2>
						</header>
						<img src="" alt="">
						<div class="content">
							<p>Prix : ${a.getMiseAPrix()}</p>
							<p>Fin de l'enchère : ${a.getDateFinEncheres()}</p>
							<p>Vendeur : ${a.getUtilisateur().getPseudo()}</p>
						</div>
					</article>
				</div>
			</c:forEach>

		</div>

	</div>

</body>
</html>