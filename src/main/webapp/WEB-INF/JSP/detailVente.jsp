<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<link href="${pageContext.request.contextPath}/CSS/detailVente.css"
	rel="stylesheet">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@200;400&family=Roboto&family=Syncopate:wght@700&display=swap" rel="stylesheet">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Roboto&family=Syncopate&display=swap" rel="stylesheet">
<meta charset="UTF-8">
<title>Détail vente</title>
</head>
<body>

	<header>
		<h1><a href="${pageContext.request.contextPath}/ServletAccueil">ENI-Enchères</a></h1>
	</header>
	<article class="art">
	<h2>Détail vente</h2>
	<p>
		Description : <br> ${av.description}
	</p>

	<p>Catégorie :  ${av.categorie.libelle}</p>
	<p>Meilleurs offre :  ${av.prixVente}</p>
	<p>Mise à prix :  ${av.miseAPrix}</p>
	<fmt:parseDate value="${av.dateFinEncheres}" pattern="yyyy-MM-dd'T'HH:mm" var="date_fin_enchere" />
	<fmt:formatDate value="${date_fin_enchere}" pattern="dd-MM-yyyy" var="dateFin" />					
	<p><b>Fin de l'enchère : </b>${dateFin}</p>
	<c:choose>
		<c:when test="${!empty av.lieuxDeRetrait.rue}">
			<p>Retrait : ${av.lieuxDeRetrait.rue}<br>${av.lieuxDeRetrait.code_postal}  ${av.lieuxDeRetrait.ville}</p>
		</c:when>
		<c:otherwise>
			<p>Retrait : Aucun lieu défini</p>
		</c:otherwise>
	</c:choose>
	<p>Vendeur : ${av.utilisateur.pseudo}</p>
	<form action="" method="get" class="form4">
		<div class="form-example">
			<label for="prop">Ma proposition : </label> <input type="number"
				name="proposition" id="proposition" required>
		</div>
		<div class="btn">
			<input type="submit" value="Enchérir!">
		</div>
	</form>
	</article>

</body>
</html>