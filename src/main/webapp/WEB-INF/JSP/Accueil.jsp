<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@200;400&family=Roboto&family=Syncopate:wght@700&display=swap" rel="stylesheet">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Roboto&family=Syncopate&display=swap" rel="stylesheet">
<link href="${pageContext.request.contextPath}/CSS/Accueil.css"
	rel="stylesheet">
<title>ENI-enchères</title>
</head>
<body>
	<div class="parent">
	</article class="navbar">
		<div class="div1">
			<div class="sdiv1">
				<c:if test="${!empty UtilisateurCo}">
					<h2>Bonjour ${UtilisateurCo.prenom}</h2>
				</c:if>
			</div>

			
				<h1>ENI-Enchères</h1>
			

			<div class="sdiv3">
				<div class ="insc">
				<form method="get" action="${pageContext.request.contextPath}/ServletInscription">
					<input type="submit" value="s'inscrire">
				</form>
				</div>
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
						<form method="get"
							action="${pageContext.request.contextPath}/ServletAfficherUnProfil">
							<input type="submit" value="Profil">
						</form>
						<form method="get"
							action="${pageContext.request.contextPath}/ServletNouvelleVente">
							<input type="submit" value="Vendre">
						</form>
					</c:otherwise>
				</c:choose>
			</div>
		</div> 
		
		<article>


		<section class="list">
			<h2>Liste des enchères</h2>
		
		<div class="div3">
			<form action="${pageContext.request.contextPath}/ServletAccueil"
				method="post" class="form-example">
				<label for="filtres">Filtres : </label> <input type="text"
					name="filtres" id="filtres"> <br> <br> <label
					for="cate">Catégorie:</label> <select name="cate" id="cate">
					<option value="0">Catégories</option>
					<c:forEach var="LIB" items="${libelles}">
						<option value="${LIB.no_categorie}">${LIB.libelle}</option>
					</c:forEach>
					<c:if test="${!empty UtilisateurCo}">
						<input type="radio" id="achats" name="choix" value="0" checked="checked">
	      				<label for="achats">Achats</label>
		      				<input type="checkbox" id="ec" name="EC" checked="checked">
		      				<label for="EC">enchères ouvertes</label>
		      				<input type="checkbox" id="ec1" name="mesEC">
		      				<label for="mesEC">mes enchères</label>
		      				<input type="checkbox" id="ec2" name="mesEcVd">
		      				<label for="mesEcVd">mes enchères remportées</label>
		      			<br>
		      			<br>
		      			<input type="radio" id="ventes" name="choix" value="1">
		      			<label for="ventes">Mes ventes</label>
		      				<input type="checkbox" id="vd" name="mesVEc">
		      				<label for="mesVEc">mes ventes en cours</label>
		      				<input type="checkbox" id="vd1" name="mesVCr">
		      				<label for="mesVCr">ventes non débutées</label>
		      				<input type="checkbox" id="vd2" name="mesVVd">
		      				<label for="mesVVd">ventes terminées</label>
					</c:if>
				</select> <br> <br> <input type="submit" value="RECHERCHER">
			</form>
		</div>
		</section>

		<div class="div4">
			<c:choose>
				<c:when test="${!empty articlesAVendre}">
					<c:forEach var="a" items="${articlesAVendre}">
					<input type="hidden" name="idArticle" value="${av.idArticle}">

						<div class="cards">
							<article class="card5">
								<div class="content">
									<h2>${a.getNomArticle()}</h2>
									<p>Prix : ${a.getMiseAPrix()}</p>
									<fmt:parseDate value="${a.dateFinEncheres}"
										pattern="yyyy-MM-dd'T'HH:mm" var="date_fin_enchere" />
									<fmt:formatDate value="${date_fin_enchere}"
										pattern="dd-MM-yyyy" var="dateFin" />
									<p><b>Fin de l'enchère : </b>${dateFin}</p>
										
									<p>Vendeur : ${a.getUtilisateur().getPseudo()}</p>
									<form action="${pageContext.request.contextPath}/ServletDetailVente">
									<c:if test="${!empty Utilisateur}">
										<button id="DetailArticleButton" class="btn btn-primary">Détails</button>
									</c:if>
								</form>
								</div>
								
							</article>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<h4>Catégorie vide</h4>
				</c:otherwise>
			</c:choose>
		</div>


	</div>

</body>
</html>